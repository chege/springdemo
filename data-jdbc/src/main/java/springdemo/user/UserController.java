package springdemo.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * keywords: HATEOAS, Spring Data, package private, CRUD service, no inner model
 */
@RequestMapping(
        path = "users"
)
@RestController
class UserController {
    private final UserRepo users;

    UserController(UserRepo users) {
        this.users = users;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}"
    )
    EntityModel<User> get(@PathVariable long id) {
        return new EntityModel<>(users.findById(id)
                                      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                                 linkTo(methodOn(UserController.class).get(id)).withSelfRel());
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    CollectionModel<EntityModel<User>> all() {
        return new CollectionModel<>(StreamSupport.stream(users.findAll().spliterator(), false)
                                                  .map(u -> new EntityModel<>(u, linkTo(methodOn(UserController.class).get(u.getId())).withSelfRel()))
                                                  .collect(Collectors.toList()),
                                     linkTo(methodOn(UserController.class).all()).withSelfRel());
    }


    @RequestMapping(
            method = RequestMethod.POST
    )
    ResponseEntity<Void> post(@RequestBody Map<String, String> usernameMap) {
        String username = Optional.ofNullable(usernameMap.get("username"))
                                  .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "username cannot be null"));
        String email = Optional.ofNullable(usernameMap.get("email"))
                               .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "email cannot be null"));

        if (users.existsByUserName(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("user %s already exists", username));
        } else {
            User savedUser = users.save(new User(username, email));
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
