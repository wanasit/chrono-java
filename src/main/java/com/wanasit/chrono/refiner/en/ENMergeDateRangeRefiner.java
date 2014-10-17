package com.wanasit.chrono.refiner.en;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.refiner.RefinerAbstract;

public class ENMergeDateRangeRefiner extends RefinerAbstract {
    
    protected static boolean ableToMerge(String text, ParsedResult prevResult, ParsedResult curResult){
        
        try{
            Pattern allowedPattern = Pattern.compile("\\s*(and|to|-|ー)?\\s*", Pattern.CASE_INSENSITIVE);
            String  textBetween = text.substring(prevResult.index + prevResult.text.length(), curResult.index);
            
            return allowedPattern.matcher(textBetween).matches();
        
        }catch(StringIndexOutOfBoundsException ex){
            
        }
        
        return false;
    }
    
    protected static ParsedResult mergeResult(String text, ParsedResult fromResult, ParsedResult toResult){
        
        if (fromResult.start.date().after(toResult.start.date())) {
            ParsedResult tmp = fromResult;
            toResult = fromResult;
            fromResult = tmp;
        }
        
        fromResult.end = toResult.start;
        
        int startIndex = Math.min(fromResult.index, toResult.index);
        int endIndex = Math.max(
                fromResult.index + fromResult.text.length(), 
                toResult.index + toResult.text.length());
        
        fromResult.index = startIndex;
        fromResult.text  = text.substring(startIndex, endIndex);
        fromResult.tags.addAll(toResult.tags);
        fromResult.tags.add(ENMergeDateRangeRefiner.class.getName());
        return fromResult;
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
            
            if (prevResult.end == null && currResult.end == null 
                    && ableToMerge(text, prevResult, currResult)) {
                
                prevResult = mergeResult(text, prevResult, currResult);
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
