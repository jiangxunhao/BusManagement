package stacs.bus.busManagement.model;

import org.springframework.stereotype.Component;
import stacs.bus.busManagement.init.RouteInit;
import stacs.bus.busManagement.init.ScheduleInit;
import stacs.bus.busManagement.init.StopInit;
import stacs.bus.busManagement.init.TimetableInit;
import stacs.bus.busManagement.util.Route;
import stacs.bus.busManagement.util.Stop;

import java.io.*;
import java.util.HashMap;

@Component
public class Model implements Serializable {
    private HashMap<String, Stop> stops;
    private HashMap<String, Route> routes;

    /**
     * Constructs an empty travel planner system model.
     */
    public Model() {
        this.stops = new HashMap<String, Stop>();
        this.routes = new HashMap<String, Route>();
        initSetup("model.ser");

    }

    /**
     * Constructs a travel planner system model with all the stops and routes.
     *
     * @param stops  the hashmap which contains all the stops
     * @param routes the hashmap which contains all the routes
     */
    public Model(HashMap<String, Stop> stops, HashMap<String, Route> routes) {
        this.stops = stops;
        this.routes = routes;
    }

    /**
     * Add a new stop to the model.
     *
     * @param stop the new stop
     */
    public void addStop(Stop stop) {
        String stopName = stop.getName();
        stops.put(stopName, stop);
    }

    /**
     * Add a new route to the model.
     *
     * @param route the new route
     */
    public void addRoute(Route route) {
        String routeName = route.getRouteName();
        routes.put(routeName, route);
    }

    /**
     * Return the hashmap which contains all the stops.
     *
     * @return the hashmap which contains all the stops
     */
    public HashMap<String, Stop> getStops() {
        return stops;
    }

    /**
     * Return the mapping stop given the stop name.
     *
     * @param stopName the name of stop
     * @return the stop mapping the stop name
     */
    public Stop getStop(String stopName) {
        return stops.get(stopName);
    }

    /**
     * Return the hashmap which contains all the routes.
     *
     * @return the hashmap which contains all routes
     */
    public HashMap<String, Route> getRoutes() {
        return routes;
    }

    /**
     * Return the mapping route given the route name.
     *
     * @param routeName the name of route
     * @return the route mapping the route name
     */
    public Route getRoute(String routeName) {
        return routes.get(routeName);
    }

    /**
     * Initial setup all information receive from json files format.
     */
    public void initSetup(String filePath) {
        File serFile = new File(filePath);
        if(serFile.isFile()) {
            try {
                load(filePath);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            StopInit stopInit = new StopInit(this);
            ScheduleInit scheduleInit = new ScheduleInit();
            TimetableInit timetableInit = new TimetableInit();
            RouteInit routeInit = new RouteInit(this);

            //Initial setup information
            stopInit.initialSetup();
            scheduleInit.initialSetup();
            timetableInit.initialSetup();
            routeInit.initialSetup();
        }
    }

    /**
     * Save the current data into .ser file.
     * @throws IOException when save failed
     */
    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream("model.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(stops);
        oos.writeObject(routes);
        oos.close();
    }

    /**
     * Load the data from .ser file.
     * @throws IOException when load failed
     * @throws ClassNotFoundException the .ser file does not contain valid class
     */
    public void load(String filePath) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        stops = (HashMap<String, Stop>) ois.readObject();
        routes = (HashMap<String, Route>) ois.readObject();
        ois.close();
    }
}
