package com.famelive.streamManagement.wowza.command

import com.famelive.common.enums.RequestType

/**
 * Created by anil on 29/10/14.
 */
class WowzaAddPublisherRequestCO extends WowzaRequestCO {

    {
        requestMethod = RequestType.POST
        contentType = "application/json; charset=utf8"
    }

    String applicationName
    String publisherName
    String publisherPassword
    String publisherDescription
    String publisherVersion = "1.0"

    String getURL() {
        return "http://" + serverIP + ":" + serverPort + "/" + wowzaAPIVersion + "/servers/" + serverName + "/vhosts/" + vhostName + "/applications/" + applicationName + "/publishers"
    }


    String getBody() {
        String body = '{' +
                '"restURI": "http://' + serverIP + ':' + serverPort + '/' + wowzaAPIVersion + '/servers/' + serverName + '/vhosts/' + vhostName + '/applications/' + applicationName + '/publishers/' + publisherName + '",' +
                '"serverName": "' + serverName + '",' +
                '"publishers": [' +
                '{' +
                '"restURI": "",' +
                '"password": "' + publisherPassword + '",' +
                '"name": "' + publisherName + '",' +
                '"serverName": "' + serverName + '",' +
                '"description": "' + publisherDescription + '",' +
                '"saveFieldList": [""],' +
                '"version": "' + publisherVersion + '"' +
                '}' +
                '],' +
                '"saveFieldList": [""],' +
                '"version": "' + publisherVersion + '"' +
                '}';
        return body;
    }

}
