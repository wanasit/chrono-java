package com.wanasit.chrono.parser.jp;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class JPStandartDateFormatParserTest extends ParserTestAbstract {
    
    @Test
    public void testWithImpossibleDate() throws IOException {
        
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("8月32日", refDate);
        assertEquals(0, results.size());
        
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("2014年8月32日", refDate);
        assertEquals(0, results.size());
        
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("2014年２月２９日", refDate);
        assertEquals(0, results.size());
    }
    
    @Test
    public void testWithSingleDateExpression() {
        
        // Short
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("8月１０日", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("8月１０日", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 8, 10, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
           
        
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("Somthing on ２月２９日", refDate);

        assertEquals(1, results.size());
        
        assertEquals(12, results.get(0).index);
        assertEquals("２月２９日", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 2, 29, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("今年８月１０日", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("今年８月１０日", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 8, 10, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("来年８月１０日", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("来年８月１０日", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2013, 8, 10, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("２０１４年８月１０日", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("２０１４年８月１０日", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 10, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("平成２５年８月１０日", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("平成２５年８月１０日", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 10, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        
        
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("Somthing on ２月２９日", refDate);

        assertEquals(1, results.size());
        
        assertEquals(12, results.get(0).index);
        assertEquals("２月２９日", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 2, 29, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        refDate = createDate(2014, 5, 3, 12, 0);
        results = Chrono.Parse("先月１６日の旅客船沈没では", refDate);
        
        assertEquals(0, results.get(0).index);
        assertEquals("先月１６日", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 4, 16, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
    }
    
    @Test
    public void testWithSingleDateExpressionWithDayOfWeek() {
        
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("2014年5月19日(月)", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("2014年5月19日(月)", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 5, 19, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("2014年5月19日(月)19時30分配信", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("2014年5月19日(月)19時30分", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 5, 19, 19, 30), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        
    }
    
}
