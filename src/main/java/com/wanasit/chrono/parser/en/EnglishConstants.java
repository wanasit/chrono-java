package com.wanasit.chrono.parser.en;

import java.util.HashMap;
import java.util.Map;

public class EnglishConstants {
    
    public static Map<String, Integer> DAY_OF_WEEK;
    public static Map<String, Integer> MONTHS;
    
    public static int valueForDayOfWeek(String dayOfWeek) {
	return DAY_OF_WEEK.get(dayOfWeek);
    }
    
    public static int valueForMonth(String month) {
	return MONTHS.get(month) - 1;
    }
    
    static {
        MONTHS = new HashMap<String, Integer>();
        MONTHS.put("january", 1);
        MONTHS.put("jan", 1);
        MONTHS.put("jan.", 1);
        MONTHS.put("february", 2);
        MONTHS.put("feb", 2);
        MONTHS.put("feb.", 2);
        MONTHS.put("march", 3);
        MONTHS.put("mar", 3);
        MONTHS.put("mar.", 3);
        MONTHS.put("april", 4);
        MONTHS.put("apr", 4);
        MONTHS.put("apr.", 4);
        MONTHS.put("may", 5);
        MONTHS.put("june", 6);
        MONTHS.put("jun", 6);
        MONTHS.put("jun.", 6);
        MONTHS.put("july", 7);
        MONTHS.put("jul", 7);
        MONTHS.put("jul.", 7);
        MONTHS.put("august", 8);
        MONTHS.put("aug", 8);
        MONTHS.put("aug.", 8);
        MONTHS.put("september", 9);
        MONTHS.put("sep", 9);
        MONTHS.put("sep.", 9);
        MONTHS.put("october", 10);
        MONTHS.put("oct", 10);
        MONTHS.put("oct.", 10);
        MONTHS.put("november", 11);
        MONTHS.put("nov", 11);
        MONTHS.put("nov.", 11);
        MONTHS.put("december", 12);
        MONTHS.put("dec", 12);
        MONTHS.put("dec.", 12);
    }
}
