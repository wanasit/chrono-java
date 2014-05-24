package com.wanasit.chrono.performance;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.wanasit.chrono.Chrono;

public class ChronoPerformanceTest {

    public static void testParsingEmptyText() {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            builder.append("Some text ");
        }

        String longText = builder.toString();

        long startTimestamp = System.currentTimeMillis();
        for (int i = 0; i < 10; i++)
            Chrono.Parse(longText);

        long endTimestamp = System.currentTimeMillis();
        System.out.println("Parsing " + longText.length()
                + " charactors text without date take time " + (endTimestamp - startTimestamp)/10
                + " ms");
    }

    public static void testParsingTextWithDates() {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            builder.append("This text contains date " + new Date() + " ");
            builder.append("plus some random text");
            builder.append("and some other random text");
        }

        String longText = builder.toString();

        long startTimestamp = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) Chrono.Parse(longText);

        long endTimestamp = System.currentTimeMillis();
        System.out.println("Parsing " + longText.length()
                + " charactors text with 100 dates take time " + (endTimestamp - startTimestamp)/10
                + " ms");
    }

    public static void testParsingDenseTextOfDates() {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 400; i++) {
            builder.append(new Date() + " ");
        }

        String longText = builder.toString();

        long startTimestamp = System.currentTimeMillis();
        Chrono.Parse(longText);

        long endTimestamp = System.currentTimeMillis();
        System.out.println("Parsing " + longText.length()
                + " charactors text of 400 dates take time " + (endTimestamp - startTimestamp)
                + " ms");
    }

    public static void testParsingDenseTextOfVariousDateFormats() {
        SimpleDateFormat format1 = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("MMMM dd, yyyy 'at' HH:mm");
        SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");

        StringBuilder builder = new StringBuilder("HEAD ");
        for (int i = 0; i < 100; i++) {
            builder.append(new Date() + " AND ");
            builder.append(format1.format(new Date()) + " AND ");
            builder.append(format2.format(new Date()) + " AND ");
            builder.append(format3.format(new Date()) + " AND ");
            builder.append("plus some random text");
        }

        String longText = builder.toString();

        long startTimestamp = System.currentTimeMillis();
        for (int i=0; i< 10; i++) Chrono.Parse(longText);

        long endTimestamp = System.currentTimeMillis();
        System.out.println("Parsing " + longText.length()
                + " charactors text of 400 dates of various formats take time "
                + (endTimestamp - startTimestamp)/10 + " ms");
    }

    public static void main(String args[]) {
        
        testParsingEmptyText();
        testParsingTextWithDates();
        testParsingDenseTextOfDates();
        testParsingDenseTextOfVariousDateFormats();
    }
}
