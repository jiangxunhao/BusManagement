package stacs.bus.busManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Main file for Spring Boot application.
 */
@CrossOrigin (origins = "*")
@SpringBootApplication
public class BusManagement {

    public static void main(String[] args) {
        SpringApplication.run(BusManagement.class, args);
    }

}

