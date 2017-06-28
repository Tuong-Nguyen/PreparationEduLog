package com.edulog.driverportal.util;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static final Locale DEFAULT_LOCALE = Locale.US;
    private static final String DEFAULT_PATTERN = "EEE MMM dd HH:mm:ss Z yyyy";

    private Locale locale = DEFAULT_LOCALE;
    private String pattern = DEFAULT_PATTERN;
    private DateFormat dateFormat;

    public DateConverter() {
        newDateFormatInstance();
    }

    public Date toDate(String source) {
        if (source == null) {
            return null;
        }

        Date date;
        try {
            date = dateFormat.parse(source);
        } catch (ParseException ex) {
            date = null;
        }

        return date;
    }

    public String toString(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormat.format(date);
    }

    public DateConverter locale(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("Locale can not be null");
        }
        this.locale = locale;
        newDateFormatInstance();
        return this;
    }

    public DateConverter pattern(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern can not be null");
        }
        this.pattern = pattern;
        newDateFormatInstance();
        return this;
    }

    private void newDateFormatInstance() {
        dateFormat = new SimpleDateFormat(pattern, locale);
    }
}
