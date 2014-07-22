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
    
    @Test
    public void testWithAllMonthName() throws IOException {

        //
        results = Chrono.Parse("January 12, 2014", refDate);
        assertDateEquals(createDate(2014, 1, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Jan 12, 2014", refDate);
        assertDateEquals(createDate(2014, 1, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Jan. 12, 2014", refDate);
        assertDateEquals(createDate(2014, 1, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("February 12, 2014", refDate);
        assertDateEquals(createDate(2014, 2, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Feb 12, 2014", refDate);
        assertDateEquals(createDate(2014, 2, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Feb. 12, 2014", refDate);
        assertDateEquals(createDate(2014, 2, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("March 12, 2014", refDate);
        assertDateEquals(createDate(2014, 3, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Mar 12, 2014", refDate);
        assertDateEquals(createDate(2014, 3, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Mar. 12, 2014", refDate);
        assertDateEquals(createDate(2014, 3, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("April 12, 2014", refDate);
        assertDateEquals(createDate(2014, 4, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Apr 12, 2014", refDate);
        assertDateEquals(createDate(2014, 4, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Apr. 12, 2014", refDate);
        assertDateEquals(createDate(2014, 4, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("May 12, 2014", refDate);
        assertDateEquals(createDate(2014, 5, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("June 12, 2014", refDate);
        assertDateEquals(createDate(2014, 6, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Jun 12, 2014", refDate);
        assertDateEquals(createDate(2014, 6, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Jun. 12, 2014", refDate);
        assertDateEquals(createDate(2014, 6, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("July 12, 2014", refDate);
        assertDateEquals(createDate(2014, 7, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Jul 12, 2014", refDate);
        assertDateEquals(createDate(2014, 7, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Jul. 12, 2014", refDate);
        assertDateEquals(createDate(2014, 7, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("August 12, 2014", refDate);
        assertDateEquals(createDate(2014, 8, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Aug 12, 2014", refDate);
        assertDateEquals(createDate(2014, 8, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Aug. 12, 2014", refDate);
        assertDateEquals(createDate(2014, 8, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("September 12, 2014", refDate);
        assertDateEquals(createDate(2014, 9, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Sep 12, 2014", refDate);
        assertDateEquals(createDate(2014, 9, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Sep. 12, 2014", refDate);
        assertDateEquals(createDate(2014, 9, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("October 12, 2014", refDate);
        assertDateEquals(createDate(2014, 10, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Oct 12, 2014", refDate);
        assertDateEquals(createDate(2014, 10, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Oct. 12, 2014", refDate);
        assertDateEquals(createDate(2014, 10, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("November 12, 2014", refDate);
        assertDateEquals(createDate(2014, 11, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("nov 12, 2014", refDate);
        assertDateEquals(createDate(2014, 11, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Nov. 12, 2014", refDate);
        assertDateEquals(createDate(2014, 11, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("December 12, 2014", refDate);
        assertDateEquals(createDate(2014, 12, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Dec 12, 2014", refDate);
        assertDateEquals(createDate(2014, 12, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("Dec. 12, 2014", refDate);
        assertDateEquals(createDate(2014, 12, 12, 12, 0), results.get(0).start);
        
    }


}
