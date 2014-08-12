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

public class JPDayOfWeekDateFormatParser extends ParserAbstract {
    
    @Override
    protected Pattern pattern() {
        return Pattern.compile("(日|月|火|水|木|金|土)曜日?", Pattern.CASE_INSENSITIVE);
    }
    
    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {
        
        Calendar calendar = Calendar.getInstance(Locale.JAPANESE);
        calendar.setTime(refDate);
        
        ParsedResult result = new ParsedResult();
        result.text  = matcher.group();
        result.index = matcher.start();
        
        int dayOfWeek = JapaneseConstants.valueForDayOfWeek(matcher.group(1));
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek - today > 3) {
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
        }
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        
        result.start = new ParsedDateComponent();
        result.start.imply(Components.Year, calendar.get(Calendar.YEAR));
        result.start.imply(Components.Month, calendar.get(Calendar.MONTH) + 1);
        result.start.imply(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
        result.start.assign(Components.DayOfWeek, dayOfWeek);
        
        return result;
    }
}
