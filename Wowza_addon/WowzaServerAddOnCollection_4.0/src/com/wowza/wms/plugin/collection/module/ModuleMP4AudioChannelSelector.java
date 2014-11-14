/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.module;

import java.util.Map;

import com.wowza.util.HTTPUtils;
import com.wowza.wms.amf.AMFDataList;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.client.IClient;
import com.wowza.wms.httpstreamer.model.IHTTPStreamerSession;
import com.wowza.wms.mediareader.h264.MediaReaderH264;
import com.wowza.wms.mediareader.h264.atom.QTAtomtrak;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.request.RequestFunction;
import com.wowza.wms.rtp.model.RTPSession;
import com.wowza.wms.stream.IMediaReader;
import com.wowza.wms.stream.IMediaReaderActionNotify;
import com.wowza.wms.stream.IMediaStream;

public class ModuleMP4AudioChannelSelector extends ModuleBase
{
	public static final String PROPERTY_AUDIOINDEX = "audioindex";
	public static final String PROPERTY_VIDEOINDEX = "videoindex";
	public static final String PROPERTY_DATAINDEX = "dataindex";
	public static final String[] PROPERTY_INDEXES = { PROPERTY_AUDIOINDEX, PROPERTY_VIDEOINDEX, PROPERTY_DATAINDEX };

	class MediaReaderListener implements IMediaReaderActionNotify
	{
		public void onMediaReaderCreate(IMediaReader mediaReader)
		{
			getLogger().info("ModuleMediaReaderNotify#MediaReaderListener.onMediaReaderCreate");
		}

		public void onMediaReaderInit(IMediaReader mediaReader, IMediaStream stream)
		{
			getLogger().info("ModuleMediaReaderNotify#MediaReaderListener.onMediaReaderInit: " + stream.getName());
		}

		public void onMediaReaderOpen(IMediaReader mediaReader, IMediaStream stream)
		{
			getLogger().info("ModuleMediaReaderNotify#MediaReaderListener.onMediaReaderOpen: " + stream.getName());

			while (true)
			{
				IClient RTMPClient = null;
				RTPSession RTSPClient = null;
				IHTTPStreamerSession HTTPClient = null;

				try
				{
					RTMPClient = stream.getClient();
				}
				catch (Exception client)
				{
				}
				try
				{
					RTSPClient = stream.getRTPStream().getSession();
				}
				catch (Exception client)
				{
				}
				try
				{
					HTTPClient = stream.getHTTPStreamerSession();
				}
				catch (Exception client)
				{
				}

				if (RTMPClient == null && RTSPClient == null && HTTPClient == null)
					break;

				int audioindex = -1;
				int videoIndex = -1;
				int dataIndex = -1;

				if (RTMPClient != null)
				{

					Integer audioindexObj = (Integer)RTMPClient.getProperties().getProperty(PROPERTY_AUDIOINDEX);
					Integer videoIndexObj = (Integer)RTMPClient.getProperties().getProperty(PROPERTY_VIDEOINDEX);
					Integer dataIndexObj = (Integer)RTMPClient.getProperties().getProperty(PROPERTY_DATAINDEX);

					if (audioindexObj != null)
						audioindex = audioindexObj.intValue();
					if (videoIndexObj != null)
						videoIndex = videoIndexObj.intValue();
					if (dataIndexObj != null)
						dataIndex = dataIndexObj.intValue();
				}

				if (RTSPClient != null)
				{

					Integer audioindexObj = (Integer)RTSPClient.getProperties().getProperty(PROPERTY_AUDIOINDEX);
					Integer videoIndexObj = (Integer)RTSPClient.getProperties().getProperty(PROPERTY_VIDEOINDEX);
					Integer dataIndexObj = (Integer)RTSPClient.getProperties().getProperty(PROPERTY_DATAINDEX);

					if (audioindexObj != null)
						audioindex = audioindexObj.intValue();
					if (videoIndexObj != null)
						videoIndex = videoIndexObj.intValue();
					if (dataIndexObj != null)
						dataIndex = dataIndexObj.intValue();
				}

				if (HTTPClient != null)
				{

					Integer audioindexObj = (Integer)HTTPClient.getProperties().getProperty(PROPERTY_AUDIOINDEX);
					Integer videoIndexObj = (Integer)HTTPClient.getProperties().getProperty(PROPERTY_VIDEOINDEX);
					Integer dataIndexObj = (Integer)HTTPClient.getProperties().getProperty(PROPERTY_DATAINDEX);

					if (audioindexObj != null)
						audioindex = audioindexObj.intValue();
					if (videoIndexObj != null)
						videoIndex = videoIndexObj.intValue();
					if (dataIndexObj != null)
						dataIndex = dataIndexObj.intValue();
				}

				if (mediaReader instanceof MediaReaderH264)
				{
					MediaReaderH264 mediaReaderH264 = (MediaReaderH264)mediaReader;

					int audioTrackCount = mediaReaderH264.getTrackCountAudio();
					for (int i = 0; i < audioTrackCount; i++)
					{
						String langStr = mediaReaderH264.getTrackLanguageAudio(i);
						long trackId = mediaReaderH264.getTrackAudioTrackId(i);
						QTAtomtrak trackAtom = mediaReaderH264.getTrackAudioAtom(i);
						getLogger().info("  audio[" + i + "]: trackId:" + trackId + " lang:" + langStr + " more:" + trackAtom.getTkhdAtom().toString());
					}

					int videoTrackCount = mediaReaderH264.getTrackCountVideo();
					for (int i = 0; i < videoTrackCount; i++)
					{
						long trackId = mediaReaderH264.getTrackVideoTrackId(i);
						long trackWidth = mediaReaderH264.getTrackVideoWidth(i);
						long trackHeight = mediaReaderH264.getTrackVideoHeight(i);
						QTAtomtrak trackAtom = mediaReaderH264.getTrackVideoAtom(i);
						getLogger().info("  video[" + i + "]: trackId:" + trackId + " width:" + trackWidth + " height:" + trackHeight + " more:" + trackAtom.getTkhdAtom().toString());
					}

					int dataTrackCount = mediaReaderH264.getTrackCountData();
					for (int i = 0; i < dataTrackCount; i++)
					{
						String langStr = mediaReaderH264.getTrackLanguageData(i);
						long trackId = mediaReaderH264.getTrackDataTrackId(i);
						QTAtomtrak trackAtom = mediaReaderH264.getTrackDataAtom(i);
						getLogger().info("  data[" + i + "]: trackId:" + trackId + " lang:" + langStr + " more:" + trackAtom.getTkhdAtom().toString());
					}

					if (audioindex >= 0)
					{
						getLogger().info("  setTrackIndexAudio: " + audioindex);
						mediaReaderH264.setTrackIndexAudio(audioindex);
					}
					if (videoIndex >= 0)
					{
						getLogger().info("  setTrackIndexVideo: " + videoIndex);
						mediaReaderH264.setTrackIndexVideo(videoIndex);
					}
					if (dataIndex >= 0)
					{
						getLogger().info("  setTrackIndexData: " + dataIndex);
						mediaReaderH264.setTrackIndexData(dataIndex);
					}
				}

				break;
			}
		}

