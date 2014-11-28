package com.famelive.admin.util

class AdminCloudinaryUtils {

    static String getRandomImageName() {
        return UUID.randomUUID().toString().replaceAll("-", "")
    }

    static String getEventImagePath(Map cloudinaryConfig) {
        return cloudinaryConfig['eventImageFolder']
    }
}
