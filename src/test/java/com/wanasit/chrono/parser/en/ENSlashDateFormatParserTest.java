package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class ENSlashDateFormatParserTest extends ParserTestAbstract {

    @Override
    public void setup() {
	super.setup();
	refDate = createDate(2012, 6, 5, 12, 0);
    }

    @Test
    public void testWithImpossibleDate() throws IOException {

	results = Chrono.Parse("8/32", refDate);
	assertEquals(0, results.size());

	results = Chrono.Parse("8/32/2014", refDate);
	assertEquals(0, results.size());

	results = Chrono.Parse("2/29/2014", refDate);
	assertEquals(0, results.size());
    }

    @Test
    public void testWithSingleDateExpression() {

	// Short
	results = Chrono.Parse("8/10", refDate);

	assertEquals(1, results.size());

	assertEquals(0, results.get(0).index);
	assertEquals("8/10", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 8, 10, 12, 0), results.get(0).start);

	assertNull(results.get(0).end);

	results = Chrono.Parse("Somthing on 2/29", refDate);

	assertEquals(1, results.size());

	assertEquals(12, results.get(0).index);
	assertEquals("2/29", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2012, 2, 29, 12, 0), results.get(0).start);

	assertNull(results.get(0).end);

	// Full
	results = Chrono.Parse("8/10/2014", refDate);

	assertEquals(1, results.size());

	assertEquals(0, results.get(0).index);
	assertEquals("8/10/2014", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2014, 8, 10, 12, 0), results.get(0).start);

	assertNull(results.get(0).end);

    }

}
