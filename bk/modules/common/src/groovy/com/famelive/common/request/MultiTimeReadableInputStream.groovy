package com.famelive.common.request

/**
 * Created by anil on 17/11/14.
 */
class MultiTimeReadableInputStream extends BufferedInputStream {
    public MultiTimeReadableInputStream(InputStream inputStream) {
        super(inputStream);
        super.mark(Integer.MAX_VALUE);
    }

    @Override
    public void close() throws IOException {
        super.reset();
    }

    public void finallyClose() {
        super.close();
    }
}

