package io.bascom.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    private static final DateTimeFormatter HUMAN_READABLE_DATETIME_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy hh:mm:ss a");
    private static final DateTimeFormatter HUMAN_READABLE_TIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm:ss a");

    public static String getHumanReadableDateAndTime() {
        return LocalDateTime.now().format(HUMAN_READABLE_DATETIME_FORMAT);
    }

    public static String getHumanReadableTime() {
        return LocalTime.now().format(HUMAN_READABLE_TIME_FORMAT);
    }
}
