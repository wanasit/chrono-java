package com.wanasit.chrono.parser.en;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.parser.ParserAbstract;

public class ENSlashDateFormatParser extends ParserAbstract {


    protected static String regPattern = "(\\W|^)(Sun|Sunday|Mon|Monday|Tue|Tuesday|Wed|Wednesday|Thur|Thursday|Fri|Friday|Sat|Saturday)?\\s*\\,?\\s*([0-9]{1,2})[\\/\\.]([0-9]{1,2})([\\/\\.]([0-9]{4}|[0-9]{2}))?(\\W|$)";

    @Override
    protected Pattern pattern() {
        return Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {
        // TODO Auto-generated method stub


        Pattern datePattern = Pattern.compile("^\\d.\\d$", Pattern.CASE_INSENSITIVE);
        if (datePattern.matcher(matcher.group()).find()) return null;

        ParsedResult result = new ParsedResult(
                this,
                matcher.start() + matcher.group(1).length(),
                matcher.group());

        result.text = result.text.substring(matcher.group(1).length(),
                result.text.length() - matcher.group(7).length());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(refDate);

        int day = Integer.parseInt(matcher.group(4));
        int month = Integer.parseInt(matcher.group(3));
        int year = calendar.get(Calendar.YEAR);
        if (matcher.group(6) != null) year = Integer.parseInt(matcher.group(6));

        if (month < 1 || month > 12) return null;
        if (day < 1 || day > 31) return null;
        if (year < 100) {
            if (year > 50) {
                year = year + 2500 - 543; //BE
            } else {
                year = year + 2000; //AD
            }
        }

        calendar.set(year, month - 1, day);

        // Check leap day or impossible date
        if (calendar.get(Calendar.DAY_OF_MONTH) != day) return null;

        result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
        result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
        result.start.assign(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));

        return result;
    }

}
