package com.wanasit.chrono.parser.en;

import java.util.HashMap;
import java.util.Map;

public class EnglishConstants {
    
    private static Map<String, Integer> DAY_OF_WEEK;
    private static Map<String, Integer> MONTHS;
    private static Map<String, Integer> NUMBERS;

    public static int valueForDayOfWeek(String dayOfWeek) {
	return DAY_OF_WEEK.get(dayOfWeek.toLowerCase());
    }
    
    public static int valueForMonth(String month) {
	return MONTHS.get(month.toLowerCase()) - 1;
    }

    public static int valueForNumber(String number) {
        return NUMBERS.getOrDefault(number,-1);
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
        
        DAY_OF_WEEK = new HashMap<String, Integer>();
        DAY_OF_WEEK.put("sunday", 1);
        DAY_OF_WEEK.put("sun", 1);
        DAY_OF_WEEK.put("monday", 2);
        DAY_OF_WEEK.put("mon", 2);
        DAY_OF_WEEK.put("tuesday", 3);
        DAY_OF_WEEK.put("tues", 3);
        DAY_OF_WEEK.put("tue", 3);
        DAY_OF_WEEK.put("wednesday", 4);
        DAY_OF_WEEK.put("wed", 4);
        DAY_OF_WEEK.put("thursday", 5);
        DAY_OF_WEEK.put("thurs", 5);
        DAY_OF_WEEK.put("thur", 5);
        DAY_OF_WEEK.put("thu", 5);
        DAY_OF_WEEK.put("friday", 6);
        DAY_OF_WEEK.put("fri", 6);
        DAY_OF_WEEK.put("saturday", 7);
        DAY_OF_WEEK.put("sat", 7);


        NUMBERS = new HashMap<String, Integer>();
        NUMBERS.put("one", 1);
        NUMBERS.put("two", 2);
        NUMBERS.put("three", 3);
        NUMBERS.put("four", 4);
        NUMBERS.put("five", 5);
        NUMBERS.put("six", 6);
        NUMBERS.put("seven", 7);
        NUMBERS.put("eight", 8);
        NUMBERS.put("nine", 9);
        NUMBERS.put("ten", 10);
        NUMBERS.put("eleven", 11);
        NUMBERS.put("twelve", 12);
    }
}
