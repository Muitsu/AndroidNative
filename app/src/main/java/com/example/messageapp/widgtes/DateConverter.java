package com.example.messageapp.widgtes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    public static Date convertStringToDate(String dateString, String format) {
        // Define the date format (dd/MM/yyyy)
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        Date date = null;
        try {
            // Parse the date string into a Date object
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToString(Date currentDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(currentDate);

    }
}

//Example:
//  Date date = DateConverter.convertStringToDate(item.getCreateDate(), "yyyy-MM-dd HH:mm:ss");
// - item.getCreateDate() - got from API. convert it into date

//  String formattedDate = DateConverter.dateToString(date, "dd MMMM yyyy");
//  Formatted the date into