package springdemo.person;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(
        path = "person"
)
public class PersonController {
    PersonRepo persons;

    @RequestMapping(
            method = RequestMethod.POST
    )
    public ResponseEntity<Void> post(Person person) {
        if (persons.exists(Example.of(person))) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .build();
        } else {
            Person savedPerson = persons.save(person);
            return ResponseEntity.created(fromCurrentRequest()
                                                  .path("/{id}")
                                                  .buildAndExpand(savedPerson.getId())
                                                  .toUri())
                                 .build();
        }
    }
}
