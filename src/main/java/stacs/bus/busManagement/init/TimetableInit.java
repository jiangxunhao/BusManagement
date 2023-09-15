package stacs.bus.busManagement.init;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import stacs.bus.busManagement.util.Schedule;
import stacs.bus.busManagement.util.Timetable;

import java.io.FileReader;
import java.util.ArrayList;

public class TimetableInit {

    ScheduleInit scheduleInit;
    ArrayList<Timetable> timetableArrayList;

    /**
     * Initial setup for Timetable. Receive information from Timetable.json and create Timetable.
     */
    public void initialSetup() {
        scheduleInit = new ScheduleInit();
        scheduleInit.initialSetup();

        timetableArrayList = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("Timetable.json"));
            JSONArray jsonObject = (JSONArray) obj;

            jsonObject.forEach(eachTimetable -> parseJsonObject((JSONObject) eachTimetable));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Receive information of each Timetable inside json file, create Timetable and add into timetableArrayList.
     *
     * @param eachTimetable - each Timetable information read from json
     */
    private void parseJsonObject(JSONObject eachTimetable) {

        String routeName = (String) eachTimetable.get("routeName");
        ArrayList<String> stringTimetable = (ArrayList<String>) eachTimetable.get("timetable");

        ArrayList<Schedule> timetable = transferToSchedules(stringTimetable);

        Timetable newTimetable = new Timetable(routeName, timetable);
        timetableArrayList.add(newTimetable);
    }

    /**
     * Transfer information from String in json file into Schedule.
     *
     * @param stringTimetable - String of Stop's name
     * @return Timetable
     */
    private ArrayList<Schedule> transferToSchedules(ArrayList<String> stringTimetable) {
        ArrayList<Schedule> schedules = new ArrayList<Schedule>();

        for (int j = 0; j < stringTimetable.size(); j++) {
            for (int i = 0; i < scheduleInit.getScheduleArrayList().size(); i++) {
                if (stringTimetable.get(j).equals(scheduleInit.getScheduleArrayList().get(i).getRouteName())) {
                    schedules.add(scheduleInit.getScheduleArrayList().get(i));
                }
            }
        }
        return schedules;
    }

    /**
     * Get Timetable as an ArrayList.
     *
     * @return ArrayList of Timetable
     */
    public ArrayList<Timetable> getTimetableArrayList() {
        return timetableArrayList;
    }
}