package com.famelive.common.util

import com.famelive.common.constant.CommonConstants
import org.joda.time.DateTime

import java.text.SimpleDateFormat

class DateTimeUtil {

    static Date getDateFromMilliSeconds(Long millis) {
        return new Date(millis)
    }

    static Date getGMTFormattedDate(Date date) {
        SimpleDateFormat isoFormat = new SimpleDateFormat(CommonConstants.INPUT_DATE_FORMAT, Locale.ENGLISH)
        isoFormat.setTimeZone(TimeZone.getTimeZone("GMT"))
        isoFormat.parse("2010-05-23T09:01:02")
/*
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        df.setTimeZone(TimeZone.getTimeZone("GMT"))
        System.out.println(df.format(date))*/
    }

    static Date addMinutesToDate(Date date, Integer minutes) {
        new DateTime(date).plusMinutes(minutes).toDate()
    }

    static Date subtractMinutesFromDate(Date date, Integer minutes) {
        new DateTime(date).minusMinutes(minutes).toDate()
    }

    static Date addDaysToDate(Date date, Integer days) {
        new Date(date.getTime()) + days
    }

    static Date subtractDaysFromDate(Date date, Integer days) {
        new Date(date.getTime()) - days
    }

    static Date addHoursToDate(Date date, Integer hours) {
        new DateTime(date).plusHours(hours)
    }

    static Date subtractHoursFromDate(Date date, Integer hours) {
        new DateTime(date).minusHours(hours)
    }

    static Date roundOffDayInDate(Date date) {
        Date newDate = new Date(((long) (date.getTime() / 1000)) * 1000)
        newDate.setHours(0)
        newDate.setMinutes(0)
        newDate.setSeconds(0)
        return newDate
    }

    static Date roundOffHourInDate(Date date) {
        Date newDate = new Date(((long) (date.getTime() / 1000)) * 1000)
        newDate.setMinutes(0)
        newDate.setSeconds(0)
        return newDate
    }

    static Date roundOffMinuteInDate(Date date) {
        Date newDate = new Date(((long) (date.getTime() / 1000)) * 1000)
        newDate.setSeconds(0)
        return newDate
    }

    static Date getCurrentDate() {
        new Date()
    }
}
