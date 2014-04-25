package com.wanasit.chrono.parser.en;

import static com.wanasit.chrono.ParserTestAbstract.Assert.assertEquals;
import static org.junit.Assert.*;

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
    public void testSingleDate() {
        
        
        results = Chrono.Parse("August 10", refDate);

        assertEquals(1, results.size());

        
        assertEquals(0, results.get(0).index);
        assertEquals("August 10", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertEquals(createDate(2012, 8, 10, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
        
        
        //results = Chrono.Parse("32 August", refDate);
        //assertEquals(0, results.size());
    }

}
