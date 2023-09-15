package stacs.bus.busManagement.utilTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import stacs.bus.busManagement.util.Route;
import stacs.bus.busManagement.util.Schedule;
import stacs.bus.busManagement.util.Stop;
import stacs.bus.busManagement.util.Timetable;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the route class.
 */
public class RouteTest {
    Route route;

    /**
     * Initial the route which contains two stops and two schedules.
     */
    @BeforeEach
    public void initial() {
        ArrayList<Stop> stops = new ArrayList<>();
        Stop stop1 = new Stop("DRA", "KY16 9LY");
        Stop stop2 = new Stop("St.andrews", "bus station");
        stops.add(stop1);
        stops.add(stop2);

        ArrayList<Schedule> schedules = new ArrayList<>();
        ArrayList<LocalTime> times1 = new ArrayList<>();
        times1.add(LocalTime.parse("08:00"));
        times1.add(LocalTime.parse("08:30"));
        Schedule schedule1 = new Schedule("15A", times1);
        schedules.add(schedule1);
        ArrayList<LocalTime> times2 = new ArrayList<>();
        times2.add(LocalTime.parse("09:00"));
        times2.add(LocalTime.parse("09:30"));
        Schedule schedule2 = new Schedule("15A", times2);
        schedules.add(schedule2);
        Timetable timetable = new Timetable("15A", schedules);

        route = new Route("15A", stops, timetable);
    }

    /**
     * Test the addStop function.
     * All schedules would add an arriving time based on the input time
     */
    @Test
    public void testAddStop() {
        Stop stop = new Stop("Morrison", "KY16 8PJ");
        route.addStop("DRA", stop, LocalTime.parse("07:30"));

        assertEquals(route.getTimetable().getSchedule(1).getTime(0), LocalTime.parse("08:30"));
    }

    /**
     * Test the addSchedule function.
     * Route would generate a new schedule contains all stop arriving time based on input time
     */
    @Test
    public void testAddSchedule() {
        route.addSchedule(LocalTime.parse("07:00"));
        assertEquals(route.getTimetable().getSchedule(0).getTime(1), LocalTime.parse("07:30"));
    }
}
