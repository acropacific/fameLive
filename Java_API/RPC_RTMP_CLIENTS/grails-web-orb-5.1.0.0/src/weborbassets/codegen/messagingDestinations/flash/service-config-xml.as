var xml:XML =
<services>
	<service id="remoting-service">
		<destination id="ChatDestination">
		  <channels>
			<channel ref="my-polling-amf"/>
		  </channels>
		</destination>
	</service>
	<channels>
		<channel id="my-polling-amf" type="mx.messaging.channels.AMFChannel">
		  <endpoint uri="weborb.wo"/>
		  <properties>
			<polling-enabled>
			  true
			</polling-enabled>
			<polling-interval-seconds>
			  1
			</polling-interval-seconds>
		  </properties>
		</channel>              
	</channels>
</services>