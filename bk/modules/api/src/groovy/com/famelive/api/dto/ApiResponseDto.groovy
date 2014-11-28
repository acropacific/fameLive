package com.famelive.api.dto

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.enums.APIActions
import com.famelive.api.enums.APIErrorCode
import com.famelive.api.exceptions.ApiUserAccountBlockException
import com.famelive.api.util.ApiMessagesUtil
import grails.converters.JSON

import java.lang.annotation.Annotation
import java.lang.reflect.Field

@APIResponseClass(isParentResponse = true, isIncludeParent = false)
class ApiResponseDto {

    @APIResponseField(include = true)
    public int status = -1
    @APIResponseField(include = true)
    public String message = ""
    @APIResponseField(include = true, key = "data")
    public Object object
    @APIResponseField(include = true)
    public int code = -1

    void updateSuccessAction(APIActions apiActions) {
        this.code = apiActions.successCode
        this.message = apiActions.successMessage
    }

    ApiResponseDto() {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
    }

    ApiResponseDto(Object object) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.object = object
    }

    ApiResponseDto(ApiUserAccountBlockException apiUserAccountBlockException) {
        this.status = ApiConstants.MOBILE_API_USER_BLOCK_ERROR_CODE
        APIErrorCode error = APIErrorCode.valueOf(apiUserAccountBlockException.getClass().getSimpleName())
        this.code = error.errorCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
        this.object = ""
    }

    ApiResponseDto(RuntimeException runtimeException) {
        this.status = ApiConstants.MOBILE_API_ERROR_CODE
        APIErrorCode error = APIErrorCode.valueOf(runtimeException.getClass().getSimpleName())
        this.code = error.errorCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
        this.object = ""
    }

    JSON toJSON() {
        toJSON(null)
    }

    JSON toJSON(json) {
        Class clazz = this.getClass()
        Map map = [:]
        Map dataMap = [:]
        if (clazz.isAnnotationPresent(APIResponseClass.class)) {
            Annotation annotation = clazz.getAnnotation(APIResponseClass.class);
            APIResponseClass apiResponseClass = (APIResponseClass) annotation;
            boolean isParentResponse = apiResponseClass.parentResponse
            boolean isIncludeParentResponse = apiResponseClass.includeParent
            if (isIncludeParentResponse) {
                includeParentResponse(this.class, this, map, dataMap)
            }
            if (isParentResponse) {
                buildMapFromObject(clazz, this, map)
            } else {
                buildMapFromObject(clazz, this, dataMap)
                map.put("data", dataMap)
            }
        }
        if (map?.data) {
            if (map?.data?.keySet()?.contains("messages")) {
                map.data.put("messages", fetchAllErrorCodes() + fetchAllSuccessCodes())
            }
        }
        if (!map?.data) {
            map.put("data", [:])
        }
        JSON response = map as JSON
        println 'returning JSON'
        println response.toString(true)
        return response
    }

    void includeParentResponse(Class clazz, Object obj, Map map, Map dataMap) {
        Class superClass = clazz.getSuperclass()
        if (superClass.isAnnotationPresent(APIResponseClass.class)) {
            Annotation annotation = superClass.getAnnotation(APIResponseClass.class);
            APIResponseClass apiResponseClass = (APIResponseClass) annotation;
            boolean isParentResponse = apiResponseClass.parentResponse
            boolean isIncludeParentResponse = apiResponseClass.includeParent
            if (isParentResponse) {
                buildMapFromObject(superClass, this, map)
            } else {
                buildMapFromObject(superClass, this, dataMap)
            }
            if (isIncludeParentResponse) {
                includeParentResponse(superClass, obj, map, dataMap)
            }
        }
    }

    static Map fetchAllErrorCodes() {
        Map errorMap = [:]
        APIErrorCode.values().each {
            errorMap.put(it.errorCode, ApiMessagesUtil.messageSource.getProperty("${it.errorCode}"))
        }
        return errorMap
    }

    static Map fetchAllSuccessCodes() {
        Map successMap = [:]
        APIActions.values().each {
            successMap.put(it.successCode, ApiMessagesUtil.messageSource.getProperty("${it.successCode}"))
        }
        return successMap
    }

    void buildMapFromObject(Class clazz, Object obj, Map map) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(APIResponseField.class)) {
                Annotation annotation = field.getAnnotation(APIResponseField.class);
                APIResponseField apiResponseField = (APIResponseField) annotation;
                if (apiResponseField.include()) {
                    String key = apiResponseField.key() ?: field.getName()
                    String name = apiResponseField.parentName()
                    String value = ""
                    ArrayList listValue = []
                    Map mapValue = [:]
                    try {
                        if (field?.get(obj)?.class == ArrayList) {
                            listValue = field.get(obj) as ArrayList
                        } else if (field?.get(obj)?.class == Set) {
                            listValue = field.get(obj) as ArrayList
                        } else if (field?.get(obj) instanceof Map) {
                            mapValue = field.get(obj) as Map
                        } else {
                            value = field.get(obj)
                        }
                    } catch (Exception exception) {
                        println "unable read value from DTO"
                        exception.printStackTrace()
                    }
                    if (name) {
                        if (map.keySet().contains(name)) {
                            map."${name}".put(key, value)
                        } else {
                            Map newMap = [:]
                            newMap.put(key, value)
                            map.put(name, newMap)
                        }
                    } else {
                        try {
                            if (listValue) {
                                map.put(key, listValue)
                            } else if (mapValue.size() > 0) {
                                map.put(key, mapValue)
                            } else if (field?.get(obj)?.class == ArrayList && !field?.get(obj)) {
                                map.put(key, listValue)
                            } else {
                                map.put(key, value)
                            }
                        } catch (Exception exception) {
                            println "unable read value from DTO"
                            exception.printStackTrace()
                        }
                    }
                }
            }
        }
    }

}