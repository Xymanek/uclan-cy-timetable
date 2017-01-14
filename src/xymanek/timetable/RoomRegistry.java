package xymanek.timetable;

import java.util.Hashtable;
import java.util.Map;

/**
 * Author: Osmolovskiy Artem
 * Email: AOsmolovskiy@uclan.ac.uk
 */
public class RoomRegistry {
    private Map<String, Room> roomMap;

    public RoomRegistry () {
        this.roomMap = new Hashtable<>();
    }

    public Room getRoomByCode (String code) {
        if (roomMap.get(code) == null) {
            roomMap.put(code, new Room(code));
        }

        return roomMap.get(code);
    }

    public Room[] getArray () {
        return roomMap.values().toArray(new Room[0]);
    }
}
