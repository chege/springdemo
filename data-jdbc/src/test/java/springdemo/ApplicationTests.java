package springdemo;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ApplicationTests {

    @LocalServerPort
    String port;

    @Autowired
    private WebTestClient client;

    @Test
    public void findAllUsers() {
        client.get()
              .uri("users")
              .exchange()
              .expectStatus()
              .isOk()
              .expectBody()
              .json(String.format(
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
}
