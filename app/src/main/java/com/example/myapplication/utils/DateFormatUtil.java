package com.example.myapplication.utils;

import android.util.Pair;

import java.util.Calendar;

public class DateFormatUtil {
    private static String dateFormat = "ddmmyyyy";
    private static Calendar cal = Calendar.getInstance();
    public static Pair<String, Integer> format(CharSequence s, String current) {
        String clean = s.toString().replaceAll("[^\\d.]", "");
        String cleanC = current.replaceAll("[^\\d.]", "");

        int cl = clean.length();
        int selection = cl;
        for (int i = 2; i <= cl && i < 6; i += 2) {
            selection++;
        }
        //Fix for pressing delete next to a forward slash
        if (clean.equals(cleanC)) selection--;

        if (clean.length() < 8) {
            clean = clean + dateFormat.substring(clean.length());
        } else {
            //This part makes sure that when we finish entering numbers
            //the date is correct, fixing it otherwise
            int day  = Integer.parseInt(clean.substring(0,2));
            int mon  = Integer.parseInt(clean.substring(2,4));
            int year = Integer.parseInt(clean.substring(4,8));

            mon = Math.min(mon, 12);
            mon = Math.max(mon, 1);
            cal.set(Calendar.MONTH, mon-1);

            year = (year < 1900) ? 1900 : Math.min(year, 2100);
            cal.set(Calendar.YEAR, year);
            // ^ first set year for the line below to work correctly
            //with leap years - otherwise, date e.g. 29/02/2012
            //would be automatically corrected to 28/02/2012
            day = Math.max(day, 1);
            day = Math.min(day, cal.getActualMaximum(Calendar.DATE));
            clean = String.format("%02d%02d%02d",day, mon, year);
        }
        clean = String.format("%s/%s/%s", clean.substring(0, 2),
                clean.substring(2, 4),
                clean.substring(4, 8));

        selection = Math.max(selection, 0);

        return new Pair<String, Integer>(clean, selection);
    }

}
