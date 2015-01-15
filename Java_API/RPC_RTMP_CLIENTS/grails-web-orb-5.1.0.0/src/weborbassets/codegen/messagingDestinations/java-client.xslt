<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<folder name="weborb-codegen">
			<info>info text</info>
            <xsl:choose>
                <xsl:when test="runtime/data/fullCode = 'true'">
                <folder name="eclipse">
                    <file path="java/.project"/>
                    <folder name="libs">
                        <file path="../../javaclient/weborbclient.jar" />
                    </folder>
                    <file name=".classpath"><![CDATA[<?xml version="1.0" encoding="UTF-8"?>
    <classpath>
        <classpathentry kind="src" path="src"/>
        <classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER"/>
        <classpathentry kind="lib" path="libs/weborbclient.jar"/>
        <classpathentry kind="output" path="bin"/>
    </classpath>
                    ]]></file>
                  <xsl:call-template name="sourcecodefile" />
                </folder>
                <folder name="idea">
                  <file path="java/idea/WeborbMessagingClient.iml"/>
                  <folder name=".idea" path="java/idea/.idea" />
                  <xsl:call-template name="sourcecodefile" />
                  <folder name="lib">
                    <file path="../../javaclient/weborbclient.jar" />
                  </folder>
                </folder>
                </xsl:when>
                <xsl:otherwise>
                   <xsl:call-template name="sourcecodefile" />
                </xsl:otherwise>
            </xsl:choose>
		</folder>
	</xsl:template>

    <xsl:template name="sourcecodefile">
 		<folder name="src">
			<folder name="examples">
				<folder name="weborb">
					<file name="ClientExample.java"><![CDATA[package examples.weborb;

import java.util.Scanner;

import weborb.client.Fault;
import weborb.client.IResponder;
import weborb.client.WeborbClient;
import weborb.reader.StringType;
import weborb.v3types.AsyncMessage;

public class ClientExample implements IResponder
{
  public static void main( String[] args ) throws Exception
  {
    WeborbClient client = new WeborbClient("]]><xsl:value-of select="runtime/data/weborbURL"/><![CDATA[", "]]><xsl:value-of select="runtime/data/destinationId"/><![CDATA[", "Java Client" );
    IResponder listener = new ClientExample();
    client.subscribe( listener );

    String input = null;
    Scanner scanner = new Scanner( System.in );

    while( !"exit".equals( input ) )
    {
      System.out.print( "Type a message and press [Enter] to send it or 'exit' to quit\n> " );
      input = scanner.nextLine();
      if( input != null && !input.isEmpty() )
        try
        {
          client.publish( input );
        }
        catch( Exception e )
        {
          e.printStackTrace( System.out );
        }
    }

    System.exit( 1 );
  }

  public void errorHandler( Fault fault )
  {
    System.out.println( "Error: " + fault.getMessage() + "\n" + fault.getDetail() + "\n" );
  }

  public void responseHandler( Object adaptedObject )
  {
    for( Object message : (Object[]) adaptedObject )
    {
      if( message instanceof AsyncMessage )
      {
        AsyncMessage asyncMessage = (AsyncMessage) message;

        for( Object body : (Object[]) asyncMessage.body.body )
          if( body instanceof Object[] )
            for( Object bodyElement : (Object[]) body )
              processMessage( asyncMessage, bodyElement );
          else
            processMessage( asyncMessage, body );
      }
    }
  }

  private void processMessage( AsyncMessage asyncMessage, Object body )
  {
    String sender = (String) asyncMessage.headers.get( "WebORBClientId" );

    if( sender == null )
      sender = "Anonymous";

    System.out.println( "Received message from '" + sender + "' : " + body );
  }
}
					]]></file>
				</folder>
			</folder>
        </folder>
    </xsl:template>
</xsl:stylesheet>