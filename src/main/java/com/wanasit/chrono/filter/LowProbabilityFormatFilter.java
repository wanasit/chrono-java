package com.wanasit.chrono.filter;

import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOptions;
import com.wanasit.chrono.ParsedResult;

public class LowProbabilityFormatFilter extends Filter {
 
    @Override
    public boolean isValid(String text, ChronoOptions options, ParsedResult result) {
        return !Pattern.matches("^\\d\\.\\d{1,2}$", result.text);
    }
    
    
}
