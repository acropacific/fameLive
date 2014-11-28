package com.famelive.common.callBroker

import com.famelive.common.enums.RequestType
import com.famelive.common.util.FameLiveUtil
import com.famelive.streamManagement.command.RequestCO
import com.famelive.streamManagement.wowza.command.WowzaRequestCO

/**
 * Created by anil on 17/10/14.
 */
class WowzaRequestCallBroker implements RequestCallBroker {

    String userAgent = "Mozilla/5.0"

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
            println "exception :" + exception
        }
        return requestCallBrokerResponse
    }

    RequestCallBrokerResponse sendRequest(WowzaRequestCO wowzaRequestCO) {
        String getReqURL = wowzaRequestCO.getURL()
        RequestCallBrokerResponse requestCallBrokerResponse = new RequestCallBrokerResponse()
        int responseCode = -1
        try {
            URL obj = new URL(getReqURL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //add request header
            con.setRequestProperty("Content-Type", wowzaRequestCO.contentType);
            con.setRequestProperty("User-Agent", userAgent);

            // optional default is GET
            con.setRequestMethod(wowzaRequestCO.requestMethod.value.toUpperCase());

            con.setDoOutput(true);
            if (wowzaRequestCO.requestMethod != RequestType.GET) {
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                String requestBody = wowzaRequestCO.getBody()
                if (requestBody == null) {
                    requestBody = ""
                }
                wr.writeBytes(requestBody);
                wr.flush();
                wr.close();
            }



            responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + getReqURL);
            System.out.println("Response Code : " + responseCode);
            try {
                requestCallBrokerResponse.responseStream = FameLiveUtil.fetchMultiTimeReadableInputStream(con.getInputStream())
                System.out.println("Response : " + FameLiveUtil.fetchStringFromInputStream(requestCallBrokerResponse.responseStream))
            } catch (Exception e) {
                System.out.println("Exception occurred when reading ouput streamManagement exp:" + e);
            }
        } catch (Exception e) {
            println "Exception occured while sending request to server exp:" + e;
//            String response = new String("<?xml version='1.0' encoding='UTF-8' ?><Applications restURI='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications'>    <Application id='androidLiveChannel_dev' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/androidLiveChannel_dev'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>        <DRMEnabled>false</DRMEnabled><TranscoderEnabled>true</TranscoderEnabled>    </Application>    <Application id='bwcheck' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/bwcheck'>        <DVREnabled>false</DVREnabled>        <DRMEnabled>false</DRMEnabled><TranscoderEnabled>false</TranscoderEnabled>    </Application>    <Application id='ch1' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/ch1'>        <AppType>Live</AppType>        <DVREnabled>false</DVREnabled>  <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>false</TranscoderEnabled>    </Application>    <Application id='ch2' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/ch2'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>   <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>true</TranscoderEnabled>    </Application>    <Application id='iosLiveChannel_dev' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/iosLiveChannel_dev'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>        <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>true</TranscoderEnabled>    </Application>    <Application id='live' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/live'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>        <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>true</TranscoderEnabled>    </Application>    <Application id='vod' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/vod'>        <AppType>VOD</AppType>        <DVREnabled>false</DVREnabled>        <DRMEnabled>false</DRMEnabled> <TranscoderEnabled>false</TranscoderEnabled>    </Application>    <Application id='vods3' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/vods3'>        <AppType>VODEdge</AppType>        <DVREnabled>false</DVREnabled>        <DRMEnabled>false</DRMEnabled><TranscoderEnabled>false</TranscoderEnabled>    </Application>    <Application id='webLiveChannel_dev' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/webLiveChannel_dev'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>        <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>true</TranscoderEnabled>    </Application></Applications>");
//            inputStream = IOUtils.toInputStream(response)
            responseCode = 0  // Testing purpose response code
        }
        requestCallBrokerResponse.statusCode = responseCode
        return requestCallBrokerResponse
    }
