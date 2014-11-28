package com.famelive.common.request

import org.apache.commons.io.IOUtils

import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

/**
 * Created by anil on 16/9/14.
 */
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
    private ByteArrayOutputStream cachedBytes;

    public MultiReadHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cachedBytes == null)
            cacheInputStream();

        return new CachedServletInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    private void cacheInputStream() throws IOException {
        /* Cache the inputstream in order to read it multiple times. For
         * convenience, I use apache.commons IOUtils
         */
        cachedBytes = new ByteArrayOutputStream();
        IOUtils.copy(super.getInputStream(), cachedBytes);
    }

    /* An inputstream which reads the cached request body */

    public class CachedServletInputStream extends ServletInputStream {
        private ByteArrayInputStream input;

        public CachedServletInputStream() {
            /* create a new input streamManagement from the cached request body */
            input = new ByteArrayInputStream(cachedBytes.toByteArray());
        }

        @Override
        public int read() throws IOException {
            return input.read();
        }
    }

    @Override
    public String getContentType() {
        String contentType = super.getContentType()
        /*if (contentType == null) {
            contentType = "text/xml"

//            contentType='application/x-www-form-urlencoded'
        }*/
        return contentType
    }
}
