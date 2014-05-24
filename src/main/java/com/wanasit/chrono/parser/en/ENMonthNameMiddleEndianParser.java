package com.wanasit.chrono.parser.en;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoConstants;
import com.wanasit.chrono.ChronoOptions;
import com.wanasit.chrono.ParsedDateComponent;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.parser.ParserAbstract;

public class ENMonthNameMiddleEndianParser extends ParserAbstract {

    protected static String regFullPattern  = "(\\W|^)((Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sun|Mon|Tue|Wed|Thu|Fri|Sat)\\s*,?\\s*)?(Jan|January|Feb|February|Mar|March|Apr|April|May|Jun|June|Jul|July|Aug|August|Sep|September|Oct|October|Nov|November|Dec|December)\\s*(([0-9]{1,2})(st|nd|rd|th)?\\s*(to|\\-)\\s*)?([0-9]{1,2})(st|nd|rd|th)?(,)?(\\s*[0-9]{4})(\\s*BE)?(\\W|$)";
    protected static String regShortPattern = "(\\W|^)((Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sun|Mon|Tue|Wed|Thu|Fri|Sat)\\s*,?\\s*)?(Jan|January|Feb|February|Mar|March|Apr|April|May|Jun|June|Jul|July|Aug|August|Sep|September|Oct|October|Nov|November|Dec|December)\\s*(([0-9]{1,2})(st|nd|rd|th)?\\s*(to|\\-)\\s*)?([0-9]{1,2})(st|nd|rd|th)?([^0-9]|$)";

    @Override
    protected Pattern pattern() {
        return Pattern.compile(regShortPattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOptions options) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(refDate);

        ParsedResult result = new ParsedResult();

        String monthName = matcher.group(4);
        String dayStr = matcher.group(9);
        int year = calendar.get(Calendar.YEAR);

        Pattern fullPattern = Pattern.compile(regFullPattern);
        Matcher fullMatcher = fullPattern.matcher(text);
        if (fullMatcher.find(matcher.start()) && fullMatcher.start() == matcher.start()) {
            matcher = fullMatcher;
            result.index = matcher.start() + matcher.group(1).length();
            result.text = matcher.group().substring(matcher.group(1).length(),
                    matcher.group().length() - matcher.group(14).length());

            year = Integer.parseInt(matcher.group(12).trim());
            if (matcher.group(13) != null) {
                year -= 543;
            }
        } else {

            result.index = matcher.start() + matcher.group(1).length();
            result.text = matcher.group().substring(matcher.group(1).length(),
                    matcher.group().length() - matcher.group(11).length());
        }
        
        int day   = Integer.parseInt(dayStr);
        int month = ChronoConstants.MONTH_NAMES.get(monthName.toLowerCase())-1;        
        calendar.set(year, month, day);
        
        // Check for an impossible date
        if (calendar.get(Calendar.DAY_OF_MONTH) != day) return null;
        
        result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
        result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
        result.start.assign(Components.Day, calendar.get(Calendar.DAY_OF_MONTH));

        if (matcher.group(5) != null) {
            
            int startDay = Integer.parseInt(matcher.group(6));
            calendar.set(Calendar.DAY_OF_MONTH, startDay);
            
            // Check for an impossible date
            if (calendar.get(Calendar.DAY_OF_MONTH) != startDay) return null;
            
            // SWAP start - end
            result.end = result.start;
            
            result.start = new ParsedDateComponent();
            result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
            result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
            result.start.assign(Components.Day, calendar.get(Calendar.DAY_OF_MONTH));
        }
        
        return result;
    }

}
