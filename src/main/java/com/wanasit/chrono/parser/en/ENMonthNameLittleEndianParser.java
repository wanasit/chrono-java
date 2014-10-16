package com.wanasit.chrono.parser.en;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedDateComponent;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.parser.ParserAbstract;
import com.wanasit.chrono.ParsedResult;

public class ENMonthNameLittleEndianParser extends ParserAbstract {

    protected static String regPattern = "(\\W|^)((Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sun|Mon|Tue|Wed|Thu|Fri|Sat)\\s*,?\\s*)?([0-9]{1,2})(st|nd|rd|th)?(\\s*(to|\\-|\\s)\\s*([0-9]{1,2})(st|nd|rd|th)?)?\\s*(Jan(?:uary|\\.)?|Feb(?:ruary|\\.)?|Mar(?:ch|\\.)?|Apr(?:il|\\.)?|May|Jun(?:e|\\.)?|Jul(?:y|\\.)?|Aug(?:ust|\\.)?|Sep(?:tember|\\.)?|Oct(?:ober|\\.)?|Nov(?:ember|\\.)?|Dec(?:ember|\\.)?)((\\s*[0-9]{2,4})(\\s*BE)?)?(\\W|$)";
    
    @Override
    protected Pattern pattern() {
        return Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {

        ParsedResult result = new ParsedResult(this, matcher.start() + matcher.group(1).length(),
                matcher.group());
        result.text = result.text.substring(matcher.group(1).length(), result.text.length()
                - matcher.group(14).length());

        int year = 0;
        if (matcher.group(11) != null) {
            year = Integer.parseInt(matcher.group(12).trim());

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
        
        int day   = Integer.parseInt(matcher.group(4));
        int month = EnglishConstants.valueForMonth(matcher.group(10).toLowerCase());
        
        if (year > 0) {
            
            calendar.set(year, month, day);
            if (calendar.get(Calendar.DAY_OF_MONTH) == day)
                date = calendar.getTime();
            
        } else {
            
            year  = calendar.get(Calendar.YEAR);
            calendar.set(year, month, day);
            if (calendar.get(Calendar.DAY_OF_MONTH) == day)
                date = calendar.getTime();
            
            calendar.set(year+1, month, day);
            if (calendar.get(Calendar.DAY_OF_MONTH) == day) {
                
                Date nextYear = calendar.getTime();
                
                if (date == null || 
                        Math.abs(nextYear.getTime() - refDate.getTime()) < 
                        Math.abs(date.getTime() - refDate.getTime())) {

                    date = nextYear;
                }
            }
            
            calendar.set(year-1, month, day);
            if (calendar.get(Calendar.DAY_OF_MONTH) == day) {
                
                Date lastYear = calendar.getTime();
                
                if (date == null || 
                        Math.abs(lastYear.getTime() - refDate.getTime()) < 
                        Math.abs(date.getTime() - refDate.getTime())) {

                    date = lastYear;
                }
            }
        }
        
        if (date == null) return null;

        // Text text is 'range' value, ex. '12 - 13 January 2012'
        if (matcher.group(8) != null) {
            int endDay = Integer.parseInt(matcher.group(8));
            int startDay = Integer.parseInt(matcher.group(4));

            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, startDay);
            // Check leap day or impossible date
            if (calendar.get(Calendar.DAY_OF_MONTH) != startDay)
                return null;

            result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
            result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
            result.start.assign(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));

            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, endDay);
            // Check leap day or impossible date
            if (calendar.get(Calendar.DAY_OF_MONTH) != endDay)
                return null;

            result.end = new ParsedDateComponent();
            result.end.assign(Components.Year, calendar.get(Calendar.YEAR));
            result.end.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
            result.end.assign(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));

            return result;
        } else {
            // Check leap day or impossible date
            calendar.setTime(date);

            result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
            result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
            result.start.assign(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
            return result;
        }
    }
}
