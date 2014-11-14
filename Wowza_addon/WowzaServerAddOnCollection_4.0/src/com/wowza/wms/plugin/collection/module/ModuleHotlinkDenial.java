/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.module;

import com.wowza.wms.amf.AMFDataList;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.client.IClient;
import com.wowza.wms.logging.WMSLogger;
import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.logging.WMSLoggerIDs;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.request.RequestFunction;

public class ModuleHotlinkDenial extends ModuleBase
{

	public static final String MODULE_NAME = "ModuleHotlinkDenial";
	public static final String PROP_NAME_PREFIX = "hotlink";
	private WMSLogger logger;

	private boolean logConnections = false;
	private boolean logRejections = true;
	private IApplicationInstance appInstance;
	private String domainLockStr = "";
	private String allowedEncoderStr = "";

	public void onAppStart(IApplicationInstance appInstance)
	{
		logger = WMSLoggerFactory.getLoggerObj(appInstance);
		this.appInstance = appInstance;
		// old property name
		this.domainLockStr = appInstance.getProperties().getPropertyStr("domainLock", this.domainLockStr);
		// new property name
		this.domainLockStr = appInstance.getProperties().getPropertyStr(PROP_NAME_PREFIX + "Domains", this.domainLockStr);
		// old property name
		this.allowedEncoderStr = appInstance.getProperties().getPropertyStr("AllowEncoder", this.allowedEncoderStr);
		// new property name
		this.allowedEncoderStr = appInstance.getProperties().getPropertyStr(PROP_NAME_PREFIX + "Encoders", this.allowedEncoderStr);

		this.logConnections = appInstance.getProperties().getPropertyBoolean(PROP_NAME_PREFIX + "LogConnections", this.logConnections);
		this.logRejections = appInstance.getProperties().getPropertyBoolean(PROP_NAME_PREFIX + "LogRejections", this.logRejections);
		if (logger.isDebugEnabled())
		{
			this.logConnections = true;
			this.logRejections = true;
		}
	}

	public void onConnect(IClient client, RequestFunction function, AMFDataList params)
	{
		if (logConnections)
			logger.info(MODULE_NAME + ".onConnect: " + client.getClientId(), WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);

		String flashver = client.getFlashVer().toLowerCase();
		if (logConnections)
			logger.info(MODULE_NAME + " Client Flashver: " + flashver, WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);

		try
		{
			String[] allowedEncoder = null;

			allowedEncoder = this.allowedEncoderStr.toLowerCase().split(",");
			if (allowedEncoder != null)
			{
				for (int i = 0; i < allowedEncoder.length; i++)
				{
					if (flashver.startsWith(allowedEncoder[i].trim()) && allowedEncoder[i].length() > 0)
					{
						if (logConnections)
							logger.info(MODULE_NAME + " Encoder Allowed: " + flashver + " matches " + allowedEncoder[i], WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);
						return;
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.error(MODULE_NAME + " Exception: " + e.getMessage(), e);
		}

		boolean reject = true;
		String[] domainLocks = null;
		String[] domainUrl = null;
		String pageUrl = "";
		try
		{
			pageUrl = client.getProperties().getPropertyStr("connectpageUrl").toLowerCase();
			domainLocks = this.domainLockStr.toLowerCase().split(",");
			domainUrl = pageUrl.split("/");

			if (logConnections)
				logger.info(MODULE_NAME + " domainLock: " + this.domainLockStr.toLowerCase(), WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);
			if (logConnections)
				logger.info(MODULE_NAME + " pageUrl: " + pageUrl, WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);
			for (int i = 0; i < domainLocks.length; i++)
			{
				if (domainLocks[i].trim().startsWith("*"))
				{
					String lock = domainLocks[i].trim().substring(1);
					if (domainUrl[2].endsWith(lock))
					{
						reject = false;
					}
				}
				else if (domainUrl[2].equalsIgnoreCase(domainLocks[i].trim()))
				{
					reject = false;
				}
			}
		}
		catch (Exception ex)
		{
			reject = true;
		}
		if (reject)
		{
			if (logRejections)
				logger.info(MODULE_NAME + " Client Rejected. IP: " + client.getIp() + ": domainLock: " + this.domainLockStr.toLowerCase() + ": pageUrl: " + pageUrl, WMSLoggerIDs.CAT_application, WMSLoggerIDs.EVT_comment);
			client.rejectConnection();
		}
	}

	public boolean isLogConnections()
	{
		return logConnections;
	}

	public void setLogConnections(boolean logConnections)
	{
		this.logConnections = logConnections;
	}

	public boolean isLogRejections()
	{
		return logRejections;
	}

	public void setLogRejections(boolean logRejections)
	{
		this.logRejections = logRejections;
	}
}
