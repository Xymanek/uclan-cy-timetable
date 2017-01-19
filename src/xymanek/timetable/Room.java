package xymanek.timetable;

/**
 * Author: Osmolovskiy Artem
 * Email: AOsmolovskiy@uclan.ac.uk
 */
class Room implements Comparable<Room> {
    private String code;

    public Room (String code) {
        this.code = code;
    }

    public String getCode () {
        return code;
    }

    @Override
    public int compareTo (Room other) {
        return code.compareToIgnoreCase(other.getCode());
    }
}
