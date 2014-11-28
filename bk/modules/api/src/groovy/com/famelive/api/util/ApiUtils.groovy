package com.famelive.api.util

import java.text.DateFormat
import java.text.SimpleDateFormat

class ApiUtils {

    static Date getDateInGMTFormat(){
        DateFormat gmtFormat = new SimpleDateFormat();
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        gmtFormat.setTimeZone(gmtTime);
        gmtFormat.format(new Date())
    }
}
