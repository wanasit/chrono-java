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
        DayOfMonth,
        Hour,
        Minute,
        Second,
        TimezoneOffset,
        DayOfWeek,
        Meridiem,
    }
    
    protected final Map<Components, Integer> knownValues = new EnumMap<ParsedDateComponent.Components, Integer>(Components.class);
    protected final Map<Components, Integer> impliedValues = new EnumMap<ParsedDateComponent.Components, Integer>(Components.class);
    
    public ParsedDateComponent () {
	this.imply(Components.Hour, 12);
        this.imply(Components.Minute, 0);
        this.imply(Components.Second, 0);
    }
    
    
    public ParsedDateComponent (ParsedDateComponent other) {
	this.knownValues.putAll(other.knownValues);
	this.impliedValues.putAll(other.impliedValues);
    }
    
    
    public Date date(){
        
        Map<Components, Integer> joinInfo = new HashMap<ParsedDateComponent.Components, Integer>();
        
        joinInfo.putAll(this.impliedValues);
        joinInfo.putAll(this.knownValues);
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(joinInfo.get(Components.Year), 
                joinInfo.get(Components.Month)-1, 
                joinInfo.get(Components.DayOfMonth),
                joinInfo.get(Components.Hour),
                joinInfo.get(Components.Minute),
                joinInfo.get(Components.Second));
        
        if(joinInfo.containsKey(Components.TimezoneOffset)) {
            int targetOffset = joinInfo.get(Components.TimezoneOffset);
            int currentOffset = calendar.getTimeZone().getOffset(calendar.get(Calendar.ERA),
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.MILLISECOND)) / 60000;
            int adjustedOffset = targetOffset - currentOffset;
            calendar.add(Calendar.MINUTE, -adjustedOffset);
        }
        
        
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
