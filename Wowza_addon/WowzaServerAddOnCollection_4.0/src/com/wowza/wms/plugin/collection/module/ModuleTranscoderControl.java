/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.module;

import com.wowza.util.StringUtils;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.logging.WMSLogger;
import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.stream.IMediaStream;
import com.wowza.wms.stream.livetranscoder.ILiveStreamTranscoderControl;

public class ModuleTranscoderControl extends ModuleBase
{

	private class TranscoderControl implements ILiveStreamTranscoderControl
	{

		public boolean isLiveStreamTranscode(String transcoder, IMediaStream stream)
		{
			if (stream.isTranscodeResult())
			{
				return false;
			}

			logger.info(MODULE_NAME + ".isLiveStreamTranscode [" + transcoder + " : " + stream.getName() + " : " + names + "]", stream);
			if (names.equals("*"))
			{
				logger.info(MODULE_NAME + ".isLiveStreamTranscode [" + transcoder + " : " + stream.getName() + " :  names is wildcard. returning " + matchAllow + "]", stream);
				return matchAllow;
			}
			if (StringUtils.isEmpty(names))
			{
				logger.info(MODULE_NAME + ".isLiveStreamTranscode [" + transcoder + " : " + stream.getName() + " : names is empty. returning " + noMatchAllow + "]", stream);
				return noMatchAllow;
			}

			String[] namesArray = names.split(",");
			for (String name : namesArray)
			{
				if (stream.getName().matches(name.trim()))
				{
					logger.info(MODULE_NAME + ".isLiveStreamTranscode [" + transcoder + " : " + stream.getName() + " : match found : " + name.trim() + " : returning " + matchAllow + "]", stream);
					return matchAllow;
				}
			}
			logger.info(MODULE_NAME + ".isLiveStreamTranscode [" + transcoder + " : " + stream.getName() + " : No match found : returning " + noMatchAllow + "]", stream);
			return noMatchAllow;
		}
	}

	public static final String MODULE_NAME = "ModuleTranscoderControl";
	public static final String PROP_NAME_PREFIX = "transcoderControl";
	
	private WMSLogger logger;
	private String names = "*";
	private boolean matchAllow = true;
	private boolean noMatchAllow = false;

	public void onAppStart(IApplicationInstance appInstance)
	{
		logger = WMSLoggerFactory.getLoggerObj(appInstance);
		
		names = appInstance.getProperties().getPropertyStr(PROP_NAME_PREFIX + "Names", names);
		matchAllow = appInstance.getProperties().getPropertyBoolean(PROP_NAME_PREFIX + "MatchAllow", matchAllow);
		noMatchAllow = appInstance.getProperties().getPropertyBoolean(PROP_NAME_PREFIX + "NoMatchAllow", noMatchAllow);

		appInstance.setLiveStreamTranscoderControl(new TranscoderControl());
	}
}
