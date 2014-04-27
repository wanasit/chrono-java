package com.wanasit.chrono;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class Parser {
    
    
    protected abstract Pattern pattern();
    
    protected abstract ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOptions options);
    
    public List<ParsedResult> execute(String text, Date refDate, ChronoOptions options) {
        
        List<ParsedResult> results = new LinkedList<ParsedResult>(); 
        
        int searchingIndex = 0;
        Matcher matcher = this.pattern().matcher(text);
        
        while (searchingIndex < text.length() && matcher.find(searchingIndex)) {
            
            ParsedResult result = this.extract(text, refDate, matcher, options);
            
            if(result == null){ 
                // Failed to extract the date result, MOVE ON
                searchingIndex = matcher.start() + 1;
                continue;
            }
            
            /*
            if ( !result.start.isCertain(Components.Hour) || 
               ( result.end != null && !result.end.isCertain(Components.Hour))) {
                
                ParsedResult timedResult = this.extractTimeFollowsResult(result);
                if (timedResult != null) result = timedResult; 
            }
            
            if ( !result.start.isCertain(Components.TimezoneOffset) || 
               ( result.end != null && !result.end.isCertain(Components.TimezoneOffset))) {
                
                ParsedResult timezonedResult = this.extractTimezoneFollowsResult(result);
                if (timezonedResult != null) result = timezonedResult; 
            }
            
            if (results.size() > 0) {
                
                ParsedResult prevResult = results.get(results.size() - 1);
                ParsedResult mergedResult = this.mergeResults(result, prevResult);
                if (mergedResult != null) result = mergedResult; 
            }*/
            
            searchingIndex = result.index + result.text.length() + 1;
            results.add(result);
        }
        
        return results;
    } 
    
}
