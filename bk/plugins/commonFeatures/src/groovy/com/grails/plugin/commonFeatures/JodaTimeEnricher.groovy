package com.grails.plugin.commonFeatures

import org.joda.time.*

import static org.joda.time.DateTimeFieldType.*

class JodaTimeEnricher {

    static void registerDynamicMethods() {
        // consistency with format(String) method Groovy adds to java.util.Date
        ReadableInstant.metaClass.format = { String pattern ->
            delegate.toString(pattern)
        }
        ReadablePartial.metaClass.format = { String pattern ->
            delegate.toString(pattern)
        }

        // next and previous
        ReadableInstant.metaClass.next = { ->
            JodaTimeEnricher.next(delegate)
        }
        ReadablePartial.metaClass.next = { ->
            JodaTimeEnricher.next(delegate)
        }
        ReadableInstant.metaClass.previous = { ->
            JodaTimeEnricher.previous(delegate)
        }
        ReadablePartial.metaClass.previous = { ->
            JodaTimeEnricher.previous(delegate)
        }

        // compatibility with Groovy operators where JodaTime method name conventions differ
        [Days, Hours, Minutes, Months, Seconds, Weeks, Years].each { clazz ->
            clazz.metaClass.negative = {
                delegate.negated()
            }
            clazz.metaClass.multiply = { int scalar ->
                delegate.multipliedBy(scalar)
            }
            clazz.metaClass.div = { int divisor ->
                delegate.dividedBy(divisor)
            }
        }

        // scoped current time overriding
        DateTimeUtils.metaClass.static.withCurrentMillisFixed = { long fixed, Closure yield ->
            JodaTimeEnricher.withCurrentMillisFixed(fixed, yield)
        }
        DateTimeUtils.metaClass.static.withCurrentMillisOffset = { long offset, Closure yield ->
            JodaTimeEnricher.withCurrentMillisOffset(offset, yield)
        }

        // range extensions
        Range.metaClass.step = { DurationFieldType increment ->
            JodaTimeEnricher.step(delegate, 1, increment)
        }
        Range.metaClass.step = { int step, DurationFieldType increment ->
            JodaTimeEnricher.step(delegate, step, increment)
        }
        Range.metaClass.step = { DurationFieldType increment, Closure closure ->
            JodaTimeEnricher.step(delegate, 1, increment, closure)
        }
        Range.metaClass.step = { int step, DurationFieldType increment, Closure closure ->
            JodaTimeEnricher.step(delegate, step, increment, closure)
        }
    }

    static ReadablePartial next(ReadablePartial delegate) {
        if (delegate.isSupported(dayOfMonth())) {
            delegate.plusDays(1)
        } else if (delegate.isSupported(hourOfDay())) {
            delegate.plusHours(1)
        } else if (delegate.isSupported(monthOfYear())) {
            delegate.plusMonths(1)
        }
    }

    static ReadableInstant next(ReadableInstant delegate) {
        if (delegate.isSupported(dayOfMonth())) {
            delegate.plusDays(1)
        } else if (delegate.isSupported(hourOfDay())) {
            delegate.plusHours(1)
        } else if (delegate.isSupported(monthOfYear())) {
            delegate.plusMonths(1)
        }
    }

    static ReadablePartial previous(ReadablePartial delegate) {
        if (delegate.isSupported(dayOfMonth())) {
            delegate.minusDays(1)
        } else if (delegate.isSupported(hourOfDay())) {
            delegate.minusHours(1)
        } else if (delegate.isSupported(monthOfYear())) {
            delegate.minusMonths(1)
        }
    }

    static ReadableInstant previous(ReadableInstant delegate) {
        if (delegate.isSupported(dayOfMonth())) {
            delegate.minusDays(1)
        } else if (delegate.isSupported(hourOfDay())) {
            delegate.minusHours(1)
        } else if (delegate.isSupported(monthOfYear())) {
            delegate.minusMonths(1)
        }
    }

    static withCurrentMillisFixed(long fixed, Closure yield) {
        try {
            DateTimeUtils.setCurrentMillisFixed fixed
            return yield()
        } finally {
            DateTimeUtils.setCurrentMillisSystem()
        }
    }

    static withCurrentMillisOffset(long offset, Closure yield) {
        try {
            DateTimeUtils.setCurrentMillisOffset offset
            return yield()
        } finally {
            DateTimeUtils.setCurrentMillisSystem()
        }
    }

    static List step(Range self, int step, DurationFieldType increment) {
        if (self.from instanceof ReadablePartial || self.from instanceof ReadableInstant) {
            new DateTimeRange(increment, self.from, self.to).step(step)
        } else {
            throw new MissingMethodException("step", self.getClass(), [increment] as Object[])
        }
    }

    static void step(Range self, int step, DurationFieldType increment, Closure closure) {
        if (self.from instanceof ReadablePartial || self.from instanceof ReadableInstant) {
            new DateTimeRange(increment, self.from, self.to).step(step).each(closure)
        } else {
            throw new MissingMethodException("step", self.getClass(), [increment] as Object[])
        }
    }
}

class DateTimeRange extends ObjectRange {

    private final DurationFieldType increment

    static <T extends ReadablePartial> Range<T> asRange(DurationFieldType increment, T from, T to) {
        new DateTimeRange(increment, from, to)
    }

    static <T extends ReadableInstant> Range<T> asRange(DurationFieldType increment, T from, T to) {
        new DateTimeRange(increment, from, to)
    }

    static Range<DateTime> asRange(DurationFieldType increment, Interval interval) {
        new DateTimeRange(increment, interval)
    }

    DateTimeRange(DurationFieldType increment, ReadablePartial from, ReadablePartial to) {
        super(from, to)
        this.increment = increment
    }

    DateTimeRange(DurationFieldType increment, ReadablePartial from, ReadablePartial to, boolean reverse) {
        super(from, to, reverse)
        this.increment = increment
    }

    DateTimeRange(DurationFieldType increment, ReadableInstant from, ReadableInstant to) {
        super(from, to)
        this.increment = increment
    }

    DateTimeRange(DurationFieldType increment, ReadableInstant from, ReadableInstant to, boolean reverse) {
        super(from, to, reverse)
        this.increment = increment
    }

    DateTimeRange(DurationFieldType increment, Interval interval) {
        super(interval.start, interval.end)
        this.increment = increment
    }

    DateTimeRange(DurationFieldType increment, Interval interval, boolean reverse) {
        super(interval.start, interval.end, reverse)
        this.increment = increment
    }

    List step(DurationFieldType increment) {
        new DateTimeRange(increment, from, to)
    }

    void step(DurationFieldType increment, Closure closure) {
        new DateTimeRange(increment, from, to).each(closure)
    }

    List step(int step, DurationFieldType increment) {
        new DateTimeRange(increment, from, to).step(step)
    }

    void step(int step, DurationFieldType increment, Closure closure) {
        new DateTimeRange(increment, from, to).step(step).each(closure)
    }

    @Override
    protected Object increment(Object value) {
        value.withFieldAdded(increment, 1)
    }

    @Override
    protected Object decrement(Object value) {
        value.withFieldAdded(increment, -1)
    }

}
