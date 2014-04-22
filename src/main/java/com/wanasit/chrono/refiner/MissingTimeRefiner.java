package com.wanasit.chrono.refiner;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOptions;
import com.wanasit.chrono.ParsedDateComponent;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.Refiner;

public class MissingTimeRefiner extends Refiner {
    
    protected static String FIRST_REG_PATTERN = "\\s*,?\\s*(at|from)?\\s*,?\\s*([0-9]{1,4}|noon|midnight)((\\.|\\:|\\：)([0-9]{1,2})((\\.|\\:|\\：)([0-9]{1,2}))?)?(\\s*(AM|PM))?(\\W|$)";
    protected static String SECOND_REG_PATTERN = "\\s*(\\-|\\~|\\〜|to|\\?)\\s*([0-9]{1,4})((\\.|\\:|\\：)([0-9]{1,2})((\\.|\\:|\\：)([0-9]{1,2}))?)?(\\s*(AM|PM))?";
    
    
    protected static Pattern sharedFirstPattern = null;
    protected static Pattern sharedSecondPattern = null;
    
    static {
        sharedFirstPattern = Pattern.compile(FIRST_REG_PATTERN, Pattern.CASE_INSENSITIVE);
        sharedSecondPattern = Pattern.compile(SECOND_REG_PATTERN, Pattern.CASE_INSENSITIVE);
    }
    
    protected ParsedResult extractTimeFollowsResult(String text, ParsedResult result) {
        
        Matcher matcher = sharedFirstPattern.matcher(text);
        if (!matcher.find(result.index + result.text.length())) return result;
        
        int hour = 0;
        int minute = 0;
        int second = 0;
        int meridiem = -1;
        
        // ----- Hours
        if(matcher.group(2).toLowerCase().equals("noon")){
            meridiem = 1; 
            hour = 12;
        }else if(matcher.group(2).toLowerCase().equals("midnight")){
            meridiem = 0; 
            hour = 0;
        } else {
            hour = Integer.parseInt(matcher.group(2));
        }
        
        // ----- Minutes
        if(matcher.group(5) != null){ 
            minute = Integer.parseInt(matcher.group(5));
            if(minute >= 60) return null;
            
        } else if(hour > 100) { 
            minute = hour%100;
            hour   = hour/100;
        }
        
        // ----- Second
        if(matcher.group(8) != null){ 
            second = Integer.parseInt(matcher.group(8));
            if(second >= 60) return result;
        }
        
        // ----- AM & PM  
        if(matcher.group(10) != null) {
            if(hour > 12) return null;
            if(matcher.group(10).toLowerCase().equals("am")){
                meridiem = 0; 
                if(hour == 12) hour = 0;
            }
            
            if(matcher.group(10) .toLowerCase().equals("pm")){
                meridiem = 1; 
                if(hour != 12) hour += 12;
            }
        }
        
        if (hour > 24) return result;
        if (hour >= 12) meridiem = 1;
        
        result.text = result.text + matcher.group().substring(0, 
                matcher.group().length() - matcher.group(11).length());
        
        if (!result.start.isCertain(Components.Hour)) {
            result.start.assign(Components.Hour, hour);
            result.start.assign(Components.Minute, minute);
            result.start.assign(Components.Second, second);
            
            if (meridiem >= 0) 
                result.start.assign(Components.Meridiem, meridiem);
        }
        
        matcher = sharedSecondPattern.matcher(text);
        if (!matcher.find(result.index + result.text.length())) {
            
            if(result.end != null && result.end.isCertain(Components.Hour)){
                result.end.assign(Components.Hour, hour);
                result.end.assign(Components.Minute, minute);
                result.end.assign(Components.Second, second);
                
                if (meridiem >= 0) 
                    result.end.assign(Components.Meridiem, meridiem);
            }
            
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
            if(matcher.group(10) == null) return result;
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
                    result.end.assign(Components.Day, result.end.get(Components.Day) + 1);
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

    @Override
    public List<ParsedResult> refine(List<ParsedResult> results, String text, ChronoOptions options) {
        // TODO Auto-generated method stub
        
        for (ParsedResult result : results){
            if ( !result.start.isCertain(Components.Hour) || 
               ( result.end != null && !result.end.isCertain(Components.Hour))) {
                     
                result = this.extractTimeFollowsResult(text, result);
            }
        }
        
        return results;
    }

}
