package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class ENSlashBigEndianDateFormatParserTest extends ParserTestAbstract{

    @Override
    public void setup() {
        super.setup();
        refDate = createDate(2012, 6, 5, 12, 0);
    }
    
    @Test
    public void testWithImpossibleDate() throws IOException {
        
        results = Chrono.Parse("2014/8/32", refDate);
        assertEquals(0, results.size());
        
        results = Chrono.Parse("2014/2/29", refDate);
        assertEquals(0, results.size());
    }
    
    @Test
    public void testWithSingleDateExpression() {
        
        results = Chrono.Parse("2014/8/10", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("2014/8/10", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 10, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        
        results = Chrono.Parse("somthing on 2014/8/10", refDate);

        assertEquals(1, results.size());
        
        assertEquals(12, results.get(0).index);
        assertEquals("2014/8/10", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 10, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
        
    }
    
}
