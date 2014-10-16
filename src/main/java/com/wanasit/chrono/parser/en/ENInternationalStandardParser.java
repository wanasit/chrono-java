package com.wanasit.chrono.parser.en;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedDateComponent;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.parser.ParserAbstract;

public class ENInternationalStandardParser extends ParserAbstract {

    // YYYY-MM-DDThh:mm:ss.sTZD 
    // TZD = (Z or +hh:mm or -hh:mm)
    protected static String regPattern = "(?<=\\W|^)" + "([0-9]{4})\\-([0-9]{1,2})\\-([0-9]{1,2})"
            + "(?:T" //..
            + "([0-9]{1,2}):([0-9]{1,2})" // hh:mm
            + "(?::([0-9]{1,2})(?:\\.\\d{1,4})?)?" // :ss.s
            + "(?:Z|([+-]\\d{2}):?(\\d{2})?)" // TZD (Z or ±hh:mm or ±hhmm or ±hh)
            + ")?"  //..
            + "(?=\\W|$)";

    protected static final int YEAR_NUMBER_GROUP = 1;
    protected static final int MONTH_NUMBER_GROUP = 2;
    protected static final int DATE_NUMBER_GROUP = 3;

    protected static final int HOUR_NUMBER_GROUP = 4;
    protected static final int MINUTE_NUMBER_GROUP = 5;
    protected static final int SECOND_NUMBER_GROUP = 6;

    protected static final int TZD_HOUR_OFFSET_GROUP = 7;
    protected static final int TZD_MINUTE_OFFSET_GROUP = 8;

    @Override
    protected Pattern pattern() {
        return Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {

        ParsedResult result = new ParsedResult(this, matcher.start(), matcher.group());

        result.start = new ParsedDateComponent();
        result.start.assign(Components.Year, Integer.parseInt(matcher.group(YEAR_NUMBER_GROUP)));
        result.start.assign(Components.Month, Integer.parseInt(matcher.group(MONTH_NUMBER_GROUP)));
        result.start.assign(Components.DayOfMonth,
                Integer.parseInt(matcher.group(DATE_NUMBER_GROUP)));

        if (matcher.group(HOUR_NUMBER_GROUP) != null) {

            result.start.assign(Components.Hour,
                    Integer.parseInt(matcher.group(HOUR_NUMBER_GROUP)));
            result.start.assign(Components.Minute,
                    Integer.parseInt(matcher.group(MINUTE_NUMBER_GROUP)));

            if (matcher.group(SECOND_NUMBER_GROUP) != null) {
                result.start.assign(Components.Second,
                        Integer.parseInt(matcher.group(SECOND_NUMBER_GROUP)));
            }

            if (matcher.group(TZD_HOUR_OFFSET_GROUP) == null) {

                result.start.assign(Components.TimezoneOffset, 0);

            } else {

                int minuteOffset = 0;
                int hourOffset = Integer.parseInt(matcher.group(TZD_HOUR_OFFSET_GROUP));
                if (matcher.group(TZD_MINUTE_OFFSET_GROUP) != null)
                    minuteOffset = Integer.parseInt(matcher.group(TZD_MINUTE_OFFSET_GROUP));

                int offset = hourOffset * 60;

                if (offset < 0)
                    offset -= minuteOffset;
                else
                    offset += minuteOffset;

                result.start.assign(Components.TimezoneOffset, offset);
            }

        }

        return result;
    }
}
