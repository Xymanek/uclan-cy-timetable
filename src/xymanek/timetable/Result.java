package xymanek.timetable;

/**
 * Author: Osmolovskiy Artem
 * Email: AOsmolovskiy@uclan.ac.uk
 */
public class Result {
    private Session[] sessions;
    private Room[] rooms;

    public Result (Session[] sessions, Room[] rooms) {
        this.sessions = sessions;
        this.rooms = rooms;
    }

    public Session[] getSessions () {
        return sessions;
    }

    public Room[] getRooms () {
        return rooms;
    }
}
