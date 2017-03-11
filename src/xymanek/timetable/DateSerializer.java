package xymanek.timetable;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Author: Osmolovskiy Artem
 * Email: AOsmolovskiy@uclan.ac.uk
 */
public class DateSerializer implements JsonSerializer<Date> {
    @Override
    public JsonElement serialize (Date src, Type typeOfSrc, JsonSerializationContext context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return new JsonPrimitive(dateFormat.format(src));
    }
}
