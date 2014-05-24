package com.wanasit.chrono.refiner;

import java.util.List;

import com.wanasit.chrono.ChronoOptions;
import com.wanasit.chrono.ParsedResult;

public interface Refiner {
    
    List<ParsedResult> refine(List<ParsedResult> results, String text, ChronoOptions options);
}
