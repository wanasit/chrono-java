package com.wanasit.chrono;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.wanasit.chrono.filter.LowProbabilityFormatFilter;
import com.wanasit.chrono.filter.PrefixCheckFilter;
import com.wanasit.chrono.parser.en.*;
import com.wanasit.chrono.parser.jp.JPStandartDateFormatParser;
import com.wanasit.chrono.refiner.MergeDateAndTimeRefiner;
import com.wanasit.chrono.refiner.MergeDateRangeRefiner;
import com.wanasit.chrono.refiner.RemoveOverlapRefiner;

public class ChronoOptions {
    
    
    
    public static final ChronoOptions sharedOptions = createStandartOptions();
    
    public static ChronoOptions createStandartOptions() {
        ChronoOptions options = new ChronoOptions();
        
        // All Parsers
        options.parserClasses.add(ENInternationalStandardParser.class);
        options.parserClasses.add(ENMonthNameLittleEndianParser.class);
        options.parserClasses.add(ENMonthNameMiddleEndianParser.class);
        options.parserClasses.add(ENSlashBigEndianDateFormatParser.class);
        options.parserClasses.add(ENSlashDateFormatParser.class);
        options.parserClasses.add(ENTimeExpressionParser.class);
        options.parserClasses.add(JPStandartDateFormatParser.class);
        
        // Standard Pipeline
        options.refinerClasses.add(PrefixCheckFilter.class);
        options.refinerClasses.add(RemoveOverlapRefiner.class);
        options.refinerClasses.add(MergeDateAndTimeRefiner.class);
        options.refinerClasses.add(MergeDateRangeRefiner.class);
        options.refinerClasses.add(LowProbabilityFormatFilter.class);
        
        return options;
    }
    
    public List< Class<? extends Parser > > parserClasses = null;
    public List< Class<? extends Refiner> > refinerClasses = null;
    
    public Integer timezoneOffset = null;
    public Map<String, Integer> timezoneMap = null;
    
    
    protected ChronoOptions() {
        
        this.parserClasses = new LinkedList<Class<? extends Parser>>();
        this.refinerClasses = new LinkedList<Class<? extends Refiner>>();
        this.timezoneOffset = Calendar.getInstance().getTimeZone().getOffset(0);
        this.timezoneMap = new HashMap<String, Integer>();
    }
    
    
    
}
