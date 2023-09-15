package stacs.bus.busManagement.util;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class Timetable implements Serializable {
    private String routeName;
    private ArrayList<Schedule> schedules;

    /**
     * Constructs a timetable given the route name, schedules of the day and the interval of two schedules.
     *
     * @param routeName the name of route
     * @param schedules the schedules of the day
     */
    public Timetable(String routeName, ArrayList<Schedule> schedules) {
        this.routeName = routeName;
        this.schedules = schedules;
    }

    /**
     * Adds arriving times of the day on a specific stop to the timetable given the index of stop position.
     *
     * @param indexOfStop           the index of stop position
     * @param timeOfFirstScheduling the arriving time of the first schedule of the day
     */
    public void addStopTimes(int indexOfStop, LocalTime timeOfFirstScheduling) {
        LocalTime startTimeOfFirstScheduling = schedules.get(0).getTime(0);
        LocalTime difference = timeOfFirstScheduling.minusNanos(startTimeOfFirstScheduling.toNanoOfDay());
        for (int i = 0; i < schedules.size(); i++) {
            Schedule schedule = schedules.get(i);
            LocalTime startTimeOfScheduling = schedule.getTime(0);
            schedule.addStopTime(indexOfStop, startTimeOfScheduling.plusNanos(difference.toNanoOfDay()));
        }
    }

    /**
     * Add new Schedule into existing Timetable by calculate all time to add
     * using start time of schedule and interval of first Schedule in this Timetable.
     *
     * @param indexOfSchedule       - position to add new Schedule
     * @param startTimeOfScheduling - start time of new Schedule
     */
    public void addSchedule(int indexOfSchedule, LocalTime startTimeOfScheduling) {
        Schedule firstSchedule = schedules.get(0);
        ArrayList<LocalTime> firstScheduleArray = firstSchedule.getSchedule();
        LocalTime startTime = firstScheduleArray.get(0);

        ArrayList<LocalTime> scheduleArray = new ArrayList<>();
        for (LocalTime arrivingTimeFirstSchedule : firstScheduleArray) {
            LocalTime difference = arrivingTimeFirstSchedule.minusNanos(startTime.toNanoOfDay());
            scheduleArray.add(startTimeOfScheduling.plusNanos(difference.toNanoOfDay()));
        }

        Schedule schedule = new Schedule(routeName, scheduleArray);
        schedules.add(indexOfSchedule, schedule);
    }

    /**
     * Returns the schedule given the index of schedule.
     *
     * @param index the index of the schedule of the day
     * @return the schedule
     */
    public Schedule getSchedule(int index) {
        return schedules.get(index);
    }

    /**
     * Returns the name of route.
     *
     * @return the name of route
     */
    public String getRouteName() {
        return routeName;
    }

    /**
     * Sets the route name.
     *
     * @param routeName the name of route
     */
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    /**
     * Returns the timetable which contains all schedules.
     *
     * @return the timetable which contains all schedules
     */
    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    /**
     * Sets the timetable which contains schedules of the day.
     *
     * @param schedules the new timetable which contains schedules of the day
     */
    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    /**
     * Returns the array of two dimension of timetable.
     *
     * @return the array of two dimension of timetable
     */
    public LocalTime[][] getTimetableArray() {
        LocalTime[][] timetableArray = new LocalTime[schedules.size()][getSchedule(0).size()];
        for (int i = 0; i < schedules.size(); i++) {
            timetableArray[i] = getSchedule(i).getScheduleArray();
        }
        return timetableArray;
    }
}
