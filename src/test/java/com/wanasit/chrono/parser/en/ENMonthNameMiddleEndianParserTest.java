package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.ParserTestAbstract;
import com.wanasit.chrono.Chrono;

public class ENMonthNameMiddleEndianParserTest extends ParserTestAbstract {
    
    @Override
    public void setup() {
        super.setup();
        refDate = createDate(2012, 6, 5, 12, 0);
    }
    
    @Test
    public void testWithImpossibleDate() throws IOException {
        
        results = Chrono.Parse("August 32", refDate);
        assertEquals(0, results.size());
        
        results = Chrono.Parse("August 32, 2014", refDate);
        assertEquals(0, results.size());
        
        results = Chrono.Parse("Feb 29th 2014", refDate);
        assertEquals(0, results.size());
    }
    
    @Test
    public void testWithSingleDateExpression() {
        
        // Short
        results = Chrono.Parse("August 10", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("August 10", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 8, 10, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        // Full
        results = Chrono.Parse("August 10, 2014", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("August 10, 2014", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 10, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
        
    }
    
    
    @Test
    public void testWithRangeExpression() {
        
        // Short
        results = Chrono.Parse("August 10-12", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("August 10-12", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 8, 10, 12, 0), results.get(0).start);
        
        assertNotNull(results.get(0).end);
        assertDateEquals(createDate(2012, 8, 12, 12, 0), results.get(0).end);
        
        // Full
        results = Chrono.Parse("August 10 - 11, 2014", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("August 10 - 11, 2014", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 10, 12, 0), results.get(0).start);
        
        assertNotNull(results.get(0).end);
        assertDateEquals(createDate(2014, 8, 11, 12, 0), results.get(0).end);
        
    }

}
