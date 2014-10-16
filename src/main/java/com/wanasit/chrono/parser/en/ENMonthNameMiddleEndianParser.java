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

public class ENMonthNameMiddleEndianParser extends ParserAbstract {

    protected static String regPattern = "(?<=\\W|^)"
            + "(?:(?:Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sun|Mon|Tue|Wed|Thu|Fri|Sat)\\s*,?\\s*)?"
            + "(Jan(?:uary|\\.)?|Feb(?:ruary|\\.)?|Mar(?:ch|\\.)?|Apr(?:il|\\.)?|May|Jun(?:e|\\.)?|Jul(?:y|\\.)?|Aug(?:ust|\\.)?|Sep(?:tember|\\.)?|Oct(?:ober|\\.)?|Nov(?:ember|\\.)?|Dec(?:ember|\\.)?)"
            + "\\s*"
            + "(?:([0-9]{1,2})(?:st|nd|rd|th)?\\s*(?:to|\\-|~)\\s*)?"
            + "([0-9]{1,2})(?:st|nd|rd|th)?"
            + "(?:,?(\\s*[0-9]{4})(\\s*BE)?)?(?=\\W|$)";


    protected static final int MONTH_NAME_GROUP = 1;
    protected static final int RANGE_DATE_NUMBER_GROUP = 2;
    protected static final int DATE_NUMBER_GROUP = 3;
    protected static final int YEAR_NUMBER_GROUP = 4;
    protected static final int YEAR_BE_GROUP = 5;


    @Override
    protected Pattern pattern() {
        return Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(refDate);

        ParsedResult result = new ParsedResult(this, matcher.start(), matcher.group());
        String monthName = matcher.group(MONTH_NAME_GROUP);
        String dayStr = matcher.group(DATE_NUMBER_GROUP);
        int year = calendar.get(Calendar.YEAR);

        if (matcher.group(YEAR_NUMBER_GROUP) != null) {
            year = Integer.parseInt(matcher.group(YEAR_NUMBER_GROUP).trim());
            if (matcher.group(YEAR_BE_GROUP) != null) {
                year -= 543;
            }
        }

        int day = Integer.parseInt(dayStr);
        int month = EnglishConstants.valueForMonth(monthName.toLowerCase());
        calendar.set(year, month, day);

        // Check for an impossible date
        if (calendar.get(Calendar.DAY_OF_MONTH) != day) return null;

        result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
        result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
        result.start.assign(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));

        if (matcher.group(RANGE_DATE_NUMBER_GROUP) != null) {

            int startDay = Integer.parseInt(matcher.group(RANGE_DATE_NUMBER_GROUP));
            calendar.set(Calendar.DAY_OF_MONTH, startDay);

            // Check for an impossible date
            if (calendar.get(Calendar.DAY_OF_MONTH) != startDay) return null;

            // SWAP start - end
            result.end = result.start;

            result.start = new ParsedDateComponent();
            result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
            result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
            result.start.assign(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
        }

        return result;
    }

}
