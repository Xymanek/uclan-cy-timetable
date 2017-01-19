package xymanek.timetable.config;

import java.net.MalformedURLException;
import java.net.URL;

public class Validator {
    public static String validateUrl (String url) {
        if (url.equalsIgnoreCase("")) {
            return "URL cannot be empty";
        }

        try {
            new URL(url);
        } catch (MalformedURLException e) {
            return "Not a valid URL";
        }

        if (url.endsWith("?")) {
            return "URL should not end with question mark (\"?\")";
        }

        return null;
    }

    public static String validateToken (String token) {
        if (token.equalsIgnoreCase("")) {
            return "Token cannot be empty";
        }

        return null;
    }
}
