package com.wanasit.chrono;

import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ParsedDateComponent {
    
    public enum Components{
        Year,
        Month,
        Day,
        Hour,
        Minute,
        Second,
        TimezoneOffset,
        DayOfWeek,
        Meridiem,
    }
    
    protected Map<Components, Integer> knownValues;
    protected Map<Components, Integer> impliedValues;
    
    public ParsedDateComponent () {
        this(new EnumMap<ParsedDateComponent.Components, Integer>(Components.class));
    }
    
    public ParsedDateComponent (Map<Components, Integer> knownValues) {
        this(knownValues, new EnumMap<ParsedDateComponent.Components, Integer>(Components.class));
    }
    
    public ParsedDateComponent (Map<Components, Integer> knownValues, Map<Components, Integer> impliedValues) {
        this.knownValues   = knownValues;
        this.impliedValues = impliedValues;
        
        if (!this.isCertain(Components.Hour) && !this.isCertain(Components.Minute)) {
            this.imply(Components.Hour, 12);
            this.imply(Components.Minute, 0);
            this.imply(Components.Second, 0);
        }
    }
    
    public ParsedDateComponent (ParsedDateComponent other) {
        this(new HashMap<ParsedDateComponent.Components, Integer>(other.knownValues),
             new HashMap<ParsedDateComponent.Components, Integer>(other.impliedValues));
    }
    
    
    public Date date(){
        
        Map<Components, Integer> joinInfo = new HashMap<ParsedDateComponent.Components, Integer>();
        
        joinInfo.putAll(this.impliedValues);
        joinInfo.putAll(this.knownValues);
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(joinInfo.get(Components.Year), 
                joinInfo.get(Components.Month)-1, 
                joinInfo.get(Components.Day),
                joinInfo.get(Components.Hour),
                joinInfo.get(Components.Minute),
                joinInfo.get(Components.Second));
        
        return calendar.getTime();
    }
    
    
    public Integer get(Components component) {
        if (this.knownValues.containsKey(component)) return this.knownValues.get(component);
        if (this.impliedValues.containsKey(component)) return this.impliedValues.get(component);
        return null;
    }
    
    public boolean isCertain(Components component) {
        return this.knownValues.containsKey(component);
    }
    
    
    public void assign(Components component, int value) {
        this.knownValues.put(component, value);
        this.impliedValues.remove(component);
    }
    
    public void imply(Components component, int value) {
        if (!knownValues.containsKey(component)) {
            this.impliedValues.put(component, value);
        }
    }
    
    @Override
    public String toString() {
        return knownValues.toString() + " +("+ impliedValues.toString()+")";
    }
}
