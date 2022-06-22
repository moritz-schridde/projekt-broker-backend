package com.example.financeapp.modules.user;

import com.example.financeapp.modules.user.requests.UserCreateRequest;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> getUser() {
        String email = getCurrentUsersEmail();
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createUser(@RequestBody UserCreateRequest userCreateRequest) throws URISyntaxException {
        UUID created = userService.createUser(userCreateRequest);
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

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        User user = userService.getUserByEmail(getCurrentUsersEmail());
        boolean updated = userService.updateUser(user, userUpdateRequest);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteUser() {
        String email = getCurrentUsersEmail();
        boolean deleted = userService.deleteUser(userService.getUserByEmail(email));
        return (deleted) ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }

    private String getCurrentUsersEmail() {
        return ((com.example.financeapp.auth.models.User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getEmail();
    }
}
