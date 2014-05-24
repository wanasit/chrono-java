package com.wanasit.chrono;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;

import com.wanasit.chrono.ParsedDateComponent;
import com.wanasit.chrono.ParsedResult;


public class ParserTestAbstract {
    
    public static void assertDateEquals(Date date1, Date date2) {
        org.junit.Assert.assertEquals(date1.getTime(), date2.getTime(), 1000);
    }
    
    public static void assertDateEquals(Date date1, ParsedDateComponent component) {
        assertDateEquals(date1, component.date());
    }
    
    protected Date refDate = null;
    protected List<ParsedResult> results = null;
    
    @Before
    public void setup() {
        this.refDate = new Date();
    }
    
    public Date createDate(int year, int month, int date, int hour, int minute) {
        return createDate(year, month, date, hour, minute, 0);
    }
    
    protected Date createDate(int year, int month, int date, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, date, hour, minute, second);
        return calendar.getTime();
    }
    
    
}
