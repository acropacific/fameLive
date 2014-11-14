/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.module;

import java.io.File;

import com.wowza.util.FileUtils;
import com.wowza.util.IFileProcess;
import com.wowza.wms.amf.AMFDataItem;
import com.wowza.wms.amf.AMFDataList;
import com.wowza.wms.amf.AMFDataMixedArray;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.client.IClient;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.request.RequestFunction;

public class ModuleVideoNameList extends ModuleBase
{
	public static final String MODULE_NAME = "ModuleVideoNameList";

	AMFDataMixedArray recordedMovies = new AMFDataMixedArray();
	String storageDir;

	class PutFile implements IFileProcess
	{
		public void onFile(File file)
		{
			String sizeSuffix = "";
			if (file.length() < 1024000)
			{
				sizeSuffix = "[" + Math.round((file.length() * .001)) + " kb]";
			}
			else
			{
				sizeSuffix = "[" + Math.round((file.length() * .000001)) + " MB]";
			}

			String fileName = file.getName();

			file.getAbsolutePath().replace("\\", "/");
			file.getParent().replace("\\", "/");
			file.getPath().replace("\\", "/");

			fileName = fileName.replace(storageDir, "");
			String fn = file.getName().toLowerCase();
			if (fn.indexOf(".m4v") > -1 || fn.indexOf(".mov") > -1 || fn.indexOf(".mp4") > -1 || fn.indexOf(".f4v") > -1)
			{
				fileName = "mp4:" + fileName;
			}

			if (fn.indexOf(".mp3") > -1)
				fileName = "mp3:" + fileName;

			if (fn.indexOf(".flv") > -1)
				fileName = "flv:" + fileName;
			
			if (file.length() > 0 && fileName.indexOf(".") > -1)
			{
				recordedMovies.put(fileName, new AMFDataItem(fileName.replaceAll("(\\.flv|\\.mp4|\\.mp3)", "") + " " + sizeSuffix));
				getLogger().info(MODULE_NAME + " fileName: " + fileName);
			}
		}
	}

	public void getVideoNames(IClient client, RequestFunction function, AMFDataList params)
	{
		getLogger().info(MODULE_NAME + " getFiles");
		storageDir = client.getAppInstance().getStreamStoragePath();
		recordedMovies = new AMFDataMixedArray();

		IApplicationInstance app = client.getAppInstance();

		PutFile putfile = new PutFile();
		FileUtils.traverseDirectory(new File(app.getStreamStoragePath().replace("_definst_", app.getName())), putfile);
		sendResult(client, params, recordedMovies);
	}
}
