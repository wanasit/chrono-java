package com.wanasit.chrono;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Chrono {
    
    
    protected static Chrono globalInstance = new Chrono(ChronoOptions.sharedOptions);
    
    public static List<ParsedResult> Parse(String text) {
        return globalInstance.parse(text);
    }
    
    public static List<ParsedResult> Parse(String text, Date refDate) {
        return globalInstance.parse(text, refDate);
    }
    
    public static List<ParsedResult> Parse(String text, Date refDate, ChronoOptions options) {
        return globalInstance.parse(text, refDate, options);
    }
    
    
    
    protected ChronoOptions options = null;
    protected List<Parser>  parsers = null;
    protected List<Refiner> refiners = null;
    
    public Chrono (ChronoOptions options) {
        this.options = options;
        this.parsers = new LinkedList<Parser>();
        this.refiners = new LinkedList<Refiner>();
        
        
        for (Class <? extends Parser> c : options.parserClasses) {
            try {
                Parser parser = c.newInstance();
                this.parsers.add(parser);
                
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {}
        }
        
        for (Class <? extends Refiner> c : options.refinerClasses) {
            
            try {
                Refiner refiner = c.newInstance();
                this.refiners.add(refiner);
                
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {}
        }
        
    }
    
    public List<ParsedResult> parse(String text) {
        return parse(text, new Date());
    }
    
    public List<ParsedResult> parse(String text, Date refDate) {
        return parse(text, refDate, this.options);
    }
    
    public List<ParsedResult> parse(String text, Date refDate, ChronoOptions options) {
        
        List<ParsedResult> allResults = new LinkedList<ParsedResult>();
        
        for (Parser parser : this.parsers) {
            
            List<ParsedResult> results = parser.execute(text, refDate, options);
            results = refineWithAllRefiners(results, text, options);
            
            allResults.addAll(results);
        }
        
        Collections.sort(allResults);
        allResults = refineWithAllRefiners(allResults, text, options);
        
        return allResults;
    }
    
    protected List<ParsedResult> refineWithAllRefiners(List<ParsedResult> results, String text, ChronoOptions options) {
        
        for (Refiner refiner : this.refiners) {
            
            results = refiner.refine(results, text, options);
        }
        
        return results;
    }
    
    
    
    
    
}
