package com.wowza.wms.plugin.collection.serverlistener;

import java.io.File;

import com.wowza.wms.application.Application;
import com.wowza.wms.application.IApplication;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.bootstrap.Bootstrap;
import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.plugin.collection.mediacache.preload.handlers.PollingCacheThread;
import com.wowza.wms.plugin.collection.mediacache.preload.handlers.QueueFilesThread;
import com.wowza.wms.server.IServer;
import com.wowza.wms.server.IServerNotify2;
import com.wowza.wms.vhost.IVHost;
import com.wowza.wms.vhost.VHostSingleton;

public class ServerListenerMediaCachePreload implements IServerNotify2
{
	private static final String LOG_PREFIX = "ServerListenerMediaCachePreload.";
	private static final String PROPERTY_NAME_PREFIX = "mediaCachePreload";
	
	private Thread queueThread;
	private Thread pollingThread;
	private static boolean mediaCachePreloadDebug = false;

	/*
	 * log wrappers
	 */
	public static void info(String msg)
	{
		WMSLoggerFactory.getLogger(null).info(LOG_PREFIX + msg);
	}

	public static void debug(String msg)
	{
		if (ServerListenerMediaCachePreload.mediaCachePreloadDebug)
		{
			WMSLoggerFactory.getLogger(null).info(LOG_PREFIX + msg);
		}
	}

	public static void error(String msg)
	{
		WMSLoggerFactory.getLogger(null).error(LOG_PREFIX + msg);
	}

	public static void warn(String msg)
	{
		WMSLoggerFactory.getLogger(null).warn(LOG_PREFIX + msg);
	}

	public static IApplicationInstance getAppInstance(String mediaCacheVhost, String mediaCacheApplication, String mediaCacheApplicationInstance)
	{
		IApplicationInstance appInstance = null;
		IVHost vhost = VHostSingleton.getInstance(IVHost.VHOST_DEFAULT);
		if (mediaCacheVhost != null && mediaCacheVhost.length() > 0)
		{
			vhost = VHostSingleton.getInstance(mediaCacheVhost);
		}

		if (mediaCacheApplication != null && mediaCacheApplication.length() > 0)
		{
			IApplication application = vhost.getApplication(mediaCacheApplication);

			if (mediaCacheApplicationInstance != null)
			{
				appInstance = application.getAppInstance(mediaCacheApplicationInstance);
			}
			else
			{
				appInstance = application.getAppInstance("_definst_");
			}

			ServerListenerMediaCachePreload.debug(LOG_PREFIX + "[application: " + application.getName() + "]");

		}
		else
		{
			if (vhost.getApplicationFolderNames().size() > 0)
			{
				String applicationName = (String)vhost.getApplicationFolderNames().get(0);
				Application application = new Application(applicationName, vhost);

				if (mediaCacheApplicationInstance != null)
				{
					appInstance = application.getAppInstance(mediaCacheApplicationInstance);
				}
				else
				{
					appInstance = application.getAppInstance("_definst_");
				}

				ServerListenerMediaCachePreload.debug(LOG_PREFIX + " [application: " + applicationName + "]");

			}
		}
		return appInstance;
	}

	@Override
	public void onServerCreate(IServer server)
	{
	}

