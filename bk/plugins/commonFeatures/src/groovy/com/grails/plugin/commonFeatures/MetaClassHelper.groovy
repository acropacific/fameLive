package com.grails.plugin.commonFeatures

import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpServletRequest
import java.text.DateFormat
import java.text.SimpleDateFormat

class MetaClassHelper {
    public static void enrichClasses(def application) {
        addConstructorToGregorianCalendar()
        enrichListClass()
        enrichDateClass()
        enrichRequestClass()
        enrichStringClass()
        enrichIntegerClass()
        enhanceSaveMethod()
        enrichMapClass()
        enrichMultipartFile()
        addAsyncMethods(application)
        enrichJodaClasses()
    }

    public static void enrichMapClass() {
        LinkedHashMap.metaClass {
            convertToQueryString = {
                List queryParams = []
                delegate.each { key, val ->
                    queryParams << "${key}=${val.encodeAsURL()}"
                }
                return queryParams.join("&")
            }
        }
    }

    public static void enrichListClass() {
        List.metaClass {
            partition = { size ->
                if (!delegate)
                    return []

                def rslt = delegate.inject([[]]) { ret, elem ->
                    (ret.last() << elem).size() >= size ? (ret << []) : ret
                }
                !rslt.last() ? rslt[0..-2] : rslt
            }

            collectWithIndex = { cls ->
                def i = 0;
                def arr = [];
                delegate.each { obj ->
                    arr << cls(obj, i++)
                }
                return arr
            }
        }
    }

    public static void enrichStringClass() {
        String.metaClass {
            initCap = {
                return delegate[0]?.toUpperCase() + delegate[1..<(delegate.length())]
            }

            properCase = {
                delegate = delegate.toLowerCase()
                return delegate.tokenize(" ")*.initCap().join(" ")
            }

            initialWordCap = {
                delegate = delegate.toLowerCase()
                return delegate.initCap()
            }

            trimString = { Integer trimLength, String suffix ->
                String trimString = delegate?.toString()
                String trimmed = ""
                int trimLengthToBeUsed = trimLength - suffix.length()
                if (trimLengthToBeUsed && (trimString?.length() > trimLengthToBeUsed)) {
                    List words = trimString.tokenize(" ")
                    for (String word in words) {
                        if ((trimmed.length() + word.length() + 1) <= trimLengthToBeUsed) {
                            trimmed += " ${word}"
                        } else {
                            if ((trimmed.length() + 1) <= trimLengthToBeUsed) {
                                trimmed += " "
                            }
                            break
                        }
                    }
                    trimmed += suffix
                } else {
                    trimmed = trimString
                }
                return trimmed
            }

            trimString = { Integer trimLength ->
                trimString(trimLength, "...")
            }

            removeHTMLSpaces = {
                String trimmedString = delegate?.toString()
                String spaceChar = "&nbsp;".decodeHTML()
                if (trimmedString.contains(spaceChar)) {
                    trimmedString = trimmedString.replaceAll(spaceChar, " ").trim()
                }
                return trimmedString
            }

            customCollate = {
                List<String> list = []
                if (delegate) {
                    Integer counter = delegate?.size() - 1
                    while (counter >= 0) {
                        list << delegate[0..counter]
                        counter--
                    }
                }
                return list
            }
        }
    }

    public static void enrichIntegerClass() {
        Integer.metaClass.static.parseInteger = { String s ->
            try {
                return Integer.parseInt(s)
            } catch (Exception e) {
                return null
            }
        }
    }

    public static void enrichRequestClass() {
        HttpServletRequest.metaClass {
            getRemoteIPAddress = {
                String ipListAsString = delegate.getHeader("x-forwarded-for") ?: delegate.remoteAddr
                ipListAsString?.split(",")?.collect { it.trim() }?.first()
            }
            isAjax = {
                delegate.getHeader("X-Requested-With") == "XMLHttpRequest"
            }

            getSiteUrl = {
                return (delegate.scheme + "://" + delegate.serverName + ":" + delegate.serverPort + delegate.getContextPath())
            }

            getUserAgent = {
                return delegate.getHeader("User-Agent")
            }
            getReferer = {
                return delegate.getHeader("referer")
            }
        }


    }

    public static void addConstructorToGregorianCalendar() {
        GregorianCalendar.metaClass.constructor = { Date date ->
            date.clearTime()
            GregorianCalendar calendar = new GregorianCalendar()
            calendar.time = date
            return calendar
        }
    }

    public static void enrichDateClass() {
        Date.metaClass {
            getFormattedDay = {
                DateFormat formatter = new SimpleDateFormat("EEE");
                return formatter.format(delegate)
            }

            getFormattedMonth = {
                return delegate.format('MMMM').toLowerCase()
            }
        }
    }

    static void enhanceSaveMethod() {
        Object.metaClass.mySave = {
            def o = null
            o = delegate.save(flush: true)
            if (!o) {
                delegate.errors.allErrors.each {
                    println it
                }
            }
            o
        }
    }

    public static void enrichMultipartFile() {
        MultipartFile.metaClass {
            isAllowedImage = { def types ->
                if (delegate) {
                    String contentType = delegate.contentType.tokenize("/")[1]
                    return types instanceof List ? contentType in types : contentType.equalsIgnoreCase(types)
                }
                return false
            }
        }
    }

    public static addAsyncMethods(def application) {
        for (artifactClasses in [application.controllerClasses, application.serviceClasses, application.domainClasses]) {
            for (clazz in artifactClasses) {
                clazz.metaClass.runAsync = { Runnable runme ->
                    application.mainContext.executorService.withPersistence(runme)
                }
            }
        }
    }

    public static enrichJodaClasses() {
        JodaTimeEnricher.registerDynamicMethods()
    }
}
