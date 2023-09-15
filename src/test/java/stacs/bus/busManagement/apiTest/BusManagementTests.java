package stacs.bus.busManagement.apiTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import stacs.bus.busManagement.api.Controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test cases for main functions.
 */
@SpringBootTest
class BusManagementTests {

    WebTestClient client;

    /**
     * Before start each test case, initiate web client.
     */
    @BeforeEach
    void setup() {
        client = WebTestClient.bindToController(new Controller()).build();
    }

    /**
     * Test GET "/list/routes" endpoint.
     *
     * Result: OK status with body return. All Route should be return.
     * So, body length should equal to 2.
     */
    @Test
    void listRoutes() {
        client.get().uri("/list/routes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2);
    }

    /**
     * Test GET "/list/stops" endpoint.
     *
     * Result: OK status with body return. All Stop should be return.
     * So, body length should equal to 7.
     */
    @Test
    void listStops() {
        client.get().uri("/list/stops")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(7);
    }

    /**
     * Test GET "/list/route/St Andrews Bus station" endpoint.
     *
     * Result: OK status with body return. All Route on Stop, "St Andrews Bus station", should be return.
     * routeName should be "Dundee to St Andrews" and "St Andrews to Dundee".
     */
    @Test
    void listAllRoutesOnStop() {
        client.get().uri("/list/route/St Andrews Bus station")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[?(@.routeName == \"Dundee to St Andrews\")]").isNotEmpty()
                .jsonPath("$[?(@.routeName == \"St Andrews to Dundee\")]").isNotEmpty();
    }

    /**
     * Test GET "/list/route/St Andrews" endpoint.
     *
     * Result: 500 status.
     */
    @Test
    void listAllRoutesOnInvalidStop() {
        client.get().uri("/list/route/St Andrews")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    /**
     * Test GET "/list/route/St Andrews Bus station/05:30/10" endpoint.
     *
     * Result: OK status with body return. All Route on Stop, "St Andrews Bus station",
     * at a specific time, "05:30" should be return. routeName should be "St Andrews to Dundee" only.
     */
    @Test
    void listAllRoutesOnStopAndTime() {
        client.get().uri("/list/route/St Andrews Bus station/05:30/10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[?(@.routeName == \"Dundee to St Andrews\")]").isEmpty()
                .jsonPath("$[?(@.routeName == \"St Andrews to Dundee\")]").isNotEmpty();
    }

    /**
     * Test GET "/list/route/St Andrews Bus station/05:00/10" endpoint.
     *
     * Result: OK status with body return. No Route on Stop, "St Andrews Bus station",
     * at a specific time, "05:00" should be return.
     */
    @Test
    void listAllRoutesOnStopAndTimeBefore() {
        client.get().uri("/list/route/St Andrews Bus station/05:00/10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("[]");
    }

    /**
     * Test GET "/list/route/St Andrews/05:30/10" endpoint.
     *
     * Result: 500 status.
     */
    @Test
    void listAllRoutesOnInvalidStopAtTime() {
        client.get().uri("/list/route/St Andrews/05:30/10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    /**
     * Test GET "/list/time/St Andrews Bus station" endpoint.
     *
     * Result: OK status with body return. All time on Stop, "St Andrews Bus station", should be return.
     * Result length should equal to 4.
     */
    @Test
    void listAllTimesOnStop() {
        client.get().uri("/list/time/St Andrews Bus station")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(4);
    }

    /**
     * Test GET "/list/time/St Andrews" endpoint.
     *
     * Result: 500 status.
     */
    @Test
    void listAllTimesOnInvalidStop() {
        client.get().uri("/list/time/St Andrews")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    /**
     * Test POST "/addStopRoute/St Andrews to Dundee/Drumoig National Golf Centre/Dundee City/05:50" endpoint.
     *
     * Return: 500 status.
     */
    @Test
    void addInvalidStopOnRoute() {
        client.post().uri("/addStopRoute/St Andrews to Dundee/Drumoig National Golf Centre/" +
                        "Dundee City/05:50")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    /**
     * Test POST "/addStopRoute/St Andrews to Dundee/Drumoig National Golf Centre/Dundee City Centre Seagate bus station/05:50" endpoint.
     * By checking all Route on Dundee City Centre Seagate bus station before and after call endpoint.
     *
     * Return: Before call endpoint should not return route "St Andrews to Dundee".
     * It should return after call endpoint.
     */
    @Test
    void addStopOnRoute() {
        client.get().uri("/list/route/Dundee City Centre Seagate bus station")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[?(@.routeName == \"St Andrews to Dundee\")]").isEmpty();

        client.post().uri("/addStopRoute/St Andrews to Dundee/Drumoig National Golf Centre/" +
                        "Dundee City Centre Seagate bus station/05:50")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        client.get().uri("/list/route/Dundee City Centre Seagate bus station")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[?(@.routeName == \"St Andrews to Dundee\")]").isNotEmpty();
    }

    /**
     * Test POST "/addStop/St Michaels, Dundee Road/St Michaels" endpoint.
     * By checking length of all Stop before and after call endpoint.
     *
     * Return: Before call endpoint should be 7. After, should be 8.
     */
    @Test
    void addNewStop() {
        client.get().uri("/list/stops")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(7);

        client.post().uri("/addStop/St Michaels, Dundee Road/St Michaels")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        client.get().uri("/list/stops")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(8);
    }

    /**
     * Test POST "/addRoute/Guardbridge to Leuchars/Guardbridge/Leuchars Railway Station/06:00/06:10" endpoint.
     *
     * Return: 500 status.
     */
    @Test
    void addNewRouteWithInvalidStop() {
        client.post().uri("/addRoute/Guardbridge to Leuchars/Guardbridge/" +
                        "Leuchars Railway Station/06:00/06:10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    /**
     * Test POST "/addRoute/Guardbridge to Leuchars/Guardbridge Innerbridge Street/Leuchars Railway Station/06:00/06:10" endpoint.
     * By checking length of all Route before and after call endpoint.
     *
     * Return: Before call endpoint should be 2. After, should be 3.
     */
    @Test
    void addNewRoute() {
        client.get().uri("/list/routes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2);

        client.post().uri("/addRoute/Guardbridge to Leuchars/Guardbridge Innerbridge Street/" +
                        "Leuchars Railway Station/06:00/06:10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        client.get().uri("/list/routes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3);
    }

    /**
     * Test POST "/addScheduleOnRoute/St Andrews to Dundee/04:00" endpoint.
     * By checking length of all Route at St Andrews bus station before and after call endpoint.
     *
     * Return: Before call endpoint should be 4. After, should be 5.
     */
    @Test
    void addScheduleOnRoute() {
        client.get().uri("/list/time/St Andrews Bus station")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(4);

        client.post().uri("/addScheduleOnRoute/St Andrews to Dundee/04:00")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        client.get().uri("/list/time/St Andrews Bus station")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(5);

    }

}
