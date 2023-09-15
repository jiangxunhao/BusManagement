package stacs.bus.busManagement.init;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import stacs.bus.busManagement.model.Model;
import stacs.bus.busManagement.util.Route;
import stacs.bus.busManagement.util.Stop;
import stacs.bus.busManagement.util.Timetable;

import java.io.FileReader;
import java.util.ArrayList;

public class RouteInit {

    Model model;
    TimetableInit timetableInit;
    ArrayList<Route> routeArrayList;

    /**
     * Initial RouteInit constructor.
     *
     * @param model - model
     */
    public RouteInit(Model model) {
        this.model = model;

        timetableInit = new TimetableInit();
        timetableInit.initialSetup();
    }

    /**
     * Initial setup for Route. Receive information from Routes.json and create Route.
     */
    public void initialSetup() {
        routeArrayList = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("Routes.json"));
            JSONArray jsonObject = (JSONArray) obj;

            jsonObject.forEach(eachRoute -> parseJsonObject((JSONObject) eachRoute));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Receive information of each Route inside json file, create Route and add into model.
     *
     * @param eachRoute - each Route information read from json
     */
    private void parseJsonObject(JSONObject eachRoute) {
        String routeName = (String) eachRoute.get("routeName");
        ArrayList<String> stringStops = (ArrayList<String>) eachRoute.get("stops");
        String stringTimetable = (String) eachRoute.get("timetable");

        ArrayList<Stop> stops = transferToStop(stringStops);
        Timetable timetable = transferToTimetable(stringTimetable);

        Route newRoute = new Route(routeName, stops, timetable);
        routeArrayList.add(newRoute);
        model.addRoute(newRoute);
    }

    /**
     * Transfer information from String in json file into Stop.
     *
     * @param stringStops - String of Stop's name
     * @return Stop
     */
    private ArrayList<Stop> transferToStop(ArrayList<String> stringStops) {
        ArrayList<Stop> stops = new ArrayList<Stop>();

        for (int i = 0; i < stringStops.size(); i++) {
            if (model.getStop(stringStops.get(i)) != null) {
                stops.add(model.getStop(stringStops.get(i)));
            }
        }
        return stops;
    }

    /**
     * Transfer information from String in json file into Timetable.
     *
     * @param stringTimetable - String of Timetable's route name
     * @return Timetable
     */
    private Timetable transferToTimetable(String stringTimetable) {
        Timetable timetable = null;
        for (int i = 0; i < timetableInit.getTimetableArrayList().size(); i++) {
            if (stringTimetable.equals(timetableInit.getTimetableArrayList().get(i).getRouteName())) {
                timetable = timetableInit.getTimetableArrayList().get(i);
            }
        }

        return timetable;
    }

    /**
     * Get Route as an ArrayList.
     *
     * @return ArrayList of Route
     */
    public ArrayList<Route> getRouteArrayList() {
        return routeArrayList;
    }
}