package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class ENCasualDateExprssionParserTest extends ParserTestAbstract {

    @Test
    public void test() throws IOException {

        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.casual.parse("yesterday", refDate);

        assertEquals(results.size(), 1);
        assertEquals("yesterday", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 4, 19, 12, 0), results.get(0).start);

        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.casual.parse("today", refDate);

        assertEquals(results.size(), 1);
        assertEquals("today", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 4, 20, 12, 0), results.get(0).start);

        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.casual.parse("tomorrow", refDate);

        assertEquals(results.size(), 1);
        assertEquals("tomorrow", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 4, 21, 12, 0), results.get(0).start);

        // =========================

        refDate = createDate(2014, 4, 20, 13, 0);
        results = Chrono.casual.parse("tonight", refDate);

        assertEquals(results.size(), 1);
        assertEquals("tonight", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 4, 21, 0, 0), results.get(0).start);

        refDate = createDate(2014, 4, 21, 1, 0);
        results = Chrono.casual.parse("tonight", refDate);
        assertDateEquals(createDate(2014, 4, 21, 0, 0), results.get(0).start);

        // =========================

        refDate = createDate(2014, 4, 20, 13, 0);
        results = Chrono.casual.parse("last night", refDate);

        assertEquals(results.size(), 1);
        assertEquals("last night", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 4, 20, 0, 0), results.get(0).start);

        refDate = createDate(2014, 4, 21, 1, 0);
        results = Chrono.casual.parse("last night", refDate);
        assertDateEquals(createDate(2014, 4, 20, 0, 0), results.get(0).start);

        refDate = createDate(2014, 4, 21, 6, 0);
        results = Chrono.casual.parse("last night", refDate);
        assertDateEquals(createDate(2014, 4, 21, 0, 0), results.get(0).start);

        // =========================

        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.casual.parse("this afternoon", refDate);

        assertEquals(results.size(), 1);
        assertEquals("this afternoon", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 4, 20, 15, 0), results.get(0).start);

        refDate = createDate(2014, 4, 20, 20, 0);
        results = Chrono.casual.parse("this afternoon", refDate);
        assertDateEquals(createDate(2014, 4, 20, 15, 0), results.get(0).start);

        // =========================

        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.casual.parse("this evening", refDate);

        assertEquals(results.size(), 1);
        assertEquals("this evening", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 4, 20, 18, 0), results.get(0).start);

        refDate = createDate(2014, 4, 20, 20, 0);
        results = Chrono.casual.parse("this evening", refDate);
        assertDateEquals(createDate(2014, 4, 20, 18, 0), results.get(0).start);

        // =========================

        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.casual.parse("this morning", refDate);

        assertEquals(results.size(), 1);
        assertEquals("this morning", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 4, 20, 6, 0), results.get(0).start);

        refDate = createDate(2014, 4, 20, 20, 0);
        results = Chrono.casual.parse("this morning", refDate);
        assertDateEquals(createDate(2014, 4, 20, 6, 0), results.get(0).start);

        refDate = createDate(2014, 4, 20, 1, 0);
        results = Chrono.casual.parse("this morning", refDate);
        assertDateEquals(createDate(2014, 4, 19, 6, 0), results.get(0).start);
    }

    @Test
    public void testWithinSentence() throws IOException {

        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.casual.parse("I'm going to work tomorrow.", refDate);

        assertEquals(results.size(), 1);
        assertEquals("tomorrow", results.get(0).text);
        assertEquals(18, results.get(0).index);
        assertDateEquals(createDate(2014, 4, 21, 12, 0), results.get(0).start);

        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.casual.parse("I'm going to work tmr.", refDate);

        assertEquals(results.size(), 1);
        assertEquals("tmr", results.get(0).text);
        assertEquals(18, results.get(0).index);
        assertDateEquals(createDate(2014, 4, 21, 12, 0), results.get(0).start);

        refDate = createDate(2014, 4, 20, 12, 0);
        results = Chrono.casual.parse("I'm going to work tmr at 7.00 am.", refDate);

        assertEquals(results.size(), 1);
        assertEquals("tmr at 7.00 am", results.get(0).text);
        assertEquals(18, results.get(0).index);
        assertDateEquals(createDate(2014, 4, 21, 7, 0), results.get(0).start);
    }

    @Test
    public void testCaseSensitiveTest() throws IOException {

        refDate = createDate(2014, 4, 21, 1, 0);
        results = Chrono.casual.parse("Last Night", refDate);
        assertDateEquals(createDate(2014, 4, 20, 0, 0), results.get(0).start);
        assertEquals(results.get(0).text, "Last Night");

        refDate = createDate(2014, 4, 21, 1, 0);
        results = Chrono.casual.parse("Tonight", refDate);
        assertDateEquals(createDate(2014, 4, 21, 0, 0), results.get(0).start);
        assertEquals(results.get(0).text, "Tonight");

        refDate = createDate(2014, 4, 20, 1, 0);
        results = Chrono.casual.parse("This Morning", refDate);
        assertDateEquals(createDate(2014, 4, 19, 6, 0), results.get(0).start);
        assertEquals(results.get(0).text, "This Morning");
    }


}
