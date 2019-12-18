package springdemo.user;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJdbcTest
public class UserRepoTest {
    @Autowired
    JdbcTemplate template;
    @Autowired
    UserRepo repo;

    @Test
    public void existsByUsername() {
        Assertions.assertThat(repo.existsByUserName("smalahove"))
                  .isTrue();
    }
}