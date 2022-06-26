package com.example.financeapp.modules.user;

import com.example.financeapp.modules.user.communication.models.UserCreateCommunicationModel;
import com.example.financeapp.modules.user.communication.models.UserRequestCommunicationModel;
import com.example.financeapp.modules.user.communication.models.UserUpdateCommunicationModel;
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

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserRequestCommunicationModel> getUser() {
        String email = userService.getCurrentUsersEmail();
        User users = userService.getUserByEmail(email);
        return ResponseEntity.ok(new UserRequestCommunicationModel());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createUser(@RequestBody UserCreateCommunicationModel userCreateCommunicationModel) throws URISyntaxException {
        UUID created = userService.createUser(userCreateCommunicationModel);
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

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateUser(@RequestBody UserUpdateCommunicationModel userUpdateRequest) {
        User user = userService.getUserByEmail(userService.getCurrentUsersEmail());
        boolean updated = userService.updateUser(user, userUpdateRequest);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteUser() {
        String email = userService.getCurrentUsersEmail();
        boolean deleted = userService.deleteUser(userService.getUserByEmail(email));
        return (deleted) ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }

}
