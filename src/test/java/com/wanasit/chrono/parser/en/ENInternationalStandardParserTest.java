package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;
import com.wanasit.chrono.ParsedDateComponent.Components;

public class ENInternationalStandardParserTest extends ParserTestAbstract {

    @Override
    public void setup() {
        super.setup();

        refDate = createDate(2012, 2, 5, 12, 0);
    }

    @Test
    public void testDate() throws IOException {
        
        results = Chrono.Parse("2013-02-07", refDate);

        assertEquals(1, results.size());

        
        assertEquals(0, results.get(0).index);
        assertEquals("2013-02-07", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2013, 2, 7, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
    }
    
    @Test
    public void testDateAndTime() throws IOException {
        
        results = Chrono.Parse("1994-11-05T13:15:30Z");
        assertEquals(1, results.size());
        assertEquals("1994-11-05T13:15:30Z", results.get(0).text);
        assertEquals(results.get(0).start.get(Components.Year), (Integer) 1994);
        assertEquals(results.get(0).start.get(Components.Month), (Integer) 11);
        assertEquals(results.get(0).start.get(Components.DayOfMonth), (Integer) 5);
        assertEquals(results.get(0).start.get(Components.Hour), (Integer) 13);
        assertEquals(results.get(0).start.get(Components.Minute), (Integer) 15);
        assertEquals(results.get(0).start.get(Components.Second), (Integer) 30);
        assertEquals(results.get(0).start.get(Components.TimezoneOffset), (Integer) 0);
        assertDateEquals(new Date(784041330000L), results.get(0).start);

        
        results = Chrono.Parse("1994-11-05T08:15:30-05:00");
        assertEquals(1, results.size());
        assertEquals("1994-11-05T08:15:30-05:00", results.get(0).text);
        assertEquals(results.get(0).start.get(Components.Year), (Integer) 1994);
        assertEquals(results.get(0).start.get(Components.Month), (Integer) 11);
        assertEquals(results.get(0).start.get(Components.DayOfMonth), (Integer) 5);
        assertEquals(results.get(0).start.get(Components.Hour), (Integer) 8);
        assertEquals(results.get(0).start.get(Components.Minute), (Integer) 15);
        assertEquals(results.get(0).start.get(Components.Second), (Integer) 30);
        assertEquals(results.get(0).start.get(Components.TimezoneOffset), (Integer) (-300));
        assertDateEquals(new Date(784041330000L), results.get(0).start);
        
        
        results = Chrono.Parse("1994-11-05T08:15:30-05:30");
        assertEquals(1, results.size());
        assertEquals("1994-11-05T08:15:30-05:30", results.get(0).text);
        assertEquals(results.get(0).start.get(Components.Year), (Integer) 1994);
        assertEquals(results.get(0).start.get(Components.Month), (Integer) 11);
        assertEquals(results.get(0).start.get(Components.DayOfMonth), (Integer) 5);
        assertEquals(results.get(0).start.get(Components.Hour), (Integer) 8);
        assertEquals(results.get(0).start.get(Components.Minute), (Integer) 15);
        assertEquals(results.get(0).start.get(Components.Second), (Integer) 30);
        assertEquals(results.get(0).start.get(Components.TimezoneOffset), (Integer) (-330));
        assertDateEquals(new Date(784043130000L), results.get(0).start);
        
        
        results = Chrono.Parse("1994-11-05T08:15:30-0530");
        assertEquals("1994-11-05T08:15:30-0530", results.get(0).text);
        assertDateEquals(new Date(784043130000L), results.get(0).start);
        
        
        results = Chrono.Parse("1994-11-05T08:15:30+0530");
        assertEquals("1994-11-05T08:15:30+0530", results.get(0).text);
        assertDateEquals(new Date(784003530000L), results.get(0).start);
        
        
        results = Chrono.Parse("1994-11-05T08:15:30.300+0530");
        assertEquals("1994-11-05T08:15:30.300+0530", results.get(0).text);
        assertDateEquals(new Date(784003530000L), results.get(0).start);
    }
    
    
    @Test
    public void testComponentCertainty() throws IOException {

	    refDate = createDate(2014, 4, 20, 12, 0);
	    results = Chrono.Parse("2014-04-18", refDate);
	
	    assertEquals(results.size(), 1);
	    assertTrue(results.get(0).start.isCertain(Components.DayOfMonth));
	    assertTrue(results.get(0).start.isCertain(Components.Month));
	    assertTrue(results.get(0).start.isCertain(Components.Year));
    }
}
