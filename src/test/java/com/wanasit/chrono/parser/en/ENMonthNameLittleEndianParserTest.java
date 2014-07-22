package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class ENMonthNameLittleEndianParserTest extends ParserTestAbstract {

    @Override
    public void setup() {
        super.setup();
        refDate = createDate(2012, 6, 5, 12, 0);
    }
    
    @Test
    public void testWithImpossibleDate() throws IOException {
        
        results = Chrono.Parse("32 August", refDate);
        assertEquals(0, results.size());
        
        results = Chrono.Parse("32 August 2014", refDate);
        assertEquals(0, results.size());
        
        results = Chrono.Parse("29 Feb 2014", refDate);
        assertEquals(0, results.size());
    }
    
    
    
    @Test
    public void testWithSingleDateExpression() throws IOException {

        // Short-Version
        results = Chrono.Parse("10 August", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("10 August", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 8, 10, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        
        // Full-Version
        results = Chrono.Parse("10 August 2014", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("10 August 2014", results.get(0).text);
        
        assertNull(results.get(0).end);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 10, 12, 0), results.get(0).start);
        
        
        // Full-Version inside text
        results = Chrono.Parse("INSIDE - 10 August 2014 - TEXT", refDate);

        assertEquals(1, results.size());
        
        assertEquals(9, results.get(0).index);
        assertEquals("10 August 2014", results.get(0).text);
        
        assertNull(results.get(0).end);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 10, 12, 0), results.get(0).start);
        
    }
    
    @Test
    public void testWithRangeExpression() throws IOException {

        //
        results = Chrono.Parse("10 - 12 August", refDate);

        assertEquals(1, results.size());

        assertEquals(0, results.get(0).index);
        assertEquals("10 - 12 August", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 8, 10, 12, 0), results.get(0).start);
        
        assertNotNull(results.get(0).end);
        assertDateEquals(createDate(2012, 8, 12, 12, 0), results.get(0).end);
        
        
        // 
        results = Chrono.Parse("10 - 12 August 2014", refDate);

        assertEquals(1, results.size());

        assertEquals(0, results.get(0).index);
        assertEquals("10 - 12 August 2014", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 10, 12, 0), results.get(0).start);
        
        assertNotNull(results.get(0).end);
        assertDateEquals(createDate(2014, 8, 12, 12, 0), results.get(0).end);
        
        
        // 
        results = Chrono.Parse("INSIDE - 10 - 12 August 2014 - TEXT", refDate);
        
        assertEquals(9, results.get(0).index);
        assertEquals("10 - 12 August 2014", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 10, 12, 0), results.get(0).start);
        
        assertNotNull(results.get(0).end);
        assertDateEquals(createDate(2014, 8, 12, 12, 0), results.get(0).end);
    }
    
    @Test
    public void testWithAllMonthName() throws IOException {

        //
        results = Chrono.Parse("12 January 2014", refDate);
        assertDateEquals(createDate(2014, 1, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Jan 2014", refDate);
        assertDateEquals(createDate(2014, 1, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Jan. 2014", refDate);
        assertDateEquals(createDate(2014, 1, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 February 2014", refDate);
        assertDateEquals(createDate(2014, 2, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Feb 2014", refDate);
        assertDateEquals(createDate(2014, 2, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Feb. 2014", refDate);
        assertDateEquals(createDate(2014, 2, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 March 2014", refDate);
        assertDateEquals(createDate(2014, 3, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Mar 2014", refDate);
        assertDateEquals(createDate(2014, 3, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Mar. 2014", refDate);
        assertDateEquals(createDate(2014, 3, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 April 2014", refDate);
        assertDateEquals(createDate(2014, 4, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Apr 2014", refDate);
        assertDateEquals(createDate(2014, 4, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Apr. 2014", refDate);
        assertDateEquals(createDate(2014, 4, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 May 2014", refDate);
        assertDateEquals(createDate(2014, 5, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 June 2014", refDate);
        assertDateEquals(createDate(2014, 6, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Jun 2014", refDate);
        assertDateEquals(createDate(2014, 6, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Jun. 2014", refDate);
        assertDateEquals(createDate(2014, 6, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 July 2014", refDate);
        assertDateEquals(createDate(2014, 7, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Jul 2014", refDate);
        assertDateEquals(createDate(2014, 7, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Jul. 2014", refDate);
        assertDateEquals(createDate(2014, 7, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 August 2014", refDate);
        assertDateEquals(createDate(2014, 8, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Aug 2014", refDate);
        assertDateEquals(createDate(2014, 8, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Aug. 2014", refDate);
        assertDateEquals(createDate(2014, 8, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 September 2014", refDate);
        assertDateEquals(createDate(2014, 9, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Sep 2014", refDate);
        assertDateEquals(createDate(2014, 9, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Sep. 2014", refDate);
        assertDateEquals(createDate(2014, 9, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 October 2014", refDate);
        assertDateEquals(createDate(2014, 10, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Oct 2014", refDate);
        assertDateEquals(createDate(2014, 10, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Oct. 2014", refDate);
        assertDateEquals(createDate(2014, 10, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 November 2014", refDate);
        assertDateEquals(createDate(2014, 11, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 nov 2014", refDate);
        assertDateEquals(createDate(2014, 11, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Nov. 2014", refDate);
        assertDateEquals(createDate(2014, 11, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 December 2014", refDate);
        assertDateEquals(createDate(2014, 12, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Dec 2014", refDate);
        assertDateEquals(createDate(2014, 12, 12, 12, 0), results.get(0).start);
        
        results = Chrono.Parse("12 Dec. 2014", refDate);
        assertDateEquals(createDate(2014, 12, 12, 12, 0), results.get(0).start);
        
    }


}
