package com.example.financeapp.modules.user;

import com.example.financeapp.modules.user.id.UserId;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

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
    public ResponseEntity<User> getUser(@PathVariable("userId") UserId userId) {
        return ResponseEntity.ok(userService.getUser(userId.getUUID()));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createUser(User user) throws URISyntaxException {
        UserId created = userService.createUser(user);
        if (created != null) {
            URI uri = new URIBuilder()
                    .addParameter("scheme", "https")
                    .addParameter("host", "api.trade-empire.karottenkameraden.de")
                    .addParameter("path", "/user/create/" + created.getUUID().toString())
                    .build();
            return ResponseEntity.created(uri).build();
        } else {
            return ResponseEntity.badRequest().body("User already exists or the input is invalid");
        }
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateUser(@PathVariable("userId") UserId userId, User user) {
        boolean updated = userService.updateUser(userId.getUUID(), user);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") UserId userId) {
        boolean deleted = userService.deleteUser(userId);
        return (deleted) ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }
}
