package stacs.bus.busManagement.api;

import org.springframework.web.bind.annotation.*;
import stacs.bus.busManagement.exception.InvalidRouteNameException;
import stacs.bus.busManagement.exception.InvalidStopNameException;
import stacs.bus.busManagement.model.Model;
import stacs.bus.busManagement.util.Route;
import stacs.bus.busManagement.util.Schedule;
import stacs.bus.busManagement.util.Stop;
import stacs.bus.busManagement.util.Timetable;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * REST API file.
 */
@CrossOrigin(origins = "*")
@RestController
public class Controller {
    Model model = new Model();

    /**
     * REST API with POST method to create new Stop in a format
     * "/addStop/{stopName}/{location}".
     * <p>
     * Example to call through curl command is
     * "curl -X POST http://localhost:8080/addStop/St Michaels, Dundee Road/St Michaels"
     * where {stopName} is St Michaels, Dundee Road
     * {location} is St Michaels
     *
     * @param stopName - Stop's name
     * @param location - Stop's location
     * @throws IOException failed saving
     */
    @PostMapping("/addStop/{stopName}/{location}")
    public void addNewStop(@PathVariable String stopName, @PathVariable String location) throws IOException {
        Stop newStop = new Stop(stopName, location);
        model.addStop(newStop);

        model.save();
    }

    /**
     * REST API with POST method to create new Route in a format
     * "/addRoute/{routeName}/{startStopName}/{endStopName}/{startTimeText}/{endTimeText}".
     * <p>
     * Example to call through curl command is
     * "curl -X POST http://localhost:8080/addRoute/Guardbridge to Leuchars/Guardbridge Innerbridge Street/Leuchars Railway Station/06:00/06:10"
     * where {routeName} is Guardbridge to Leuchars
     * {startStopName} is Guardbridge Innerbridge Street
     * {endStopName} is Leuchars Railway Station
     * {startTimeText} is 06:00
     * {endTimeText} is 06:10
     *
     * @param routeName     - Route's name
     * @param startStopName - first Stop's name of this Route
     * @param endStopName   - last Stop's name of this Route
     * @param startTimeText - first start time for this Route
     * @param endTimeText   - end time for this Route
     * @throws InvalidStopNameException - invalid stop error
     * @throws IOException failed saving
     */
    @PostMapping("/addRoute/{routeName}/{startStopName}/{endStopName}/{startTimeText}/" +
            "{endTimeText}")
    public void addNewRoute(@PathVariable String routeName, @PathVariable String startStopName,
                            @PathVariable String endStopName, @PathVariable String startTimeText,
                            @PathVariable String endTimeText)
            throws InvalidStopNameException, IOException {
        ArrayList<Stop> stopsArray = new ArrayList<>();
        Stop startStop = model.getStop(startStopName);
        Stop endStop = model.getStop(endStopName);
        if (startStop == null || endStop == null) {
            throw new InvalidStopNameException("Cannot find the stop!");
        }
        stopsArray.add(startStop);
        stopsArray.add(endStop);

        LocalTime startTime = LocalTime.parse(startTimeText);
        LocalTime endTime = LocalTime.parse(endTimeText);
        ArrayList<LocalTime> scheduleArray = new ArrayList<>();
        scheduleArray.add(startTime);
        scheduleArray.add(endTime);
        Schedule schedule = new Schedule(routeName, scheduleArray);

        ArrayList<Schedule> schedulesArray = new ArrayList<>();
        schedulesArray.add(schedule);

        Timetable timetable = new Timetable(routeName, schedulesArray);

        Route route = new Route(routeName, stopsArray, timetable);

        model.addRoute(route);

        model.save();
    }

