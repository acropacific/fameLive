package com.mycompany.wms.streamactionnotify;

import com.wowza.wms.amf.AMFPacket;
import com.wowza.wms.application.WMSProperties;
import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.module.*;
import com.wowza.wms.stream.IMediaStream;
import com.wowza.wms.stream.IMediaStreamActionNotify2;

//http://www.wowza.com/forums/showthread.php?8669-how-to-get-wowza-stream-play-status
public class ModuleMediaStreamActionNotify2 extends ModuleBase {
	public void onStreamCreate(IMediaStream stream) {
		getLogger().info("onStreamCreate by: " + stream.getClientId());
		IMediaStreamActionNotify2 actionNotify  = new StreamListener();
		
		WMSProperties props = stream.getProperties();
		synchronized(props)
		{
			props.put("streamActionNotifier", actionNotify);
		}
		stream.addClientListener(actionNotify);
	}
	public void onStreamDestroy(IMediaStream stream) {
		getLogger().info("onStreamDestroy by: " + stream.getClientId());
		
		IMediaStreamActionNotify2 actionNotify = null;
		WMSProperties props = stream.getProperties();
		synchronized(props)
		{
			actionNotify = (IMediaStreamActionNotify2)stream.getProperties().get("streamActionNotifier");
		}
		if (actionNotify != null)
		{
			stream.removeClientListener(actionNotify);
			getLogger().info("removeClientListener: "+stream.getSrc());
		}
	}
	
	class StreamListener  implements IMediaStreamActionNotify2 
	{
		public void onPlay(IMediaStream stream, String streamName, double playStart, double playLen, int playReset) 
		{
			streamName = stream.getName();
			getLogger().info("Stream Name: " + streamName);
		}
		
		public void onMetaData(IMediaStream stream, AMFPacket metaDataPacket) 
		{
			getLogger().info("onMetaData By: " + stream.getClientId());
		}
		
		public void onPauseRaw(IMediaStream stream, boolean isPause, double location) 
		{
			getLogger().info("onPauseRaw By: " + stream.getClientId());
		}

		public void onSeek(IMediaStream stream, double location)
		{
			getLogger().info("onSeek");
		}
		
		public void onStop(IMediaStream stream)
		{
			getLogger().info("onStop By: " + stream.getClientId());
		}
		
		public void onUnPublish(IMediaStream stream, String streamName, boolean isRecord, boolean isAppend)
		{
			getLogger().info("onUNPublish");
		}

		public  void onPublish(IMediaStream stream, String streamName, boolean isRecord, boolean isAppend)
		{
			getLogger().info("onPublish");
		}
		public void onPause(IMediaStream stream, boolean isPause, double location)
		{
			getLogger().info("onPause");
		}
	}
}