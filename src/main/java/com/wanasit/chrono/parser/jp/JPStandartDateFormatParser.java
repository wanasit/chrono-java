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

public class JPStandartDateFormatParser extends ParserAbstract {
    
    @Override
    protected Pattern pattern() {
        return Pattern.compile("(((平成|昭和)?([0-9０-９]{2,4})年|今年|去年|来年)|[^年]|^)([0-9０-９]{1,2}|今|先|来)月([0-9０-９]{1,2})日\\s*(?:\\((?:日|月|火|水|木|金|土)\\))?", Pattern.CASE_INSENSITIVE);
    }
    
    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {
        
        Calendar calendar = Calendar.getInstance(Locale.JAPANESE);
        calendar.setTime(refDate);
        
        ParsedResult result = new ParsedResult();
        
        int year  = calendar.get(Calendar.YEAR);
        if (matcher.group(4) != null) {
            year = Integer.parseInt(matcher.group(4)); 
            result.text  = matcher.group();
            result.index = matcher.start();
            
            if ("平成".equals(matcher.group(3))) {
                year += 1989;
            } else if ("昭和".equals(matcher.group(3))){
                year += 1926;
            }
        } else {
            
            if ("今年".equals(matcher.group(2))) {
                result.text  = matcher.group();
                result.index = matcher.start();
            } else if ("去年".equals(matcher.group(2))) {
                result.text  = matcher.group();
                result.index = matcher.start();
                year -= 1;
            } else if ("来年".equals(matcher.group(2))) {
                result.text  = matcher.group();
                result.index = matcher.start();
                year += 1;
            } else {
                result.text  = matcher.group().substring(matcher.group(1).length());
                result.index = matcher.start() + matcher.group(1).length();
            }
        }
        
        int month = calendar.get(Calendar.MONTH) + 1;
        if (matcher.group(5).equals("先")){
            month = month - 1;
        } else if (matcher.group(5).equals("来")){
            month = month + 1;
        }
        else if (!matcher.group(5).equals("今")){
            month = Integer.parseInt(matcher.group(5)); 
        }
        
        int day   = Integer.parseInt(matcher.group(6)); 
        
        calendar.set(year, month-1, day);
        if (calendar.get(Calendar.DAY_OF_MONTH) != day) return null;
        
        result.start = new ParsedDateComponent();
        result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
        result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
        result.start.assign(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
        return result;
    }
}
