package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class ENTimeExpressionParserTest extends ParserTestAbstract {

    @Override
    public void setup() {
        super.setup();
        refDate = createDate(2012, 6, 5, 12, 0);
    }
    
    @Test
    public void testWithImpossibleTime() throws IOException {
        
        results = Chrono.Parse("8:62", refDate);
        assertEquals(0, results.size());
        
        results = Chrono.Parse("25:12", refDate);
        assertEquals(0, results.size());
    }
    
    @Test
    public void testWithSingleTimeExpression() {
        
        // Short
        results = Chrono.Parse("8:10", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("8:10", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 5, 8, 10), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        // 
        results = Chrono.Parse("8:10 PM", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("8:10 PM", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 5, 20, 10), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        // Inside text          0123456789012345
        results = Chrono.Parse("Somthing happen at 8:10", refDate);

        assertEquals(1, results.size());
        
        assertEquals(16, results.get(0).index);
        assertEquals("at 8:10", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 5, 8, 10), results.get(0).start);
        
        assertNull(results.get(0).end);
        
    }
    
    
    @Test
    public void testWithRangeExpression() {
        
        // Short
	refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("8:10 - 9:10", refDate);

        assertEquals(1, results.size());
        
        assertEquals(0, results.get(0).index);
        assertEquals("8:10 - 9:10", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 5, 8, 10), results.get(0).start);
        
        assertNotNull(results.get(0).end);
        assertDateEquals(createDate(2012, 6, 5, 9, 10), results.get(0).end);
        
        
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("The event will go from 6:30pm to 11:00pm.", refDate);
        
        assertEquals(1, results.size());
        
        assertEquals("from 6:30pm to 11:00pm", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 5, 18, 30), results.get(0).start);
        
        assertNotNull(results.get(0).end);
        assertDateEquals(createDate(2012, 6, 5, 23, 0), results.get(0).end);
        
        
    }

}
