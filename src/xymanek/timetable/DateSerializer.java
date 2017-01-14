package xymanek.timetable;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Osmolovskiy Artem
 * Email: AOsmolovskiy@uclan.ac.uk
 */
public class DateSerializer implements JsonSerializer<Date> {
    @Override
    public JsonElement serialize (Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(new SimpleDateFormat("H:mm:ss").format(src));
    }
}
