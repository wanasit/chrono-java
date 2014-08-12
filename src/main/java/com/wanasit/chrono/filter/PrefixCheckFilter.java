package com.wanasit.chrono.filter;

import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedResult;

public class PrefixCheckFilter extends Filter {
 
    @Override
    public boolean isValid(String text, ChronoOption options, ParsedResult result) {
        String prefix = text.substring(0, result.index); 
        return !Pattern.matches("(\\d+\\/)$", prefix);
    }
    
    
}
