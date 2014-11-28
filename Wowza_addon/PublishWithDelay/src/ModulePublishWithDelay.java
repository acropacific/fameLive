package com.wowza.wms.plugin.publishwithdelay;

import java.util.*;

import com.wowza.wms.module.*;
import com.wowza.wms.client.*;
import com.wowza.wms.request.*;
import com.wowza.wms.amf.*;

public class ModulePublishWithDelay extends ModuleBase
{
	Map<String, PublishWithDelayWorker> delayPublishers = new HashMap<String, PublishWithDelayWorker>();
	
	public void startDelayPublisher(IClient client, RequestFunction function, AMFDataList params)
	{
		String srcStreamName = params.getString(PARAM1);
		String dstStreamName = params.getString(PARAM2);
		int delay = (int)Math.round(params.getDouble(PARAM3) * 1000);
		
		PublishWithDelayWorker worker = new PublishWithDelayWorker(client.getAppInstance(), srcStreamName, dstStreamName);
		worker.setDelay(delay);
		
		getLogger().info("startDelayPublisher");
		getLogger().info("  srcStreamName: "+srcStreamName);
		getLogger().info("  dstStreamName: "+dstStreamName);
		getLogger().info("  delay: "+delay);
		
		synchronized (delayPublishers)
		{
			delayPublishers.put(srcStreamName, worker);
		}
		
		worker.setDaemon(true);
		worker.start();
	}

	public void stopDelayPublisher(IClient client, RequestFunction function, AMFDataList params)
	{
		String srcStreamName = params.getString(PARAM1);

		getLogger().info("stopDelayPublisher");
		getLogger().info("  srcStreamName: "+srcStreamName);

		PublishWithDelayWorker worker = null;
		synchronized (delayPublishers)
		{
			worker = delayPublishers.remove(srcStreamName);
		}
		
		if (worker != null)
			worker.quit();
	}

}
