package com.wanasit.chrono.parser.en;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedDateComponent;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.parser.ParserAbstract;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wanasit on 10/12/14.
 */
public class ENWeekExpressionParser extends ParserAbstract {

    private static String regPattern = "(?<=\\W|^)"
            + "((?:last|next)\\s*(?:(\\d+)|(\\w{3,9}))?\\s*weeks?|this\\s*week)"
            + "(?=\\W|$)";

    @Override
    protected Pattern pattern() {
        return Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {

        ParsedResult result = new ParsedResult(this, matcher.start(), matcher.group());
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(refDate);

        int numberOfWeek = 1;
        if (matcher.group(2) != null) {
            numberOfWeek = Integer.parseInt(matcher.group(2));
        } else if (matcher.group(3) != null) {
            numberOfWeek = EnglishConstants.valueForNumber(matcher.group(3));
            if (numberOfWeek < 0) return null;
        }

        if (result.text.toLowerCase().startsWith("last")) {
            calendar.add(Calendar.WEEK_OF_YEAR, -numberOfWeek);
        } else if (result.text.toLowerCase().startsWith("next")) {
            calendar.add(Calendar.WEEK_OF_YEAR, numberOfWeek);
        }

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        result.start.imply(ParsedDateComponent.Components.Year, calendar.get(Calendar.YEAR));
        result.start.imply(ParsedDateComponent.Components.Month, calendar.get(Calendar.MONTH) + 1);
        result.start.imply(ParsedDateComponent.Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
        return result;
    }
}
