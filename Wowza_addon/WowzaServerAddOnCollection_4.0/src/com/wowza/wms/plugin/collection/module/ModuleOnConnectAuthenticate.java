/**
 * Wowza server software and all components Copyright 2006 - 2014, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.collection.module;

// com.wowza.wms.plugin.collection.module.ModuleOnConnectAuthenticate

import java.io.*;
import java.util.*;

import com.wowza.util.*;
import com.wowza.wms.amf.*;
import com.wowza.wms.application.*;
import com.wowza.wms.authentication.*;
import com.wowza.wms.authentication.file.*;
import com.wowza.wms.client.*;
import com.wowza.wms.module.*;
import com.wowza.wms.request.*;
import com.wowza.wms.util.*;
import com.wowza.wms.vhost.*;

public class ModuleOnConnectAuthenticate extends ModuleBase
{ 
	public static final String AUTHPASSWORDFILEPATH = "${com.wowza.wms.context.VHostConfigHome}/conf/connect.password";
	private File passwordFile = null;
	private String usernamePasswordProviderClass = null;

	public void onAppStart(IApplicationInstance appInstance)
	{
		getLogger().info("ModuleOnConnectAuthenticate started");
		WMSProperties props = appInstance.getProperties();
				
		String passwordFileStr = props.getPropertyStr("rtmpAuthenticateFile", AUTHPASSWORDFILEPATH);
		this.usernamePasswordProviderClass = props.getPropertyStr("usernamePasswordProviderClass", this.usernamePasswordProviderClass);
		if (passwordFileStr != null)
		{
			Map<String, String> envMap = new HashMap<String, String>();
			
			IVHost vhost = appInstance.getVHost();
			envMap.put("com.wowza.wms.context.VHost", vhost.getName());
			envMap.put("com.wowza.wms.context.VHostConfigHome", vhost.getHomePath());
			envMap.put("com.wowza.wms.context.Application", appInstance.getApplication().getName());
			envMap.put("com.wowza.wms.context.ApplicationInstance", appInstance.getName());

			passwordFileStr = SystemUtils.expandEnvironmentVariables(passwordFileStr, envMap);
			passwordFile = new File(passwordFileStr);
		}
		
		if (passwordFile != null)
			getLogger().info("ModuleOnConnectAuthenticate: Authorization password file: "+passwordFile.getAbsolutePath());
		if (usernamePasswordProviderClass != null)
			getLogger().info("ModuleOnConnectAuthenticate: Authorization password class: "+usernamePasswordProviderClass);
	}

	
	
	public void onConnect(IClient client, RequestFunction function, AMFDataList params)
	{
		boolean isAuthenticated = false;
		
		String allowedEncoder;
		Boolean isPublisher;
		
		String flashver = client.getFlashVer();
		getLogger().info("Flashver: " + flashver);
		
		try
		{
		allowedEncoder = client.getAppInstance().getProperties().getPropertyStr("AllowEncoder");
		isPublisher = flashver.startsWith(allowedEncoder); 
		
		if (isPublisher) 
		{
			client.acceptConnection();
			return;
		}
		}
		catch(Exception ex)
		{	
		}
		
		String username = null;
		String password = null;
		try
		{
			while(true)
			{
				if (params.size() <= PARAM2)
					break;
				
				username = params.getString(PARAM1);
				password = params.getString(PARAM2);
				
				if (username == null || password == null)
					break;
								
				IAuthenticateUsernamePasswordProvider filePtr = null;
				if (usernamePasswordProviderClass != null)
					filePtr = AuthenticationUtils.createUsernamePasswordProvider(usernamePasswordProviderClass);
				else if (passwordFile != null)
					filePtr = AuthenticationPasswordFiles.getInstance().getPasswordFile(passwordFile);
				
				if (filePtr == null)
					break;

				filePtr.setClient(client);

				String userPassword = filePtr.getPassword(username);
				if (userPassword == null)
					break;

				if (!userPassword.equals(password))
					break;
				
				isAuthenticated = true;
				break;
			}
		}
		catch(Exception e)
		{
			getLogger().error("ModuleOnConnectAuthenticate.onConnect: "+e.toString());
			isAuthenticated = false;
		}
		
		getLogger().info("ModuleOnConnectAuthenticate Authenticated: " + isAuthenticated);
		
		if (!isAuthenticated)
			client.rejectConnection("Authentication Failed["+client.getClientId()+"]: "+username);
		else
			client.acceptConnection();
	}
}
