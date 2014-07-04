package com.wanasit.chrono.parser.en;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.wanasit.chrono.Chrono;
import com.wanasit.chrono.ParserTestAbstract;

public class EnglishRandomParsingTest extends ParserTestAbstract {

    @Test
    public void testWithRandomDateExpression() throws IOException {

	refDate = createDate(2014, 4, 20, 12, 0);
	results = Chrono.Parse("Mon March 31, 2014", refDate);

	assertEquals("Mon March 31, 2014", results.get(0).text);
	assertDateEquals(createDate(2014, 3, 31, 12, 0), results.get(0).start);
    }
    
    
    @Test
    public void testWithUnlikelyRandomNumber() throws IOException {

	refDate = createDate(2014, 4, 20, 12, 0);
	
	results = Chrono.Parse("1", refDate);
	assertEquals(0, results.size());
	
	results = Chrono.Parse("1.2", refDate);
	assertEquals(0, results.size());
	
	results = Chrono.Parse("3.24", refDate);
	assertEquals(0, results.size());
	
	results = Chrono.Parse("3/2", refDate);
	assertEquals(0, results.size());
	
	results = Chrono.Parse("3321", refDate);
	assertEquals(0, results.size());
	
	results = Chrono.Parse("2532", refDate);
	assertEquals(0, results.size());
	
	results = Chrono.Parse(" 35.49 ", refDate);
	assertEquals(0, results.size());
	
	results = Chrono.Parse(" 0.5 ", refDate);
	assertEquals(0, results.size());
	
	results = Chrono.Parse(" 12.53% ", refDate);
	assertEquals(0, results.size());
	
	results = Chrono.Parse("%%%%%12.53 AM%%%%% ", refDate);
	assertEquals(1, results.size());
    }

}
