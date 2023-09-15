package stacs.bus.busManagement.util;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class Schedule implements Serializable {
    private String routeName;
    private ArrayList<LocalTime> schedule;

    /**
     * Constructs a schedule given the route name and the arriving time at each stop in this schedule.
     *
     * @param routeName the name of route
     * @param schedule  the arriving time at each stop in this schedule
     */
    public Schedule(String routeName, ArrayList<LocalTime> schedule) {
        this.routeName = routeName;
        this.schedule = schedule;
    }

    /**
     * Adds an arriving time in this schedule given the specified index of position.
     * It is called when add a new stop to the route
     *
     * @param index the index of inserted position
     * @param time  the arriving time needed to be added
     */
    public void addStopTime(int index, LocalTime time) {
        schedule.add(index, time);
    }

    /**
     * Returns the arriving time given the index of stop.
     *
     * @param index the index of the stop
     * @return the arriving time of given stop
     */
    public LocalTime getTime(int index) {
        return schedule.get(index);
    }

    /**
     * Return the name of this route.
     *
     * @return the name of route
     */
    public String getRouteName() {
        return routeName;
    }

    /**
     * Sets the given route name as new route name.
     *
     * @param routeName the new route name
     */
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    /**
     * Returns the schedule which contains all arriving times at each stop.
     *
     * @return the schedule
     */
    public ArrayList<LocalTime> getSchedule() {
        return schedule;
    }

    /**
     * Sets the given schedule as new schedule.
     *
     * @param schedule the new schedule which contains arriving time at each stop
     */
    public void setSchedule(ArrayList<LocalTime> schedule) {
        this.schedule = schedule;
    }

    /**
     * Returns the size of the schedule.
     * This represents the total of stop in the route
     *
     * @return the size of schedule
     */
    public int size() {
        return schedule.size();
    }

    /**
     * Returns the array form of the schedule.
     *
     * @return the array of the schedule
     */
    public LocalTime[] getScheduleArray() {
        return schedule.toArray(new LocalTime[schedule.size()]);
    }
}
