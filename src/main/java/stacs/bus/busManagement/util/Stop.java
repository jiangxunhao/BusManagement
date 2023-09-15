package stacs.bus.busManagement.util;

import java.io.Serializable;

public class Stop implements Serializable {
    private String stopName;
    private String location;

    /**
     * Constructs a stop given the name and location.
     *
     * @param name     the name of stop
     * @param location the location of stop
     */
    public Stop(String name, String location) {
        setName(name);
        setLocation(location);
    }

    /**
     * Returns the name of the stop.
     *
     * @return the name of the stop
     */
    public String getName() {
        return stopName;
    }

    /**
     * Sets the name of stop.
     *
     * @param name the new name of stop
     */
    public void setName(String name) {
        this.stopName = name;
    }

    /**
     * Return the location of the stop.
     *
     * @return the location of the stop
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of stop.
     *
     * @param location the new location of stop
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Return true if the two objects has the same name and location.
     *
     * @param obj the object needed to compare with this
     * @return true if the two objects has the same name and location
     */
    @Override
    public boolean equals(Object obj) {
        Stop stop = (Stop) obj;
        return this.stopName == stop.getName() && this.location == stop.getLocation();
    }
}
