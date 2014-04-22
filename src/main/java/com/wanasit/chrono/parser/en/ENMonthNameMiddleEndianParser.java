package com.wanasit.chrono.parser.en;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOptions;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.Parser;

public class ENMonthNameMiddleEndianParser extends Parser {

    protected static String regFullPattern  = "(\\W|^)((Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sun|Mon|Tue|Wed|Thu|Fri|Sat)\\s*,?\\s*)?(Jan|January|Feb|February|Mar|March|Apr|April|May|Jun|June|Jul|July|Aug|August|Sep|September|Oct|October|Nov|November|Dec|December)\\s*(([0-9]{1,2})(st|nd|rd|th)?\\s*(to|\\-)\\s*)?([0-9]{1,2})(st|nd|rd|th)?(,)?(\\s*[0-9]{4})(\\s*BE)?(\\W|$)";
    protected static String regShortPattern = "(\\W|^)((Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sun|Mon|Tue|Wed|Thu|Fri|Sat)\\s*,?\\s*)?(Jan|January|Feb|February|Mar|March|Apr|April|May|Jun|June|Jul|July|Aug|August|Sep|September|Oct|October|Nov|November|Dec|December)\\s*(([0-9]{1,2})(st|nd|rd|th)?\\s*(to|\\-)\\s*)?([0-9]{1,2})(st|nd|rd|th)?([^0-9]|$)";
    
    
    @Override
    protected Pattern pattern() {
        return Pattern.compile(regShortPattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOptions options) {
        
        Pattern fullPattern = Pattern.compile(regFullPattern);
        Matcher fullMatcher = fullPattern.matcher(text);
        if (fullMatcher.find(matcher.start())) {
            
            
            
        }
        
        
        
        
        // TODO Auto-generated method stub
        return null;
    }
    
}
