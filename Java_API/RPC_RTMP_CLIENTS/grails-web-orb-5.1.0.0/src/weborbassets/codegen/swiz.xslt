<?xml version="1.0"?>
<xsl:stylesheet version="2.0" 
	xmlns:codegen="urn:weborb-cogegen-xslt-lib:xslt"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:import href="codegen.xslt"/>
  <xsl:variable name="up" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
  <xsl:variable name="lo" select="'abcdefghijklmnopqrstuvwxyz'"/>
  
    <xsl:template name="codegen.appmain">
    <file name="main.mxml"><![CDATA[<?xml version="1.0" encoding="utf-8"?>]]>
&lt;s:Application xmlns:fx="http://ns.adobe.com/mxml/2009"
			   xmlns:s="library://ns.adobe.com/flex/spark"
			   xmlns:mx="library://ns.adobe.com/flex/mx"
			   xmlns:config="<xsl:value-of select="//service/@namespace"/>.config.*"
			   xmlns:swiz="http://swiz.swizframework.org"
               creationComplete="init()"
			   minWidth="955" minHeight="600">
	&lt;fx:Declarations>
		&lt;swiz:Swiz>
			&lt;swiz:beanProviders>
				&lt;config:Beans />
			&lt;/swiz:beanProviders>
			&lt;swiz:config>
				&lt;swiz:SwizConfig eventPackages="<xsl:value-of select="//service/@namespace"/>.event.*" />
			&lt;/swiz:config>
		&lt;/swiz:Swiz>
	&lt;/fx:Declarations>
    <xsl:variable name="firstLetter" select="substring(//service/@name, 1,1)"/>
    <xsl:variable name="className" select = "concat(translate($firstLetter, $up, $lo), substring(//service/@name, 2, 30))"/>

  &lt;fx:Script>
    &lt;![CDATA[
        import <xsl:value-of select="//service/@namespace"/>.event.EventInitializer;
        import <xsl:value-of select="//service/@namespace"/>.controller.<xsl:value-of select="//service/@name"/>Controller;
        import <xsl:value-of select="//service/@namespace"/>.controller.<xsl:value-of select="//service/@name"/>ControllerModel;

        [Inject]
        [Bindable]
        public var controllerModel:<xsl:value-of select="//service/@name"/>ControllerModel;

        [Inject]
        public var controller:<xsl:value-of select="//service/@name"/>Controller;

        public function init():void
        {
          // It is necessary to invoke the method below in order to force the Flex
          // compiler to include all the event classes into the compilation scope.
          // Failure to invoke the method may lead to Swiz initialization errors.
          // This may happen if a particular event is not used in the application, but is
          // referenced in [EventHandler] attribute in the controller
          EventInitializer.initEvents();
        }
	 ]]&gt;
	&lt;/fx:Script>
&lt;/s:Application>
    </file>
  </xsl:template>

  <xsl:template name="libs.folder.additional.content">
      <file path="frameworks/swiz-framework-v1.2.0.swc" hideContent="true" />
  </xsl:template>
  
  <xsl:template name="codegen.service">
       <xsl:if test="count(//datatype) != 0">
        <file name="DataTypeInitializer.as">
          <xsl:call-template name="codegen.datatypelist">
            <xsl:with-param name="namespaceName" select="@namespace" />
            <xsl:with-param name="subnamespace" select="'.model.'" />
          </xsl:call-template>
        </file>
       </xsl:if>
	   <folder name="controller">
		<xsl:variable name="firstLetter" select="substring(@name, 1,1)"/>
		<xsl:variable name="className" select = "concat(translate($firstLetter, $lo, $up), substring(@name, 2, 30))"/>
		
        <file name="{$className}Controller.as">
         <xsl:call-template name="codegen.description">
              <xsl:with-param name="file-name" select="concat($className,'Controller.as')" />
         </xsl:call-template>
        <xsl:call-template name="controllers" />
        </file>
        <file name="{$className}ControllerModel.as">
          <xsl:call-template name="codegen.controller.model">
              <xsl:with-param name="model-class-name" select="concat($className,'ControllerModel')" />
         </xsl:call-template>
        </file>
      </folder>
	  
	  <folder name="config">
        <file name="Beans.mxml">
          <xsl:call-template name="beans" />
        </file>
      </folder>

