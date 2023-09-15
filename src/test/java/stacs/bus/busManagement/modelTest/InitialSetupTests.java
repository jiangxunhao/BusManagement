package stacs.bus.busManagement.modelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import stacs.bus.busManagement.init.RouteInit;
import stacs.bus.busManagement.init.ScheduleInit;
import stacs.bus.busManagement.init.StopInit;
import stacs.bus.busManagement.init.TimetableInit;
import stacs.bus.busManagement.model.Model;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for initial setup information.
 */
@SpringBootTest
public class InitialSetupTests {

    Model model;

    StopInit stopInit;
    ScheduleInit scheduleInit;
    TimetableInit timetableInit;
    RouteInit routeInit;

    /**
     * Setup information before each test case.
     */
    @BeforeEach
    public void setup() {
        model = new Model();

        stopInit = new StopInit(model);
        scheduleInit = new ScheduleInit();
        timetableInit = new TimetableInit();
        routeInit = new RouteInit(model);

        //Initial setup information
        stopInit.initialSetup();
        scheduleInit.initialSetup();
        timetableInit.initialSetup();
        routeInit.initialSetup();
    }

    /**
     * Model contains 7 Stops.
     */
    @Test
    public void stopsSetup() {
        assertFalse(model.getStops().isEmpty());
        assertEquals(7, model.getStops().size());
    }

    /**
     * Total 4 Schedule is created. St Andrews to Dundee (0530) Schedule is created.
     */
    @Test
    public void scheduleSetup() {
        assertFalse(scheduleInit.getScheduleArrayList().isEmpty());
        assertEquals(4, scheduleInit.getScheduleArrayList().size());
        assertEquals("St Andrews to Dundee (0530)", scheduleInit.getScheduleArrayList().get(0).getRouteName());
    }

    /**
     * Total 2 Timetable is created. Timetable contains St Andrews to Dundee (0530) Schedule.
     */
    @Test
    public void timetableSetup() {
        assertFalse(timetableInit.getTimetableArrayList().isEmpty());
        assertEquals(2, timetableInit.getTimetableArrayList().size());
        assertEquals("St Andrews to Dundee (0530)", timetableInit.getTimetableArrayList().get(0).getSchedules().get(0).getRouteName());
    }

    /**
     * Model contains 2 Routes.
     */
    @Test
    public void routeSetup() {
        assertFalse(model.getRoutes().isEmpty());
        assertEquals(2, model.getRoutes().size());
    }

}
