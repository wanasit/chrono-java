package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class ENTimeDealineFormatParserTest extends ParserTestAbstract {

    @Override
    public void setup() {
        super.setup();
        refDate = createDate(2012, 6, 5, 12, 0);
    }

    @Test
    public void testWithImpossibleTime() throws IOException {

        results = Chrono.Parse("in 0 hours", refDate);
        assertEquals(0, results.size());
    }

    @Test
    public void testHoursAgoExpression() {
        //==================
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("within 1 hour", refDate);
        assertEquals(1, results.size());

        assertEquals(0, results.get(0).index);
        assertEquals("within 1 hour", results.get(0).text);

        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 5, 13, 0), results.get(0).start);

        //==================
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("I'll see him in 3 hours", refDate);
        assertEquals(1, results.size());

        assertEquals(13, results.get(0).index);
        assertEquals("in 3 hours", results.get(0).text);

        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 5, 15, 0), results.get(0).start);

        //==================
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("I'll see him in 12 hours", refDate);
        assertEquals(1, results.size());

        assertEquals(13, results.get(0).index);
        assertEquals("in 12 hours", results.get(0).text);

        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 6, 0, 0), results.get(0).start);

    }

    @Test
    public void testDaysAgoExpression() {

        //==================
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("within 1 day", refDate);
        assertEquals(1, results.size());

        assertEquals(0, results.get(0).index);
        assertEquals("within 1 day", results.get(0).text);

        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 6, 12, 0), results.get(0).start);

        //==================
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("I'll see him in 3 days", refDate);
        assertEquals(1, results.size());

        assertEquals(13, results.get(0).index);
        assertEquals("in 3 days", results.get(0).text);

        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 8, 12, 0), results.get(0).start);

        //==================
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("I'll see him in 26 days", refDate);
        assertEquals(1, results.size());

        assertEquals(13, results.get(0).index);
        assertEquals("in 26 days", results.get(0).text);

        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 7, 1, 12, 0), results.get(0).start);

    }

    @Test
    public void testMinutesAgoExpression() {

        //==================
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("in 1 minute", refDate);
        assertEquals(1, results.size());

        assertEquals(0, results.get(0).index);
        assertEquals("in 1 minute", results.get(0).text);

        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 5, 12, 1), results.get(0).start);

        //==================

        results = Chrono.Parse("I'll see her within 3 minutes", refDate);
        assertEquals(1, results.size());

        assertEquals(13, results.get(0).index);
        assertEquals("within 3 minutes", results.get(0).text);

        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 5, 12, 3), results.get(0).start);

        //==================
        refDate = createDate(2012, 6, 5, 12, 0);
        results = Chrono.Parse("I'll see her within 60 minutes", refDate);
        assertEquals(1, results.size());

        assertEquals(13, results.get(0).index);
        assertEquals("within 60 minutes", results.get(0).text);

        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2012, 6, 5, 13, 0), results.get(0).start);

    }

}
