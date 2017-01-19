package xymanek.timetable;

import java.util.Date;

/**
 * Author: Osmolovskiy Artem
 * Email: AOsmolovskiy@uclan.ac.uk
 */
public class Result {
    private Session[] sessions;
    private Room[] rooms;
    private Date startAt;

    public Result (Session[] sessions, Room[] rooms, Date startAt) {
        this.sessions = sessions;
        this.rooms = rooms;
        this.startAt = startAt;
    }

    public Session[] getSessions () {
        return sessions;
    }

    public Room[] getRooms () {
        return rooms;
    }

    public Date getStartAt () {
        return startAt;
    }
}
