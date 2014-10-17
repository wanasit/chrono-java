package com.wanasit.chrono.parser.en;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ENWeekExpressionParserTest extends ParserTestAbstract {

    @Test
    public void test() throws IOException {

        refDate = createDate(2014, 10, 15, 12, 0);

        results = Chrono.casual.parse("this week", refDate);
        assertEquals(1, results.size());
        assertEquals("this week", results.get(0).text);
        assertDateEquals(createDate(2014, 10, 12, 12, 0), results.get(0).start);


        results = Chrono.casual.parse("last week", refDate);
        assertEquals(1, results.size());
        assertEquals("last week", results.get(0).text);
        assertDateEquals(createDate(2014, 10, 5, 12, 0), results.get(0).start);


        results = Chrono.casual.parse("next week", refDate);
        assertEquals(1, results.size());
        assertEquals("next week", results.get(0).text);
        assertDateEquals(createDate(2014, 10, 19, 12, 0), results.get(0).start);


        results = Chrono.casual.parse("last 2 weeks", refDate);
        assertEquals(1, results.size());
        assertEquals("last 2 weeks", results.get(0).text);
        assertDateEquals(createDate(2014, 9, 28, 12, 0), results.get(0).start);

        results = Chrono.casual.parse("next 2 weeks", refDate);
        assertEquals(1, results.size());
        assertEquals("next 2 weeks", results.get(0).text);
        assertDateEquals(createDate(2014, 10, 26, 12, 0), results.get(0).start);

        results = Chrono.casual.parse("next two weeks", refDate);
        assertEquals(1, results.size());
        assertEquals("next two weeks", results.get(0).text);
        assertDateEquals(createDate(2014, 10, 26, 12, 0), results.get(0).start);
    }

    @Test
    public void testCombinationWithWeekday() throws IOException {

        refDate = createDate(2014, 10, 16, 12, 0);

        results = Chrono.casual.parse("this week on Friday", refDate);
        assertEquals(1, results.size());
        assertEquals("this week on Friday", results.get(0).text);
        assertDateEquals(createDate(2014, 10, 17, 12, 0), results.get(0).start);

        results = Chrono.casual.parse("Friday this week", refDate);
        assertEquals(1, results.size());
        assertEquals("Friday this week", results.get(0).text);
        assertDateEquals(createDate(2014, 10, 17, 12, 0), results.get(0).start);

        results = Chrono.casual.parse("Friday last week", refDate);
        assertEquals(1, results.size());
        assertEquals("Friday last week", results.get(0).text);
        assertDateEquals(createDate(2014, 10, 10, 12, 0), results.get(0).start);

        results = Chrono.casual.parse("From Monday to Friday next week", refDate);
        assertEquals(1, results.size());
        assertEquals("Monday to Friday next week", results.get(0).text);
        assertDateEquals(createDate(2014, 10, 20, 12, 0), results.get(0).start);
        assertDateEquals(createDate(2014, 10, 24, 12, 0), results.get(0).end);

        results = Chrono.casual.parse("Saturday next four weeks", refDate);
        assertEquals(1, results.size());
        assertEquals("Saturday next four weeks", results.get(0).text);
        assertDateEquals(createDate(2014, 11, 15, 12, 0), results.get(0).start);
    }

    @Test
    public void testWithinSentence() throws IOException {

        refDate = createDate(2014, 10, 16, 12, 0);

        results = Chrono.casual.parse("I will be in Tokyo from Monday to Thursday next week", refDate);
        assertEquals(1, results.size());
        assertEquals(24, results.get(0).index);
        assertEquals("Monday to Thursday next week", results.get(0).text);
        assertDateEquals(createDate(2014, 10, 20, 12, 0), results.get(0).start);
        assertDateEquals(createDate(2014, 10, 23, 12, 0), results.get(0).end);
    }
}