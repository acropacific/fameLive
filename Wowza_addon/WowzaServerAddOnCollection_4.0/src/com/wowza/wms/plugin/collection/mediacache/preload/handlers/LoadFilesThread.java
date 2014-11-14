/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.mediacache.preload.handlers;

import java.util.List;

import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.mediacache.impl.MediaCacheImpl;
import com.wowza.wms.mediacache.impl.MediaCacheRandomAccessReader;
import com.wowza.wms.mediacache.impl.MediaCacheSourceBasic;
import com.wowza.wms.mediacache.model.IMediaCacheItem;
import com.wowza.wms.mediacache.model.IMediaCacheReader;
import com.wowza.wms.mediacache.model.IMediaCacheSource;
import com.wowza.wms.mediacache.model.MediaCache;
import com.wowza.wms.mediacache.model.MediaCacheItemInfo;
import com.wowza.wms.plugin.collection.serverlistener.ServerListenerMediaCachePreload;
import com.wowza.wms.stream.IMediaStream;

/*
 * This class handles the spawning of new threads per Stream preload
 * request. Each request will be spawned through a cached thread pool such
 * that one preloading cannot interfere with another. More directly, it will
 * try to load each cache item in parallel.
 */
public class LoadFilesThread implements Runnable
{
	private String logPrefix = "LoadFilesThread.";
	private String streamPath;
	private String mediaCacheApplication;
	private String mediaCacheApplicationInstance;
	private String mediaCacheVhost;
	private String mediaCacheLoadType;
	private int mediaCacheLoadValue;
	private int fileBlockRead = -2;

	public LoadFilesThread(String streamPath, String mediaCacheTargetFilePath, String mediaCacheApplication, String mediaCacheApplicationInstance, String mediaCacheVhost, String mediaCacheLoadType, int mediaCacheLoadValue, int fileBlockReads)
	{

		this.streamPath = streamPath;
		this.mediaCacheApplication = mediaCacheApplication;
		this.mediaCacheApplicationInstance = mediaCacheApplicationInstance;
		this.mediaCacheLoadType = mediaCacheLoadType;
		this.mediaCacheLoadValue = mediaCacheLoadValue;
		this.mediaCacheVhost = mediaCacheVhost;
		this.fileBlockRead = fileBlockReads;
	}

