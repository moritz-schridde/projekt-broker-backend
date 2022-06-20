package com.example.financeapp.modules.user;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> getUser() {
        return ResponseEntity.ok(new User());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createUser(@RequestBody String name, @RequestBody String surname, @RequestBody String email, @RequestBody String phoneNumber, @RequestBody String street, @RequestBody String houseNumber, @RequestBody String postalCode, @RequestBody String city, @RequestBody String country, @RequestBody String taxNumber, @RequestBody String birhtDay, @RequestBody String birhtMonth, @RequestBody String birthYear) throws URISyntaxException {
        Boolean created = true;
        if (created != null) {
            URI uri = new URIBuilder()
                    .addParameter("scheme", "https")
                    .addParameter("host", "api.trade-empire.karottenkameraden.de")
                    .addParameter("path", "/user/create/" + created)
                    .build();
            return ResponseEntity.created(uri).body("Success");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateUser(@RequestBody String name, @RequestBody String surname, @RequestBody String email, @RequestBody String phoneNumber, @RequestBody String street, @RequestBody String houseNumber, @RequestBody String postalCode, @RequestBody String city, @RequestBody String country, @RequestBody String taxNumber, @RequestBody String birhtDay, @RequestBody String birhtMonth, @RequestBody String birthYear) {
        boolean updated = true;
        if (updated) {
            return ResponseEntity.ok().body("Success");
        } else {
            return ResponseEntity.status(409).body("Failed");
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") UUID userId) {
        boolean deleted = userService.deleteUser(userId);
        return (deleted) ? ResponseEntity.ok().body("Success") : ResponseEntity.badRequest().body("Failed");
    }
}
