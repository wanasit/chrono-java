package com.wanasit.chrono;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class ParsedResult implements Comparable<ParsedResult>, Cloneable{
    
    public int index;
    public String text;
    public String concordance;
    
    public ParsedDateComponent start;
    public ParsedDateComponent end;

    public final Set<String> tags = new HashSet<String>();

    public ParsedResult() { 
        this.start = new ParsedDateComponent();
    }
    
    public ParsedResult(int index, String text) { 
        this.index = index;
        this.text  = text;
        this.start = new ParsedDateComponent();
    }

    public ParsedResult(Object source, int index, String text) {
        this(index, text);
        this.tags.add(source.getClass().getName());
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
