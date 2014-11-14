/**
 * Wowza Streaming Engine Software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.module;

import java.util.*;

import com.wowza.wms.amf.*;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.client.*;
import com.wowza.wms.module.*;
import com.wowza.wms.request.*;
import com.wowza.wms.stream.IMediaStream;
import com.wowza.wms.stream.publish.*;

public class ModuleStreamControl extends ModuleBase {
	
	public void getStreamNames(IClient client, RequestFunction function, AMFDataList params) {
            getLogger().info("getFiles");
            
            AMFDataObj publishedStreams = new AMFDataObj();
            IApplicationInstance app = client.getAppInstance();
            
            List<Publisher> publishers = client.getAppInstance().getPublishers();
            Iterator<Publisher> iterp = publishers.iterator();
            iterp = publishers.iterator();
			while(iterp.hasNext())
			{
				Publisher publisher =  iterp.next();
				getLogger().info("Stream Name: " + publisher.getStream().getName());
				
			}
            
          

            List<String> streams = app.getStreams().getPublishStreamNames();
            Iterator<String> iter = streams.iterator();
				while(iter.hasNext())
				{
					String streamName =  iter.next();
					IMediaStream stream = app.getStreams().getStream(streamName);
					publishedStreams.put(streamName, streamName);
				}
            sendResult(client, params, publishedStreams);
    }
	
	public void openPlaylistOnStream(IClient client, RequestFunction function,
			AMFDataList params) {
					
		String streamName = getParamString(params, PARAM1);
		
		String playlistName = getParamString(params, PARAM2);
		
		Stream stream = (Stream)client.getAppInstance().getProperties().getProperty(streamName);
		
		Playlist playlist = (Playlist)client.getAppInstance().getProperties().getProperty(playlistName);
		
		playlist.open(stream);
	
	}
	public void addItemToPlaylist(IClient client, RequestFunction function,
			AMFDataList params) {

		AMFDataObj obj = getParamObj(params, PARAM1);
		
		Playlist playlist = (Playlist)client.getAppInstance().getProperties().getProperty(obj.getString("playListName"));
		
		playlist.addItem(obj.getString("itemName"), obj.getInt("itemStart"), obj.getInt("itemDuration"));	
	}
	
	public void removeItemFromPlaylist(IClient client, RequestFunction function,
			AMFDataList params) {

			String streamName = getParamString(params, PARAM1);
		
			Stream stream = (Stream)client.getAppInstance().getProperties().getProperty(streamName);
			
			Boolean success = stream.removeFromPlaylist("Extremists.flv");
	}
	
	
	public void playNextPlaylistItem(IClient client, RequestFunction function,
			AMFDataList params) {

		String streamName = getParamString(params, PARAM1);
		
		Stream stream = (Stream)client.getAppInstance().getProperties().getProperty(streamName);
		
		stream.next();
		
	}
	
	public void addNewStream(IClient client, RequestFunction function,
			AMFDataList params) {
		
		String streamName = getParamString(params, PARAM1);
		
		Stream stream = Stream.createInstance(client.getAppInstance(), streamName);
		
		client.getAppInstance().getProperties().put(streamName, stream);
	
	}
	
	public void addNewPlaylist(IClient client, RequestFunction function,
			AMFDataList params) {
		
		String playListName = getParamString(params, PARAM1);
		
		Playlist playlist = new Playlist(playListName);
		
		playlist.setRepeat(true);
		
		client.getAppInstance().getProperties().put(playListName, playlist);
		
	}
	public void stopStream(IClient client, RequestFunction function,
			AMFDataList params) {
		String streamName = getParamString(params, PARAM1);

		Stream stream = (Stream)client.getAppInstance().getProperties().remove(streamName);
		if(stream != null)
		stream.close();
	}
}
