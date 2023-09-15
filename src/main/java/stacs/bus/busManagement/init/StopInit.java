package stacs.bus.busManagement.init;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import stacs.bus.busManagement.model.Model;
import stacs.bus.busManagement.util.Stop;

import java.io.FileReader;
import java.util.ArrayList;

public class StopInit {

    Model model;
    ArrayList<Stop> stopArrayList;

    /**
     * Initial StopInit constructor.
     *
     * @param model - model
     */
    public StopInit(Model model) {
        this.model = model;
    }

    /**
     * Initial setup for Stop. Receive information from Stop.json and create Stop.
     */
    public void initialSetup() {
        stopArrayList = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("Stop.json"));
            JSONArray jsonObject = (JSONArray) obj;

            jsonObject.forEach(stop -> parseJsonObject((JSONObject) stop));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Receive information of each Stop inside json file, create Stop and add into model.
     *
     * @param stop - each Stop information read from json
     */
    private void parseJsonObject(JSONObject stop) {
        String name = (String) stop.get("name");
        String location = (String) stop.get("location");

        Stop newStop = new Stop(name, location);
        stopArrayList.add(newStop);
        model.addStop(newStop);
    }

    /**
     * Get Stop as an ArrayList.
     *
     * @return ArrayList of Stop
     */
    public ArrayList<Stop> getStopArrayList() {
        return stopArrayList;
    }
}