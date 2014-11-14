package com.wowza.wms.plugin.collection.http;

import com.wowza.wms.http.*;
import com.wowza.wms.vhost.*;

public class HttpCustomResponseCode extends HTTProvider2Base
{
	
	public void onHTTPRequest(IVHost vhost, IHTTPRequest req, IHTTPResponse resp)
	{
		if (!doHTTPAuthentication(vhost, req, resp))
			return;
		int responseCode = this.properties.getPropertyInt("responseCode", 200);
		resp.setResponseCode(responseCode);
	}
}
