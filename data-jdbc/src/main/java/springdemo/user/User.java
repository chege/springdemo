package springdemo.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

class User {
    @Id
    private final Long id;
    private final String username;

    User(String username) {
        this.id = null;
        this.username = username;
    }

    @PersistenceConstructor
    User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * This is needed when the ID is autogenerated by the database
     */
    public User withId(Long id) {
        return new User(id, this.username);
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }
}