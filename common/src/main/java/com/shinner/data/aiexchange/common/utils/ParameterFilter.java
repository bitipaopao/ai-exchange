package com.shinner.data.aiexchange.common.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterFilter {

    public static String textFiler(String text) {
        if (StringUtils.isEmpty(text)) {
            return text;
        }
        final String format = "[^\\u4E00-\\u9FA5A-Za-z0-9_-]";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            throw new IllegalArgumentException("Illegal argument." + text);
        }
        return text;
    }

    public static String listStrFiler(String listStr) {
        final String format = "[^\\u4E00-\\u9FA5A-Za-z0-9_,]";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(listStr);
        if (matcher.find()) {
            throw new IllegalArgumentException("Illegal argument." + listStr);
        }
        return listStr;
    }

    public static String numberFilter(String number) {
        final String format = "[^0-9]";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(number);
        if (matcher.find()) {
            throw new IllegalArgumentException("Illegal argument." + number);
        }
        return number;
    }
}
