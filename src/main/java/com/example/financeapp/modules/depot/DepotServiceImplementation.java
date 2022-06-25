package com.example.financeapp.modules.depot;

import com.example.financeapp.modules.depot.responses.DepotPerformanceResponse;
import com.example.financeapp.modules.user.User;
import com.example.financeapp.modules.user.UserController;
import com.example.financeapp.modules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepotServiceImplementation implements DepotService {

    private final UserService userService;

    @Autowired
    public DepotServiceImplementation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<DepotPerformanceResponse> getDepotPerformance() throws Exception {
        String email = UserController.getCurrentUsersEmail();
        User currentUser = userService.getUserByEmail(email);
        return null;
    }

    @Override
    public Depot getUserDepot() throws Exception {
        String email = UserController.getCurrentUsersEmail();
        User currentUser = userService.getUserByEmail(email);
        Depot userDepot = currentUser.getMyDepot();
        return userDepot;
    }
}
