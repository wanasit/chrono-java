package com.wanasit.chrono;

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
    
    
    
}
