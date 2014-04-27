package com.wanasit.chrono.parser.en;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOptions;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.Parser;

public class ENCasualDateExpressionParser extends Parser {
    
    protected static String regPattern = "(today|tonight|tomorrow|yesterday|last\\s*night|([0-9]+)\\s*day(s)\\s*ago)(\\W|$)";
    
    @Override
    protected Pattern pattern() {
        return Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOptions options) {
        // TODO Auto-generated method stub
        return null;
    } 
}
