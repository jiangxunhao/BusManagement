package stacs.bus.busManagement.utilTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import stacs.bus.busManagement.util.Schedule;
import stacs.bus.busManagement.util.Timetable;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the Timetable class.
 */
public class TimetableTest {
    Timetable timetable;

    /**
     * Initial the timetable which contains two schedules.
     */
    @BeforeEach
    public void initial() {
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

        timetable = new Timetable("15A", schedules);
    }

    /**
     * Test the addStopTimes function.
     * All the schedules would add a stop time based on the input time
     */
    @Test
    public void testAddStopTimes() {
        timetable.addStopTimes(1, LocalTime.parse("08:15"));

        assertEquals(timetable.getSchedule(0).getTime(1), LocalTime.parse("08:15"));
        assertEquals(timetable.getSchedule(1).getTime(1), LocalTime.parse("09:15"));
    }

    /**
     * Test the addSchedule function.
     * timetable would generate a new schedule contains all stop arriving time based on the input time
     */
    @Test
    public void testAddSchedule() {
        timetable.addSchedule(0, LocalTime.parse("07:00"));

        assertEquals(timetable.getSchedule(0).getTime(1), LocalTime.parse("07:30"));
    }
}
