package com.wanasit.chrono.parser.en;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ENWeekExpressionParserTest extends ParserTestAbstract {

    @Test
    public void test() throws IOException {

        refDate = createDate(2014, 10, 15, 12, 0);

        results = Chrono.casual.parse("this week", refDate);
        assertEquals(1, results.size());

        assertEquals(0, results.get(0).index);
        assertEquals("this week", results.get(0).text);
        assertDateEquals(createDate(2014, 10, 12, 12, 0), results.get(0).start);


        results = Chrono.casual.parse("last week", refDate);
        assertEquals(1, results.size());

        assertEquals(0, results.get(0).index);
        assertEquals("this week", results.get(0).text);
        assertDateEquals(createDate(2014, 10, 5, 12, 0), results.get(0).start);


        results = Chrono.casual.parse("last week", refDate);
        assertEquals(1, results.size());

        assertEquals(0, results.get(0).index);
        assertEquals("next week", results.get(0).text);
        assertDateEquals(createDate(2014, 10, 19, 12, 0), results.get(0).start);
    }

    @Test
    public void testWithinSentence() throws IOException {


    }

    @Test
    public void testCaseSensitiveTest() throws IOException {


    }

}