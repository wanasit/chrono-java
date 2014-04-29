package com.wanasit.chrono.refiner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOptions;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.ParsedDateComponent;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.Refiner;

public class MergeDateAndTimeRefiner extends Refiner {

    
    protected static boolean isDateOnly(ParsedResult result){
        return !result.start.isCertain(Components.Hour);
    }
    
    protected static boolean isTimeOnly(ParsedResult result){
        return !result.start.isCertain(Components.Day) && !result.start.isCertain(Components.DayOfWeek);
    }
    
    protected static boolean ableToMerge(String text, ParsedResult prevResult, ParsedResult curResult){
        
        Pattern allowedPattern = Pattern.compile("\\s*(at|on|of|,)?\\s*", Pattern.CASE_INSENSITIVE);
        String  textBetween = text.substring(prevResult.index + prevResult.text.length(), curResult.index);
        
        return allowedPattern.matcher(textBetween).matches();
    }
    
    protected static ParsedResult mergeResult(String text, ParsedResult dateResult, ParsedResult timeResult){
        
        ParsedDateComponent beginDate = dateResult.start;
        ParsedDateComponent beginTime = timeResult.start;
        
        ParsedDateComponent beginDateTime = new ParsedDateComponent(beginDate);
        beginDateTime.assign(Components.Hour, beginTime.get(Components.Hour));
        beginDateTime.assign(Components.Minute, beginTime.get(Components.Minute));
        beginDateTime.assign(Components.Second, beginTime.get(Components.Second));
        
        dateResult.start = beginDateTime;
        
        if (dateResult.end != null || timeResult.end != null) {
            
            ParsedDateComponent endDate   = dateResult.end == null ? dateResult.start : dateResult.end;            
            ParsedDateComponent endTime   = timeResult.end == null ? timeResult.start : timeResult.end;

            ParsedDateComponent endDateTime = new ParsedDateComponent(endDate);
            endDateTime.assign(Components.Hour, endTime.get(Components.Hour));
            endDateTime.assign(Components.Minute, endTime.get(Components.Minute));
            endDateTime.assign(Components.Second, endTime.get(Components.Second));
            
            dateResult.end = endDateTime;
        }
        
        int startIndex = Math.min(dateResult.index, timeResult.index);
        int endIndex = Math.max(
                dateResult.index + dateResult.text.length(), 
                timeResult.index + timeResult.text.length());
        
        dateResult.index = startIndex;
        dateResult.text  = text.substring(startIndex, endIndex);
        return dateResult;
    }
    
    
    @Override
    public List<ParsedResult> refine(List<ParsedResult> results, String text, ChronoOptions options) {
        
        if (results.size() < 2) return results;
        
        List<ParsedResult> mergedResult = new ArrayList<ParsedResult>();
        
        
        ParsedResult currResult = null;
        ParsedResult prevResult = null;
        for (int i=1; i<results.size(); i++){
            
            currResult = results.get(i);
            prevResult = results.get(i-1);
            
            if (isDateOnly(prevResult) && isTimeOnly(currResult) 
                    && ableToMerge(text, prevResult, currResult)) {
                
                prevResult = mergeResult(text, prevResult, currResult);
                currResult = null;
                i += 1;
                
            } else if (isDateOnly(currResult) && isTimeOnly(prevResult)
                    && ableToMerge(text, prevResult, currResult)) {
                
                prevResult = mergeResult(text, currResult, prevResult);
                currResult = null;
                i += 1;
            }
            
            mergedResult.add(prevResult);
        }
        
        if (currResult != null) {
            mergedResult.add(currResult);
        }

        return mergedResult;
    }

}
