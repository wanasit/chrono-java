package com.wanasit.chrono.refiner.en;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.refiner.RefinerAbstract;
import com.wanasit.chrono.ParsedDateComponent;
import com.wanasit.chrono.ParsedResult;

public class ENMergeDateAndTimeRefiner extends RefinerAbstract {

    
    protected static boolean isDateOnly(ParsedResult result){
        return !result.start.isCertain(Components.Hour);
    }
    
    protected static boolean isTimeOnly(ParsedResult result){
        return !result.start.isCertain(Components.DayOfMonth) && !result.start.isCertain(Components.DayOfWeek);
    }
    
    protected static boolean ableToMerge(String text, ParsedResult prevResult, ParsedResult curResult){
        
        try{
            Pattern allowedPattern = Pattern.compile("\\s*(T|at|on|of|,|-)?\\s*", Pattern.CASE_INSENSITIVE);
            String  textBetween = text.substring(prevResult.index + prevResult.text.length(), curResult.index);
            return allowedPattern.matcher(textBetween).matches();
            
        }catch(Exception ex){ }
        
        return false;
    }
    
    protected static ParsedResult mergeResult(String text, ParsedResult dateResult, ParsedResult timeResult){
        
        ParsedDateComponent beginDate = dateResult.start;
        ParsedDateComponent beginTime = timeResult.start;
        
        ParsedDateComponent beginDateTime = new ParsedDateComponent(beginDate);
        beginDateTime.assign(Components.Hour, beginTime.get(Components.Hour));
        beginDateTime.assign(Components.Minute, beginTime.get(Components.Minute));
        beginDateTime.assign(Components.Second, beginTime.get(Components.Second));
        
        if (beginTime.isCertain(Components.Meridiem)) 
            beginDateTime.assign(Components.Meridiem, beginTime.get(Components.Meridiem));
        else if (beginTime.get(Components.Meridiem) != null)
            beginDateTime.imply(Components.Meridiem, beginTime.get(Components.Meridiem));
        
        dateResult.start = beginDateTime;
        
        if (dateResult.end != null || timeResult.end != null) {
            
            ParsedDateComponent endDate   = dateResult.end == null ? dateResult.start : dateResult.end;            
            ParsedDateComponent endTime   = timeResult.end == null ? timeResult.start : timeResult.end;

            ParsedDateComponent endDateTime = new ParsedDateComponent(endDate);
            endDateTime.assign(Components.Hour, endTime.get(Components.Hour));
            endDateTime.assign(Components.Minute, endTime.get(Components.Minute));
            endDateTime.assign(Components.Second, endTime.get(Components.Second));
            
            if (endTime.isCertain(Components.Meridiem)) 
        	endDateTime.assign(Components.Meridiem, endTime.get(Components.Meridiem));
            else if (beginTime.get(Components.Meridiem) != null)
        	endDateTime.imply(Components.Meridiem, endTime.get(Components.Meridiem));
            
            dateResult.end = endDateTime;
        }
        
        int startIndex = Math.min(dateResult.index, timeResult.index);
        int endIndex = Math.max(
                dateResult.index + dateResult.text.length(), 
                timeResult.index + timeResult.text.length());
        
        dateResult.index = startIndex;
        dateResult.text  = text.substring(startIndex, endIndex);
        dateResult.tags.addAll(timeResult.tags);
        dateResult.tags.add(ENMergeDateAndTimeRefiner.class.getName());
        return dateResult;
    }
    
    
    @Override
    public List<ParsedResult> refine(List<ParsedResult> results, String text, ChronoOption options) {
        
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
