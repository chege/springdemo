package springdemo;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ApplicationTests {

    @LocalServerPort
    String port;

    @Autowired
    private WebTestClient client;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void _findAllUsers() { // starts with underscore to force junit 4 to run it before methods mutating the DB. JUnit5 has an @Order annotation
        client.get()
              .uri("users")
              .exchange()
              .expectStatus()
              .isOk()
              .expectBody()
              .json(format(
                      "{\n"
                              + "  \"_embedded\": {\n"
                              + "    \"userList\": [\n"
                              + "      {\n"
                              + "        \"id\": 1,\n"
                              + "        \"username\": \"prince\",\n"
                              + "        \"_links\": {\n"
                              + "          \"self\": {\n"
                              + "            \"href\": \"http://localhost:%s/users/1\"\n"
                              + "          }\n"
                              + "        }\n"
                              + "      },\n"
                              + "      {\n"
                              + "        \"id\": 2,\n"
                              + "        \"username\": \"elvis\",\n"
                              + "        \"_links\": {\n"
                              + "          \"self\": {\n"
                              + "            \"href\": \"http://localhost:%s/users/2\"\n"
                              + "          }\n"
                              + "        }\n"
                              + "      }\n"
                              + "    ]\n"
                              + "  },\n"
                              + "  \"_links\": {\n"
                              + "    \"self\": {\n"
                              + "      \"href\": \"http://localhost:%s/users\"\n"
                              + "    }\n"
                              + "  }\n"
                              + "}", port, port, port
              ));

    }

    @Test
    public void _getUser() {
        client.get()
              .uri("users/1")
              .exchange()
              .expectStatus()
              .isOk()
              .expectBody()
              .json(format(
                      "{\n"
                              + "  \"id\": 1,\n"
                              + "  \"username\": \"prince\",\n"
                              + "  \"email\": \"prince.pedersen@sol.no\",\n"
                              + "  \"_links\": {\n"
                              + "    \"self\": {\n"
                              + "      \"href\": \"http://localhost:%s/users/1\"\n"
                              + "    }\n"
                              + "  }\n"
                              + "}", port
              ));

    }

    @Test
    public void getNonExistentUser() {
        client.get()
              .uri("users/999")
              .exchange()
              .expectStatus()
              .isNotFound();
    }


    @Test
    public void addUser() {
        client.post()
              .uri("users")
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue("{\n"
                                 + "  \"username\": \"alex\",\n"
                                 + "  \"email\":\"alex.lifeson@rush.com\"\n"
                                 + "}")
              .exchange()
              .expectStatus()
              .isCreated()
              .expectHeader()
              .valueMatches("Location", format("http://localhost:%s/users/3", port));

        assertThat(jdbcTemplate.queryForMap(format("select * from user where id=%d", 3)))
                .contains(entry("username", "alex"))
                .contains(entry("email", "alex.lifeson@rush.com"));
    }

    @Test
    public void addExistentUsername() {
        // using the count from the db removes the need to update the test if more test data are added to the test in-mem DB (data.sql)
        int previousCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "user");

        client.post()
              .uri("users")
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue("{\n"
                                 + "  \"username\": \"elvis\",\n"
                                 + "  \"email\":\"elvis.ellingsen@sol.no\"\n"
                                 + "}")
              .exchange()
              .expectStatus()
              .isEqualTo(HttpStatus.CONFLICT);

        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "user"))
                .isEqualTo(previousCount);
    }

    @Test
    public void deleteUser() {
        client.delete()
              .uri("users/1")
              .exchange()
              .expectStatus()
              .isNoContent();

        assertThat(jdbcTemplate.queryForList(format("select * from user where id=%d", 1)))
                .isEmpty();
    }

    @Test
    public void deleteNonExistentUser() {
        // using the count from the db removes the need to update the test if more test data are added to the test in-mem DB (data.sql)
        int previousCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "user");

        client.delete()
              .uri("users/999")
              .exchange()
              .expectStatus()
              .isNotFound();

        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "user"))
                .isEqualTo(previousCount);
    }
}
