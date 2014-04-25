package com.wanasit.chrono;

import java.text.SimpleDateFormat;

public class ParsedResult implements Comparable<ParsedResult>, Cloneable{
    
    
    
    public int index;
    public String text;
    public String concordance;
    
    public ParsedDateComponent start;
    public ParsedDateComponent end;
    
    
    public ParsedResult() { 
        this.start = new ParsedDateComponent();
    }
    
    public ParsedResult(int index, String text) { 
        this.index = index;
        this.text  = text;
        this.start = new ParsedDateComponent();
    }
    
    
    public int compareTo(ParsedResult o) {
        return this.index - o.index;
    }
    
    
    protected static final SimpleDateFormat PRINT_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    @Override
    public String toString() {
        return "ParsedResult: \"" + this.text + "\" > "+ PRINT_FORMAT.format(this.start.date()) + 
                ((this.end != null)? (" - " +PRINT_FORMAT.format(this.end.date())) : "");
    }
}
