package com.wanasit.chrono.parser;

import java.util.Date;
import java.util.List;
import com.wanasit.chrono.ChronoOptions;
import com.wanasit.chrono.ParsedResult;


public interface Parser {
    List<ParsedResult> execute(String text, Date refDate, ChronoOptions options);
}
