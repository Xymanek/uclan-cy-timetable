package xymanek.timetable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Osmolovskiy Artem
 * Email: AOsmolovskiy@uclan.ac.uk
 */
public class FetchedSessionProcessor {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "HH:mm";

    private FetchedSession fetchedSession;
    private SimpleDateFormat format;

    FetchedSessionProcessor (FetchedSession fetchedSession) {
        format = new SimpleDateFormat(DATE_FORMAT + " " + TIME_FORMAT);

        this.fetchedSession = fetchedSession;
    }

    public Date getStart () throws ParseException {
        return format.parse(fetchedSession.getSessionDateFormatted() + " " + fetchedSession.getStartTimeFormatted());
    }

    public Date getEnd () throws ParseException {
        return format.parse(fetchedSession.getSessionDateFormatted() + " " + fetchedSession.getEndTimeFormatted());
    }
}
