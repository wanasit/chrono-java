package com.wanasit.chrono.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedResult;
import com.wanasit.chrono.refiner.Refiner;


public abstract class ParserAbstract implements Parser {

    protected final List<Refiner> refiners = new ArrayList<Refiner>();

    protected abstract Pattern pattern();

    protected abstract ParsedResult extract(String text, Date refDate, Matcher matcher, ChronoOption option);

    public List<ParsedResult> execute(String text, Date refDate, ChronoOption options) {

        List<ParsedResult> results = new LinkedList<ParsedResult>();

        int searchingIndex = 0;
        Matcher matcher = this.pattern().matcher(text);

        while (searchingIndex < text.length() && matcher.find(searchingIndex)) {

            ParsedResult result = this.extract(text, refDate, matcher, options);

            if (result == null) {
                // Failed to extract the date result, MOVE ON
                searchingIndex = matcher.start() + 1;
                continue;
            }

            searchingIndex = result.index + result.text.length() + 1;
            results.add(result);
        }

        for (Refiner refiner : this.refiners) {
            results = refiner.refine(results, text, options);
        }

        return results;
    }

}
