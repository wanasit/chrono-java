package com.wanasit.chrono;

import java.util.HashMap;
import java.util.Map;

public class ChronoConstants {
    
    public static Map<String, Integer> MONTH_NAMES;
    
    static {
        MONTH_NAMES = new HashMap<String, Integer>();
        MONTH_NAMES.put("january", 1);
        MONTH_NAMES.put("jan", 1);
        MONTH_NAMES.put("february", 2);
        MONTH_NAMES.put("feb", 2);
        MONTH_NAMES.put("march", 3);
        MONTH_NAMES.put("mar", 3);
        MONTH_NAMES.put("april", 4);
        MONTH_NAMES.put("apr", 4);
        MONTH_NAMES.put("may", 5);
        MONTH_NAMES.put("june", 6);
        MONTH_NAMES.put("jun", 6);
        MONTH_NAMES.put("july", 7);
        MONTH_NAMES.put("jul", 7);
        MONTH_NAMES.put("august", 8);
        MONTH_NAMES.put("aug", 8);
        MONTH_NAMES.put("september", 9);
        MONTH_NAMES.put("sep", 9);
        MONTH_NAMES.put("october", 10);
        MONTH_NAMES.put("oct", 10);
        MONTH_NAMES.put("november", 11);
        MONTH_NAMES.put("nov", 11);
        MONTH_NAMES.put("december", 12);
        MONTH_NAMES.put("dec", 12);
    }
}
