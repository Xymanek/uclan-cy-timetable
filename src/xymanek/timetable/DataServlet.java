package xymanek.timetable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Author: Osmolovskiy Artem
 * Email: AOsmolovskiy@uclan.ac.uk
 */
public class DataServlet extends HttpServlet {
    private static final int OFFSET = -3;

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String day = request.getParameter("day");
        Date date;

        if (day != null) {
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(day);
            } catch (ParseException e) {
                response.setStatus(400);
                return;
            }
        } else {
            date = new Date();
        }

        String urlPath = "https://cyprustimetable.uclan.ac.uk/TimetableAPI/TimetableWebService.asmx/getTimetableByDate?";

        urlPath += "securityToken=e84e281d4c9b46f8a30e4a2fd9aa7058&";
        urlPath += "queryDate=" + new SimpleDateFormat("yyyy-MM-dd").format(date);

        System.out.println("urlPath = " + urlPath);

        URL url = new URL(urlPath);
        URLConnection connection = url.openConnection();

        // TODO: proper timeouts
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);
        InputStream input = connection.getInputStream();

        Document doc;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(input);
        } catch (ParserConfigurationException | SAXException e) {
            throw new ServletException("Failed to fetch data", e);
        }

        Element root = doc.getDocumentElement();
        String json = root.getTextContent();

        FetchedSession[] sessions = new Gson().fromJson(json, FetchedSession[].class);

        // Filter sessions
        sessions = filterSessions(sessions);

        // Building final arrays
        Result result;
        try {
            result = buildResult(sessions);
        } catch (ParseException e) {
            throw new ServletException(e);
        }

        String output = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Room.class, new RoomSerializer())
            .registerTypeAdapter(Date.class, new DateSerializer())
            .create()
            .toJson(result);

        response.getWriter().println(output);
    }

    private FetchedSession[] filterSessions (FetchedSession[] sessions) {
        // Manual filtering since App Engine doesn't support streams
        List<FetchedSession> filtered = new ArrayList<>();

        for (FetchedSession session : sessions) {
            if (session.getInstanceId() != -1) {
                filtered.add(session);
            }
        }

        return filtered.toArray(new FetchedSession[0]);
    }

    private Result buildResult (FetchedSession[] data) throws ParseException {
        List<Session> sessions = new ArrayList<>();
        RoomRegistry rooms = new RoomRegistry();

        SimpleDateFormat format = new SimpleDateFormat("H:m");

        for (FetchedSession fetchedSession : data) {
            sessions.add(new Session(
                fetchedSession.getInstanceId(),
                fetchedSession.getModuleName(),
                fetchedSession.getModuleCode(),
                fetchedSession.getSessionDescription(),
                fetchedSession.getLecturerName(),
                rooms.getRoomByCode(fetchedSession.getRoomCode()),
                format.parse(fetchedSession.getStartTimeFormatted()),
                getDateWithOffset(fetchedSession.getEndTimeFormatted(), format)
            ));
        }

        return new Result(sessions.toArray(new Session[0]), rooms.getArray());
    }

    private Date getDateWithOffset (String string, DateFormat format) throws ParseException {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(format.parse(string));
        calendar.add(Calendar.SECOND, OFFSET);

        return calendar.getTime();
    }
}
