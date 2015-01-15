<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:import href="../codegen.project.eclipse.flex.xslt" />

    <xsl:template name="codegen.project.eclipse.flex.name"><xsl:value-of select="runtime/data/destinationId"/>App</xsl:template>
    <xsl:template name="codegen.project.eclipse.flex.actionscript.properties.mainApplicationPath">FlexExample.mxml</xsl:template>

    <xsl:template name="codegen.project.eclipse.flex.actionscript.properties.applications"><application path="FlexExample.mxml" /></xsl:template>

	<xsl:template match="/">
		<folder name="weborb-codegen">
			<info>info text</info>
			<xsl:if test="runtime/data/fullCode = 'true'">
                <xsl:call-template name="codegen.project.eclipse.flex" />
                <xsl:call-template name="codegen.project.eclipse.flex.properties" />
                <xsl:call-template name="codegen.project.eclipse.flex.actionscript.properties" />
				</xsl:if>
				<folder name="src">
					<file path="flex/FlexExample.mxml"/>
					<file name="FlexExample.as"><![CDATA[import mx.messaging.Consumer;
import mx.messaging.Producer;
import mx.messaging.events.MessageEvent;
import mx.messaging.messages.AsyncMessage;

private var consumer:Consumer = new Consumer();
private var producer:Producer = new Producer();
private var destination:String = "]]><xsl:value-of select="runtime/data/destinationId"/><![CDATA[";

private function init():void
{
	consumer.destination = destination;
	consumer.addEventListener(MessageEvent.MESSAGE, messageReceived);
	consumer.subscribe();

	producer.destination = destination;
}

private function messageReceived(event:MessageEvent):void
{
	var message:AsyncMessage = AsyncMessage(event.message);
	
	var sender:String = message.headers[ "WebORBClientId" ];

    if( sender == "" )
    	sender = "Anonymous";
	
	log.text += sender +" : "+ message.body + "\n";
}

private function onClick():void
{
	var message:AsyncMessage = new AsyncMessage();
	message.headers = {"WebORBClientId": clientIdField.text};
	message.body = messageField.text;
	producer.send(message);
}
					]]></file>
				</folder>
		</folder>
	</xsl:template>
</xsl:stylesheet>