package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

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
    
    
    
    
    
}
