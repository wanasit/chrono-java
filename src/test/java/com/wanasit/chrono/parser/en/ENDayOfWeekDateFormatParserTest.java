package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class ENDayOfWeekDateFormatParserTest extends ParserTestAbstract {

    @Test
    public void test() {

        refDate = createDate(2014, 8, 18, 12, 0); // Monday
        results = Chrono.casual.parse("Monday", refDate);

        assertEquals(results.size(), 1);
        assertEquals("Monday", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 18, 12, 0), results.get(0).start);

        // =========================

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("Wednesday", refDate);

        assertEquals(results.size(), 1);
        assertEquals("Wednesday", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 20, 12, 0), results.get(0).start);

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("This Wednesday", refDate);

        assertEquals(results.size(), 1);
        assertEquals("This Wednesday", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 20, 12, 0), results.get(0).start);

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("last Wednesday", refDate);

        assertEquals(results.size(), 1);
        assertEquals("last Wednesday", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 13, 12, 0), results.get(0).start);

        // =========================

        refDate = createDate(2014, 8, 18, 12, 0); // Monday
        results = Chrono.casual.parse("Friday", refDate);

        assertEquals(results.size(), 1);
        assertEquals("Friday", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 15, 12, 0), results.get(0).start);

        // =========================

        refDate = createDate(2014, 8, 18, 12, 0); // Monday
        results = Chrono.casual.parse("Sunday", refDate);

        assertEquals(results.size(), 1);
        assertEquals("Sunday", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 17, 12, 0), results.get(0).start);

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("This Sunday", refDate);

        assertEquals(results.size(), 1);
        assertEquals("This Sunday", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 17, 12, 0), results.get(0).start);


        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("Next Sunday", refDate);

        assertEquals(results.size(), 1);
        assertEquals("Next Sunday", results.get(0).text);
        assertNotNull(results.get(0).start);
        assertDateEquals(createDate(2014, 8, 24, 12, 0), results.get(0).start);

    }

    @Test
    public void testWithAllEveryDayOfWeek() {

        refDate = createDate(2014, 8, 18, 12, 0); // Monday
        results = Chrono.casual.parse("Sunday", refDate);

        assertEquals("Sunday", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 17, 12, 0), results.get(0).start);

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("Sun", refDate);

        assertEquals("Sun", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 17, 12, 0), results.get(0).start);

        //=========

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("Monday", refDate);

        assertEquals("Monday", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 18, 12, 0), results.get(0).start);

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("Mon", refDate);

        assertEquals("Mon", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 18, 12, 0), results.get(0).start);

        //=========

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("Tuesday", refDate);

        assertEquals("Tuesday", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 19, 12, 0), results.get(0).start);

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("Tues", refDate);

        assertEquals("Tues", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 19, 12, 0), results.get(0).start);

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("Tue", refDate);

        assertEquals("Tue", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 19, 12, 0), results.get(0).start);

        //=========

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("Wednesday", refDate);

        assertEquals("Wednesday", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 20, 12, 0), results.get(0).start);

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("Wed", refDate);

        assertEquals("Wed", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 20, 12, 0), results.get(0).start);

        //=========

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("thursday", refDate);

        assertEquals("thursday", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 21, 12, 0), results.get(0).start);

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("thur", refDate);

        assertEquals("thur", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 21, 12, 0), results.get(0).start);

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("thu", refDate);

        assertEquals("thu", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 21, 12, 0), results.get(0).start);

        //=========

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("friday", refDate);

        assertEquals("friday", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 15, 12, 0), results.get(0).start);

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("fri", refDate);

        assertEquals("fri", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 15, 12, 0), results.get(0).start);

        //=========

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("saturday", refDate);

        assertEquals("saturday", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 16, 12, 0), results.get(0).start);

        refDate = createDate(2014, 8, 18, 12, 0);
        results = Chrono.casual.parse("sat", refDate);

        assertEquals("sat", results.get(0).text);
        assertDateEquals(createDate(2014, 8, 16, 12, 0), results.get(0).start);
    }

}
