package com.egt.demo.demo.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTransformator {
    private DateTransformator() {
        //util method
    }

    public static LocalDateTime transform(long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        Date date = Date.from(instant);
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
