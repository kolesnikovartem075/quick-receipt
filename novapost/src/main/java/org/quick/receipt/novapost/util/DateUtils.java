package org.quick.receipt.novapost.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private static final String NOVA_POST_FORMAT = "dd.MM.yyyy";

    public static String from(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(NOVA_POST_FORMAT));
    }

    public static LocalDate to(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(NOVA_POST_FORMAT));
    }
}