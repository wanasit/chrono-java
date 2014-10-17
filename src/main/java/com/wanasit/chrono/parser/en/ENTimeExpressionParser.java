package com.wanasit.chrono.parser.en;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedDateComponent;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.parser.ParserAbstract;

public class ENTimeExpressionParser extends ParserAbstract {

    protected static String FIRST_REG_PATTERN = "(^|\\W|T)(at|from)?\\s*(\\d{1,2}|noon|midnight)((\\.|\\:|\\：)(\\d{2})((\\.|\\:|\\：)(\\d{2}))?)?(?!%)(\\s*(AM|PM|A\\.M\\.|P\\.M\\.))?(?=\\W|$)";
    protected static String SECOND_REG_PATTERN = "^\\s*(\\-|\\~|\\〜|to|\\?)\\s*(\\d{1,2})((\\.|\\:|\\：)(\\d{2})((\\.|\\:|\\：)(\\d{2}))?)?(?!%)(\\s*(AM|PM|A\\.M\\.|P\\.M\\.))?(?=\\W|$)";
    
    @Override
    protected Pattern pattern() {
        return Pattern.compile(FIRST_REG_PATTERN, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(refDate);
        if (matcher.group(2) == null && matcher.group(11) == null && matcher.group(6) == null)
            return null;
        
        ParsedResult result = new ParsedResult();
        result.tags.add(this.getClass().getName());
        result.start.imply(Components.DayOfMonth,   calendar.get(Calendar.DAY_OF_MONTH));
        result.start.imply(Components.Month, calendar.get(Calendar.MONTH)+1);
        result.start.imply(Components.Year,  calendar.get(Calendar.YEAR));
        
        int hour = 0;
        int minute = 0;
        int second = 0;
        int meridiem = -1;
        
        // ----- Hours
        if(matcher.group(3).toLowerCase().equals("noon")){
            meridiem = 1; 
            hour = 12;
        }else if(matcher.group(3).toLowerCase().equals("midnight")){
            meridiem = 0; 
            hour = 0;
        } else {
            hour = Integer.parseInt(matcher.group(3));
        }
        
        // ----- Minutes
        if(matcher.group(6) != null){ 
            minute = Integer.parseInt(matcher.group(6));
            if(minute >= 60) return null;
            
        } else if(hour > 100) { 
            minute = hour%100;
            hour   = hour/100;
        }
        
        // ----- Second
        if(matcher.group(9) != null){ 
            second = Integer.parseInt(matcher.group(9));
            if(second >= 60) return null;
        }
        
        // ----- AM & PM  
        if(matcher.group(11) != null) {
            if(hour > 12) return null;
            if(matcher.group(11).replace(".", "").toLowerCase().equals("am")){
                meridiem = 0; 
                if(hour == 12) hour = 0;
            }
            
            if(matcher.group(11).replace(".", "").toLowerCase().equals("pm")){
                meridiem = 1; 
                if(hour != 12) hour += 12;
            }
        }
        
        if (hour > 24) return null;
        if (hour >= 12) meridiem = 1;
        
        result.index = matcher.start() + matcher.group(1).length();
        result.text  = matcher.group().substring(matcher.group(1).length());
        
        result.start.assign(Components.Hour, hour);
        result.start.assign(Components.Minute, minute);
        result.start.assign(Components.Second, second);
            
        if (meridiem >= 0) 
            result.start.assign(Components.Meridiem, meridiem);
        
        Pattern secondPattern = Pattern.compile(SECOND_REG_PATTERN, Pattern.CASE_INSENSITIVE);
        matcher = secondPattern.matcher(text.substring(result.index + result.text.length()));
        if (!matcher.find()) {
            return result;
        }
        
        meridiem = -1;
        minute = 0;
        second = 0;
        hour = Integer.parseInt(matcher.group(2));
        
        // ----- Minute
        if (matcher.group(5)!= null) {
            
            minute = Integer.parseInt(matcher.group(5));
            if(minute >= 60) return result;
            
        } else if (hour > 100) {
            minute = hour%100;
            hour   = hour/100;
        }
        
        // ----- Second
        if (matcher.group(8) != null) { 
            second = Integer.parseInt(matcher.group(8));
            if(second >= 60) return result;
        }
        
        // ----- AM & PM 
        if (matcher.group(10) != null){
             
            if (hour > 12) return result;
            if (matcher.group(10).toLowerCase().equals("am")) {
                if(hour == 12) {
                    hour = 0;
                    if(result.end == null){
                        result.end = new ParsedDateComponent(result.start);
                    }
                    result.end.assign(Components.DayOfMonth, result.end.get(Components.DayOfMonth) + 1);
                }
            }
            
            if (matcher.group(10).toLowerCase().equals("pm")) {
                if (hour != 12) hour += 12;
            }
            
            if (!result.start.isCertain(Components.Meridiem)) {
                if(matcher.group(10).toLowerCase().equals("am")){
                    
                    result.start.imply(Components.Meridiem, 0);
                    
                    if (result.start.get(Components.Hour) == 12) 
                        result.start.assign(Components.Hour, 0);
                }
                if(matcher.group(10).toLowerCase().equals("pm")){
                    result.start.imply(Components.Meridiem, 1);
                    
                    if (result.start.get(Components.Hour) != 12) 
                        result.start.assign(Components.Hour, result.start.get(Components.Hour) + 12); 
                }
            }
        }
        
        if(hour >= 12) meridiem = 1;
        
        result.text = result.text + matcher.group();
        
        if(result.end == null){
            result.end = new ParsedDateComponent(result.start);
        }
        
        result.end.assign(Components.Hour, hour);
        result.end.assign(Components.Minute, minute);
        result.end.assign(Components.Second, second);
        
        if (meridiem >= 0) 
            result.end.assign(Components.Meridiem, meridiem);
        
	return result;

    }

}
