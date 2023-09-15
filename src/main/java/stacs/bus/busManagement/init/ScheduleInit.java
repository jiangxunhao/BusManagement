package stacs.bus.busManagement.init;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import stacs.bus.busManagement.util.Schedule;

import java.io.FileReader;
import java.time.LocalTime;
import java.util.ArrayList;

public class ScheduleInit {

    ArrayList<Schedule> scheduleArrayList;

    /**
     * Initial setup for Schedule. Receive information from Schedule.json and create Schedule.
     */
    public void initialSetup() {
        scheduleArrayList = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("Schedule.json"));
            JSONArray jsonObject = (JSONArray) obj;

            jsonObject.forEach(eachSchedule -> parseJsonObject((JSONObject) eachSchedule));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Receive information of each Schedule inside json file, create Schedule and add into scheduleArrayList.
     *
     * @param eachSchedule - each Schedule information read from json
     */
    private void parseJsonObject(JSONObject eachSchedule) {

        String routeName = (String) eachSchedule.get("routeName");
        ArrayList<String> stirngSchedule = (ArrayList<String>) eachSchedule.get("schedule");
        ArrayList<LocalTime> schedule = new ArrayList<>();
        for (String s : stirngSchedule) {
            LocalTime scheduleTime = LocalTime.parse(s);
            schedule.add(scheduleTime);
        }

        Schedule newSchedule = new Schedule(routeName, schedule);
        scheduleArrayList.add(newSchedule);
    }

    /**
     * Get Schedule as an ArrayList.
     *
     * @return ArrayList of Schedule
     */
    public ArrayList<Schedule> getScheduleArrayList() {
        return scheduleArrayList;
    }
}