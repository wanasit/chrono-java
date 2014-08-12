package com.wanasit.chrono.parser.jp;

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

public class JPDateAgoFormatParser extends ParserAbstract {
    
    @Override
    protected Pattern pattern() {
        return Pattern.compile("(\\d+)(分|時間|日)前", Pattern.CASE_INSENSITIVE);
    }
    
    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {
        
        Calendar calendar = Calendar.getInstance(Locale.JAPANESE);
        calendar.setTime(refDate);
        
        ParsedResult result = new ParsedResult();
        result.text  = matcher.group();
        result.index = matcher.start();
        
        int amount = Integer.parseInt(matcher.group(1));
        
        if (matcher.group(2).equals("日")) {
            calendar.add(Calendar.DAY_OF_MONTH, -amount);
            
            result.start = new ParsedDateComponent();
            result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
            result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
            result.start.assign(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
            
        } else {
            
            if (matcher.group(2).equals("分")) {
                calendar.add(Calendar.MINUTE, -amount);
            } else {
        	calendar.add(Calendar.HOUR, -amount);
            }
            
            result.start = new ParsedDateComponent();
            result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
            result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
            result.start.assign(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
            result.start.assign(Components.Minute, calendar.get(Calendar.MINUTE));
            result.start.assign(Components.Hour, calendar.get(Calendar.HOUR));
        }
        
        return result;
    }
}
