/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.avmix;

public class StreamInfo
{
	private String outputName;
	private String videoName;
	private String audioName;
	private long sortDelay = 10000l;
	private boolean useOriginalTimeCodes = false;
	
	public String getOutputName()
	{
		return outputName;
	}
	public void setOutputName(String outputName)
	{
		this.outputName = outputName;
	}
	public String getVideoName()
	{
		return videoName;
	}
	public void setVideoName(String videoName)
	{
		this.videoName = videoName;
	}
	public String getAudioName()
	{
		return audioName;
	}
	public void setAudioName(String audioName)
	{
		this.audioName = audioName;
	}
	public long getSortDelay()
	{
		return sortDelay;
	}
	public void setSortDelay(long sortDelay)
	{
		this.sortDelay = sortDelay;
	}
	public boolean isUseOriginalTimeCodes()
	{
		return useOriginalTimeCodes;
	}
	public void setUseOriginalTimeCodes(boolean useOriginalTimeCodes)
	{
		this.useOriginalTimeCodes = useOriginalTimeCodes;
	}
}
