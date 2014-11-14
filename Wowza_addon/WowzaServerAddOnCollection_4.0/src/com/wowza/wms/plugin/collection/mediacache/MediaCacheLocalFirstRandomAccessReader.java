/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.mediacache;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.wowza.io.DirectRandomAccessReader;
import com.wowza.io.IRandomAccessReader;
import com.wowza.io.ITrackRandomAccessReaderPerformance;
import com.wowza.util.IOPerformanceCounter;
import com.wowza.util.SystemUtils;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.stream.IMediaStream;
import com.wowza.wms.vhost.IVHost;

public class MediaCacheLocalFirstRandomAccessReader implements IRandomAccessReader, ITrackRandomAccessReaderPerformance
{
	public static final String CLASS_NAME = "MediaCacheLocalFirstRandomAccessReader";
	public static final String PROP_NAME_PREFIX = "mediaCacheLocalFirst";
	
	private IRandomAccessReader randomReader = null;
	private String mediacacheRandomAccessReaderClassName = "com.wowza.wms.mediacache.impl.MediaCacheRandomAccessReaderVODEdge"; 

	private IRandomAccessReader getMediaCacheRandomAccessReader(IApplicationInstance appInstance)
	{
		mediacacheRandomAccessReaderClassName = appInstance.getProperties().getPropertyStr(PROP_NAME_PREFIX + "RandomAccessReaderClassName", mediacacheRandomAccessReaderClassName);
		try
		{
			Class readerClass = Class.forName(mediacacheRandomAccessReaderClassName);

			if (readerClass != null)
				return (IRandomAccessReader)readerClass.newInstance();
		}
		catch (Exception e)
		{
			WMSLoggerFactory.getLoggerObj(appInstance).error(CLASS_NAME + ".init() Cannot load mediacacheRandomAccessReaderClass :" + this.mediacacheRandomAccessReaderClassName);
		}
		return null;
	}

	public void init(IApplicationInstance appInstance, IMediaStream stream, String basePath, String mediaName, String mediaExtension)
	{
		Map<String, String> envMap = new HashMap<String, String>();
		IVHost vhost = appInstance.getVHost();
		envMap.put("com.wowza.wms.context.VHost", vhost.getName());
		envMap.put("com.wowza.wms.context.Application", appInstance.getApplication().getName());
		envMap.put("com.wowza.wms.context.ApplicationInstance", appInstance.getName());

			File file = stream.getStreamFileForRead(mediaName, mediaExtension, stream.getQueryStr());
			if (file.exists())
			{
				randomReader = new DirectRandomAccessReader();
			}
			else
			{
				String mediaCachePath = SystemUtils.expandEnvironmentVariables(appInstance.getProperties().getPropertyStr(PROP_NAME_PREFIX + "MediaCachePath", ""), envMap);
				if (!mediaName.startsWith(mediaCachePath))
				{
					mediaName = (mediaCachePath.endsWith("/") ? mediaCachePath : mediaCachePath + "/") + mediaName;
				}
				randomReader = getMediaCacheRandomAccessReader(appInstance); //new MediaCacheRandomAccessReader();
			}
			
			// if randomReader is null an exception will be thrown and caught by the RandomAccessReaderFactory.createInstance method.
			randomReader.init(appInstance, stream, basePath, mediaName, mediaExtension);
	}

	public void setStreamIOTracker(IOPerformanceCounter ioPerforamnceCounter)
	{
		if (randomReader instanceof ITrackRandomAccessReaderPerformance)
			((ITrackRandomAccessReaderPerformance)randomReader).setStreamIOTracker(ioPerforamnceCounter);
	}

	public void setClientIOTracker(IOPerformanceCounter ioPerforamnceCounter)
	{
		if (randomReader instanceof ITrackRandomAccessReaderPerformance)
			((ITrackRandomAccessReaderPerformance)randomReader).setClientIOTracker(ioPerforamnceCounter);
	}

	public void open() throws IOException
	{
		randomReader.open();
	}

	public void close() throws IOException
	{
		randomReader.close();
	}

	public boolean isOpen()
	{
		return randomReader.isOpen();
	}

	public long getFilePointer()
	{
		return randomReader.getFilePointer();
	}

	public void seek(long pos)
	{
		randomReader.seek(pos);
	}

	public int read(byte[] buf, int off, int size)
	{
		return randomReader.read(buf, off, size);
	}

	public int getDirecton()
	{
		return randomReader.getDirecton();
	}

	public void setDirecton(int directon)
	{
		randomReader.setDirecton(directon);
	}

	public String getBasePath()
	{
		return randomReader.getBasePath();
	}

	public String getMediaName()
	{
		return randomReader.getMediaName();
	}

	public String getMediaExtension()
	{
		return randomReader.getMediaExtension();
	}

	public boolean exists()
	{
		return randomReader.exists();
	}

	public long lastModified()
	{
		return randomReader.lastModified();
	}

	public long length()
	{
		return randomReader.length();
	}

	public String getPath()
	{
		return randomReader.getPath();
	}
}
