package com.wanasit.chrono.parser.jp;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.parser.ParserAbstract;

public class JPTimeExpressionParser extends ParserAbstract {

    protected static String FIRST_REG_PATTERN = "(?<!午前|午後|\\d)(午前|午後)?([0-9０-９]{1,2})時(([0-9]{2})分|半)?";
    
    @Override
    protected Pattern pattern() {
        return Pattern.compile(FIRST_REG_PATTERN, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(refDate);
        
        ParsedResult result = new ParsedResult();
        result.index = matcher.start();
        result.text = matcher.group();
        result.start.imply(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
        result.start.imply(Components.Month, calendar.get(Calendar.MONTH)+1);
        result.start.imply(Components.Year,  calendar.get(Calendar.YEAR));
        
        int hour = Integer.parseInt(matcher.group(2));
        int minute = 0;
        int meridiem = -1; 
        
        if(matcher.group(3) != null) { 
            
            if (matcher.group(3).equals("半"))
        	minute = 30;
            else
        	minute = Integer.parseInt(matcher.group(4));
            
            if(minute >= 60) return null;
        }
        
        // ----- AM & PM  
        if(matcher.group(1) != null) {
            if(hour > 12) return null;
            if(matcher.group(1).toLowerCase().equals("午前")){
                meridiem = 0; 
                if(hour == 12) hour = 0;
            }
            
            if(matcher.group(1) .toLowerCase().equals("午後")){
                meridiem = 1; 
                if(hour != 12) hour += 12;
            }
        }
        
        if (hour > 24) return null;
        if (hour >= 12) meridiem = 1;
        
        result.start.assign(Components.Hour, hour);
        result.start.assign(Components.Minute, minute);
        result.start.imply(Components.Second, 0);
        if (meridiem >= 0) 
            result.start.assign(Components.Meridiem, meridiem);
        
	return result;
    }

}
