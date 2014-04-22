package com.wanasit.chrono.parser.en;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOptions;
import com.wanasit.chrono.ParsedDateComponent;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.Parser;

public class ENMonthNameLittleEndianParser extends Parser {

    protected static String regPattern = "(\\W|^)((Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sun|Mon|Tue|Wed|Thu|Fri|Sat)\\s*,?\\s*)?([0-9]{1,2})(st|nd|rd|th)?(\\s*(to|\\-|\\s)\\s*([0-9]{1,2})(st|nd|rd|th)?)?\\s*(January|Jan|February|Feb|March|Mar|April|Apr|May|June|Jun|July|Jul|August|Aug|September|Sep|October|Oct|November|Nov|December|Dec)((\\s*[0-9]{2,4})(\\s*BE)?)?(\\W|$)";

    @Override
    protected Pattern pattern() {
        return Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher,
            ChronoOptions options) {

        ParsedResult result = new ParsedResult(matcher.start()
                - matcher.group(1).length(), matcher.group());
        result.text = result.text.substring(matcher.group(1).length(),
                result.text.length() - matcher.group(14).length()
                        - matcher.group(1).length());

        int year = 0;
        if (matcher.group(11) != null) {
            year = Integer.parseInt(matcher.group(12));

            if (year < 100) {
                if (year > 20)
                    year = 0; // 01 - 20
                else
                    year = year + 2000;
            } else if (matcher.group(13) != null) { // BC
                year = year - 543;
            }
        }

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(refDate);
        Date date = null;

        if (year > 0) {
            String cleanedText = matcher.group(4) + ' ' + matcher.group(10)
                    + " " + year;
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            try {
                date = format.parse(cleanedText);
            } catch (ParseException e) {
                return null;
            }
        } else {
            String cleanedText = matcher.group(4) + ' ' + matcher.group(10)
                    + " " + calendar.get(Calendar.YEAR);
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

            try {
                date = format.parse(cleanedText);
            } catch (ParseException e) {
                return null;
            }

            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 1);
            Date nextYear = calendar.getTime();

            calendar.setTime(date);
            calendar.add(Calendar.YEAR, -1);
            Date lastYear = calendar.getTime();
            if (Math.abs(nextYear.getTime() - refDate.getTime()) < Math
                    .abs(date.getTime() - refDate.getTime())) {
                date = nextYear;
            } else if (Math.abs(lastYear.getTime() - refDate.getTime()) < Math
                    .abs(date.getTime() - refDate.getTime())) {
                date = lastYear;
            }
        }

        // Text text is 'range' value, ex. '12 - 13 January 2012'
        if (matcher.group(8) != null) {
            int endDay = Integer.parseInt(matcher.group(8));
            int startDay = Integer.parseInt(matcher.group(4));

            // Check leap day or impossible date
            // if(date.format('D') != matchedTokens[4]) return null;
            // if(endDate.format('D') != matchedTokens[8]) return null;

            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, startDay);
            result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
            result.start.assign(Components.Month, calendar.get(Calendar.MONTH) +1);
            result.start.assign(Components.Day,
                    calendar.get(Calendar.DAY_OF_MONTH));

            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, endDay);
            result.end = new ParsedDateComponent();
            result.end.assign(Components.Year, calendar.get(Calendar.YEAR));
            result.end.assign(Components.Month, calendar.get(Calendar.MONTH) +1);
            result.end.assign(Components.Day,
                    calendar.get(Calendar.DAY_OF_MONTH));

            return result;
        } else {
            // Check leap day or impossible date
            calendar.setTime(date);
            result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
            result.start.assign(Components.Month, calendar.get(Calendar.MONTH) +1);
            result.start.assign(Components.Day,
                    calendar.get(Calendar.DAY_OF_MONTH));
            return result;
        }
    }
}
