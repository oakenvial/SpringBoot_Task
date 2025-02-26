package org.example.springboottask1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootTask1ApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    private static final GenericContainer<?> devApp = new GenericContainer<>("devapp").withExposedPorts(8080);
    private static final GenericContainer<?> prodApp = new GenericContainer<>("prodapp").withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void devAppReturnsDevInfo() {
        String URL = "http://localhost:" + devApp.getMappedPort(8080) + "/profile";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(URL, String.class);
        assertEquals("Current profile is dev", forEntity.getBody());
    }

    @Test
    void prodAppReturnsProdInfo() {
        String URL = "http://localhost:" + prodApp.getMappedPort(8081) + "/profile";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(URL, String.class);
        assertEquals("Current profile is production", forEntity.getBody());
    }

}
