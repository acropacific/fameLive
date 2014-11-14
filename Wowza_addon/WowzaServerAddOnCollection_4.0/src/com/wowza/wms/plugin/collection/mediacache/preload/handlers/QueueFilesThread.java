/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.mediacache.preload.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.mediacache.impl.MediaCacheImpl;
import com.wowza.wms.mediacache.impl.MediaCacheSourceBasic;
import com.wowza.wms.mediacache.model.IMediaCacheSource;
import com.wowza.wms.mediacache.model.MediaCache;
import com.wowza.wms.plugin.collection.serverlistener.ServerListenerMediaCachePreload;
import com.wowza.wms.server.IServer;

/*
 * This thread handles the files as listed in the targets.txt file and queues it for caching. It loops
 * through the list and manages open vs. active threads. It sends off
 * requests to the CacheLoaderThread for execution when necessary.
 */
public class QueueFilesThread implements Runnable
{
	private ArrayList<String> mediaCacheFiles;
	private String mediaCacheFilePath;
	private ThreadPoolExecutor eventRequestThreadPool;
	private String mediaCacheTargetFilePath;
	private int mediaCacheThreads;
	private String mediaCacheApplication;
	private String mediaCacheApplicationInstance;
	private String mediaCacheVhost;
	private String mediaCacheLoadType;
	private int mediaCacheLoadValue;
	private long mediaCacheWaitBeforeNextCheck;
	private String logPrefix = "MediaCacheQueueThread.";

	private ArrayList<String> getPreloadedVODFiles(String mediaCacheTargetFilePath)
	{

		ArrayList<String> mediaCacheFiles = new ArrayList<String>();

		FileInputStream in = null;
		BufferedReader br = null;
		try
		{
			in = new FileInputStream(mediaCacheTargetFilePath);
			br = new BufferedReader(new InputStreamReader(in));
			String cacheItem;

			while ((cacheItem = br.readLine()) != null)
			{
				cacheItem = cacheItem.trim();
				if (!cacheItem.startsWith("#") && cacheItem.length() > 0)
				{
					mediaCacheFiles.add(cacheItem);
				}
			}
		}
		catch (Exception e)
		{
			ServerListenerMediaCachePreload.error(this.logPrefix + "getPreloadedVODFiles [" + e.getMessage() + "]");
		}
		finally
		{
			try
			{
				if (br != null)
					br.close();
			}
			catch (IOException e)
			{
			}
			br = null;
			try
			{
				if (in != null)
					in.close();
			}
			catch (IOException e)
			{
			}
			in = null;
		}
		return mediaCacheFiles;
	}

