<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:import href="codegen.xslt"/>
  <xsl:import href="codegen.invoke.xslt"/>
  	
  <xsl:template name="comment.service">
    <xsl:variable name="fullname" select="concat(@namespace, '.', @name)"/>
    /***********************************************************************
    The generated code provides a simplified mechanism for invoking methods
    on the <xsl:value-of select="$fullname" /> class via WebORB.
    You can add the code to your Flex Builder project and use the 
    class as shown below:

           import <xsl:value-of select="$fullname" />;

           var serviceProxy:<xsl:value-of select="@name" /> = new <xsl:value-of select="@name" />();
           // make sure to substitute foo() with a method from the class
           serviceProxy.foo();

    The generated code does not provide any handling of the result values.
    We recommend the following approach to integrate this class into your
    application:

    (If using Model-View-Controller)
    - Modify the constructor of the class below to accept the controller object
    - Modify response handlers to pass return values to the controller

    (if not using MVC)
    - Modify the constructor of the class below to accept your View object
    - Modify response handlers to display the result directly in the View
    ************************************************************************/
  </xsl:template>
  <xsl:template name="codegen.info">
<b>What has just happened?</b> You selected a class deployed in WebORB and the console produced a corresponding client-side code to invoke methods on the selected class.<br /><br />
<b>What can the generated code do?</b> The generated code accomplishes several goals:<ul>
<li>Generates ActionScript v3 value object classes for all complex types used in the remote .NET class.</li><li>Generates RemoteObject declaration and handler functions for each corresponding remote method</li><li>Generates a utility wrapper class making it easier to perform remoting calls</li>
</ul><br /><b>What can I do with this code?</b> You can download the code, add it to your Flex Builder (or Flex SDK) project and start invoking your .NET methods. The code is the basic minimum one would need to perform a remote invocation. It includes all the stubs for each remote method. Make sure to add your application logic to the handler functions.<br /><br />
<b>How can I download the code?</b> There is a 'Download Code' button in the bottom right corner. The button fetches a zip file with all the generated source code<br />    
  </xsl:template>

  <xsl:template name="codegen.appmain">
    <file name="main.mxml">
