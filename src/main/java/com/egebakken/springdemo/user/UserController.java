package com.egebakken.springdemo.user;

import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequestMapping(
        path = "user"
)
@RestController
class UserController {
    UserRepo users;

    UserController(UserRepo users) {
        this.users = users;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}"
    )
    User get(@PathVariable long id) {
        return users.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    Iterable<User> all() {
        return users.findAll();
    }


    @RequestMapping(
            method = RequestMethod.POST
    )
    ResponseEntity<Void> post(@RequestBody Map<String, String> usernameMap) {
        String username = Optional.ofNullable(usernameMap.get("username"))
                                  .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "username cannot be null"));

        if (users.existsByUserName(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("user %s already exists", username));
        } else {
            User savedUser = users.save(new User(username));
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                                                                     .path("/{id}")
                                                                     .buildAndExpand(savedUser.getId())
                                                                     .toUri())
                                 .build();
        }
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/{id}"
    )
    ResponseEntity<Void> delete(@PathVariable long id) {
        if (users.existsById(id)) {
            users.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}