package com.famelive.common.enums.slotmanagement

/**
 * Created by anil on 5/11/14.
 */
public enum SlotManagementConstantKeys {

    EVENT_START_BUFFER("eventStartBuffer", Integer.class),//In minutes
    EVENT_END_BUFFER("eventEndBuffer", Integer.class),//In minutes
    EVENT_GAP_TIME("eventGapTime", Integer.class),//In minutes
    IS_CLEAN_RESERVED_CHANNEL_SLOT_DATA_ON_REINITIALIZATION("isCleanReservedChannelSlotDataOnReinitialization", Boolean.class),
    ALLOW_EVENT_SLOT_BOOKING_TILL_DAYS_FROM_CURRENT_DATE("allowEventSlotBookingTillDaysFromCurrentDate", Integer.class),
    IS_USER_ALLOWED_FOR_RESERVED_SLOTS("isUserAllowedForReservedSlots", Boolean.class)
    String key;
    Class valueType;

    SlotManagementConstantKeys(String key, Class valueType) {
        this.key = key;
        this.valueType = valueType;
    }
}
