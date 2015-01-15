<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
	xmlns:codegen="urn:weborb-cogegen-xslt-lib:xslt" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:import href="codegen.xslt" />
	<xsl:import href="codegen.invoke.xslt" />
	
	<xsl:template name="codegen.flex.properties" />
	
	<xsl:template name="codegen.actionscript.properties" />
    <xsl:template name="codegen.vo.specialinclusions" />

	<xsl:template name="codegen.project">
		<file name=".flashProjectProperties" type="xml">
			<projectDescription>
				<id>
					<xsl:value-of select="//runtime/@randomUUID" />
				</id>
				<name>
					<xsl:value-of select="//service/@name" />
				</name>
				<asVersion>0</asVersion>
				<classPath>.</classPath>
				<defaultApp />
				<rootURI>
					<xsl:value-of select="//runtime/@path" />
				</rootURI>
				<flexSDK />
				<sources editable="false" />
				<libraries editable="false" />
				<externalLibraries editable="false" />
				<compileList editable="false" />
				<recentFiles editable="false" />
				<locations editable="false" />
			</projectDescription>
		</file>	
	</xsl:template>	

	<xsl:template name="codegen.service">
	    <file name="readme.txt">
		The generated code provides full Flash remoting support 
		for the selected server-side class (<xsl:value-of select="@name"/>).
		To use the generated code download it using the Download 
		Code button (it is recommended to download with the 
		project file included) and add the following ActionScript
		code to your Flash application:
		
		=========================================================
		// import the generated service proxy class
		import <xsl:value-of select="@fullname"/>;
        <xsl:if test="count(//datatype) != 0">		
		// force Flash compiler to include generated classes into compilation
		new <xsl:value-of select="../@fullname" />.DataTypeInitializer();
		</xsl:if>
		// create service proxy object
		var service:<xsl:value-of select="@name"/> = new <xsl:value-of select="@name"/>();
		
		// invoke remote method
		service.[PUT ANY SERVER SIDE METHOD HERE]([METHOD, ARGS, GO, HERE]);
		=========================================================
		</file>
		<file name="{@name}.as">
			<xsl:call-template name="codegen.description">
				<xsl:with-param name="file-name" select="concat(@name,'.as')" />
			</xsl:call-template>
			<xsl:call-template name="codegen.code" />
		</file>
	  <xsl:if test="count(//datatype) != 0">
        <file name="DataTypeInitializer.as">
          <xsl:call-template name="codegen.datatypelist">
            <xsl:with-param name="namespaceName" select="@namespace" />
          </xsl:call-template>  
        </file>
      </xsl:if> 
	</xsl:template>
	
	<xsl:template name="codegen.datatypelist">
    <xsl:param name="namespaceName" />
    /*****************************************************************
    *
    *  To force the compiler to include all the generated complex types
    *  into the compiled application, add the following line of code 
    *  into the main function of your Flex application:
    *
    *  new <xsl:value-of select="$namespaceName" />.DataTypeInitializer();
    *
    ******************************************************************/
    package <xsl:value-of select="$namespaceName" />
    {
      <xsl:for-each select="//datatype">
      import flash.net.*;
      import <xsl:value-of select="../@fullname" />.vo.<xsl:value-of select="@name"/>;</xsl:for-each>
      public class DataTypeInitializer
      {
        public function DataTypeInitializer()
        {
          <xsl:for-each select="//datatype">new <xsl:value-of select="../@fullname" />.vo.<xsl:value-of select="@name"/>();
          registerClassAlias( "<xsl:value-of select="../@fullname" />.<xsl:value-of select="@name"/>", <xsl:value-of select="../@fullname" />.vo.<xsl:value-of select="@name"/> );
          </xsl:for-each>
        }
      }  
    }  
  </xsl:template>

	<xsl:template name="vo">
		package
		<xsl:value-of select="//service/@namespace" />
		.vo
		{
		// Public Properties:
		<xsl:for-each select="field">
			public var
			<xsl:value-of select="@name" />
			:
			<xsl:value-of select="@type" />
			;
		</xsl:for-each>

		// Initialization:
		public function
		<xsl:value-of select="@name" />
		() { }

		// Public Methods:
		public function toString():String
		{
		return
		"Condition:" + Condition + ", Temperature:" + Temperature;
		}
		}

	</xsl:template>


	<xsl:template name="codegen.code.class">
  package <xsl:value-of select="@namespace" />
  {
   import flash.net.NetConnection;
   import flash.net.Responder;
  <xsl:call-template name="codegen.import.alltypes"/>
	
   public class <xsl:value-of select="@name" /> 
   {		
     // Constants:
     private const weborbUrl:String = "<xsl:value-of select='@endpointURL'/>";
     private const destination:String = "<xsl:value-of select="@fullname" />";
	 
     // Private Properties:
     private var gateway:NetConnection;
	
     // Initialization:
     public function <xsl:value-of select="@name" /> () 
     { 
       gateway = new NetConnection();
       gateway.connect(weborbUrl);
     } 
		
     // Public Methods:<xsl:for-each select="method">
     public function <xsl:value-of select="@name"/>(<xsl:for-each select="arg">
     <xsl:if test="position() != 1">,</xsl:if>
        <xsl:value-of select="@name"/>:<xsl:value-of select="@type" />
      </xsl:for-each>)
     {
       gateway.call(destination + ".<xsl:value-of select="@name"/>", new Responder(<xsl:value-of select="@name"/>Handler, OnErrorHandler) <xsl:for-each select="arg">, <xsl:value-of select="@name"/></xsl:for-each>);
     }
		  
     private function <xsl:value-of select="@name"/>Handler(result:Object):void
     {<xsl:if test="@type!='void'">
       var returnValue:<xsl:value-of select="@type"/>  = result as <xsl:value-of select="@type"/>;
       trace( "received result - " + returnValue );
     </xsl:if>}
     </xsl:for-each>
     private function OnErrorHandler(error:Object):void
     {
       trace( error );
     }
  }
}	
	</xsl:template>

  <xsl:template name="codegen.code">
    	<xsl:call-template name="codegen.code.class" />
  </xsl:template>
  

</xsl:stylesheet>
