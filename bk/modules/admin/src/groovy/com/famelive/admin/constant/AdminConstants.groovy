package com.famelive.admin.constant

class AdminConstants {
    final static String Messages_PATH = "/WEB-INF/admin_messages"


    public static Integer DEFAULT_OFFSET = 0
    public static Integer DEFAULT_MAX_PAGINATION = 100
    //User
    public static Integer USER_RESULT_PER_PAGE = 10
    public static String USER_DEFAULT_ORDER = 'desc'
    public static String USER_DEFAULT_SORT_COLUMN = 'id'
    //Event
    public static Integer EVENT_RESULT_PER_PAGE = 10
    public static String EVENT_DEFAULT_ORDER = 'asc'
    public static String EVENT_DEFAULT_SORT_COLUMN = 'startTime'


    public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss z"
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm"

    public static final String DATE_FORMAT = "dd/MM/yyyy"
    public static final String TIME_FORMAT = "kk:mm"
    public static final String TIME_FORMAT_24_HR = "HH:mm"

    //Cloudinary

    public static final List<String> USER_IMAGE_TAGS = ["user"]
    public static final List<String> EVENT_IMAGE_TAGS = ["event"]

}
