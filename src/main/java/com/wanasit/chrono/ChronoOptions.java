package com.wanasit.chrono;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.wanasit.chrono.filter.LowProbabilityFormatFilter;
import com.wanasit.chrono.filter.PrefixCheckFilter;
import com.wanasit.chrono.parser.Parser;
import com.wanasit.chrono.parser.ParserAbstract;
import com.wanasit.chrono.parser.en.*;
import com.wanasit.chrono.parser.jp.JPDateAgoFormatParser;
import com.wanasit.chrono.parser.jp.JPDayOfWeekDateFormatParser;
import com.wanasit.chrono.parser.jp.JPStandartDateFormatParser;
import com.wanasit.chrono.parser.jp.JPTimeExpressionParser;
import com.wanasit.chrono.refiner.Refiner;
import com.wanasit.chrono.refiner.RefinerAbstract;
import com.wanasit.chrono.refiner.en.ENMergeDateAndTimeRefiner;
import com.wanasit.chrono.refiner.en.ENMergeDateRangeRefiner;
import com.wanasit.chrono.refiner.en.ENRemoveOverlapRefiner;

public class ChronoOptions {
    
    public static final ChronoOptions sharedOptions = standartOptions();
    
    
    public final List< Class<? extends Parser > > parserClasses  = new ArrayList<Class<? extends Parser>>();
    public final List< Class<? extends Refiner> > refinerClasses = new ArrayList<Class<? extends Refiner>>();
    
    public final Map<String, Integer> timezoneMap = new HashMap<String, Integer>();
    public Integer timezoneOffset = null;
    
    
    private ChronoOptions() {
        this.timezoneOffset = Calendar.getInstance().getTimeZone().getOffset(0);
    }
    
    
    
    public static ChronoOptions standartOptions() {
        ChronoOptions options = new ChronoOptions();
        
        // All Parsers
        options.parserClasses.add(ENInternationalStandardParser.class);
        options.parserClasses.add(ENMonthNameLittleEndianParser.class);
        options.parserClasses.add(ENMonthNameMiddleEndianParser.class);
        options.parserClasses.add(ENSlashBigEndianDateFormatParser.class);
        options.parserClasses.add(ENSlashDateFormatParser.class);
        options.parserClasses.add(ENTimeExpressionParser.class);
        options.parserClasses.add(JPStandartDateFormatParser.class);
        options.parserClasses.add(JPDayOfWeekDateFormatParser.class);
        options.parserClasses.add(JPDateAgoFormatParser.class);
        options.parserClasses.add(JPTimeExpressionParser.class);
        
        // Standard Pipeline
        options.refinerClasses.add(PrefixCheckFilter.class);
        options.refinerClasses.add(ENRemoveOverlapRefiner.class);
        options.refinerClasses.add(ENMergeDateAndTimeRefiner.class);
        options.refinerClasses.add(ENMergeDateRangeRefiner.class);
        options.refinerClasses.add(LowProbabilityFormatFilter.class);
        
        return options;
    }
    
    
    
    
    
    

    
    
}
