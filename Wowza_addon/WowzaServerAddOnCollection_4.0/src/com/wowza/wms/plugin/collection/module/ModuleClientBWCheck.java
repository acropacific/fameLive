/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.module;

import com.wowza.util.IOPerformanceCounter;
import com.wowza.wms.amf.AMFDataItem;
import com.wowza.wms.amf.AMFDataList;
import com.wowza.wms.amf.AMFDataObj;
import com.wowza.wms.client.IClient;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.request.RequestFunction;

public class ModuleClientBWCheck extends ModuleBase
{

	static public void onClientBWCheck(IClient client, RequestFunction function, AMFDataList params)
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