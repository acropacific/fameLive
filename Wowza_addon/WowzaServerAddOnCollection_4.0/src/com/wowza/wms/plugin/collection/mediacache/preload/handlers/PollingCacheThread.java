/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.mediacache.preload.handlers;

import java.util.Iterator;
import java.util.List;

import com.wowza.wms.application.Application;
import com.wowza.wms.application.IApplication;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.mediacache.impl.MediaCacheImpl;
import com.wowza.wms.mediacache.model.MediaCache;
import com.wowza.wms.mediacache.model.MediaCacheStore;
import com.wowza.wms.plugin.collection.serverlistener.ServerListenerMediaCachePreload;
import com.wowza.wms.vhost.IVHost;
import com.wowza.wms.vhost.VHostSingleton;

/*
 * Helper thread This checks the cache on an interval merely to display
 * whats been added within the log (DEBUG Mode required)
 */
public class PollingCacheThread implements Runnable
{
	private String logPrefix = "PollingCacheThread.";
	private int mediaCacheContentsDisplayInterval;
	private String mediaCacheApplication;
	private String mediaCacheApplicationInstance;
	private String mediaCacheVhost;

	public PollingCacheThread(int mediaCacheContentsDisplayInterval, String mediaCacheApplication, String mediaCacheApplicationInstance, String mediaCacheVhost)
	{
		this.mediaCacheContentsDisplayInterval = mediaCacheContentsDisplayInterval;
		this.mediaCacheApplication = mediaCacheApplication;
		this.mediaCacheApplicationInstance = mediaCacheApplicationInstance;
		this.mediaCacheVhost = mediaCacheVhost;
	}

	private IApplicationInstance getAppInstance()
	{
		IVHost vhost = VHostSingleton.getInstance(IVHost.VHOST_DEFAULT);
		if (this.mediaCacheVhost != null && this.mediaCacheVhost.length() > 0)
		{
			vhost = VHostSingleton.getInstance(this.mediaCacheVhost);
		}

		IApplicationInstance appInstance = null;
		if (this.mediaCacheApplication != null && this.mediaCacheApplication.length() > 0)
		{
			IApplication application = vhost.getApplication(this.mediaCacheApplication);

			if (this.mediaCacheApplicationInstance != null)
			{
				appInstance = application.getAppInstance(this.mediaCacheApplicationInstance);
			}
			else
			{
				appInstance = application.getAppInstance("_definst_");
			}
			ServerListenerMediaCachePreload.debug(this.logPrefix + " [application: " + application.getName() + "]");

		}
		else
		{
			if (vhost.getApplicationFolderNames().size() > 0)
			{
				String applicationName = (String)vhost.getApplicationFolderNames().get(0);
				Application application = new Application(applicationName, vhost);

				if (this.mediaCacheApplicationInstance != null)
				{
					appInstance = application.getAppInstance(this.mediaCacheApplicationInstance);
				}
				else
				{
					appInstance = application.getAppInstance("_definst_");
				}

				ServerListenerMediaCachePreload.debug(this.logPrefix + " [application: " + applicationName + "]");
			}
		}
		return appInstance;
	}

	@Override
	public void run()
	{
		try
		{
			Thread.sleep(10000);
		}
		catch (InterruptedException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true)
		{
			ServerListenerMediaCachePreload.info(this.logPrefix + " [Check the MediaCache Contents]");

			try
			{
				MediaCache mediaCache = MediaCacheImpl.getMediaCache();

				List<String> cacheItems = mediaCache.cacheItemNames();
				if (cacheItems.size() > 0)
				{
					Iterator<String> cacheIterator = cacheItems.iterator();

					while (cacheIterator.hasNext())
					{
						String cacheName = cacheIterator.next();
						ServerListenerMediaCachePreload.info(this.logPrefix + " [Cache Item: " + cacheName + "]");

					}

					Iterator<MediaCacheStore> stores = mediaCache.getStores().iterator();
					while (stores.hasNext())
					{
						MediaCacheStore elem = stores.next();

						String storeInfo = elem.getPath() + " : " + "[" + elem.getCurrentSize() + "]";
						ServerListenerMediaCachePreload.info(this.logPrefix + " [Cache store: " + storeInfo + "]");
					}
				}
				else
				{
					ServerListenerMediaCachePreload.info(this.logPrefix + " [No cache items!]");
				}

				try
				{
					Thread.sleep(this.mediaCacheContentsDisplayInterval);
				}
				catch (InterruptedException e)
				{
					ServerListenerMediaCachePreload.error(this.logPrefix + " [InterruptedException: " + e.getMessage() + "]");
				}
			}
			catch (Exception ex)
			{
				ServerListenerMediaCachePreload.info(this.logPrefix + " [Exception: " + ex.getMessage() + "]");
			}
		}
	}

}