package com.egebakken.springdemo.user;

import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;


@JdbcTest
@Import(
        UserRepo.class
)
class UserRepoTest {
    @Autowired
    JdbcTemplate template;
    @Autowired
    UserRepo repo;

    @Test
    public void addUser() {
        repo.add(new User("kalleblanka"));
        Assertions.assertThat(countRowsInTable(template, "user"))
                  .isEqualTo(2);
    }

    @Test
    public void findUser() {
        User user = repo.find(1);
        Assertions.assertThat(user.username)
                  .isEqualTo("Jon Hopkins");
    }
}