package xymanek.timetable;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This file is part of xymanek.timetable
 */
public class DateSelectionParser {
    public static Date parse (String string) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(string);
    }

    public static Date getDateFromRequest (HttpServletRequest request) throws ParseException {
        String day = request.getParameter("day");
        Date date;

        if (day != null) {
            date = parse(day);
        } else {
            date = new Date();
        }

        return date;
    }
}
