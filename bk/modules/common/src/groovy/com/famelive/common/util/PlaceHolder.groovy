package com.famelive.common.util

import org.apache.commons.lang.StringUtils

class PlaceHolder {
    public static final String EVENT_NAME = '[EVENT_NAME]'



    static Set<String> getAllPlaceholders() {
        Set placeholders = new HashSet();
        PlaceHolder.declaredFields.each {
            if (it.type == String) {
                placeholders.add(PlaceHolder[it.name])
            }
        }
        return placeholders
    }

    static String getPopulatedContent(Map parameters, String content) {
        Set<String> placeHolders = getAllPlaceholders()
        placeHolders.each { String ph ->
            if (content?.contains(ph)) {
                String value = parameters[ph] ?: ""
                content = StringUtils.replace(content, ph, value)
            }
        }
        return content
    }
}
