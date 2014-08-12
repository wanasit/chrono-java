package com.wanasit.chrono.parser.en;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.parser.ParserAbstract;

public class ENCasualDateExpressionParser extends ParserAbstract {
    
    protected static String regPattern = "(today|tonight|tomorrow|yesterday|last\\s*night|([0-9]+)\\s*day(s)\\s*ago)(\\W|$)";
    
    @Override
    protected Pattern pattern() {
        return Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {
        // TODO Auto-generated method stub
        return null;
    } 
}
