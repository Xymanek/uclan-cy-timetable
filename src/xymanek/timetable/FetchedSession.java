package xymanek.timetable;

import com.google.gson.annotations.SerializedName;

/**
 * Author: Osmolovskiy Artem
 * Email: AOsmolovskiy@uclan.ac.uk
 */
public class FetchedSession {
    @SerializedName("INSTANCE_ID") private int instanceId;
    @SerializedName("MODULE_CODE") private String moduleCode;
    @SerializedName("MODULE_NAME") private String moduleName;
    @SerializedName("START_TIME_FORMATTED") private String startTimeFormatted;
    @SerializedName("END_TIME_FORMATTED") private String endTimeFormatted;
    @SerializedName("ROOM_CODE") private String roomCode;
    @SerializedName("LECTURER_NAME") private String lecturerName;
    @SerializedName("SESSION_DESCRIPTION") private String sessionDescription;

    public int getInstanceId () {
        return instanceId;
    }

    public String getModuleCode () {
        return moduleCode;
    }

    public String getModuleName () {
        return moduleName;
    }

    public String getStartTimeFormatted () {
        return startTimeFormatted;
    }

    public String getEndTimeFormatted () {
        return endTimeFormatted;
    }

    public String getRoomCode () {
        return roomCode;
    }

    public String getLecturerName () {
        return lecturerName;
    }

    public String getSessionDescription () {
        return sessionDescription;
    }
}
