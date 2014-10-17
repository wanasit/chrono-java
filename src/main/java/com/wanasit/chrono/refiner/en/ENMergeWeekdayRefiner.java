package com.wanasit.chrono.refiner.en;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedDateComponent;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.parser.en.ENDayOfWeekDateFormatParser;
import com.wanasit.chrono.parser.en.ENWeekExpressionParser;
import com.wanasit.chrono.refiner.RefinerAbstract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class ENMergeWeekdayRefiner extends RefinerAbstract {

    private static String WEEK_TAG = ENWeekExpressionParser.class.getName();
    private static String WEEK_DAY_TAG = ENDayOfWeekDateFormatParser.class.getName();

    private static Pattern ALLOWED_PATTERN_BETWEEN = Pattern.compile("\\s*('s|on)?\\s*", Pattern.CASE_INSENSITIVE);


    protected static boolean checkPatternBetween(String text, ParsedResult prevResult, ParsedResult curResult) {

        try {
            String textBetween = text.substring(prevResult.index + prevResult.text.length(), curResult.index);
            return ALLOWED_PATTERN_BETWEEN.matcher(textBetween).matches();

        } catch (StringIndexOutOfBoundsException ex) {

        }

        return false;
    }

    protected static boolean checkMergingCompatible(ParsedResult weekResult, ParsedResult weekDayResult) {

        if (weekResult.start.isCertain(Components.DayOfMonth) || weekResult.end != null)
            return false;

        if (weekDayResult.start.isCertain(Components.DayOfMonth))
            return false;

        if (weekDayResult.end != null && weekDayResult.start.isCertain(Components.DayOfMonth))
            return false;

        return true;
    }

    protected static ParsedResult mergeResult(String text, ParsedResult weekResult, ParsedResult weekDayResult) {

        ParsedResult mergedResult = new ParsedResult();
        mergedResult.tags.add(ENMergeWeekdayRefiner.class.getName());
        mergedResult.tags.addAll(weekResult.tags);
        mergedResult.tags.addAll(weekDayResult.tags);


        int startIndex = Math.min(weekResult.index, weekDayResult.index);
        int endIndex = Math.max(
                weekResult.index + weekResult.text.length(),
                weekDayResult.index + weekDayResult.text.length());

        mergedResult.index = startIndex;
        mergedResult.text  = text.substring(startIndex, endIndex);

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(weekResult.start.date());
        calendar.set(Calendar.DAY_OF_WEEK, weekDayResult.start.get(Components.DayOfWeek));

        mergedResult.start.imply(Components.Year, calendar.get(Calendar.YEAR));
        mergedResult.start.imply(Components.Month, calendar.get(Calendar.MONTH) + 1);
        mergedResult.start.imply(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
        mergedResult.start.assign(Components.DayOfWeek, calendar.get(Calendar.DAY_OF_WEEK));

        if (weekDayResult.end != null) {

            calendar.setTime(weekResult.start.date());
            calendar.set(Calendar.DAY_OF_WEEK, weekDayResult.end.get(Components.DayOfWeek));

            mergedResult.end = new ParsedDateComponent();
            mergedResult.end.imply(Components.Year, calendar.get(Calendar.YEAR));
            mergedResult.end.imply(Components.Month, calendar.get(Calendar.MONTH) + 1);
            mergedResult.end.imply(Components.DayOfMonth, calendar.get(Calendar.DAY_OF_MONTH));
            mergedResult.end.assign(Components.DayOfWeek, calendar.get(Calendar.DAY_OF_WEEK));
        }

        return mergedResult;
    }


    @Override
    public List<ParsedResult> refine(List<ParsedResult> results, String text, ChronoOption options) {

        if (results.size() < 2) return results;

        final List<ParsedResult> mergedResult = new ArrayList<ParsedResult>();
        ParsedResult currResult = null;
        ParsedResult prevResult = null;

        for (int i = 1; i < results.size(); i++) {

            currResult = results.get(i);
            prevResult = results.get(i - 1);

            ParsedResult weekResult = null;
            ParsedResult weekDayResult = null;

            if (prevResult.tags.contains(WEEK_TAG) && currResult.tags.contains(WEEK_DAY_TAG)) {

                weekResult = prevResult;
                weekDayResult = currResult;

            } else if (prevResult.tags.contains(WEEK_DAY_TAG) && currResult.tags.contains(WEEK_TAG)) {

                weekResult = currResult;
                weekDayResult = prevResult;
            }

            if (weekResult != null && weekDayResult != null
                    && checkPatternBetween(text, prevResult, currResult)
                    && checkMergingCompatible(weekResult, weekDayResult)) {

                prevResult = mergeResult(text, weekResult, weekDayResult);
                currResult = null;
                i += 1;
            }

            mergedResult.add(prevResult);
        }

        if (currResult != null) {
            mergedResult.add(currResult);
        }

        return mergedResult;
    }


}
