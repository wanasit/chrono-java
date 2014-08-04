package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;

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
    public void test() throws IOException {
        
        results = Chrono.Parse("2013-02-07", refDate);

        assertEquals(1, results.size());

        
        assertEquals(0, results.get(0).index);
        assertEquals("2013-02-07", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2013, 2, 7, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
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
