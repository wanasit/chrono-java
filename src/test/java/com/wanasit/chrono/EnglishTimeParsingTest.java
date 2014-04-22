package com.wanasit.chrono;

import static org.junit.Assert.*;
import static com.wanasit.chrono.ParserTestAbstract.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;

public class EnglishTimeParsingTest extends ParserTestAbstract {

    @Override
    public void setup() {
        super.setup();
    }

    @Test
    public void testSingleTimeExpFollowingDate() throws IOException {
        
        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.Parse("Something happen on 2014-04-18 3.00 AM", refDate);

        
        assertEquals("2014-04-18 3.00 AM", results.get(0).text);
        assertEquals(createDate(2014, 4, 18, 3, 0), results.get(0).start);
        
        
        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.Parse("Something happen on 2014-04-18 3.00 PM", refDate);

        
        assertEquals("2014-04-18 3.00 PM", results.get(0).text);
        assertEquals(createDate(2014, 4, 18, 15, 0), results.get(0).start);
        
        
        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.Parse("Something happen on 2014-04-18 13.00", refDate);

        
        assertEquals("2014-04-18 13.00", results.get(0).text);
        assertEquals(createDate(2014, 4, 18, 13, 0), results.get(0).start);
        
        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.Parse("Something happen on 2014-04-18 13:00 sa", refDate);

        
        assertEquals("2014-04-18 13:00", results.get(0).text);
        assertEquals(createDate(2014, 4, 18, 13, 0), results.get(0).start);
    }
    
    @Test
    public void testTimeRange() throws IOException {
        
        
        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.Parse("Something happen on 2014-04-18 13:00 - 16:00 as", refDate);
        
        assertEquals("2014-04-18 13:00 - 16:00", results.get(0).text);
        assertEquals(createDate(2014, 4, 18, 13, 0), results.get(0).start);
        assertEquals(createDate(2014, 4, 18, 16, 0), results.get(0).end);
        
        
        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.Parse("Something happen on 2014-04-18 7:00 - 8:00 AM...", refDate);
        
        assertEquals("2014-04-18 7:00 - 8:00 AM", results.get(0).text);
        assertEquals(createDate(2014, 4, 18, 7, 0), results.get(0).start);
        assertEquals(createDate(2014, 4, 18, 8, 0), results.get(0).end);
        
        
        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.Parse("Something happen on 2014-04-18 7:00 - 8:00 PM...", refDate);

        assertEquals("2014-04-18 7:00 - 8:00 PM", results.get(0).text);
        assertEquals(createDate(2014, 4, 18, 19, 0), results.get(0).start);
        assertEquals(createDate(2014, 4, 18, 20, 0), results.get(0).end);
    }
    
    
    
    
    
}
