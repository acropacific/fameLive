package com.famelive.common.callBroker

import com.famelive.common.util.FameLiveUtil
import com.famelive.streamManagement.api.command.APIRequestCO
import com.famelive.streamManagement.command.RequestCO
import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * Created by anil on 17/10/14.
 */
class APIRequestCallBroker implements RequestCallBroker {
    String url
    String userAgent //= "Mozilla/5.0";
    RequestCallBrokerResponse execute(RequestCO requestCO) {
        RequestCallBrokerResponse requestCallBrokerResponse
        try {
            if (requestCO._isTestRequest) {
                requestCallBrokerResponse = new RequestCallBrokerResponse()
                requestCallBrokerResponse.statusCode = 200
                requestCallBrokerResponse.responseStream = FameLiveUtil.fetchInputStreamFromString("<Result></Result>")
            } else {
                requestCallBrokerResponse = sendRequest(requestCO)
            }
        } catch (Exception exception) {
            exception.printStackTrace()
        }
        return requestCallBrokerResponse
    }

    private RequestCallBrokerResponse sendRequest(APIRequestCO apiRequestCO) throws Exception {
        RequestCallBrokerResponse requestCallBrokerResponse = new RequestCallBrokerResponse()
        int responseCode = -1
        try {
//        url = "http://fl.zz:8081/fameLive/api/v1";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod(apiRequestCO.requestMethod.value.toUpperCase());
            con.setRequestProperty("User-Agent", userAgent);
            con.setRequestProperty("Content-Type", apiRequestCO.contentType);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


            JSONObject requestBodyJSON = apiRequestCO.toJson()
            String requestBody = ""
            if (requestBodyJSON) {
                requestBody = requestBodyJSON.toString()
            }

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(requestBody);
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();
            try {
                System.out.println("\nSending " + apiRequestCO.requestMethod.value.toUpperCase() + " request to URL : " + url);
                System.out.println("Post parameters : " + requestBody.toString());
                System.out.println("Response Code : " + responseCode);
                requestCallBrokerResponse.responseStream = FameLiveUtil.fetchMultiTimeReadableInputStream(con.getInputStream())
                System.out.println("Response : " + FameLiveUtil.fetchStringFromInputStream(requestCallBrokerResponse.responseStream))
            } catch (Exception e) {
                System.out.println("Exception when reading response streamManagement exp: " + e);
            }
        } catch (Exception e) {
            println "Exception occured while sending request to server exp:" + e;
            responseCode = 0;
        }
        requestCallBrokerResponse.statusCode = responseCode
        return requestCallBrokerResponse
    }

}