<folder name="event">
  <xsl:for-each select="//service">
    <xsl:for-each select="method">
<xsl:variable name="evtName" select = "translate(@name, $lo, $up)"/>
<xsl:variable name="firstLetterMethodName" select="substring(@name, 1,1)"/>
<xsl:variable name="methodNamePascal" select = "concat(translate($firstLetterMethodName, $lo, $up), substring(@name, 2, 30))"/>

  <file name="{$methodNamePascal}Event.as">
    <xsl:call-template name="codegen.description">
      <xsl:with-param name="file-name" select="concat($methodNamePascal,'Event.as')" />
    </xsl:call-template>
  package <xsl:value-of select="../@namespace" />.event
  {
    import flash.events.Event;
    <xsl:if test="count(arg) != 0">
      <xsl:for-each select='arg'><xsl:variable name="argtype" select="@type"/><xsl:if test="//datatype[@name=$argtype]">import <xsl:value-of select="substring-before(@javatype, @type)"/>model.<xsl:value-of select='@type'/></xsl:if>;
      </xsl:for-each>
    </xsl:if>
			
    public class <xsl:value-of select="$methodNamePascal" />Event extends Event
    {
      public static const <xsl:value-of select="$evtName" />_REQUESTED : String = "<xsl:value-of select="@name" />";
      <xsl:if test="count(arg) != 0">
        <xsl:for-each select='arg'><xsl:variable name="argtype" select="@type"/>public var <xsl:value-of select='@name'/>:<xsl:if test="//datatype[@name=$argtype]"><xsl:value-of select="substring-before(@javatype, @type)"/>model.</xsl:if><xsl:value-of select='@type'/>;
        </xsl:for-each>
      </xsl:if>
      public function <xsl:value-of select="$methodNamePascal" />Event( type:String )
      {
        super( type, true );
      }
    }
  }
</file>
</xsl:for-each>
</xsl:for-each>
   <file name="EventInitializer.as">
   package <xsl:value-of select="//service/@namespace" />.event
   {
     public class EventInitializer
     {
       public static function initEvents():void
       {<xsl:for-each select="//service">
         <xsl:for-each select="method">
     <xsl:variable name="evtName" select = "translate(@name, $lo, $up)"/>
     <xsl:variable name="firstLetterMethodName" select="substring(@name, 1,1)"/>
     <xsl:variable name="methodNamePascal" select = "concat(translate($firstLetterMethodName, $lo, $up), substring(@name, 2, 30))"/>
          var <xsl:value-of select="@name" />Event:<xsl:value-of select="$methodNamePascal" />Event = new <xsl:value-of select="$methodNamePascal" />Event( "dummy" );</xsl:for-each>
       </xsl:for-each>
       }
     }
   }
   </file>
</folder>
	
<folder name="service">
  <file name="{@name}Service.as">
    <xsl:call-template name="codegen.description">
      <xsl:with-param name="file-name" select="concat(@name,'Event.as')" />
    </xsl:call-template>
  package <xsl:value-of select="@namespace" />.service
  {
    import flash.events.IEventDispatcher;
    import mx.rpc.AsyncToken;
    import mx.rpc.IResponder;
    import mx.rpc.remoting.RemoteObject;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.events.FaultEvent;
    import mx.collections.ArrayCollection;
    <xsl:call-template name="codegen.import.alltypes"><xsl:with-param name="subnamespace" select="'model'" /></xsl:call-template>
			
    public class <xsl:value-of select="@name" />Service
    {
      [Dispatcher]
      /**
       * The [Dispatcher] metadata tag instructs Swiz to inject an event dispatcher.
       * Event's dispatched via this dispatcher can trigger event mediators.
       */ 
      public var dispatcher : IEventDispatcher;
      private var remoteObject:RemoteObject;
	  
      public function <xsl:value-of select="@name" />Service()
      {
        remoteObject  = new RemoteObject("GenericDestination");
        remoteObject.source = "<xsl:value-of select='@fullname'/>";
      }
    <xsl:for-each select="method">
      public function <xsl:value-of select="@name"/>( <xsl:for-each select="arg"><xsl:value-of select="@name"/>:<xsl:value-of select="@type" />, </xsl:for-each>responder:IResponder = null ):AsyncToken
      {
        var asyncToken:AsyncToken = remoteObject.<xsl:value-of select="@name"/>(<xsl:for-each select="arg">
          <xsl:if test="position() != 1">,</xsl:if>
          <xsl:value-of select="@name"/>
        </xsl:for-each>);
		
        if( responder != null )
          asyncToken.addResponder( responder );
		
        return asyncToken;
      }
    </xsl:for-each>
    }
  }
