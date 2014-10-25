package com.wanasit.chrono.parser.en;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedDateComponent;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.parser.ParserAbstract;
import com.wanasit.chrono.refiner.en.ENMergeDateRangeRefiner;

public class ENDayOfWeekDateFormatParser extends ParserAbstract {

    protected static String regPattern = "(?<=\\W|^)"
            + "(?:(this|last|next)\\s*)?"
            + "(Sunday|Sun|Monday|Mon|Tuesday|Tues|Tue|Wednesday|Wed|Thursday|Thu(?:rs|r)?|Friday|Fri|Saturday|Sat)"
            + "(?=\\W|$)";

    public ENDayOfWeekDateFormatParser () {
        this.refiners.add(new ENMergeDateRangeRefiner());
    }

    @Override
    protected Pattern pattern() { return Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE); }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(refDate);

        ParsedResult result = new ParsedResult(this, matcher.start(), matcher.group());

        int dayOfWeek = EnglishConstants.valueForDayOfWeek(matcher.group(2));
        int today = calendar.get(Calendar.DAY_OF_WEEK);

        if (matcher.group(1) == null || matcher.group(1).toLowerCase().equals("this")) {

            if (Math.abs(dayOfWeek - 7 - today) < Math.abs(dayOfWeek - today)) {
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
            } else if (Math.abs(dayOfWeek + 7 - today) < Math.abs(dayOfWeek - today)) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
            }

        } else {

            if (matcher.group(1).toLowerCase().equals("last")) {
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
            } else if (matcher.group(1).toLowerCase().equals("next")) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
            }
        }

        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        result.start = new ParsedDateComponent();
        result.start.imply(Components.Year, calendar.get(Calendar.YEAR));
        result.start.imply(Components.Month, calendar.get(Calendar.MONTH) + 1);
        result.start.imply(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
        result.start.assign(Components.DayOfWeek, dayOfWeek);

        return result;
    }
}
