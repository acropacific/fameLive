package com.famelive.common.properties

import com.famelive.common.constant.CommonConstants

/**
 * Created by anil on 25/9/14.
 */
class MessagesFileFilter implements FileFilter {
    @Override
    boolean accept(File file) {
        if (file.path.endsWith("." + CommonConstants.MESSAGES_FILE_EXTENSION)) {
            return true
        } else {
            return false
        }
    }
}
