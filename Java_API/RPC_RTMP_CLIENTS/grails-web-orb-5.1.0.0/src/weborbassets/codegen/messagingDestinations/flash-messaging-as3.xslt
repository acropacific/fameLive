<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<folder name="weborb-codegen">
			<info>info text</info>
			<folder path="flash/libs" hideContent="true"/>
			<file path="flash/main.fla" hideContent="true"/>
			<file path="flash/main.html" />
			<file path="flash/main.swf" hideContent="true"/>
			<file path="flash/service-config-xml.as" />
			<file path="flash/channels-include.as" />
			<file path="flash/classAliases.as" />
			<file name="CodeGenExample.as"><![CDATA[package 
{
import flash.display.MovieClip;
import fl.controls.TextArea;
import fl.controls.TextInput;
import fl.controls.Button;
import flash.events.Event;
import weborb.messaging.WeborbConsumer;
import weborb.messaging.WeborbProducer;
import mx.messaging.events.MessageEvent;
import flash.events.MouseEvent;
import mx.messaging.messages.*;
import mx.messaging.config.*;
import mx.messaging.channels.*;
import mx.messaging.events.MessageEvent;
import flash.net.registerClassAlias;
import flash.utils.getDefinitionByName;
import fl.controls.Label;

public class CodeGenExample extends MovieClip
{
	include "channels-include.as";
	
	private var clientIdField:TextInput = new TextInput();
	private var messageField:TextInput = new TextInput(); 
	private var addButton:Button = new Button();
	private var log:TextArea = new TextArea();
	
	private var consumer:WeborbConsumer = new WeborbConsumer("]]><xsl:value-of select="data/destinationId"/><![CDATA[");
	private var producer:WeborbProducer = new WeborbProducer("]]><xsl:value-of select="data/destinationId"/><![CDATA[");
	
	public function CodeGenExample():void
	{
		super();
		initConfig();
		initUI();
		initMessaging();
	}
	
	private function initConfig():void
	{
		include "service-config-xml.as";
		ServerConfig.xml = xml;
		
		LoaderConfig.parameters = this.loaderInfo.parameters;
		LoaderConfig.url = "]]><xsl:value-of select="data/weborbRootURL"/><![CDATA[";
	}
	
	private function initMessaging():void
	{
		var aliases:Array = [];
		include "classAliases.as";
		registerClassAliases(aliases);
		
		consumer.addEventListener(MessageEvent.MESSAGE, messageReceived);
		consumer.subscribe();
	}
	
	private function registerClassAliases(aliases:Array):void
	{
		for (var className:String in aliases)
		{
			var clientClass:Class = getDefinitionByName(className) as Class;
			registerClassAlias(aliases[className], clientClass);
		}
	}
	
	private function messageReceived(event:MessageEvent):void
	{
		var message:AsyncMessage = AsyncMessage(event.message);
		
		var sender:String = message.headers[ "WebORBClientId" ];
	            
		if( sender == "" )
			sender = "Anonymous";
					
		log.text += sender +" : "+ message.body + "\n";
	}
	
	private function onClick(e:Event):void
	{
		var message:AsyncMessage = new AsyncMessage();
		message.body = messageField.text;
		producer.producerId = clientIdField.text;
		producer.send(message); 
	}
	
	private function initUI():void
	{
		var paddingTop:Number = 10;
		var paddingLeft:Number = 10;
		var defWidth:Number = 380;
		var clientIdLable:Label = new Label();
		var messageLable:Label = new Label();
		
		addChild(clientIdLable);
		clientIdLable.text = "Client id:";
		clientIdLable.x = paddingLeft;
		clientIdLable.y = paddingTop;
		
		addChild(clientIdField);
		clientIdField.y = paddingTop;
		clientIdField.x = clientIdLable.x + clientIdLable.textField.textWidth + paddingLeft;
		clientIdField.width = defWidth - (clientIdLable.textField.textWidth + paddingLeft);
	
		addChild(log);
		log.y = clientIdField.y + clientIdField.height + paddingTop;
		log.x = paddingLeft;
		log.width = defWidth;
		log.height = 400;
		
		addChild(messageLable);
		messageLable.text = "Message:";
		messageLable.y = log.y + log.height + paddingTop;
		messageLable.x = paddingLeft;
		
		addChild(messageField);
		messageField.y = log.y + log.height + paddingTop;
		messageField.x = messageLable.x + messageLable.textField.textWidth + paddingLeft;
		messageField.width = defWidth - (messageLable.textField.textWidth + paddingLeft);
		
		addChild(addButton);
		addButton.y = messageField.y + messageField.height + paddingTop;
		addButton.x = paddingLeft;
		addButton.width = defWidth;
		addButton.label = "Publish";
		addButton.addEventListener(MouseEvent.CLICK, onClick);
	}
}
}
			]]></file>
		</folder>
	</xsl:template>
</xsl:stylesheet>