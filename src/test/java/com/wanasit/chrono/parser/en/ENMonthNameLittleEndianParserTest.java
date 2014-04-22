package com.wanasit.chrono.parser.en;

import static com.wanasit.chrono.ParserTestAbstract.Assert.assertEquals;
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
    public void test() throws IOException {

        results = Chrono.Parse("10 August", refDate);

        assertEquals(1, results.size());

        
        assertEquals(0, results.get(0).index);
        assertEquals("10 August", results.get(0).text);
        
        assertNotNull(results.get(0).start);
        assertEquals(createDate(2012, 8, 10, 12, 0), results.get(0).start);
        
        assertNull(results.get(0).end);
    }
}
