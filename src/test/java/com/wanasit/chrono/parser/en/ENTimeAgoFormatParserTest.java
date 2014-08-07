package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class ENTimeAgoFormatParserTest extends ParserTestAbstract {

    @Override
    public void setup() {
	super.setup();
	refDate = createDate(2012, 6, 5, 12, 0);
    }

    @Test
    public void testWithImpossibleTime() throws IOException {

	results = Chrono.Parse("0 hours ago", refDate);
	assertEquals(0, results.size());
    }

    @Test
    public void testHoursAgoExpression() {
	//==================
	refDate = createDate(2012, 6, 5, 12, 0);
	results = Chrono.Parse("1 hours ago", refDate);
	assertEquals(1, results.size());

	assertEquals(0, results.get(0).index);
	assertEquals("1 hours ago", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 6, 5, 11, 0), results.get(0).start);

	//==================
	results = Chrono.Parse("I saw him 3 hours ago", refDate);
	assertEquals(1, results.size());

	assertEquals(10, results.get(0).index);
	assertEquals("3 hours ago", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 6, 5, 9, 0), results.get(0).start);

	//==================
	results = Chrono.Parse("I saw him 12 hours ago", refDate);
	assertEquals(1, results.size());

	assertEquals(10, results.get(0).index);
	assertEquals("12 hours ago", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 6, 5, 0, 0), results.get(0).start);

	//==================
	results = Chrono.Parse("I saw him 13 hours ago", refDate);
	assertEquals(1, results.size());

	assertEquals(10, results.get(0).index);
	assertEquals("13 hours ago", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 6, 4, 23, 0), results.get(0).start);
    }

    @Test
    public void testDaysAgoExpression() {

	//==================
	refDate = createDate(2012, 6, 5, 12, 0);
	results = Chrono.Parse("1 days ago", refDate);
	assertEquals(1, results.size());

	assertEquals(0, results.get(0).index);
	assertEquals("1 days ago", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 6, 4, 12, 0), results.get(0).start);

	//==================
	refDate = createDate(2012, 6, 5, 12, 0);
	results = Chrono.Parse("I saw him 3 days ago", refDate);
	assertEquals(1, results.size());

	assertEquals(10, results.get(0).index);
	assertEquals("3 days ago", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 6, 2, 12, 0), results.get(0).start);

	//==================
	refDate = createDate(2012, 6, 5, 12, 0);
	results = Chrono.Parse("I saw him 5 days ago", refDate);
	assertEquals(1, results.size());

	assertEquals(10, results.get(0).index);
	assertEquals("5 days ago", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 5, 31, 12, 0), results.get(0).start);

	//==================
	refDate = createDate(2012, 6, 5, 12, 0);
	results = Chrono.Parse("I saw him 23 days ago", refDate);
	assertEquals(1, results.size());

	assertEquals(10, results.get(0).index);
	assertEquals("23 days ago", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 5, 13, 12, 0), results.get(0).start);

    }

    @Test
    public void testMinutesAgoExpression() {

	//==================
	refDate = createDate(2012, 6, 5, 12, 0);
	results = Chrono.Parse("1 minute ago", refDate);
	assertEquals(1, results.size());

	assertEquals(0, results.get(0).index);
	assertEquals("1 minute ago", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 6, 5, 11, 59), results.get(0).start);

	//==================
	results = Chrono.Parse("I saw him 3 minutes ago", refDate);
	assertEquals(1, results.size());

	assertEquals(10, results.get(0).index);
	assertEquals("3 minutes ago", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 6, 5, 11, 57), results.get(0).start);

	//==================
	refDate = createDate(2012, 6, 5, 12, 0);
	results = Chrono.Parse("60 minute ago", refDate);
	assertEquals(1, results.size());

	assertEquals(0, results.get(0).index);
	assertEquals("60 minute ago", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 6, 5, 11, 0), results.get(0).start);
    }

}