</file>
</folder>	
  </xsl:template>

  <xsl:template name="codegen.controller.model">
    <xsl:param name="model-class-name" />
	<xsl:call-template name="codegen.description">
      <xsl:with-param name="file-name" select="concat($model-class-name,'.as')" />
    </xsl:call-template>

    package <xsl:value-of select="@namespace" />.controller
    {
      <xsl:call-template name="codegen.import.alltypes" >
      <xsl:with-param name="subnamespace" select="'model'" />
      </xsl:call-template>
      [Bindable]
      public class <xsl:value-of select="$model-class-name"/>
      {<xsl:for-each select="method"><xsl:if test="@type != 'void'">
        public var <xsl:value-of select="@name" />Result:<xsl:value-of select="@type" />;</xsl:if></xsl:for-each>
      }
    }
  </xsl:template>
      <!--
    <xsl:template name="model.vo.folder">
        <xsl:if test="count(datatype) != 0">
            <folder name="model">
                <folder name="vo">
                    <xsl:for-each select="datatype">
                        <file name="{@name}.as">
                            <xsl:call-template name="codegen.description">
                                <xsl:with-param name="file-name" select="concat(@name,'.as')"/>
                            </xsl:call-template>
                            <xsl:call-template name="model.vo"/>
                        </file>
                    </xsl:for-each>
                </folder>
            </folder>
        </xsl:if>
    </xsl:template>  -->

 	<xsl:template name="codegen.vo.folder">
		<xsl:param name="version" select="3" />
      <xsl:if test="count(datatype) != 0 or count(enum) != 0">
          <folder name="model">
            <xsl:if test="count(datatype) != 0">
                <xsl:for-each select="datatype">
                    <xsl:call-template name="codegen.vo">
                        <xsl:with-param name="version" select="$version" />
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:if>
            <xsl:if test="count(enum) != 0">
                <folder name="enum">
                    <xsl:for-each select="enum">
                    <xsl:call-template name="codegen.enum">
                        <xsl:with-param name="version" select="$version" />
                    </xsl:call-template>
                </xsl:for-each>
                </folder>
            </xsl:if>
          </folder>
      </xsl:if>
	</xsl:template>

 <xsl:template name="model.vo">
  package <xsl:value-of select="@typeNamespace"/>.model.vo
  {
    <xsl:call-template name="codegen.import.fieldtypes" ><xsl:with-param name="subnamespace" select="'model'" /></xsl:call-template>
    [Bindable]
    [RemoteClass(alias="<xsl:value-of select='@fullname'/>")]
    public class <xsl:value-of select="@name" /><xsl:if test="@parentName"> extends <xsl:value-of select="//service/@namespace"/>.model.vo.<xsl:value-of select="@parentName"/></xsl:if>
    {<xsl:for-each select="field">  
      public var <xsl:value-of select="@name"/>:<xsl:value-of select="@typeNamespace"/>.vo.<xsl:value-of select="@type"/>;</xsl:for-each>
    }
  }
 </xsl:template>

