package com.wanasit.chrono.parser.jp;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class JPDateAgoFormatParserTest extends ParserTestAbstract {

    @Test
    public void testWithSingleDateExpression() {

	refDate = createDate(2012, 6, 5, 12, 0);
	results = Chrono.Parse("49分前", refDate);

	assertEquals(1, results.size());

	assertEquals(0, results.get(0).index);
	assertEquals("49分前", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 6, 5, 11, 11), results.get(0).start);

	assertNull(results.get(0).end);

	refDate = createDate(2012, 6, 5, 12, 0);
	results = Chrono.Parse("2時間前", refDate);

	assertEquals(1, results.size());

	assertEquals(0, results.get(0).index);
	assertEquals("2時間前", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 6, 5, 10, 0), results.get(0).start);

	assertNull(results.get(0).end);

	refDate = createDate(2012, 6, 5, 12, 0);
	results = Chrono.Parse("2日前", refDate);

	assertEquals(1, results.size());

	assertEquals(0, results.get(0).index);
	assertEquals("2日前", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 6, 3, 12, 0), results.get(0).start);

	assertNull(results.get(0).end);
    }

}