	/*
	 * Constructor initializes the cached, but limited, thread pool along with the default parameters.
	 */
	public QueueFilesThread(String mediaCacheFilePath, IServer server, String mediaCacheTargetFilePath, int mediaCacheThreads, String mediaCacheApplication, String mediaCacheApplicationInstance, String mediaCacheVhost, String mediaCacheLoadType, int mediaCacheLoadValue,
			long mediaCacheWaitBeforeNextCheck)
	{

		this.mediaCacheTargetFilePath = mediaCacheTargetFilePath;
		this.mediaCacheThreads = mediaCacheThreads;
		this.mediaCacheApplication = mediaCacheApplication;
		this.mediaCacheApplicationInstance = mediaCacheApplicationInstance;
		this.mediaCacheLoadType = mediaCacheLoadType;
		this.mediaCacheLoadValue = mediaCacheLoadValue;
		this.mediaCacheVhost = mediaCacheVhost;
		this.mediaCacheFilePath = mediaCacheFilePath;
		this.mediaCacheWaitBeforeNextCheck = mediaCacheWaitBeforeNextCheck;

		eventRequestThreadPool = new ThreadPoolExecutor(this.mediaCacheThreads, // core size
				this.mediaCacheThreads, // max size
				60, // idle timeout
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		eventRequestThreadPool.allowCoreThreadTimeOut(true);
	}

	// cleanup thread
	public synchronized void quit()
	{
		try
		{
			eventRequestThreadPool.awaitTermination(30, TimeUnit.SECONDS);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isItemAlreadyInCache(String streamName)
	{
		IApplicationInstance appInstance = ServerListenerMediaCachePreload.getAppInstance(mediaCacheVhost, mediaCacheApplication, mediaCacheApplicationInstance);

		if (appInstance != null)
		{
			String[] cachePrefixArr = streamName.split("/");
			String cachePrefix = cachePrefixArr[0];
			if (cachePrefix.contains(":"))
			{
				String[] prefixArr = cachePrefix.split(":");
				cachePrefix = prefixArr[1];
			}

			String streamDirPath = "";
			if (cachePrefixArr.length > 1)
			{
				for (int i = 1; i < cachePrefixArr.length; i++)
				{
					streamDirPath += cachePrefixArr[i] + "/";
				}
				streamDirPath = streamDirPath.substring(0, streamDirPath.length() - 1);
			}
			try
			{
				MediaCache mediaCache = MediaCacheImpl.getMediaCache(appInstance);
				if (mediaCache instanceof MediaCache)
				{
					List<String> sources = mediaCache.getSourceNames();
					for (int i = 0; i < sources.size(); i++)
					{
						String sourceName = sources.get(i);

						IMediaCacheSource source = mediaCache.getSource(sourceName);
						String streamPathNoPrefix = source.getName() + "/" + streamDirPath;
						String prefix = ((MediaCacheSourceBasic)source).getPrefix();
						prefix = prefix.replace("/", "").trim();
						ServerListenerMediaCachePreload.debug(this.logPrefix + "prefix == cachePrefix: " + prefix + " == " + cachePrefix);
						if (prefix.equalsIgnoreCase(cachePrefix.trim()))
						{
							if (mediaCache.cacheItemExists(streamPathNoPrefix))
							{
								ServerListenerMediaCachePreload.info(this.logPrefix + " [mediaCache.cacheItemExists: " + streamPathNoPrefix + " TRUE]");
								return true;
							}
							else
							{

								ServerListenerMediaCachePreload.info(this.logPrefix + " [mediaCache.cacheItemExists: " + streamPathNoPrefix + " FALSE]");
							}
						}
					}
				}
			}
			catch (Exception ex)
			{
				ServerListenerMediaCachePreload.error(this.logPrefix + "***--->isItemalreadyincache for Exception " + ex.getMessage());

			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * Iterates through media files listed in the targets file
	 * Fills up given core count of thread pool.
	 * If the target file has a number listed after the resource it will use that as the block number or bytes size vs default in properties. Otherwise it will use default.
	 * -1 in that file indicates to preload the entire file. 
	 * A number greater or equal to 1 will read that many blocks.
	 */
	@Override
	public void run()
	{

		Date lastFileTimeChangedDate = null;
		while (true)
		{
			try
			{
				File file = new File(this.mediaCacheFilePath);
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				Date fileDateTime = sdf.parse(sdf.format(file.lastModified()));

				ServerListenerMediaCachePreload.info(this.logPrefix + " [Last file change: " + fileDateTime + " vs. " + lastFileTimeChangedDate + "]");

				if (lastFileTimeChangedDate == null || fileDateTime.compareTo(lastFileTimeChangedDate) > 0)
				{
					lastFileTimeChangedDate = fileDateTime;

					ArrayList<String> mediaCacheFiles = this.getPreloadedVODFiles(this.mediaCacheFilePath);

					if (mediaCacheFiles.size() > 0)
					{
						for (int i = 0; i < mediaCacheFiles.size(); i++)
						{
							int readBlocks = -2;
							String streamPath = mediaCacheFiles.get(i);
							String[] parts = streamPath.split(" ");
							if (parts.length >= 2)
							{
								streamPath = parts[0];
								readBlocks = Integer.parseInt(parts[1]);
							}

							ServerListenerMediaCachePreload.debug(this.logPrefix + " [streamPath: " + streamPath + "]");
							try
							{
								if (!this.isItemAlreadyInCache(streamPath))
								{
									eventRequestThreadPool.submit(new LoadFilesThread(streamPath, this.mediaCacheTargetFilePath, this.mediaCacheApplication, this.mediaCacheApplicationInstance, this.mediaCacheVhost, this.mediaCacheLoadType, this.mediaCacheLoadValue, readBlocks));
									ServerListenerMediaCachePreload.info(this.logPrefix + " [Added cache submission: " + streamPath + " : read blocks: " + readBlocks + "]");
								}

							}
							catch (Exception ex)
							{
								ServerListenerMediaCachePreload.error(this.logPrefix + " [Exception: " + ex.getMessage() + "]");
							}
						}
					}
					else
					{
						ServerListenerMediaCachePreload.info(this.logPrefix + " [no mediacache files to execute]");

					}
				}
			}
			catch (ParseException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
				// error in parsing date
			}

			try
			{
				Thread.sleep(this.mediaCacheWaitBeforeNextCheck);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}