package com.wanasit.chrono.filter;

import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOptions;
import com.wanasit.chrono.ParsedResult;

public class PrefixCheckFilter extends Filter {
 
    @Override
    public boolean isValid(String text, ChronoOptions options, ParsedResult result) {
        String prefix = text.substring(0, result.index); 
        return !Pattern.matches("(\\d+\\/)$", prefix);
    }
    
    
}
