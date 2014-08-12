package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;
import com.wanasit.chrono.ParsedDateComponent.Components;

public class ENCasualDateExprssionParserTest extends ParserTestAbstract {

    @Override
    public void setup() {
	super.setup();

	refDate = createDate(2012, 2, 5, 12, 0);
    }

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

}
