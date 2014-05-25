package com.wanasit.chrono.parser.jp;

import java.util.HashMap;
import java.util.Map;

public class JapaneseConstants {
    
    private static final Map<String, Integer> DAY_OF_WEEK = new HashMap<String, Integer>();
    
    public static int valueForDayOfWeek(String dayOfWeek) {
	return DAY_OF_WEEK.get(dayOfWeek);
    }
    
    
    static {
	DAY_OF_WEEK.put("日", 1);
	DAY_OF_WEEK.put("月", 2);
	DAY_OF_WEEK.put("火", 3);
	DAY_OF_WEEK.put("水", 4);
	DAY_OF_WEEK.put("木", 5);
	DAY_OF_WEEK.put("金", 6);
	DAY_OF_WEEK.put("土", 7);
    }
    
}
