package com.example.financeapp.modules.user;

import com.example.financeapp.modules.user.id.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("{userId}")
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> getUserByUUID(@PathVariable("UUID") UUID userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }
}
