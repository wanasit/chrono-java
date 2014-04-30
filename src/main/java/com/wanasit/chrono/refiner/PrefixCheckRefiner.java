package com.wanasit.chrono.refiner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOptions;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.Refiner;

public class PrefixCheckRefiner extends Refiner {
 
    @Override
    public List<ParsedResult> refine(List<ParsedResult> results, String text, ChronoOptions options) {
        
        List<ParsedResult> filteredResults = new ArrayList<ParsedResult>();
        for (ParsedResult parsedResult : results) {
            String prefix = text.substring(0, parsedResult.index); 
            
            if (!Pattern.matches("(\\d+\\/)$", prefix)) {
                    filteredResults.add(parsedResult);
            }
        }
        

        
        return filteredResults;
    }
    
    
}
