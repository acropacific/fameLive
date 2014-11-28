package com.famelive.streamManagement.wowza.command

import com.famelive.common.constant.CommonConstants
import com.famelive.streamManagement.enums.APIRequestsDetails
import com.famelive.streamManagement.wowza.AdvanceSetting
import com.famelive.streamManagement.wowza.WowzaModule
import grails.util.GrailsWebUtil
import groovy.util.slurpersupport.GPathResult
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * Created by anil on 17/11/14.
 */
class WowzaFetchAdvanceApplicationConfigurationResponseCO extends WowzaResponseCO {
    List<WowzaModule> modules = []
    List<AdvanceSetting> advanceSettings = []
    String restURI
    String version
    String serverName = "WowzaStreamingEngine"
    JSONArray saveFieldList = []


    WowzaFetchAdvanceApplicationConfigurationResponseCO(InputStream xml, boolean _isTestRequest) {
        if (_isTestRequest) {
            File responseFile = GrailsWebUtil.currentApplication().mainContext.getResource(CommonConstants.WOWZA_API_TEST_RESPONSE_PATH + "/" + APIRequestsDetails.FETCH_ADVANCE_WOWZA_APPLICATION_CONFIGURATION.testResponseFile).getFile()
            xml = (InputStream) new FileInputStream(responseFile)
        }
        GPathResult response = new XmlSlurper().parse(xml)
        restURI = response.attributes().get("restURI")
        Iterator itr = response.childNodes()
        while (itr.hasNext()) {
            def applicationAdvChildNode = itr.next()
            if (applicationAdvChildNode.name.equals("Version")) {
                version = applicationAdvChildNode.text()
            } else if (applicationAdvChildNode.name.equals("Modules")) {
                Iterator modulesChildNodeItr = applicationAdvChildNode.childNodes()
                while (modulesChildNodeItr.hasNext()) {
                    WowzaModule module = new WowzaModule()
                    def modulesChildNode = modulesChildNodeItr.next()
                    if (modulesChildNode.name.equals("com.wowza.wms.rest.vhosts.applications.modules.ModuleConfig")) {
                        Iterator moduleNodeItr = modulesChildNode.childNodes()
                        while (moduleNodeItr.hasNext()) {
                            def moduleNode = moduleNodeItr.next()
                            if (moduleNode.name.equals("Order")) {
                                module.order = moduleNode.text()
                            } else if (moduleNode.name.equals("Name")) {
                                module.name = moduleNode.text()
                            } else if (moduleNode.name.equals("Description")) {
                                module.description = moduleNode.text()
                            } else if (moduleNode.name.equals("Class")) {
                                module.clazz = moduleNode.text()
                            }
                        }
                    }
                    modules.add(module)
                }
            } else if (applicationAdvChildNode.name.equals("AdvancedSettings")) {
                Iterator settingsChildNodeItr = applicationAdvChildNode.childNodes()
                while (settingsChildNodeItr.hasNext()) {
                    AdvanceSetting advanceSetting = new AdvanceSetting()
                    def settingsChildNode = settingsChildNodeItr.next()
                    if (settingsChildNode.name.equals("AdvancedSetting")) {
                        Map settingAttributes = settingsChildNode.attributes()
                        advanceSetting.enabled = settingAttributes.get("enabled")
                        advanceSetting.canRemove = settingAttributes.get("canRemove")
                        Iterator settingNodeItr = settingsChildNode.childNodes()
                        while (settingNodeItr.hasNext()) {
                            def settingNode = settingNodeItr.next()
                            if (settingNode.name.equals("Name")) {
                                advanceSetting.name = settingNode.text()
                            } else if (settingNode.name.equals("Value")) {
                                advanceSetting.value = settingNode.text()
                            } else if (settingNode.name.equals("DefaultValue")) {
                                advanceSetting.defaultValue = settingNode.text()
                            } else if (settingNode.name.equals("Type")) {
                                advanceSetting.type = settingNode.text()
                            } else if (settingNode.name.equals("SectionName")) {
                                advanceSetting.sectionName = settingNode.text()
                            } else if (settingNode.name.equals("Documented")) {
                                advanceSetting.documented = settingNode.text()
                            }
                        }
                    }
                    advanceSettings.add(advanceSetting)
                }
            }
        }
        println this
    }

    JSONObject toJson() {
        JSONObject response = new JSONObject()
        response.put("success", success)
        response.put("message", message)
        JSONObject data = new JSONObject()
        data.put("Version", version)
        data.put("restURI", restURI)
        data.put("Modules", modules*.getJSONObject() as JSONArray)
        data.put("AdvanceSettings", advanceSettings*.getJSONObject() as JSONArray)
        response.put("data", data)
        return response
    }
}
