package com.wanasit.chrono.parser.en;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.parser.ParserAbstract;

public class ENTimeDeadlineFormatParser extends ParserAbstract {

    protected static String regPattern = "(?<=\\W|^)(?:within|in)\\s*([1-9][0-9]{0,5})\\s*(minutes?|hours?|days?)(?=\\W|$)";

    @Override
    protected Pattern pattern() {
        return Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption options) {

        int amount = Integer.parseInt(matcher.group(1));
        if (amount < 1)
            return null;

        ParsedResult result = new ParsedResult(this, matcher.start(), matcher.group());
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(refDate);

        if (matcher.group(2).startsWith("day")) {

            result.start.imply(Components.Hour, calendar.get(Calendar.HOUR_OF_DAY));
            result.start.imply(Components.Minute, calendar.get(Calendar.MINUTE));

            calendar.add(Calendar.DAY_OF_YEAR, amount);
            result.start.assign(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
            result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
            result.start.assign(Components.Year, calendar.get(Calendar.YEAR));

        } else if (matcher.group(2).startsWith("hour")) {

            calendar.add(Calendar.HOUR, amount);
            result.start.assign(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
            result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
            result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
            result.start.assign(Components.Hour, calendar.get(Calendar.HOUR_OF_DAY));
            result.start.imply(Components.Minute, calendar.get(Calendar.MINUTE));

        } else if (matcher.group(2).startsWith("minute")) {

            calendar.add(Calendar.MINUTE, amount);
            result.start.assign(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
            result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
            result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
            result.start.assign(Components.Hour, calendar.get(Calendar.HOUR_OF_DAY));
            result.start.assign(Components.Minute, calendar.get(Calendar.MINUTE));
        }

        return result;
    }
}