		public void onMediaReaderExtractMetaData(IMediaReader mediaReader, IMediaStream stream)
		{
			getLogger().info("ModuleMediaReaderNotify#MediaReaderListener.onMediaReaderExtractMetaData: " + stream.getName());
		}

		public void onMediaReaderClose(IMediaReader mediaReader, IMediaStream stream)
		{
			getLogger().info("ModuleMediaReaderNotify#MediaReaderListener.onMediaReaderClose: " + stream.getName());
		}
	}

	public void onAppStart(IApplicationInstance appInstance)
	{
		appInstance.addMediaReaderListener(new MediaReaderListener());
	}

	public void play(IClient client, RequestFunction function, AMFDataList params)
	{
		String streamName = params.getString(PARAM1);

		getLogger().info("ModuleMediaReaderNotify.play: " + streamName);

		if (streamName != null)
		{
			int qindex = streamName.indexOf("?");
			if (qindex >= 0)
			{
				String queryStr = streamName.substring(qindex + 1);
				Map<String, String> queryParams = HTTPUtils.splitQueryStr(queryStr);

				for (int i = 0; i < PROPERTY_INDEXES.length; i++)
				{
					String indexStr = PROPERTY_INDEXES[i];
					if (queryParams.containsKey(indexStr))
					{
						int index = -1;
						try
						{
							index = Integer.parseInt(queryParams.get(indexStr));
						}
						catch (Exception e)
						{
						}
						if (index >= 0)
						{
							client.getProperties().setProperty(indexStr, new Integer(index));
							getLogger().info("  " + indexStr + ": " + index);
						}
					}
				}
			}
		}

		invokePrevious(client, function, params);
	}

	public void onHTTPSessionCreate(IHTTPStreamerSession httpSession)
	{
		String streamName = httpSession.getUri();
		String queryStr = httpSession.getQueryStr();
		getLogger().info("ModuleMediaReaderNotify.onHTTPSessionCreate: " + streamName + " queryStr:" + queryStr);
		Map<String, String> queryParams = HTTPUtils.splitQueryStr(queryStr);

		for (int i = 0; i < PROPERTY_INDEXES.length; i++)
		{
			String indexStr = PROPERTY_INDEXES[i];
			if (queryParams.containsKey(indexStr))
			{
				int index = -1;
				try
				{
					index = Integer.parseInt(queryParams.get(indexStr));
				}
				catch (Exception e)
				{
				}
				if (index >= 0)
				{
					httpSession.getProperties().setProperty(indexStr, new Integer(index));
					getLogger().info("  " + indexStr + ": " + index);
				}
			}
		}
	}

	public void onRTPSessionCreate(RTPSession rtpSession)
	{
		String streamName = rtpSession.getUri();
		String queryStr = rtpSession.getQueryStr();
		getLogger().info("ModuleMediaReaderNotify.onRTPSessionCreate: " + streamName + " queryStr:" + queryStr);
		Map<String, String> queryParams = HTTPUtils.splitQueryStr(queryStr);

		for (int i = 0; i < PROPERTY_INDEXES.length; i++)
		{
			String indexStr = PROPERTY_INDEXES[i];
			if (queryParams.containsKey(indexStr))
			{
				int index = -1;
				try
				{
					index = Integer.parseInt(queryParams.get(indexStr));
				}
				catch (Exception e)
				{
				}
				if (index >= 0)
				{
					rtpSession.getProperties().setProperty(indexStr, new Integer(index));
					getLogger().info("  " + indexStr + ": " + index);
				}
			}
		}
	}

}
