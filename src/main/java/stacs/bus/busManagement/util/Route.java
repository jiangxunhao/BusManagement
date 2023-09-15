package stacs.bus.busManagement.util;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class Route implements Serializable {
    private String routeName;
    private ArrayList<Stop> stops;
    private Timetable timetable;

    /**
     * Constructs a route with specified the route name, stops in the route and the timetable.
     *
     * @param routeName the name of route
     * @param stops     an arraylist which contains all stops on this route
     * @param timetable the timetable of this route
     */
    public Route(String routeName, ArrayList<Stop> stops, Timetable timetable) {
        setRouteName(routeName);
        setStops(stops);
        setTimetable(timetable);
    }

    /**
     * Adds a stop to the route specified the previous stop name, stop and the arriving time at first schedule.
     *
     * @param previousStopName      the previous stop name which the new stop connected to
     * @param newStop               the new stop
     * @param timeOfFirstScheduling arriving time at first schedule
     */
    public void addStop(String previousStopName, Stop newStop, LocalTime timeOfFirstScheduling) {
        if (previousStopName == "") {
            stops.add(0, newStop);
            timetable.addStopTimes(0, timeOfFirstScheduling);
            return;
        }
        int index = stops.indexOf(previousStopName) + 1;
        stops.add(index, newStop);
        timetable.addStopTimes(index, timeOfFirstScheduling);
    }

    /**
     * Add new Schedule into existing Timetable of this Route by calculate position to add.
     * If new Schedule is before first Schedule in Timetable, add at the first position.
     * If new Schedule is after last Schedule in Timetable, add at the last position.
     * Otherwise, if new Schedule is between existing Schedule in Timetable, add it in between existing one.
     *
     * @param startTimeOfScheduling - start time of Schedule
     */
    public void addSchedule(LocalTime startTimeOfScheduling) {
        ArrayList<Schedule> schedules = timetable.getSchedules();
        LocalTime startTimeFirstSchedule = schedules.get(0).getTime(0);
        LocalTime startTimeLastSchedule = schedules.get(schedules.size() - 1).getTime(0);
        if (startTimeOfScheduling.isBefore(startTimeFirstSchedule)) {
            timetable.addSchedule(0, startTimeOfScheduling);
        } else if (startTimeOfScheduling.isAfter(startTimeLastSchedule)) {
            timetable.addSchedule(schedules.size(), startTimeOfScheduling);
        } else {
            for (int i = 1; i < schedules.size(); i++) {
                LocalTime previousStartTime = schedules.get(i - 1).getTime(0);
                LocalTime startTime = schedules.get(i).getTime(0);
                if (startTimeOfScheduling.isAfter(previousStartTime) && startTimeOfScheduling.isBefore(startTime)) {
                    timetable.addSchedule(i, startTimeOfScheduling);
                }
            }
        }

    }

    /**
     * Returns the route name.
     *
     * @return the name of route
     */
    public String getRouteName() {
        return routeName;
    }

    /**
     * Sets the route name given new route name.
     *
     * @param routeName the new route name
     */
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    /**
     * Returns the arraylist which contains all stops on this route.
     *
     * @return the arraylist which contains all stops on the route
     */
    public ArrayList<Stop> getStops() {
        return stops;
    }

    /**
     * Sets a new route given the new route.
     *
     * @param route the new route which contains all the stops
     */
    public void setStops(ArrayList<Stop> route) {
        this.stops = route;
    }

    /**
     * Returns the timetable of this route.
     *
     * @return the timetable of the route
     */
    public Timetable getTimetable() {
        return timetable;
    }

    /**
     * Sets the new timetable.
     *
     * @param timetable the new timetable of this route
     */
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    /**
     * Returns true if the route contains the given stop.
     *
     * @param stop the stop whose presence in this route is to be tested
     * @return true if this route contains the specified stop
     */
    public boolean contains(Stop stop) {
        return stops.contains(stop);
    }
}
