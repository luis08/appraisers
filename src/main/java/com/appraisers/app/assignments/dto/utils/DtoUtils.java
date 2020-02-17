package com.appraisers.app.assignments.dto.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;

public class DtoUtils {

    public static final String EMPTY = "";

    public static Boolean parseOrNull(String str) {
        String stringOrNull = StringUtils.stripToNull(str);
        return Objects.isNull(str) ? null : Boolean.parseBoolean(stringOrNull);
    }

    public static String toStringOrNull(Boolean bool) {
        return Objects.isNull(bool) ? null : bool.toString();
    }

    public static String toStringOrNull(Date date) {
        return Objects.isNull(date) ? null : date.toString();
    }

    public static String toString(Boolean bool) {
        return  Objects.isNull(bool) ? EMPTY : bool.toString();
    }
}
