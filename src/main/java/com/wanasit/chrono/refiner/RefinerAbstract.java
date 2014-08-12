package com.wanasit.chrono.refiner;

import java.util.List;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedResult;

public abstract class RefinerAbstract implements Refiner {
    
    public abstract List<ParsedResult> refine(List<ParsedResult> results, String text, ChronoOption options);
}
