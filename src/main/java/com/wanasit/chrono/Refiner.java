package com.wanasit.chrono;

import java.util.List;

public abstract class Refiner {
    
    public abstract List<ParsedResult> refine(List<ParsedResult> results, String text, ChronoOptions options);
}
