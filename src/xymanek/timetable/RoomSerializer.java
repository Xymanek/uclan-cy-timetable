package xymanek.timetable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Author: Osmolovskiy Artem
 * Email: AOsmolovskiy@uclan.ac.uk
 */
public class RoomSerializer implements JsonSerializer<Room>{
    @Override
    public JsonElement serialize (Room src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("id", src.getCode());
        result.addProperty("name", src.getCode());

        return result;
    }
}
