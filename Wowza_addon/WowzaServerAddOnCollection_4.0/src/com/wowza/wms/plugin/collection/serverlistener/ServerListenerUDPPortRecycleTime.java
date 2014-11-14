/**
 * Wowza Streaming Engine Software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.serverlistener;

import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.logging.WMSLoggerIDs;
import com.wowza.wms.server.IServer;
import com.wowza.wms.server.IServerNotify2;
import com.wowza.wms.server.Server;
import com.wowza.wms.transport.udp.UDPPortManager;

public class ServerListenerUDPPortRecycleTime implements IServerNotify2
{
	public static final String MODULE_NAME = "ServerListenerUDPPortRecycleTime";
	public static final String PROP_NAME_PREFIX = "udpPortRecycle";
	public static final int DEFAULT_RECYCLE_TIME = 10*60*1000; // 10 minutes
	
	@Override
	public void onServerCreate(IServer server)
	{
	}

	@Override
	public void onServerInit(IServer server)
	{
		try{
			int recycleTime = server.getProperties().getPropertyInt(PROP_NAME_PREFIX+"Time", DEFAULT_RECYCLE_TIME);
			UDPPortManager udpPortManager = Server.getInstance().getUDPPortManager();
			if(recycleTime > 0)
			{
				WMSLoggerFactory.getLogger(getClass()).info(MODULE_NAME + ".onServerInit:"+"set udp port recycle time to "+recycleTime+"ms",WMSLoggerIDs.CAT_server, WMSLoggerIDs.EVT_comment);
				udpPortManager.setPortRecycleTime(recycleTime);
			}
		}
		catch(Exception e)
		{
			WMSLoggerFactory.getLogger(getClass()).error(MODULE_NAME + ".onServerInit:"+"Unable to set udp recycle time: "+e.getMessage(), e);
		}
	}

	@Override
	public void onServerShutdownComplete(IServer server)
	{
	}

	@Override
	public void onServerShutdownStart(IServer server)
	{
	}

	@Override
	public void onServerConfigLoaded(IServer server)
	{
	}
}
