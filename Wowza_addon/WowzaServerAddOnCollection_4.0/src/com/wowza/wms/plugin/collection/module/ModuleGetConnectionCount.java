/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.module;

import java.util.Iterator;
import java.util.List;

import com.wowza.wms.amf.AMFDataArray;
import com.wowza.wms.amf.AMFDataItem;
import com.wowza.wms.amf.AMFDataList;
import com.wowza.wms.application.IApplication;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.client.IClient;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.request.RequestFunction;
import com.wowza.wms.server.Server;
import com.wowza.wms.stream.IMediaStream;

public class ModuleGetConnectionCount extends ModuleBase
{
	public void getServerConnectionCount(IClient client, RequestFunction function, AMFDataList params)
	{
		Server server = Server.getInstance();
		int count = (int)server.getConnectionCounter().getCurrent();
		sendResult(client, params, count);
	}

	public void getApplicationConnectionCount(IClient client, RequestFunction function, AMFDataList params)
	{
		IApplication application = client.getApplication();
		int count = (int)application.getConnectionCounter().getCurrent();
		sendResult(client, params, count);
	}

	public void getApplicationInstanceConnectionCount(IClient client, RequestFunction function, AMFDataList params)
	{
		IApplicationInstance applicationInstance = client.getAppInstance();
		int count = (int)applicationInstance.getConnectionCounter().getCurrent();
		sendResult(client, params, count);
	}

	public void getStreamConnectionCount(IClient client, RequestFunction function, AMFDataList params)
	{
		String streamName = params.getString(PARAM1);
		int count = 0;
		IApplicationInstance applicationInstance = client.getAppInstance();
		List<IMediaStream> streamList = applicationInstance.getPlayStreamsByName(streamName);
		if (streamList != null)
			count = streamList.size();
		sendResult(client, params, count);
	}

	public void getStreamClientIds(IClient client, RequestFunction function, AMFDataList params)
	{
		AMFDataArray clientList = new AMFDataArray();
		String streamName = params.getString(PARAM1);
		IApplicationInstance applicationInstance = client.getAppInstance();
		List<IMediaStream> streamList = applicationInstance.getPlayStreamsByName(streamName);
		if (streamList != null)
		{
			Iterator<IMediaStream> iter = streamList.iterator();
			while (iter.hasNext())
			{
				IMediaStream stream = iter.next();
				if (stream == null)
					continue;
				IClient sclient = stream.getClient();
				if (sclient == null)
					continue;
				clientList.add(new AMFDataItem(sclient.getClientId()));
			}
		}
		sendResult(client, params, clientList);
	}
}
