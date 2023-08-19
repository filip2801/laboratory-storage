package com.filip2801.laboratorystorage

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TestUtils {

    static LocalDateTime parseLocalDateTime(String dateToParse) {
        return LocalDateTime.parse(dateToParse, DateTimeFormatter.ISO_DATE_TIME)
    }

    static boolean isOneSecondCloseToNow(LocalDateTime date) {
        return date.minusSeconds(1).isBefore(date)
                && date.plusSeconds(1).isAfter(date)
    }
}
