package com.wanasit.chrono.parser.en;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.parser.ParserAbstract;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wanasit on 10/12/14.
 */
public class ENWeekExpressionParser extends ParserAbstract {

    private static String regPattern = "(?<=\\W|^)"
            + "(last\\s*(d+)?\\s*weeks?|next\\s*(d+)\\s*weeks?|this\\s*week)"
            + "(?=\\W|$)";

    @Override
    protected Pattern pattern() {
        return Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option) {

        ParsedResult result = new ParsedResult(matcher.start(), matcher.group());
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(refDate);





        return result;
    }
}
