package com.wanasit.chrono.parser.jp;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class JPCasualDateExpressionParserTest extends ParserTestAbstract {

    @Override
    public void setup() {
	super.setup();

	refDate = createDate(2012, 2, 5, 12, 0);
    }

    @Test
    public void test() throws IOException {

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.casual.parse("昨日", refDate);

	assertEquals(results.size(), 1);
	assertEquals("昨日", results.get(0).text);
	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2014, 4, 19, 12, 0), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.casual.parse("今日", refDate);

	assertEquals(results.size(), 1);
	assertEquals("今日", results.get(0).text);
	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2014, 4, 20, 12, 0), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.casual.parse("明日", refDate);

	assertEquals(results.size(), 1);
	assertEquals("明日", results.get(0).text);
	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2014, 4, 21, 12, 0), results.get(0).start);

	// =========================

	refDate = createDate(2014, 4, 20, 13, 0);
	results = Chrono.casual.parse("明後日", refDate);

	assertEquals(results.size(), 1);
	assertEquals("明後日", results.get(0).text);
	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2014, 4, 22, 12, 0), results.get(0).start);

	refDate = createDate(2014, 4, 20, 13, 0);
	results = Chrono.casual.parse("一昨日", refDate);

	assertEquals(results.size(), 1);
	assertEquals("一昨日", results.get(0).text);
	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2014, 4, 18, 12, 0), results.get(0).start);
	
	refDate = createDate(2014, 4, 20, 13, 0);
	results = Chrono.casual.parse("今朝", refDate);

	assertEquals(results.size(), 1);
	assertEquals("今朝", results.get(0).text);
	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2014, 4, 20, 6, 0), results.get(0).start);
    }

    @Test
    public void testWithinSentence() throws IOException {

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.casual.parse("昨日のこと", refDate);

	assertEquals(results.size(), 1);
	assertEquals("昨日", results.get(0).text);
	assertEquals(0, results.get(0).index);
	assertDateEquals(createDate(2014, 4, 19, 12, 0), results.get(0).start);
	
    }

}
