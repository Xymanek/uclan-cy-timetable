package xymanek.timetable;

import java.util.Date;

/**
 * Author: Osmolovskiy Artem
 * Email: AOsmolovskiy@uclan.ac.uk
 */
public class Session {
    private int id;
    private String name;
    private String code;
    private String description;
    private String lecturer;
    private Room room;
    private Date start;
    private Date end;

    public Session (int id, String name, String code, String description, String lecturer, Room room, Date start, Date end) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.lecturer = lecturer;
        this.room = room;
        this.start = start;
        this.end = end;
    }

    public int getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public String getCode () {
        return code;
    }

    public String getDescription () {
        return description;
    }

    public String getLecturer () {
        return lecturer;
    }

    public Room getRoom () {
        return room;
    }

    public Date getStart () {
        return start;
    }

    public Date getEnd () {
        return end;
    }
}
