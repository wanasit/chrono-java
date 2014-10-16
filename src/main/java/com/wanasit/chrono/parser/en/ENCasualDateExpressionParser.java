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

public class ENCasualDateExpressionParser extends ParserAbstract {

    private static String regPattern = "(?<=\\W|^)"
            + "(today|tonight|tomorrow|tmr|yesterday|last\\s*night|this\\s*(morning|afternoon|evening))"
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
        result.start = new ParsedDateComponent();

        String firstMatch = matcher.group(1).toLowerCase();
        if (firstMatch.equals("tonight")) {

            result.start.imply(Components.Hour, 0);
            if (calendar.get(Calendar.HOUR_OF_DAY) > 6) {
                calendar.add(Calendar.DATE, 1);
            }

        } else if (firstMatch.equals("tomorrow") || firstMatch.equals("tmr")) {

            calendar.add(Calendar.DATE, 1);

        } else if (firstMatch.equals("yesterday")) {

            calendar.add(Calendar.DATE, -1);

        } else if (firstMatch.startsWith("last")) {

            result.start.imply(Components.Hour, 0);
            if (calendar.get(Calendar.HOUR_OF_DAY) < 6) {
                calendar.add(Calendar.DATE, -1);
            }

        } else if (firstMatch.startsWith("this")) {

            String secondMatch = matcher.group(2).toLowerCase();
            if (secondMatch.equals("afternoon")) {

                result.start.imply(Components.Hour, 15);

            } else if (secondMatch.equals("evening")) {

                result.start.imply(Components.Hour, 18);

            } else if (secondMatch.equals("morning")) {

                result.start.imply(Components.Hour, 6);
                if (calendar.get(Calendar.HOUR_OF_DAY) < 6) {
                    // When say "this morning" on before 6 AM
                    calendar.add(Calendar.DATE, -1);
                }
            }
        }

        result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
        result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
        result.start.assign(Components.DayOfMonth, calendar.get(Calendar.DATE));
        return result;
    }
}
