package com.famelive.admin.util

import com.famelive.admin.constant.AdminConstants
import org.joda.time.DateTime

import java.text.SimpleDateFormat

class AdminDateTimeUtils {

    static Date concatenateDateWithTime(String format, String dateTime) {
        new SimpleDateFormat(format).parse(dateTime)
    }

    static Date formatDate(String format, String dateTime) {
        new SimpleDateFormat(format).parse(dateTime)
    }

    static String getDefaultStartDate() {
        return getCurrentDate().format(AdminConstants.DATE_FORMAT)
    }

    static Date getDateWithEndTime(Date date) {
        new DateTime(date).withHourOfDay(23).withMinuteOfHour(59).toDate()
    }

    static Date getCurrentDate() {
        new Date()
    }
}
