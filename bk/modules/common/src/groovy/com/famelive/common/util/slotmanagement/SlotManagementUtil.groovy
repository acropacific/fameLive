package com.famelive.common.util.slotmanagement

import com.famelive.common.enums.slotmanagement.SlotManagementConstantKeys
import com.famelive.common.slotmanagement.SlotManagementConstants
import com.famelive.common.streamManagement.WowzaChannel
import com.famelive.common.user.User
import com.famelive.common.util.CommonMessagesUtil

import java.lang.reflect.Method

public class SlotManagementUtil {
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

    public static Object fetchSlotManagementConstantDefaultValue(SlotManagementConstantKeys slotManagementConstantKey) {
        Class constantValueTypeClass = slotManagementConstantKey.valueType
        String constantValue = ""
        SlotManagementConstants constant = SlotManagementConstants.findWhere(constant: slotManagementConstantKey, channel: null, userRole: null)
        if (constant != null && constant.value.length() > 0) {
            constantValue = constant.value
        } else {
            constantValue = CommonMessagesUtil.messageSource.get("common.famelive.slotManagementConstant." + slotManagementConstantKey.key + ".defaultValue").toString()
        }
        return parseValue(constantValueTypeClass, constantValue)
    }

    public
    static Object fetchSlotManagementConstantForChannel(WowzaChannel channel, SlotManagementConstantKeys slotManagementConstantKey) {
        Object slotManagementConstant = fetchSlotManagementConstantDefaultValue(slotManagementConstantKey)
        List<SlotManagementConstants> slotManagementConstantValues = SlotManagementConstants.findAllByConstant(slotManagementConstantKey)
        SlotManagementConstants slotManagementConstantValue
        if (slotManagementConstantValues == null) {
            //Do nothing, it will consider the default value of constant ie. already set
        } else if (slotManagementConstantValues.size() == 1 && slotManagementConstantValues.get(0).channel == null) {
            slotManagementConstantValue = slotManagementConstantValues.get(0)
        } else {
            slotManagementConstantValue = slotManagementConstantValues.find { value ->
                if (value.channel == channel) {
                    return true
                }
            }
            if (slotManagementConstantValue == null) {
                slotManagementConstantValue = slotManagementConstantValues.find { value ->
                    if (value.channel == null) {
                        return true
                    }
                }
            }
        }

        try {
            slotManagementConstant = parseValue(slotManagementConstantKey.valueType, slotManagementConstantValue.value)
        } catch (Exception e) {
            println "Exception occurred when fetching value for " + slotManagementConstantKey + " exp:" + e
            println "setting value for " + slotManagementConstantKey + " exp = " + slotManagementConstant
        }
        return slotManagementConstant
    }

    public
    static Object fetchSlotManagementConstantForUser(SlotManagementConstantKeys slotManagementConstantKey) {
        //TODO: Use current user to get the constant value and remove the below line to make the functionality dynamic
        User user = User.findById(1)
        Set<String> userRoles = user.getAuthorities()*.authority as Set<String>
        Object slotManagementConstant = fetchSlotManagementConstantDefaultValue(slotManagementConstantKey)
        List<SlotManagementConstants> slotManagementConstantValues = SlotManagementConstants.findAllByConstant(slotManagementConstantKey)
        SlotManagementConstants slotManagementConstantValue
        if (slotManagementConstantValues == null) {
            //Do nothing, it will consider the default value of constant ie. already set
        } else if (slotManagementConstantValues.size() == 1 && slotManagementConstantValues.get(0).userRole == null) {
            slotManagementConstantValue = slotManagementConstantValues.get(0)
        } else {
            slotManagementConstantValue = slotManagementConstantValues.find { value ->
                if (userRoles.contains(value.userRole)) {
                    return true
                }
            }
            if (slotManagementConstantValue == null) {
                slotManagementConstantValue = slotManagementConstantValues.find { value ->
                    if (value.userRole == null) {
                        return true
                    }
                }
            }
        }

        try {
            slotManagementConstant = parseValue(slotManagementConstantKey.valueType, slotManagementConstantValue.value)
        } catch (Exception e) {
            println "Exception occurred when fetching value for " + slotManagementConstantKey + " exp:" + e
            println "setting value for " + slotManagementConstantKey + " exp = " + slotManagementConstant
        }
        return slotManagementConstant
    }

}
