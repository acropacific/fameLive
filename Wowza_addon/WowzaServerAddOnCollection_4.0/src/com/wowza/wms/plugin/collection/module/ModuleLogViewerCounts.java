/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wowza.wms.amf.AMFData;
import com.wowza.wms.amf.AMFDataList;
import com.wowza.wms.amf.AMFDataMixedArray;
import com.wowza.wms.amf.AMFDataObj;
import com.wowza.wms.amf.AMFPacket;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.httpstreamer.model.IHTTPStreamerSession;
import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.logging.WMSLoggerIDs;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.stream.IMediaStream;
import com.wowza.wms.stream.IMediaStreamMetaDataProvider;
import com.wowza.wms.stream.MediaStreamMap;
import com.wowza.wms.vhost.VHostWorkerThread;

public class ModuleLogViewerCounts extends ModuleBase
{
	class MyLogger extends VHostWorkerThread
	{
		private boolean running = true;
		private boolean quit = false;
		private int logTime = 10000;
		private long lastLogTime = -1;
		private IApplicationInstance appInstance = null;

		public MyLogger(IApplicationInstance appInstance)
		{
			super(appInstance.getVHost());

			this.appInstance = appInstance;
		}

		public void quit()
		{
			this.quit = true;
		}

		private Map<String, String> getMetadataInfo(IMediaStream stream)
		{
			Map<String, String> ret = new HashMap<String, String>();
			try
			{
				IMediaStreamMetaDataProvider metaDataProvider = stream.getMetaDataProvider();

				while (true)
				{
					if (metaDataProvider == null)
						break;

					List metaDataList = new ArrayList();
					long firstTimecode = 0;
					AMFPacket packet = stream.getLastPacket();
					firstTimecode = packet == null ? 0 : packet.getAbsTimecode();
					metaDataProvider.onStreamStart(metaDataList, firstTimecode);

					if (metaDataList.size() <= 0)
						break;

					for (int i = 0; i < metaDataList.size(); i++)
					{

						AMFPacket metaPacket = (AMFPacket)metaDataList.get(i);
						AMFDataList dataList = new AMFDataList(metaPacket.getData());

						if (dataList.size() < 2)
							break;

						if (dataList.get(1).getType() == AMFData.DATA_TYPE_MIXED_ARRAY)
						{
							AMFDataMixedArray arr = (AMFDataMixedArray)dataList.get(1);
							Iterator<String> iter = arr.getKeys().iterator();
							while (iter.hasNext())
							{
								String key = iter.next();
								String value = arr.getString(key);
								if (value == null)
									continue;
								ret.put(key, value);
							}
						}
						else if (dataList.get(1).getType() == AMFData.DATA_TYPE_OBJECT)
						{
							AMFDataObj obj = (AMFDataObj)dataList.get(1);
							Iterator<String> iter = obj.getKeys().iterator();
							while (iter.hasNext())
							{
								String key = iter.next();
								String value = obj.getString(key);
								if (value == null)
									continue;
								ret.put(key, value);
							}
						}
					}

					break;
				}
			}
			catch (Exception e)
			{

			}

			return ret;
		}

		private int toCount(Integer intObj)
		{
			int ret = intObj == null ? 0 : intObj.intValue();
			return ret;
		}

		public void run()
		{
			while (true)
			{
				try
				{
					long currTime = System.currentTimeMillis();
					if (lastLogTime == -1)
						lastLogTime = currTime;

					if ((currTime - lastLogTime) > logTime)
					{
						MediaStreamMap streams = appInstance.getStreams();
						List<String> streamNames = streams.getPublishStreamNames();

						Map<String, Integer> flashCounts = appInstance.getPlayStreamCountsByName();
						Map<String, Integer> smoothCounts = appInstance.getHTTPStreamerSessionCountsByName(IHTTPStreamerSession.SESSIONPROTOCOL_SMOOTHSTREAMING);
						Map<String, Integer> cupertinoCounts = appInstance.getHTTPStreamerSessionCountsByName(IHTTPStreamerSession.SESSIONPROTOCOL_CUPERTINOSTREAMING);
						Map<String, Integer> rtspCounts = appInstance.getRTPSessionCountsByName();

						Iterator<String> iter = streamNames.iterator();
						while (iter.hasNext())
						{
							String streamName = iter.next();

							IMediaStream stream = streams.getStream(streamName);
							if (stream == null)
								continue;

							IMediaStreamMetaDataProvider metaDataProvider = stream.getMetaDataProvider();

							List<AMFPacket> metaData = new ArrayList<AMFPacket>();
							metaDataProvider.onStreamStart(metaData, 0);

							int rtmpCount = toCount(flashCounts.get(streamName));
							int cupertinoCount = toCount(cupertinoCounts.get(streamName));
							int smoothCount = toCount(smoothCounts.get(streamName));
							int rtspCount = toCount(rtspCounts.get(streamName));

							StringBuffer metaDataStr = new StringBuffer();

							int count = rtmpCount + cupertinoCount + smoothCount + rtspCount; //listeners.size();
							metaDataStr.append("viewers" + ": \"" + count + "\"");
							metaDataStr.append(", viewersRTMP" + ": \"" + rtmpCount + "\"");
							metaDataStr.append(", viewersCupertino" + ": \"" + cupertinoCount + "\"");
							metaDataStr.append(", viewersSmooth" + ": \"" + smoothCount + "\"");
							metaDataStr.append(", viewersRTSP" + ": \"" + rtspCount + "\"");

							Map<String, String> metaList = getMetadataInfo(stream);
							Iterator<String> iter2 = metaList.keySet().iterator();
							while (iter2.hasNext())
							{
								String key = iter2.next();
								String value = metaList.get(key);
								metaDataStr.append(", " + key + ": \"" + value + "\"");
							}

							String metaDataStrStr = metaDataStr.toString().replace("\n", "");

							WMSLoggerFactory.getLoggerObj(appInstance).info("{" + metaDataStrStr + "}", stream, WMSLoggerIDs.CAT_stream, "listeners", WMSLoggerIDs.STAT_general_successful, streamName);
						}

						lastLogTime = currTime;
					}

					sleep(100);

					if (quit)
					{
						running = false;
						break;
					}
				}
				catch (Exception e)
				{

				}
			}
		}

		public IApplicationInstance getAppInstance()
		{
			return appInstance;
		}

		public void setAppInstance(IApplicationInstance appInstance)
		{
			this.appInstance = appInstance;
		}
	}

	private MyLogger logger = null;

	public void onAppStart(IApplicationInstance appInstance)
	{
		this.logger = new MyLogger(appInstance);
		this.logger.setAppInstance(appInstance);
		this.logger.setName("ModuleLogViewerCounts.MyLogger");
		this.logger.setDaemon(true);
		this.logger.start();
	}

	public void onAppStop(IApplicationInstance appInstance)
	{
		if (this.logger != null)
			this.logger.quit();
		this.logger = null;
	}

}