/*
    InputStream sendPost(WowzaRequestCO wowzaRequestCO) {
        String getReqURL = wowzaRequestCO.getURL()
        InputStream inputStream
        try {
            URL obj = new URL(getReqURL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("POST");
            //add request header
            con.setRequestProperty("Content-Type", wowzaRequestCO.contentType);
            con.setRequestProperty("User-Agent", userAgent);

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(wowzaRequestCO.getBody());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + getReqURL);
            System.out.println("Response Code : " + responseCode);
            inputStream = con.getInputStream()
        } catch (Exception e) {
            String response = new String("<?xml version='1.0' encoding='UTF-8' ?><Applications restURI='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications'>    <Application id='androidLiveChannel_dev' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/androidLiveChannel_dev'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>        <DRMEnabled>false</DRMEnabled><TranscoderEnabled>true</TranscoderEnabled>    </Application>    <Application id='bwcheck' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/bwcheck'>        <DVREnabled>false</DVREnabled>        <DRMEnabled>false</DRMEnabled><TranscoderEnabled>false</TranscoderEnabled>    </Application>    <Application id='ch1' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/ch1'>        <AppType>Live</AppType>        <DVREnabled>false</DVREnabled>  <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>false</TranscoderEnabled>    </Application>    <Application id='ch2' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/ch2'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>   <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>true</TranscoderEnabled>    </Application>    <Application id='iosLiveChannel_dev' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/iosLiveChannel_dev'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>        <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>true</TranscoderEnabled>    </Application>    <Application id='live' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/live'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>        <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>true</TranscoderEnabled>    </Application>    <Application id='vod' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/vod'>        <AppType>VOD</AppType>        <DVREnabled>false</DVREnabled>        <DRMEnabled>false</DRMEnabled> <TranscoderEnabled>false</TranscoderEnabled>    </Application>    <Application id='vods3' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/vods3'>        <AppType>VODEdge</AppType>        <DVREnabled>false</DVREnabled>        <DRMEnabled>false</DRMEnabled><TranscoderEnabled>false</TranscoderEnabled>    </Application>    <Application id='webLiveChannel_dev' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/webLiveChannel_dev'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>        <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>true</TranscoderEnabled>    </Application></Applications>");
            inputStream = IOUtils.toInputStream(response)
        }
        return inputStream
    }

    InputStream sendGet(WowzaRequestCO wowzaRequestCO) {
//        String getReqURL = "http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications"
        String getReqURL = wowzaRequestCO.getURL()
        InputStream inputStream
        try {
            URL obj = new URL(getReqURL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("Content-Type", wowzaRequestCO.contentType);
            con.setRequestProperty("User-Agent", userAgent);

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(wowzaRequestCO.getBody());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + getReqURL);
            System.out.println("Response Code : " + responseCode);
            inputStream = con.getInputStream()
            *//*BufferedReader inStreamReader = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = inStreamReader.readLine()) != null) {
                response.append(inputLine);
            }
            inStreamReader.close();*//*
        } catch (Exception e) {
            String response = new String("<?xml version='1.0' encoding='UTF-8' ?><Applications restURI='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications'>    <Application id='androidLiveChannel_dev' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/androidLiveChannel_dev'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>        <DRMEnabled>false</DRMEnabled><TranscoderEnabled>true</TranscoderEnabled>    </Application>    <Application id='bwcheck' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/bwcheck'>        <DVREnabled>false</DVREnabled>        <DRMEnabled>false</DRMEnabled><TranscoderEnabled>false</TranscoderEnabled>    </Application>    <Application id='ch1' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/ch1'>        <AppType>Live</AppType>        <DVREnabled>false</DVREnabled>  <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>false</TranscoderEnabled>    </Application>    <Application id='ch2' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/ch2'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>   <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>true</TranscoderEnabled>    </Application>    <Application id='iosLiveChannel_dev' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/iosLiveChannel_dev'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>        <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>true</TranscoderEnabled>    </Application>    <Application id='live' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/live'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>        <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>true</TranscoderEnabled>    </Application>    <Application id='vod' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/vod'>        <AppType>VOD</AppType>        <DVREnabled>false</DVREnabled>        <DRMEnabled>false</DRMEnabled> <TranscoderEnabled>false</TranscoderEnabled>    </Application>    <Application id='vods3' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/vods3'>        <AppType>VODEdge</AppType>        <DVREnabled>false</DVREnabled>        <DRMEnabled>false</DRMEnabled><TranscoderEnabled>false</TranscoderEnabled>    </Application>    <Application id='webLiveChannel_dev' href='http://54.68.108.229:8087/v2/servers/WowzaStreamingEngine/vhosts/_defaultVHost_/applications/webLiveChannel_dev'>        <AppType>Live</AppType>        <DVREnabled>true</DVREnabled>        <DRMEnabled>false</DRMEnabled>        <TranscoderEnabled>true</TranscoderEnabled>    </Application></Applications>");
            inputStream = IOUtils.toInputStream(response)
        }
        return inputStream
    }*/
}
