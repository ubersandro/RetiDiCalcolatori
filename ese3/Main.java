package it.unical.dimes.reti.ese3;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Main {
public static void main(String[] args) {
	System.out.println(Locale.US);
	System.out.println(getTimeZoneByLocale("en_US"));
	
	String[] timeZoneIDList = TimeZone.getAvailableIDs();
    //List of Time Zones
    for(String timeZoneID:timeZoneIDList){
        System.out.println(timeZoneID);
    }
    //What time is it in Fiji
    Calendar fijiCalendar = Calendar.getInstance(TimeZone.getTimeZone("US/Alaska"));
    System.out.println("Time in Fiji->"+fijiCalendar.get(Calendar.HOUR)+":"+fijiCalendar.get(Calendar.MINUTE));
}

public static String getTimeZoneByLocale(String languageTag){
    //Locale locale = Locale.forLanguageTag(languageTag);
    Calendar cal = Calendar.getInstance(Locale.US);
	TimeZone timeZone = cal.getTimeZone();
	return ""+cal.getTime();
    //return timeZone.getID();
}
}
