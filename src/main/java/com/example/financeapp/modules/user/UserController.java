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

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> getUser(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createUser(User user) throws URISyntaxException {
        UUID created = userService.createUser(user);
        if (created != null) {
            URI uri = new URIBuilder()
                    .addParameter("scheme", "https")
                    .addParameter("host", "api.trade-empire.karottenkameraden.de")
                    .addParameter("path", "/user/create/" + created)
                    .build();
            return ResponseEntity.created(uri).build();
        } else {
            return ResponseEntity.badRequest().body("User already exists or the input is invalid");
        }
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateUser(@PathVariable("userId") UUID userId, User user) {
        boolean updated = userService.updateUser(userId, user);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") UUID userId) {
        boolean deleted = userService.deleteUser(userId);
        return (deleted) ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }
}