<xsl:template name="beans">
  <xsl:variable name="firstLetter" select="substring(@name, 1,1)"/>
  <xsl:variable name="className" select = "concat(translate($firstLetter, $up, $lo), substring(@name, 2, 30))"/>&lt;swiz:BeanProvider
           xmlns:swiz="http://swiz.swizframework.org"
           xmlns:mx="http://www.adobe.com/2006/mxml"
           xmlns:service="<xsl:value-of select="//service/@namespace" />.service.*"
           xmlns:controller="<xsl:value-of select="//service/@namespace" />.controller.*">
&lt;service:<xsl:value-of select="//service/@name" />Service id="<xsl:value-of select="$className"/>Service"/>
&lt;controller:<xsl:value-of select="//service/@name" />Controller id="<xsl:value-of select="$className"/>Controller"/>
&lt;controller:<xsl:value-of select="//service/@name" />ControllerModel id="<xsl:value-of select="$className"/>ControllerModel"/>
&lt;swiz:ServiceHelper id="serviceHelper" /<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
&lt;/swiz:BeanProvider>
</xsl:template>
	
<xsl:template name="controllers">
  package <xsl:value-of select="//service/@namespace" />.controller
  {
    import mx.rpc.events.ResultEvent;
    import mx.rpc.events.FaultEvent;
    import mx.utils.ObjectUtil;
    import mx.controls.Alert;
    import org.swizframework.utils.services.ServiceHelper;
    import <xsl:value-of select="//service/@namespace" />.service.<xsl:value-of select="@name"/>Service;<xsl:call-template name="codegen.import.alltypes"><xsl:with-param
        name="subnamespace" select="'model'" /></xsl:call-template>
	<xsl:variable name="firstLetter" select="substring(//service/@name, 1,1)"/>
	<xsl:variable name="className" select = "concat(translate($firstLetter, $up, $lo), substring(//service/@name, 2, 30))"/>
	
    public class <xsl:value-of select="//service/@name"/>Controller
    {
      [Inject]
      public var <xsl:value-of select="$className"/>Service:<xsl:value-of select="@name"/>Service;
	  
      [Inject]
      public var serviceHelper:ServiceHelper;

      [Inject]
      public var controllerModel:<xsl:value-of select="//service/@name" />ControllerModel;
	  <xsl:for-each select="//method">
<xsl:variable name="evtName" select = "translate(@name, $lo, $up)"/>
<xsl:variable name="firstLetterMethodName" select="substring(@name, 1,1)"/>
<xsl:variable name="methodNamePascal" select = "concat(translate($firstLetterMethodName, $lo, $up), substring(@name, 2, 30))"/>
      [EventHandler( event="<xsl:value-of select="$methodNamePascal"/>Event.<xsl:value-of select="$evtName"/>_REQUESTED" <xsl:if test="count(arg) != 0">, properties= "<xsl:for-each select='arg'><xsl:value-of select='@name'/><xsl:if test='position()!=last()'>,</xsl:if></xsl:for-each>" </xsl:if>)]
      public function <xsl:value-of select='@name'/>( <xsl:for-each select='arg'><xsl:value-of select='@name'/>:<xsl:value-of select='@type'/><xsl:if test='position()!=last()'>,</xsl:if></xsl:for-each> ):void
      {
        serviceHelper.executeServiceCall( <xsl:value-of select="$className"/>Service.<xsl:value-of select='@name'/>(<xsl:for-each select='arg'><xsl:value-of select='@name'/><xsl:if test='position()!=last()'>,</xsl:if></xsl:for-each>), handle<xsl:value-of select='$methodNamePascal'/>Result ); 
      }
	  
      public function handle<xsl:value-of select='$methodNamePascal'/>Result( event:ResultEvent ):void
      {
        trace( "received result for <xsl:value-of select='@name'/> method invocation" );
        <xsl:if test="@type != 'void'">
        controllerModel.<xsl:value-of select='@name'/>Result = event.result as <xsl:value-of select="@type" />;</xsl:if>
      }
    </xsl:for-each>}
  }
    </xsl:template>
</xsl:stylesheet>
