package com.famelive.common.util

import com.famelive.common.request.MultiReadHttpServletRequest
import com.famelive.common.request.MultiTimeReadableInputStream
import grails.util.Environment
import org.apache.commons.lang.RandomStringUtils
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil

import javax.servlet.http.HttpServletRequest

class FameLiveUtil {

    static def grailsApplication

    static def getGrailsApplication() {
        return grailsApplication
    }

    static String getRandomPassword() {
        (Environment.currentEnvironment == Environment.DEVELOPMENT) ? getDefaultPassword() : getPassword()
    }

    static String getRandomName() {
        RandomStringUtils.randomAlphabetic(4).toLowerCase() + RandomStringUtils.randomAlphabetic(2).toUpperCase() + RandomStringUtils.randomAlphabetic(4).toLowerCase()
    }

    static String getPassword() {
        return getRandomCode()
    }

    static String getRandomCode() {
        RandomStringUtils.randomNumeric(6)
    }

    static String getDefaultPassword() {
        grailsApplication.config.user.default.password as String
    }

    static String getRandomChannel() {
        return getRandomString()
    }

    static String getRandomString() {
        String randomString = UUID.randomUUID().toString().replaceAll('-', '')
        return randomString
    }

    static void removeFieldErrorsFromDomainErrors(def domain, String... fields) {
        if (domain != null && domain.hasErrors() && domain.errors.allErrors.size() > 0) {
            def savedErrors = domain.errors.allErrors.findAll { true }
            def uselessErrors = savedErrors.findAll { fields.contains(it.field) }
            savedErrors.removeAll(uselessErrors)
            domain.clearErrors()
            savedErrors.each {
                domain.errors.addError(it)
            }
        }
    }

    static List unwrapIfProxy(List objects) {
        List newList = []
        objects.each { object ->
            try {
                newList.add(GrailsHibernateUtil.unwrapIfProxy(object))
            } catch (Exception exception) {
                log.debug('Exception occurred while unwrappingProxyObject +' + object + ' Exception:' + exception)
            }
        }
        newList
    }

    static MultiTimeReadableInputStream fetchMultiTimeReadableInputStream(InputStream inputStream) {
        return new MultiTimeReadableInputStream(inputStream)
    }

    static MultiReadHttpServletRequest fetchMultiTimeReadableHttpServletRequest(HttpServletRequest httpServletRequest) {
        return new MultiReadHttpServletRequest(httpServletRequest)
    }

    static String fetchStringFromInputStream(InputStream inputStream) {
        BufferedReader inStreamReader = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = inStreamReader.readLine()) != null) {
            response.append(inputLine);
        }
        inputStream.close()
        return response
    }

    static InputStream fetchInputStreamFromString(String data) {
        return new ByteArrayInputStream(data.getBytes());
    }

    static String fetchMachineGlobalIP() {
        String ip = "";
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)) {
            Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                if (!inetAddress.toString().contains(":") && !inetAddress.toString().equals("/127.0.0.1")) {
                    ip = inetAddress.toString();
                    return ip;
                }
            }
        }
    }
}