    /**
     * REST API with POST method to add new Stop into existing Route in a format
     * "/addStopRoute/{routeName}/{previousStopName}/{stopName}/{timeOfFirstSchedulingText}".
     * <p>
     * Example to call through curl command is
     * "curl -X POST http://localhost:8080/addStopRoute/St Andrews to Dundee/Drumoig National Golf Centre/Dundee City Centre Seagate bus station/05:50"
     * where {routeName} is St Andrews to Dundee
     * {previousStopName} is Drumoig National Golf Centre
     * {stopName} is Dundee City Centre Seagate bus station
     * {timeOfFirstSchedulingText} is 05:50
     *
     * @param routeName                 - Route's name
     * @param previousStopName          - Stop in a position before the Stop that would like to add
     * @param stopName                  - Stop's name
     * @param timeOfFirstSchedulingText - first schedule time of Stop
     * @throws InvalidStopNameException - invalid stop error
     * @throws InvalidRouteNameException invalid route error
     * @throws IOException failed saving
     */
    @PostMapping("/addStopRoute/{routeName}/{previousStopName}/{stopName}/" +
            "{timeOfFirstSchedulingText}")
    public void addStopOnRoute(@PathVariable String routeName, @PathVariable String previousStopName,
                               @PathVariable String stopName, @PathVariable String timeOfFirstSchedulingText)
            throws InvalidStopNameException, InvalidRouteNameException, IOException {
        Route route = model.getRoute(routeName);
        if (route == null) {
            throw new InvalidRouteNameException("Cannot find the route");
        }
        Stop stop = model.getStop(stopName);
        if (stop == null) {
            throw new InvalidStopNameException("Cannot find the stop!");
        }
        LocalTime timeOfFirstScheduling = LocalTime.parse(timeOfFirstSchedulingText);
        route.addStop(previousStopName, stop, timeOfFirstScheduling);

        model.save();
    }

    /**
     * REST API with POST method to add new Schedule into existing Route in a format
     * "/addScheduleOnRoute/{routeName}/{startTimeText}".
     * <p>
     * Example to call through curl command is
     * "curl -X POST http://localhost:8080/addScheduleOnRoute/St Andrews to Dundee/04:00"
     * where {routeName} is St Andrews to Dundee
     * {startTimeText} is 04:00
     *
     * @param routeName     - Route's name
     * @param startTimeText - start time
     * @throws InvalidRouteNameException invalid route error
     * @throws IOException failed saving
     */
    @PostMapping("/addScheduleOnRoute/{routeName}/{startTimeText}")
    public void addScheduleOnRoute(@PathVariable String routeName, @PathVariable String startTimeText)
            throws InvalidRouteNameException, IOException {
        Route route = model.getRoute(routeName);
        if (route == null) {
            throw new InvalidRouteNameException("Cannot find the route");
        }
        LocalTime startTime = LocalTime.parse(startTimeText);
        route.addSchedule(startTime);

        model.save();
    }

    /**
     * REST API with GET method to list all Route on this Stop in a format
     * "/list/route/{stopName}".
     * <p>
     * Example to call through curl command is
     * "curl http://localhost:8080/list/route/St Andrews Bus station"
     * where {stopName} is St Andrews Bus station
     *
     * @param stopName - Stop's name
     * @return - All Routes on this Stop
     * @throws InvalidStopNameException - invalid stop error
     */
    @GetMapping("/list/route/{stopName}")
    public Route[] getAllRoutesOnStop(@PathVariable String stopName) throws InvalidStopNameException {
        ArrayList<Route> routesRes = new ArrayList<>();
        Stop stop = model.getStop(stopName);
        if (stop == null) {
            throw new InvalidStopNameException("Cannot find the stop!");
        }
        HashMap<String, Route> routes = model.getRoutes();
        Iterator<Route> iterator = routes.values().iterator();
        while (iterator.hasNext()) {
            Route route = iterator.next();
            if (route.getStops().contains(stop)) {
                routesRes.add(route);
            }
        }
        return routesRes.toArray(new Route[routesRes.size()]);
    }