&lt;mx:Application xmlns:mx="http://www.adobe.com/2006/mxml" layout="absolute" initialize="onLoad()">
&lt;mx:Script>
&lt;![CDATA[
  <xsl:if test="count(//datatype) != 0">
  import <xsl:value-of select="//service/@namespace"/>.DataTypeInitializer;
  </xsl:if>
  public function onLoad():void
  {
  <xsl:if test="count(//datatype) != 0">  
  new DataTypeInitializer();
  </xsl:if>
  }
]]&gt;
&lt;/mx:Script>
&lt;/mx:Application>
    </file>
    
  </xsl:template>
  
   
  <xsl:template name="codegen.service">
      <file name="{@name}.as">
        <xsl:call-template name="codegen.code" />
      </file>
	  <file name="{@name}Model.as">
        <xsl:call-template name="codegen.model" />
      </file>
	  
	  <xsl:if test="count(//datatype) != 0">
        <file name="DataTypeInitializer.as">
          <xsl:call-template name="codegen.datatypelist">
            <xsl:with-param name="namespaceName" select="@namespace" />
          </xsl:call-template>  
        </file>
      </xsl:if>  
	  
    <xsl:if test="method[@containsvalues=1]">

    <folder name="testdrive">
      <xsl:for-each select="method[@containsvalues=1]">
        <file name="{@name}Invoke.as">
          <xsl:call-template name="codegen.description">
            <xsl:with-param name="file-name" select="concat(@name,'Invoke.as')" />
          </xsl:call-template>

      package <xsl:value-of select="../@namespace" />.testdrive
      {
      <xsl:if test="//datatype">
        import <xsl:value-of select="../@namespace" />.vo.*;
      </xsl:if>
        import <xsl:value-of select="../@namespace" />.*;
        
        public class <xsl:value-of select="@name" />Invoke
        {
          var m_service:<xsl:value-of select="../@name"/> = new <xsl:value-of select="../@name"/>();
        
          public function Execute():void
          {
            <xsl:call-template name="codegen.invoke.method" />
          }
        }
      }
        </file>   
      </xsl:for-each>
    </folder>

    </xsl:if>

  </xsl:template>


  <xsl:template name="codegen.invoke.method.name">
    m_service.<xsl:value-of select="@name"/>
  </xsl:template>

  <xsl:template name="codegen.code.class">
    package <xsl:value-of select="@namespace" />
    {
    import mx.rpc.remoting.RemoteObject;
    import mx.controls.Alert;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.AsyncToken;
    import mx.rpc.IResponder;
    import mx.collections.ArrayCollection;
	<xsl:call-template name="codegen.import.alltypes">
    <xsl:with-param name="subnamespace" select="'vo'"/>
	</xsl:call-template>

    public class <xsl:value-of select="@name"/>
    {
      private var remoteObject:RemoteObject;
      private var model:<xsl:value-of select="@name"/>Model; 

      public function <xsl:value-of select="@name"/>( model:<xsl:value-of select="@name"/>Model = null )
      {
        remoteObject  = new RemoteObject("GenericDestination");
        remoteObject.source = "<xsl:value-of select='@fullname'/>";
        <xsl:for-each select="method">
        remoteObject.<xsl:value-of select="@name" />.addEventListener("result",<xsl:value-of select="@name" />Handler);
        </xsl:for-each>
        remoteObject.addEventListener("fault", onFault);
		
        if( model == null )
			model = new <xsl:value-of select="@name"/>Model();
    
        this.model = model;

      }
	  
      public function setCredentials( userid:String, password:String ):void
      {
        remoteObject.setCredentials( userid, password );
      }

      public function GetModel():<xsl:value-of select="@name"/>Model
      {
        return this.model;
      }


    <xsl:for-each select="method">
      public function <xsl:value-of select="@name"/>(<xsl:for-each select="arg">
        <xsl:value-of select="@name"/>:<xsl:value-of select="@type" />,
      </xsl:for-each> responder:IResponder = null ):void
      {
        var asyncToken:AsyncToken = remoteObject.<xsl:value-of select="@name"/>(<xsl:for-each select="arg">
          <xsl:if test="position() != 1">,</xsl:if>
          <xsl:value-of select="@name"/>
        </xsl:for-each>);
		
        if( responder != null )
			asyncToken.addResponder( responder );

      }
    </xsl:for-each>
    
    <xsl:for-each select="method">     
      public function <xsl:value-of select="@name" />Handler(event:ResultEvent):void
      {
        <xsl:if test="@type != 'void'">
          var returnValue:<xsl:value-of select="@type" /> = event.result as <xsl:value-of select="@type" />;
          model.<xsl:value-of select="@name" />Result = returnValue;
        </xsl:if>
      }
    </xsl:for-each>
      public function onFault (event:FaultEvent):void
      {
        Alert.show(event.fault.faultString, "Error");
      }
    }
  }	
  </xsl:template>
  
  <xsl:template name="codegen.code">
    <xsl:call-template name="codegen.description">
      <xsl:with-param name="file-name" select="concat(@name,'.as')" />
    </xsl:call-template>
    <xsl:call-template name="comment.service" />
    <xsl:call-template name="codegen.code.class" />
  </xsl:template>
  
  <xsl:template name="codegen.model">
	<xsl:call-template name="codegen.description">
      <xsl:with-param name="file-name" select="concat(@name,'Model.as')" />
    </xsl:call-template>
	
    package <xsl:value-of select="@namespace" />
    {
      <xsl:call-template name="codegen.import.alltypes">
        <xsl:with-param name="subnamespace" select="'vo'"/>
      </xsl:call-template>
      [Bindable]
      public class <xsl:value-of select="@name"/>Model
      {<xsl:for-each select="method"><xsl:if test="@type != 'void'">     
        public var <xsl:value-of select="@name" />Result:<xsl:variable name="fullName" select="@javatype"/><xsl:choose>
    <xsl:when test="//datatype[@fullname=$fullName]">
      <xsl:value-of select="substring-before(@javatype, @type)"/>vo.
    </xsl:when>
    <xsl:when test="//enum[@name=$fullName]">
      <xsl:value-of select="substring-before(@javatype, @type)"/>enum.
    </xsl:when>
  </xsl:choose><xsl:value-of select="@type" />;</xsl:if></xsl:for-each>
      }
    }
  </xsl:template>

</xsl:stylesheet>
