package com.egebakken.springdemo.user;

import org.springframework.data.annotation.Id;

public class User {
    @Id
    private Long id;
    private final String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
