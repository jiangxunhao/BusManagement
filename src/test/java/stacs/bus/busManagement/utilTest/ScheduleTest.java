package stacs.bus.busManagement.utilTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import stacs.bus.busManagement.util.Schedule;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Test the function of Schedule class.
 */
public class ScheduleTest {
    Schedule schedule;

    /**
     * Initial the schedule which contains two arriving time and a route name.
     */
    @BeforeEach
    public void initial() {
        ArrayList<LocalTime> times = new ArrayList<>();
        times.add(LocalTime.parse("08:00"));
        times.add(LocalTime.parse("08:30"));
        schedule = new Schedule("15A", times);
    }

    /**
     * Test the addStopTime function.
     */
    @Test
    public void testAddStopTime() {
        schedule.addStopTime(1, LocalTime.parse("08:15"));
        assertEquals(3, schedule.size());
    }

    /**
     * Test the getTime function.
     */
    @Test
    public void testGetTime() {
        LocalTime time = LocalTime.parse("08:00");
        assertEquals(time, schedule.getTime(0));
    }

    /**
     * Test the getRouteName funciton.
     */
    @Test
    public void testGetRouteName() {
        assertEquals("15A", schedule.getRouteName());
    }

    /**
     * Test the setName function.
     */
    @Test
    public void testSetName() {
        schedule.setRouteName("16A");
        assertEquals("16A", schedule.getRouteName());
    }

    /**
     * Test the getSchedule function.
     */
    @Test
    public void testGetSchedule() {
        ArrayList<LocalTime> timesTest = new ArrayList<>();
        timesTest.add(LocalTime.parse("08:00"));
        timesTest.add(LocalTime.parse("08:30"));
        assertEquals(timesTest, schedule.getSchedule());
    }

    /**
     * Test the setSchedule function.
     */
    @Test
    public void testSetSchedule() {
        ArrayList<LocalTime> timesTest = new ArrayList<>();
        timesTest.add(LocalTime.parse("08:10"));
        timesTest.add(LocalTime.parse("08:20"));
        schedule.setSchedule(timesTest);
        assertEquals(timesTest, schedule.getSchedule());
    }
}
