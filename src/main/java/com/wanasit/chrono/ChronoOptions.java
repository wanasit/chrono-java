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
    
    
    
    public static final ChronoOptions sharedOptions = new ChronoOptions();
    
    
    public List< Class<? extends Parser > > parserClasses = null;
    public List< Class<? extends Refiner> > refinerClasses = null;
    
    public Integer timezoneOffset = null;
    public Map<String, Integer> timezoneMap = null;
    
    
    public ChronoOptions() {
        
        this.parserClasses = new LinkedList<Class<? extends Parser>>();
        this.parserClasses.add(ENInternationalStandardParser.class);
        this.parserClasses.add(ENMonthNameLittleEndianParser.class);
        this.parserClasses.add(ENMonthNameMiddleEndianParser.class);
        this.parserClasses.add(ENSlashBigEndianDateFormatParser.class);
        this.parserClasses.add(ENSlashDateFormatParser.class);
        this.parserClasses.add(ENTimeExpressionParser.class);
        
        this.parserClasses.add(JPStandartDateFormatParser.class);
        
        this.refinerClasses = new LinkedList<Class<? extends Refiner>>();
        this.refinerClasses.add(PrefixCheckFilter.class);
        this.refinerClasses.add(RemoveOverlapRefiner.class);
        this.refinerClasses.add(MergeDateAndTimeRefiner.class);
        this.refinerClasses.add(MergeDateRangeRefiner.class);
        
        this.refinerClasses.add(LowProbabilityFormatFilter.class);
        
        this.timezoneOffset = Calendar.getInstance().getTimeZone().getOffset(0);
        
        this.timezoneMap = new HashMap<String, Integer>();
    }
    
    
    
}
