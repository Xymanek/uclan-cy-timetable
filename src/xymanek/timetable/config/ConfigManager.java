package xymanek.timetable.config;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Author: Osmolovskiy Artem
 * Email: AOsmolovskiy@uclan.ac.uk
 */
public class ConfigManager {
    public static final String KIND = "Setting";
    public static final String KEY_NAME = "timetable_api";

    public static final String URL = "url";
    public static final String TOKEN = "token";

    public static Entity fetch () throws EntityNotFoundException {
        return DatastoreServiceFactory
            .getDatastoreService()
            .get(KeyFactory.createKey(KIND, KEY_NAME));
    }

    public static void save (String url, String token) {
        Entity api_settings = new Entity(KIND, KEY_NAME);
        api_settings.setProperty("url", url);
        api_settings.setProperty("token", token);

        DatastoreServiceFactory
            .getDatastoreService()
            .put(api_settings);
    }
}
