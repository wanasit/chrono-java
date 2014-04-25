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
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.Parser;
import com.wanasit.chrono.ParsedDateComponent.Components;

public class ENMonthNameMiddleEndianParser extends Parser {

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
        Date date = null;

        String monthName = matcher.group(4);
        String dayStr = matcher.group(9);
        int year = calendar.get(Calendar.YEAR);

        Pattern fullPattern = Pattern.compile(regFullPattern);
        Matcher fullMatcher = fullPattern.matcher(text);
        if (fullMatcher.find(matcher.start()) && fullMatcher.start() == matcher.start()) {

            result.index = matcher.start() + matcher.group(1).length();
            result.text = matcher.group().substring(matcher.group(1).length(),
                    matcher.group().length() - matcher.group(14).length());

            year = Integer.parseInt(matcher.group(12));
            if (matcher.group(13) != null) {
                year -= 543;
            }
        } else {

            result.index = matcher.start() + matcher.group(1).length();
            result.text = matcher.group().substring(matcher.group(1).length(),
                    matcher.group().length() - matcher.group(11).length());

        }

        String cleanedText = dayStr + " " + monthName + " " + year;
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        try {
            date = format.parse(cleanedText);
        } catch (ParseException e) {
            return null;
        }
        
        calendar.setTime(date);
        result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
        result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
        result.start.assign(Components.Day, calendar.get(Calendar.DAY_OF_MONTH));

        if (matcher.group(5) != null) {
            
            int startDay = Integer.parseInt(matcher.group(5));
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
