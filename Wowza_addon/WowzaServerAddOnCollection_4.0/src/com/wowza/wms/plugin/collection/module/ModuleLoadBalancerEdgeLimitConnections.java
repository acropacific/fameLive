/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.module;

import com.wowza.wms.amf.AMFDataList;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.client.ConnectionCounter;
import com.wowza.wms.client.IClient;
import com.wowza.wms.httpstreamer.cupertinostreaming.httpstreamer.HTTPStreamerSessionCupertino;
import com.wowza.wms.httpstreamer.sanjosestreaming.httpstreamer.HTTPStreamerSessionSanJose;
import com.wowza.wms.httpstreamer.smoothstreaming.httpstreamer.HTTPStreamerSessionSmoothStreamer;
import com.wowza.wms.logging.WMSLogger;
import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.logging.WMSLoggerIDs;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.plugin.loadbalancer.LoadBalancerSender;
import com.wowza.wms.plugin.loadbalancer.ServerListenerLoadBalancerSender;
import com.wowza.wms.request.RequestFunction;
import com.wowza.wms.rtp.model.RTPSession;
import com.wowza.wms.server.Server;

public class ModuleLoadBalancerEdgeLimitConnections extends ModuleBase
{
	public static final String MODULE_NAME = "ModuleLoadBalancerEdgeLimitConnections";
	public static final String PROP_NAME_PREFIX = "loadBalancerEdgeLimit";
	
	public static final int MAXCONNECTIONS = 200;
	private WMSLogger logger;
	
	private ConnectionCounter counter;
	private int maxEdgeConnections = MAXCONNECTIONS;

	private LoadBalancerSender loadBalancerSender;
	private boolean debugLog = false;

	public void onAppStart(IApplicationInstance appInstance)
	{
		this.logger = WMSLoggerFactory.getLoggerObj(appInstance);
		this.counter = appInstance.getConnectionCounter();
		// old prop name
		this.maxEdgeConnections = appInstance.getProperties().getPropertyInt("maxEdgeConnections", maxEdgeConnections);
		// new prop name
		this.maxEdgeConnections = appInstance.getProperties().getPropertyInt(PROP_NAME_PREFIX + "MaxConnections", maxEdgeConnections);
		
		this.debugLog = appInstance.getProperties().getPropertyBoolean(PROP_NAME_PREFIX + "DebugLog", this.debugLog);
		if(logger.isDebugEnabled())
			this.debugLog = true;
		
		loadBalancerSender = (LoadBalancerSender)Server.getInstance().getProperties().get(ServerListenerLoadBalancerSender.PROP_LOADBALANCERSENDER);
		logger.info("ModuleLoadBalancerEdgeLimitConnections maxEdgeConnections: " + this.maxEdgeConnections + ", debugLog: " + this.debugLog, WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);
	}

	public void changeLimitEdge(IClient client, RequestFunction function, AMFDataList params)
	{
		Integer newLimit = params.getInt(PARAM1);
		this.maxEdgeConnections = newLimit;
		logger.info(MODULE_NAME + ".changeLimitEdge New Limit: " + newLimit, WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);
	}

	private void addConnection()
	{

		loadBalancerSender = (LoadBalancerSender)Server.getInstance().getProperties().get(ServerListenerLoadBalancerSender.PROP_LOADBALANCERSENDER);

		if (loadBalancerSender == null)
		{
			logger.error(MODULE_NAME + ".addConnection Error: Load Balancer Sender is not installed", WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);
			return;
		}

		long count = counter.getCurrent();

		if ((count + 1) > this.maxEdgeConnections)
		{
			loadBalancerSender.pause();
			logger.info(MODULE_NAME + ".addConnection Pause", WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);
		}
		if(this.debugLog )
			logger.info(MODULE_NAME + ".addConnection Count: " + count, WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);
	}

	private void removeConnection()
	{
		loadBalancerSender = (LoadBalancerSender)Server.getInstance().getProperties().get(ServerListenerLoadBalancerSender.PROP_LOADBALANCERSENDER);
		if (loadBalancerSender == null)
		{
			logger.error(MODULE_NAME + ".removeConnection Error: Load Balancer Sender is not installed", WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);
			return;
		}

		long count = counter.getCurrent();

		if ((count - 1) < this.maxEdgeConnections)
		{
			loadBalancerSender.unpause();
			logger.info(MODULE_NAME + ".removeConnection UnPause", WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);
		}
		if(this.debugLog)
			logger.info(MODULE_NAME + ".removeConnection Count: " + count, WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);
	}

	public void onConnect(IClient client, RequestFunction function, AMFDataList params)
	{
		addConnection();
	}

	public void onDisconnect(IClient client)
	{
		removeConnection();
	}

	public void onHTTPSmoothStreamingSessionCreate(HTTPStreamerSessionSmoothStreamer httpSmoothStreamingSession)
	{
		addConnection();
	}

	public void onHTTPSmoothStreamingSessionDestroy(HTTPStreamerSessionSmoothStreamer httpSmoothStreamingSession)
	{
		removeConnection();
	}

	public void onHTTPCupertinoStreamingSessionCreate(HTTPStreamerSessionCupertino httpCupertinoStreamingSession)
	{
		addConnection();
	}

	public void onHTTPCupertinoStreamingSessionDestroy(HTTPStreamerSessionCupertino httpCupertinoStreamingSession)
	{
		removeConnection();
	}

	public void onHTTPSanjoseStreamingSessionCreate(HTTPStreamerSessionSanJose httpSanJoseStreamingSession)
	{
		addConnection();
	}

	public void onHTTPSanjoseStreamingSessionDestroy(HTTPStreamerSessionSanJose httpSanJoseStreamingSession)
	{
		removeConnection();
	}

	public void onRTPSessionCreate(RTPSession rtpSession)
	{
		addConnection();
	}

	public void onRTPSessionDestroy(RTPSession rtpSession)
	{
		removeConnection();
	}
}