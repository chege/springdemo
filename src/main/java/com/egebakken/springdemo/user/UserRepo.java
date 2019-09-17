package com.egebakken.springdemo.user;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
class UserRepo {
    @Autowired
    JdbcTemplate template;

    SimpleJdbcInsert insert;

    UserRepo(JdbcTemplate template) {
        this.insert = new SimpleJdbcInsert(template)
                .withTableName("user")
                .usingGeneratedKeyColumns("id");
        this.template = template;
    }

    User find(long id) {
        return this.template.queryForObject(
                "select * from user where id=?",
                (rs, rowNum) -> new User(rs.getString("username")),
                id
        );
    }

    void add(User user) {
        Map<String, Object> args = new HashMap<>();
        args.put("username", user);
        this.insert.executeAndReturnKey(args);
    }


}
