package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;
import com.wanasit.chrono.ParsedDateComponent.Components;

public class EnglishTimeParsingTest extends ParserTestAbstract {

    @Test
    public void testWithSingleDateExpression() throws IOException {

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen on 2014-04-18 3.00 AM", refDate);

	assertEquals("2014-04-18 3.00 AM", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 3, 0), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen on 2014-04-18 3.00 PM", refDate);

	assertEquals("2014-04-18 3.00 PM", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 15, 0), results.get(0).start);
	
	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen on 2014-04-18 6:56 p.m.", refDate);

	assertEquals("2014-04-18 6:56 p.m.", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 18, 56), results.get(0).start);
	
	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen on 2014-04-18 6:56 p.m. follow by something", refDate);

	assertEquals("2014-04-18 6:56 p.m.", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 18, 56), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen on 2014-04-18 13.00", refDate);

	assertEquals("2014-04-18 13.00", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 13, 0), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen on 2014-04-18 13:00 sa", refDate);

	assertEquals("2014-04-18 13:00", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 13, 0), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen on 2014-04-18 7:00 sa", refDate);

	assertEquals("2014-04-18 7:00", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 7, 0), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen on 13:00 of 2014-04-18 sa", refDate);

	assertEquals("13:00 of 2014-04-18", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 13, 0), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("At 13:00 on 2014-04-18, there was an accident", refDate);

	assertEquals("At 13:00 on 2014-04-18", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 13, 0), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("On 1 PM, There was a new about the project on April 18", refDate);

	assertEquals("1 PM", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 20, 13, 0), results.get(0).start);

	assertEquals("April 18", results.get(1).text);
	assertDateEquals(createDate(2014, 4, 18, 12, 0), results.get(1).start);
    }
    
    @Test
    public void testWithTimezones() throws IOException {

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("8:14:11 PM EST", refDate);

	assertEquals("8:14:11 PM EST", results.get(0).text);
	//assertDateEquals(createDate(2014, 4, 18, 3, 0), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("November 4, 2003 8:14:11 PM EST", refDate);

	assertEquals("November 4, 2003 8:14:11 PM EST", results.get(0).text);

    }

    @Test
    public void testWithRangeExpression() throws IOException {

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen on 2014-04-18 13:00 - 16:00 as", refDate);

	assertEquals("2014-04-18 13:00 - 16:00", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 13, 0), results.get(0).start);
	assertDateEquals(createDate(2014, 4, 18, 16, 0), results.get(0).end);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen on 2014-04-18 7:00 - 8:00 AM...", refDate);

	assertEquals("2014-04-18 7:00 - 8:00 AM", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 7, 0), results.get(0).start);
	assertDateEquals(createDate(2014, 4, 18, 8, 0), results.get(0).end);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen on 2014-04-18 7:00 - 8:00 PM...", refDate);

	assertEquals("2014-04-18 7:00 - 8:00 PM", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 19, 0), results.get(0).start);
	assertDateEquals(createDate(2014, 4, 18, 20, 0), results.get(0).end);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen on 2014-04-18 7:00 - 2014-04-19 8:00 PM...",
		refDate);

	assertEquals("2014-04-18 7:00 - 2014-04-19 8:00 PM", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 7, 0), results.get(0).start);
	assertDateEquals(createDate(2014, 4, 19, 20, 0), results.get(0).end);
	
	
	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen on 2014-04-18 7:00 - 2014-04-19 8:00 P.M..",
		refDate);

	assertEquals("2014-04-18 7:00 - 2014-04-19 8:00 P.M.", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 7, 0), results.get(0).start);
	assertDateEquals(createDate(2014, 4, 19, 20, 0), results.get(0).end);
	

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Something happen between April 18 - June 2 ...", refDate);

	assertEquals("April 18 - June 2", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 18, 12, 0), results.get(0).start);
	assertDateEquals(createDate(2014, 6, 2, 12, 0), results.get(0).end);

    }

    @Test
    public void testWithRealWorldData() throws IOException {

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("MRYN 2014-03-27 11:00 名刺を本のように収納できる名刺ホルダー「harrytoree」を使ってみました",
		refDate);

	assertEquals("2014-03-27 11:00", results.get(0).text);
	assertDateEquals(createDate(2014, 3, 27, 11, 0), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse(
		"GIGAZINE http://t.co/kkHfyzUVU0 Tetsuji Kondo（近藤 哲司） 2014-03-24 22:15 ", refDate);

	assertEquals("2014-03-24 22:15", results.get(0).text);
	assertDateEquals(createDate(2014, 3, 24, 22, 15), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("(2013/11/19) ポスト3.11の世界で、宮島達男が生命体のようなアートを作った理由。", refDate);

	assertEquals("2013/11/19", results.get(0).text);
	assertDateEquals(createDate(2013, 11, 19, 12, 0), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("岡村太一 2014-03-24 07:45 [134tweets]名刺を本のように収納できる名刺ホルダー", refDate);

	assertEquals("2014-03-24 07:45", results.get(0).text);
	assertDateEquals(createDate(2014, 3, 24, 7, 45), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("http://www.vogue.co.jp/lifestyle/culture/2014-03-19", refDate);

	assertEquals("2014-03-19", results.get(0).text);
	assertDateEquals(createDate(2014, 3, 19, 12, 0), results.get(0).start);

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono
		.Parse("http://www.forbes.com/sites/ceciliarodriguez/2014/04/08/shopping-in-paris-no-more-late-nights/",
			refDate);

	assertEquals("2014/04/08", results.get(0).text);
	assertDateEquals(createDate(2014, 4, 8, 12, 0), results.get(0).start);
	
	
	
	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Wed Apr 30 2014 16:27:18 GMT+0900 (JST)", refDate);
	assertEquals("Wed Apr 30 2014 16:27:18 GMT+0900 (JST)", results.get(0).text);
	assertDateEquals(new Date(1398842838000L), results.get(0).start);

	
	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("2014-04-07T21:44:15-0600", refDate);
	assertEquals("2014-04-07T21:44:15-0600", results.get(0).text);
	assertDateEquals(new Date(1396928655000L), results.get(0).start);

	refDate = createDate(2014, 7, 11, 12, 0);
	results = Chrono.Parse(
		"Our new programming challenge is going live this Saturday, July 12th at 5pm UTC.",
		refDate);

	assertEquals("Saturday, July 12th at 5pm UTC", results.get(0).text);
	//assertDateEquals(createDate(2014, 4, 8, 12, 0), results.get(0).start);

    }
    
    @Test
    public void testComponentCertainty() throws IOException {

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("2014-04-18 3.00 AM", refDate);
	
	assertEquals(results.size(), 1);
	assertTrue(results.get(0).start.isCertain(Components.DayOfMonth));
	assertTrue(results.get(0).start.isCertain(Components.Month));
	assertTrue(results.get(0).start.isCertain(Components.Year));
	assertTrue(results.get(0).start.isCertain(Components.Hour));
	assertTrue(results.get(0).start.isCertain(Components.Minute));
	assertTrue(results.get(0).start.isCertain(Components.Meridiem));
	
	
	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("July 12, 2014 6:56 p.m. ET", refDate);
	
	assertEquals(results.size(), 1);
	assertTrue(results.get(0).start.isCertain(Components.DayOfMonth));
	assertTrue(results.get(0).start.isCertain(Components.Month));
	assertTrue(results.get(0).start.isCertain(Components.Year));
	assertTrue(results.get(0).start.isCertain(Components.Hour));
	assertTrue(results.get(0).start.isCertain(Components.Minute));
	assertTrue(results.get(0).start.isCertain(Components.Meridiem));
    }
    
    @Test
    public void testDistinguishTimeWithRange() throws IOException {

	results = Chrono.Parse("Jul. 9, 2014 - 9:41 AM PDT", refDate);
	
	assertEquals(results.size(), 1);
	assertNull(results.get(0).end);
	assertTrue(results.get(0).start.isCertain(Components.DayOfMonth));
	assertTrue(results.get(0).start.isCertain(Components.Month));
	assertTrue(results.get(0).start.isCertain(Components.Year));
	assertTrue(results.get(0).start.isCertain(Components.Hour));
	assertTrue(results.get(0).start.isCertain(Components.Minute));
	assertTrue(results.get(0).start.isCertain(Components.Meridiem));
    }

    
    
}