	/*
	 * (non-Javadoc)
	 * @see com.wowza.wms.server.IServerNotify#onServerInit(com.wowza.wms.server.IServer)
	 * Initialize all input properties from the Server.xml
	 * Set module variables as needed.  All input is optional.
	 * @mediaCacheApplication - Default application to use for the preloading
	 * @mediaCacheApplicationInstance - default app instance.
	 * @mediaCacheVhost - Default Vhost
	 * @mediaCacheTargetPath - Default mediacache target path
	 * @mediaCacheLoadType - bytes|defaultBlockSize 
	 * @mediaCacheLoadValue - Value to corresponding with load type
	 * @mediaCacheThreads - Limit number of worker threads to handle preloading of cache.  This limit will queue subsequent requests using the cached thread pool
	 * @mediaCacheContentsDisplayInterval - just used to display cache elements.  Debugging property.
	 */
	@Override
	public void onServerInit(IServer server)
	{
		String mediaCacheTargetFilePath = Bootstrap.getServerHome(Bootstrap.CONFIGHOME) + "/conf/mediacachepreloadtargets.txt";

		String mediaCacheApplication = server.getProperties().getPropertyStr(PROPERTY_NAME_PREFIX + "Application");

		String mediaCacheApplicationInstance = server.getProperties().getPropertyStr(PROPERTY_NAME_PREFIX + "ApplicationInstance", null);

		String mediaCacheVhost = server.getProperties().getPropertyStr(PROPERTY_NAME_PREFIX + "Vhost");

		// get custom file path for target file
		String mediaCacheTargetPath = server.getProperties().getPropertyStr(PROPERTY_NAME_PREFIX + "TargetPath");

		ServerListenerMediaCachePreload.mediaCachePreloadDebug = server.getProperties().getPropertyBoolean(PROPERTY_NAME_PREFIX + "DebugLog", false);

		long mediaCacheWaitBeforeNextCheck = server.getProperties().getPropertyLong(PROPERTY_NAME_PREFIX + "WaitBeforeNextCheck", 5000);

		if (mediaCacheWaitBeforeNextCheck < 5000)
		{
			mediaCacheWaitBeforeNextCheck = 5000;
		}

		if (mediaCacheTargetPath != null && mediaCacheTargetPath.length() > 0)
		{
			mediaCacheTargetFilePath = mediaCacheTargetPath;
		}

		// get mechanism by which this determins how much to cache
		int mediaCachePreloadValue = 3;
		String mediaCachePreloadType = "defaultBlockSize"; // alternative to
															// bytes
		if (server.getProperties().containsKey(PROPERTY_NAME_PREFIX + "LoadType"))
		{
			mediaCachePreloadType = server.getProperties().getPropertyStr(PROPERTY_NAME_PREFIX + "LoadType").toLowerCase();
			if (mediaCachePreloadType.equalsIgnoreCase("bytes") || mediaCachePreloadType.equalsIgnoreCase("defaultBlockSize"))
			{
				if (server.getProperties().containsKey(PROPERTY_NAME_PREFIX + "LoadValue"))
				{
					mediaCachePreloadValue = server.getProperties().getPropertyInt(PROPERTY_NAME_PREFIX + "LoadValue", mediaCachePreloadValue);
				}
				else
				{

					ServerListenerMediaCachePreload.warn("onServerInit [mediaCacheLoadValue is not found]");
				}
			}
			else
			{

				ServerListenerMediaCachePreload.warn("onServerInit [mediaCacheLoadType is not supported: " + mediaCachePreloadType + "]");
			}
		}
		else
		{

			ServerListenerMediaCachePreload.warn("onServerInit [mediaCacheLoadType not provided]");
		}

		// get max active thread count for parallel preloading of cache items,
		// default is 5
		int mediaCacheThreads = server.getProperties().getPropertyInt(PROPERTY_NAME_PREFIX + "Threads", 5);

		// get the interval at which to display mediacache contents. If none
		// supplied, it will not be shown
		int mediaCacheContentsDisplayInterval = server.getProperties().getPropertyInt(PROPERTY_NAME_PREFIX + "ContentsDisplayInterval", 5000);

		ServerListenerMediaCachePreload.info("onServerInit [Initiating mediacache target file " + mediaCacheTargetFilePath + ". Maximum active thread count: " + mediaCacheThreads + "]");

		File preloadListFile = new File(mediaCacheTargetFilePath);
		if (preloadListFile.exists())
		{
			ServerListenerMediaCachePreload.info("onServerInit [MediaCache target file starting " + preloadListFile.getAbsolutePath() + "]");

			queueThread = new Thread(new QueueFilesThread(mediaCacheTargetFilePath, server, mediaCacheTargetFilePath, mediaCacheThreads, mediaCacheApplication, mediaCacheApplicationInstance, mediaCacheVhost, mediaCachePreloadType, mediaCachePreloadValue, mediaCacheWaitBeforeNextCheck),
					"MediaCacheQueueThread");
			queueThread.start();

			if (server.getProperties().containsKey(PROPERTY_NAME_PREFIX + "ContentsDisplayInterval") && mediaCacheContentsDisplayInterval >= 1000)
			{
				pollingThread = new Thread(new PollingCacheThread(mediaCacheContentsDisplayInterval, mediaCacheApplication, mediaCacheApplicationInstance, mediaCacheVhost), "MediaCacheIntervalThread");
				pollingThread.start();
			}
			else
			{
				ServerListenerMediaCachePreload.warn("onServerInit [no mediaCachePollingThread provided]");
			}

		}
		else
		{
			ServerListenerMediaCachePreload.warn("onServerInit [MediaCache target file does not exist]");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.wowza.wms.server.IServerNotify#onServerShutdownComplete(com.wowza.wms.server.IServer)
	 * Possibly clean up the two initiated threads if they are available.
	 */
	@Override
	public void onServerShutdownComplete(IServer server)
	{
		if (pollingThread instanceof Thread)
		{
			if (pollingThread.isAlive())
			{
				pollingThread = null;
			}
		}
		if (queueThread instanceof Thread)
		{
			if (queueThread.isAlive())
			{
				queueThread = null;
			}
		}
	}

	@Override
	public void onServerShutdownStart(IServer server)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onServerConfigLoaded(IServer server)
	{
		// TODO Auto-generated method stub

	}
}