    /**
     * REST API with GET method to list all Route on this Stop at a specific time in a format
     * "/list/route/{stopName}/{timeText}/{intervalMinute}".
     * <p>
     * Example to call through curl command is
     * "curl http://localhost:8080/list/route/St Andrews Bus station/05:30/10"
     * where {stopName} is St Andrews Bus station
     * {timeText} is 05:30
     * {intervalMinute} is 10
     *
     * @param stopName       - Stop's name
     * @param timeText       - specific time
     * @param intervalMinute - bus schedule interval in minutes
     * @return - all Routes on this Stop at the specific time
     * @throws InvalidStopNameException - invalid stop error
     */
    @GetMapping("/list/route/{stopName}/{timeText}/{intervalMinute}")
    public Route[] getAllRoutesOnStopAndTime(@PathVariable String stopName,
                                             @PathVariable String timeText,
                                             @PathVariable int intervalMinute) throws InvalidStopNameException {
        LocalTime time = LocalTime.parse(timeText);
        ArrayList<Route> routesRes = new ArrayList<>();
        Route[] routes = getAllRoutesOnStop(stopName);
        for (Route route : routes) {
            Timetable timetable = route.getTimetable();
            LocalTime[][] timetableArray = timetable.getTimetableArray();
            if (containsTime(time, intervalMinute, timetableArray)) {
                routesRes.add(route);
            }
        }
        return routesRes.toArray(new Route[routesRes.size()]);
    }

    /**
     * REST API with GET method to list all time on this Stop in a format
     * "/list/time/{stopName}".
     * <p>
     * Example to call through curl command is
     * "curl http://localhost:8080/list/time/St Andrews Bus station"
     * where {stopName} is St Andrews Bus station
     *
     * @param stopName - Stop's name
     * @return - all time that bus pass this Stop
     * @throws InvalidStopNameException - invalid stop error
     */
    @GetMapping("/list/time/{stopName}")
    public LocalTime[] getAllTimesOnStop(@PathVariable String stopName) throws InvalidStopNameException {
        ArrayList<LocalTime> timesHasService = new ArrayList<>();
        Stop stop = model.getStop(stopName);
        Route[] routes = getAllRoutesOnStop(stopName);
        for (Route route : routes) {
            ArrayList<Stop> stops = route.getStops();
            int stopIndex = stops.indexOf(stop);
            Timetable timetable = route.getTimetable();
            LocalTime[][] timetableArray = timetable.getTimetableArray();
            for (int i = 0; i < timetableArray.length; i++) {
                timesHasService.add(timetableArray[i][stopIndex]);
            }
        }
        return timesHasService.toArray(new LocalTime[timesHasService.size()]);
    }

    /**
     * REST API with GET method to list all Route in a format
     * "/list/routes".
     * <p>
     * Example to call through curl command is
     * "curl http://localhost:8080/list/routes"
     *
     * @return - all Route
     */
    @GetMapping("/list/routes")
    public HashMap<String, Route> listRoutes() {
        return model.getRoutes();
    }

    /**
     * REST API with GET method to list all Stop in a format
     * "/list/stops".
     * <p>
     * Example to call through curl command is
     * "curl http://localhost:8080/list/stops"
     *
     * @return - all Stop
     */
    @GetMapping("/list/stops")
    public HashMap<String, Stop> listStops() {
        return model.getStops();
    }

    /**
     * Check if time provide is valid in Timetable
     *
     * @param time           - specific time
     * @param intervalMinute - interval for bus
     * @param times          - Timetable
     * @return - true if time valid in Timetable
     * false if otherwise
     */
    private boolean containsTime(LocalTime time, int intervalMinute, LocalTime[][] times) {
        for (int i = 0; i < times.length; i++) {
            for (int j = 0; j < times[0].length; j++) {
                if (times[i][j].isAfter(time) && times[i][j].isBefore(time.plusMinutes(intervalMinute))) {
                    return true;
                }
            }
        }
        return false;
    }

}
