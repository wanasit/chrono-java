package com.wanasit.chrono.parser.jp;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class JPDayOfWeekDateFormatParserTest extends ParserTestAbstract {

    @Test
    public void testWithSingleDateExpression() {
	
	refDate = createDate(2014, 5, 25, 12, 0);
	results = Chrono.Parse("土曜日はサンデーサイレンス系が優勢だった", refDate);
	assertEquals(1, results.size());
	assertEquals(0, results.get(0).index);
	assertEquals("土曜日", results.get(0).text);

	assertNotNull(results.get(0).start);
	assertDateEquals(createDate(2014, 5, 24, 12, 0), results.get(0).start);

    }

}
