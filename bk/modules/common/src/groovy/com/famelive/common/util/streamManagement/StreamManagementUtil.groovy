package com.famelive.common.util.streamManagement

import com.famelive.common.enums.streamManagement.StreamManagementConstantKeys
import com.famelive.common.streamManagement.StreamManagementConstants
import com.famelive.common.streamManagement.WowzaChannel
import com.famelive.common.user.User
import com.famelive.common.util.CommonMessagesUtil

import java.lang.reflect.Method

public class StreamManagementUtil {
    public static Object parseValue(Class parseTypeClass, String value) {
        List<Method> parseTypeClassMethods = parseTypeClass.getMethods()
        Method parseTypeClassParseMethod = parseTypeClassMethods.find { method ->
            if (method.getName().startsWith("parse")) {
                return true
            }
        }
        Object result
        if (parseTypeClass == String) {
            result = value
        } else if (parseTypeClass == Integer) {
            result = Integer.parseInt(value)
        } else if (parseTypeClass == Boolean) {
            result = Boolean.parseBoolean(value)
        } else {
            result = parseTypeClassParseMethod.invoke(parseTypeClass, value)
        }
        return result
    }

    public
    static Object fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys streamManagementConstantKey) {
        Class constantValueTypeClass = streamManagementConstantKey.valueType
        String constantValue = ""
        StreamManagementConstants constant = StreamManagementConstants.findWhere(constant: streamManagementConstantKey, channel: null, userRole: null)
        if (constant != null && constant.value.length() > 0) {
            constantValue = constant.value
        } else {
            constantValue = CommonMessagesUtil.messageSource.get("common.famelive.streamManagementConstant." + streamManagementConstantKey.key + ".defaultValue").toString()
        }
        return parseValue(constantValueTypeClass, constantValue)
    }

    public
    static Object fetchStreamManagementConstantForChannel(WowzaChannel channel, StreamManagementConstantKeys streamManagementConstantKey) {
        Object streamManagementConstant = fetchStreamManagementConstantDefaultValue(streamManagementConstantKey)
        List<StreamManagementConstants> streamManagementConstantValues = StreamManagementConstants.findAllByConstant(streamManagementConstantKey)
        StreamManagementConstants streamManagementConstantValue
        if (streamManagementConstantValues == null) {
            //Do nothing, it will consider the default value of constant ie. already set
        } else if (streamManagementConstantValues.size() == 1 && streamManagementConstantValues.get(0).channel == null) {
            streamManagementConstantValue = streamManagementConstantValues.get(0)
        } else {
            streamManagementConstantValue = streamManagementConstantValues.find { value ->
                if (value.channel == channel) {
                    return true
                }
            }
            if (streamManagementConstantValue == null) {
                streamManagementConstantValue = streamManagementConstantValues.find { value ->
                    if (value.channel == null) {
                        return true
                    }
                }
            }
        }

        try {
            streamManagementConstant = parseValue(streamManagementConstantKey.valueType, streamManagementConstantValue.value)
        } catch (Exception e) {
            println "Exception occurred when fetching value for " + streamManagementConstantKey + " exp:" + e
            println "setting value for " + streamManagementConstantKey + " exp = " + streamManagementConstant
        }
        return streamManagementConstant
    }

    public
    static Object fetchStreamManagementConstantForUser(StreamManagementConstantKeys streamManagementConstantKey) {
        //TODO: Use current user to get the constant value and remove the below line to make the functionality dynamic
        User user = User.findById(1)
        Set<String> userRoles = user.getAuthorities()*.authority as Set<String>

        Object streamManagementConstant = fetchStreamManagementConstantDefaultValue(streamManagementConstantKey)
        List<StreamManagementConstants> streamManagementConstantValues = StreamManagementConstants.findAllByConstant(streamManagementConstantKey)
        StreamManagementConstants streamManagementConstantValue
        if (streamManagementConstantValues == null) {
            //Do nothing, it will consider the default value of constant ie. already set
        } else if (streamManagementConstantValues.size() == 1 && streamManagementConstantValues.get(0).userRole == null) {
            streamManagementConstantValue = streamManagementConstantValues.get(0)
        } else {
            streamManagementConstantValue = streamManagementConstantValues.find { value ->
                if (userRoles.contains(value.userRole)) {
                    return true
                }
            }
            if (streamManagementConstantValue == null) {
                streamManagementConstantValue = streamManagementConstantValues.find { value ->
                    if (value.userRole == null) {
                        return true
                    }
                }
            }
        }

        try {
            streamManagementConstant = parseValue(streamManagementConstantKey.valueType, streamManagementConstantValue.value)
        } catch (Exception e) {
            println "Exception occurred when fetching value for " + streamManagementConstantKey + " exp:" + e
            println "setting value for " + streamManagementConstantKey + " exp = " + streamManagementConstant
        }
        return streamManagementConstant
    }

}
