package com.egebakken.springdemo.user;

import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

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
    public void addUser() {
        User pinnekjott = repo.save(new User("pinnekjott"));
        Assertions.assertThat(pinnekjott)
                  .hasFieldOrPropertyWithValue("id", 3L)
                  .hasFieldOrPropertyWithValue("username", "pinnekjott");
        Assertions.assertThat(countRowsInTable(template, "user"))
                  .isEqualTo(2);
    }

    @Test
    public void findUser() {
        Assertions.assertThat(repo.findById(1L))
                  .isPresent()
                  .hasValueSatisfying(u -> {
                      Assertions.assertThat(u.getUsername())
                                .isEqualTo("smalahove");
                  });
    }

    @Test
    public void existsByUsername() {
        Assertions.assertThat(repo.existsByUserName("smalahove"))
                  .isTrue();
    }
}