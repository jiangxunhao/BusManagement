package stacs.bus.busManagement.utilTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import stacs.bus.busManagement.util.Stop;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the Stop class.
 */
public class StopTest {
    Stop stop1;
    Stop stop2;

    /**
     * Initial the stop1 and stop2.
     */
    @BeforeEach
    public void initial() {
        stop1 = new Stop("DRA", "KY16 9LY");
        stop2 = new Stop("St.andrews", "bus station");
    }

    /**
     * Test the getName function.
     */
    @Test
    public void testGetName() {
        assertEquals(stop1.getName(), "DRA");
        assertEquals(stop2.getName(), "St.andrews");
    }

    /**
     * Test the getLocation function.
     */
    @Test
    public void testGetLocation() {
        assertEquals(stop1.getLocation(), "KY16 9LY");
        assertEquals(stop2.getLocation(), "bus station");
    }

    /**
     * Test the setName function.
     */
    @Test
    public void testSetName() {
        stop1.setName("Fife park");
        assertEquals(stop1.getName(), "Fife park");
    }

    /**
     * Test the setLocation function.
     */
    @Test
    public void testSetLocation() {
        stop1.setLocation("KY16 9WJ");
        assertEquals(stop1.getName(), "DRA");
    }

    /**
     * Test the equals function.
     */
    @Test
    public void testEquals() {
        Stop stop3 = new Stop("DRA", "KY16 9LY");

        assertTrue(stop1.equals(stop3));
        assertFalse(stop1.equals(stop2));
    }
}
