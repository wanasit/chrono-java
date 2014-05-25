package com.wanasit.chrono.parser.jp;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class JapaneseTimeParsingTest extends ParserTestAbstract {

    @Test
    public void testWithImpossibleDate() throws IOException {

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("午後1３時半です", refDate);
	assertEquals(0, results.size());

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("3３時半です", refDate);
	assertEquals(0, results.size());

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("午後1３時７０分です", refDate);
	assertEquals(0, results.size());
    }

    @Test
    public void testWithSingleDateExpression() throws IOException {

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("午後３時半です", refDate);

	assertEquals("午後３時半", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 20, 15, 30), results.get(0).start);

    }

}
