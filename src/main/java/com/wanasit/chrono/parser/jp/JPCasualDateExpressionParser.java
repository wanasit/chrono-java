package com.wanasit.chrono.parser.jp;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedDateComponent;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.parser.ParserAbstract;

public class JPCasualDateExpressionParser extends ParserAbstract {
    
    protected static String regPattern = ""
	    + "(今日|明日|昨日|明後日|一昨日|今朝)"
	    + "";
    
    @Override
    protected Pattern pattern() {
        return Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
    }
    
    
    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {
        
	ParsedResult result = new ParsedResult();
	result.text = matcher.group();
	result.index = matcher.start();

	Calendar calendar = Calendar.getInstance(Locale.JAPANESE);
	calendar.setTime(refDate);
	result.start = new ParsedDateComponent();

	if (matcher.group(1).equals("今朝")) {
	    
	    result.start.imply(Components.Hour, 6);
	    if (calendar.get(Calendar.HOUR_OF_DAY) < 6) { 
		// When say "this morning" on before 6 AM
		calendar.add(Calendar.DATE, -1);
	    }
	    
	} else if (matcher.group(1).equals("明日")) {
	    
	    calendar.add(Calendar.DATE, 1);

	} else if (matcher.group(1).equals("明後日")) {
	    
	    calendar.add(Calendar.DATE, 2);

	} else if (matcher.group(1).equals("昨日")) {
	    
	    calendar.add(Calendar.DATE, -1);
	} else if (matcher.group(1).equals("一昨日")) {
	    
	    calendar.add(Calendar.DATE, -2);
	}
	
	result.start.assign(Components.Year, calendar.get(Calendar.YEAR));
	result.start.assign(Components.Month, calendar.get(Calendar.MONTH) + 1);
	result.start.assign(Components.DayOfMonth, calendar.get(Calendar.DATE));
	return result;
    }
}
