package com.wowza.wms.plugin.bwcheck;

import java.util.*;

import com.wowza.util.*;
import com.wowza.wms.amf.*;
import com.wowza.wms.application.*;
import com.wowza.wms.client.*;
import com.wowza.wms.logging.*;
import com.wowza.wms.module.*;
import com.wowza.wms.request.*;

/**
 * <p>New and improved bandwidth checker.</p>
 * @author Wowza Media Systems (Roger Littin)
 *
 */
public class ModuleBWCheck2 extends ModuleBase
{
	private int bwCheckPayloadSize = 1800;
	private int bwCheckMaxLoops = 6;
	private long bwCheckMaxTime = 1000;
	
	public void onAppStart(IApplicationInstance appInstance)
	{
		WMSProperties props = appInstance.getProperties();
		if (props != null)
		{
			this.bwCheckPayloadSize = props.getPropertyInt("bwCheckPayloadSize", bwCheckPayloadSize);
			this.bwCheckMaxLoops = props.getPropertyInt("bwCheckMaxLoops", bwCheckMaxLoops);
			this.bwCheckMaxTime = props.getPropertyLong("bwCheckMaxTime", bwCheckMaxTime);
		}
		
		getLogger().info("ModuleBWCheck2.onAppStart: bwCheckPayloadSize: "+this.bwCheckPayloadSize );
		getLogger().info("ModuleBWCheck2.onAppStart: bwCheckMaxLoops: "+this.bwCheckMaxLoops );
		getLogger().info("ModuleBWCheck2.onAppStart: bwCheckMaxTime: "+this.bwCheckMaxTime );
	}
	
	public void onConnect(IClient client, RequestFunction function, AMFDataList params)
	{
		boolean autoSenseBW = params.getBoolean(PARAM1);

		if (autoSenseBW)
			calculateClientBw(client);
		else
			client.call("onBWDone", null);
	}

	public void checkBandwidth(IClient client, RequestFunction function, AMFDataList params)
	{
		calculateClientBw(client);
	}

	private void calculateClientBw(IClient client)
	{

		final AMFDataArray payload = new AMFDataArray();
		for (int i = 0; i < bwCheckPayloadSize; i++)
		{
			payload.add(new AMFDataItem((double) Math.random()));
		}

		class ResultObj implements IModuleCallResult
		{
			IOPerformanceCounter totalStats;
			long bytesOutStart;

			double latency = 0;
			int cumLatency = 1;
			int count = 0;
			int sent = 0;
			double kbitDown = 0;
			double deltaDown = 0;
			double deltaTime = 0;
			long start = 0;

			List<Long> pakSent = new ArrayList<Long>();
			List<Long> pakRecv = new ArrayList<Long>();

			public ResultObj(IClient client)
			{
				totalStats = client.getTotalIOPerformanceCounter();

			}

			public void onResult(IClient client, RequestFunction function, AMFDataList params)
			{
				Long now = new Long(System.currentTimeMillis());

				pakRecv.add(now);

				if (count == 0)
				{
					latency = now - pakSent.get(0);
					latency = Math.min(latency, 800);
					//System.out.println("latency: " + latency);
					bytesOutStart = totalStats.getMessagesOutBytes();
					start = System.currentTimeMillis();
				}
				Long timePassed = (now - start);
				count++;

				if ((count >= 1 && count <= bwCheckMaxLoops) && (timePassed < bwCheckMaxTime))
				{
					pakSent.add(sent++, now);
					cumLatency++;
					client.call("onBWCheck", this, payload);
				}

				// Time elapsed now do the calcs
				else if (sent == count)
				{
					// see if we need to normalize latency
					if (latency >= 100)
					{
						// make sure satelite and modem is detected properly
						if (pakRecv.get(1) - pakRecv.get(0) > 1000)
						{
							latency = 100;
						}
					}

					// tidy up
					// and compute bandwidth
					deltaDown = (double) ((totalStats.getMessagesOutBytes() - bytesOutStart) * 8) / 1000; // bytes
					// to
					// kbits
					deltaTime = (double) ((now - start) - (latency * cumLatency)) / 1000;

					if (deltaTime <= 0)
					{
						deltaTime = (double) (now - start) / 1000;
					}
					kbitDown = Math.round(deltaDown / deltaTime); // kbits / sec

					WMSLoggerFactory.getLogger(null).info("onBWDone: " + this.kbitDown);
					client.call("onBWDone", null, this.kbitDown, this.deltaDown, this.deltaTime, this.latency);
				}
			}
		}

		class FirstResult implements IModuleCallResult
		{

			public void onResult(IClient client, RequestFunction function, AMFDataList params)
			{
				ResultObj res = new ResultObj(client);
				long now = System.currentTimeMillis();
				res.pakSent.add(res.sent++, now);
				client.call("onBWCheck", res, "");
			}
		}
		client.call("onBwCheck", new FirstResult(), payload); // First call to
		// client is
		// ignored.
	}

	public void onClientBWCheck(IClient client, RequestFunction function, AMFDataList params)
	{
		getLogger().info("onClientBWCheck");
		AMFDataObj statValues = new AMFDataObj();

		IOPerformanceCounter stats = client.getTotalIOPerformanceCounter();

		statValues.put("cOutBytes", new AMFDataItem(stats.getMessagesInBytes()));
		statValues.put("cInBytes", new AMFDataItem(stats.getMessagesOutBytes()));
		statValues.put("time", new AMFDataItem(params.getLong(PARAM1)));

		sendResult(client, params, statValues);
	}

}