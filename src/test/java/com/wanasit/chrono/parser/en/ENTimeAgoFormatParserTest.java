package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class ENTimeAgoFormatParserTest extends ParserTestAbstract {

    @Override
    public void setup() {
        super.setup();
        refDate = createDate(2012, 6, 5, 12, 0);
    }
    
    @Test
    public void testWithImpossibleTime() throws IOException {
        
        results = Chrono.Parse("0 hours ago", refDate);
        assertEquals(0, results.size());
    }
    
    @Test
    public void testHoursAgoExpression() {
        
	results = Chrono.Parse("1 hours ago", refDate);
	assertEquals(1, results.size());
	
	assertEquals(0, results.get(0).index);
        assertEquals("1 hours ago", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 5, 11, 0), results.get(0).start);
        
	results = Chrono.Parse("I saw him 3 hours ago", refDate);
	assertEquals(1, results.size());
	
	assertEquals(10, results.get(0).index);
        assertEquals("3 hours ago", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 5, 9, 0), results.get(0).start);
    }
    
    

}
