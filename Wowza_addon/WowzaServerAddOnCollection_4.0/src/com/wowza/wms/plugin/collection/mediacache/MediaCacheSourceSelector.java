package com.wowza.wms.plugin.collection.mediacache;

import java.io.IOException;

import com.wowza.io.IRandomAccessReader;
import com.wowza.io.ITrackRandomAccessReaderPerformance;
import com.wowza.util.IOPerformanceCounter;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.mediacache.impl.MediaCacheRandomAccessReader;
import com.wowza.wms.stream.IMediaReader;
import com.wowza.wms.stream.IMediaStream;

public class MediaCacheSourceSelector implements IRandomAccessReader, ITrackRandomAccessReaderPerformance
{
	private IRandomAccessReader randomReader = null;
	private String prefixes = "http/";

	public void init(IApplicationInstance appInstance, IMediaStream stream, String basePath, String mediaName, String mediaExtension)
	{

		prefixes = appInstance.getProperties().getPropertyStr("mediaCacheSourceFilePrefixes", prefixes);
		randomReader = new MediaCacheRandomAccessReader();

		if (appInstance.getMediaReaderContentType(mediaExtension) != IMediaReader.CONTENTTYPE_MEDIALIST) // SMIL
		{
			for(String prefix : prefixes.split(","))
			{
				prefix = prefix.trim();
				int idx = mediaName.indexOf(prefix);
				if(idx >= 0)
				{
					mediaName = mediaName.substring(idx, mediaName.length());
					break;
				}
			}
			
		}
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