	/*
	 * Based on the given streamPath, find the matching instance in the media cache.
	 * Then calculate the number of blocks to preload
	 * Init the preloading process.
	 */
	private void playStream(IApplicationInstance appInstance)
	{
		String[] cachePrefixArr = this.streamPath.split("/");
		String cachePrefix = cachePrefixArr[0];

		String mediaType = "mp4";
		if (cachePrefix.contains(":"))
		{
			String[] prefixArr = cachePrefix.split(":");
			cachePrefix = prefixArr[1];
			mediaType = prefixArr[0];
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
		ServerListenerMediaCachePreload.debug(this.logPrefix + " [init preloading of " + appInstance.getName() + "/" + this.streamPath + "]");

		int block = 0;
		MediaCache mediaCache = MediaCacheImpl.getMediaCache(appInstance);
		if (mediaCache instanceof MediaCache)
		{
			List<String> sources = mediaCache.getSourceNames();
			for (int i = 0; i < sources.size(); i++)
			{
				String sourceName = sources.get(i);

				IMediaCacheSource source = mediaCache.getSource(sourceName);

				String streamPathNoPrefix = source.getName() + "/" + streamDirPath;

				int defaultBlockSize = source.getDefaultBlockSize();

				String prefix = ((MediaCacheSourceBasic)source).getPrefix();
				prefix = prefix.replace("/", "").trim();
				ServerListenerMediaCachePreload.debug(this.logPrefix + " [prefix == cachePrefix: " + prefix + " == " + cachePrefix + "]");
				if (prefix.equalsIgnoreCase(cachePrefix.trim()))
				{
					if (defaultBlockSize > 0)
					{
						if (this.mediaCacheLoadType.equalsIgnoreCase("bytes"))
						{
							ServerListenerMediaCachePreload.info(this.logPrefix + " [bytes:" + this.mediaCacheLoadValue + "]");
							if (this.fileBlockRead == -1)
							{
								block = -1;
							}
							else if (defaultBlockSize > this.mediaCacheLoadValue)
							{
								block = this.fileBlockRead;
								if (this.fileBlockRead <= -2)
								{
									block = defaultBlockSize;
								}
							}
							else
							{
								double roundedBlockSize = Math.ceil(this.fileBlockRead / defaultBlockSize);
								if (this.fileBlockRead <= -2)
								{
									roundedBlockSize = Math.ceil(this.mediaCacheLoadValue / defaultBlockSize);
								}
								block = (int)roundedBlockSize;
							}
						}
						else
						{
							ServerListenerMediaCachePreload.debug(this.logPrefix + " [source.getDefaultBlockSize(): " + source.getDefaultBlockSize() + " * " + this.mediaCacheLoadValue + "]");

							block = this.fileBlockRead;
							if (this.fileBlockRead <= -2)
							{
								block = this.mediaCacheLoadValue;
							}
						}
					}

					if (this.loadCache(appInstance, null, source.getSourcePath(), streamPathNoPrefix, mediaType, block, defaultBlockSize))
					{
						ServerListenerMediaCachePreload.debug(this.logPrefix + " [LOAD***loadCache: " + streamPathNoPrefix + "]");
					}
					else
					{
						ServerListenerMediaCachePreload.debug(this.logPrefix + " [NO loadCache: " + streamPathNoPrefix + "]");
					}
					break;
				}
			}
		}
		else
		{
			ServerListenerMediaCachePreload.info(this.logPrefix + "mediacache not available...");
		}
	}

	/*
	 * Use mediacache object to obtain the appropriate reference and read in the file and cache.
	 */
	private boolean loadCache(IApplicationInstance appInstance, IMediaStream stream, String basePath, String mediaName, String mediaExtension, int blocksToPreload, int defaultBlockSize)
	{
		try
		{
			MediaCache mediaCache = MediaCacheImpl.getMediaCache(appInstance);
			String mediaCacheMediaName = mediaCache.getMediaNameFromContext(appInstance, stream, basePath, mediaName, mediaExtension);

			IMediaCacheReader mediaCacheReader = mediaCache.acquireReader(mediaCacheMediaName);
			IMediaCacheItem mediaCacheItem = mediaCacheReader.getMediaCacheItem();

			ServerListenerMediaCachePreload.info(this.logPrefix + " [preloading cache:" + mediaCacheMediaName + "]");

			MediaCacheItemInfo info = mediaCacheItem.getInfo();
			if (blocksToPreload == -1)
			{
				mediaCacheItem.preload();
			}
			else if (blocksToPreload > 0)
			{
				long totalBlockSizeToReadAhead = defaultBlockSize * blocksToPreload;

				MediaCacheRandomAccessReader rar = new MediaCacheRandomAccessReader();
				rar.init(appInstance, stream, basePath, mediaName, mediaExtension);
				if (rar.isOpen())
				{
					rar.close();
				}
				ServerListenerMediaCachePreload.info(" [MediaCacheRandomAccessReader.opening with blocks to preload: " + totalBlockSizeToReadAhead +"]");
				rar.open();

				for (int i = 0; i < blocksToPreload; i++)
				{
					byte[] buffer = new byte[(int)defaultBlockSize];
					rar.read(buffer, 0, buffer.length);
					long filePointer = rar.getFilePointer();
					ServerListenerMediaCachePreload.info(" [MediaCacheRandomAccessReader::opening with blocks to preload[" + i + "]: offset: " + filePointer + "]");
				}
			}
			ServerListenerMediaCachePreload.info(this.logPrefix + "mediaCacheItem.getInfo() mediaName[" + mediaName + "]: info.getLength(): " + info.getLength() + ": info.isActive(): " + info.isActive());

			return mediaCache.releaseReader(mediaCacheReader);
		}
		catch (Exception ex)
		{
			ServerListenerMediaCachePreload.error(this.logPrefix + "Exception:" + ex.getMessage());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * Setup default application information used for preloading.
	 * Call playstream to prepare the stream for preloading.
	 */
	@Override
	public void run()
	{
		IApplicationInstance appInstance = ServerListenerMediaCachePreload.getAppInstance(mediaCacheVhost, mediaCacheApplication, mediaCacheApplicationInstance);
		if (appInstance != null)
		{
			this.playStream(appInstance);
		}
		else
		{
			ServerListenerMediaCachePreload.error(this.logPrefix + " [ERROR: app instance is not valid]");
		}
	}
}