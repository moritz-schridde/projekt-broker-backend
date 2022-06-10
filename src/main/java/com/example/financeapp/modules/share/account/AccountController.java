package com.example.financeapp.modules.share.account;

import com.example.financeapp.auth.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    
    @Autowired
    AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> findAllAccounts() {
        return ResponseEntity.ok(accountService.findAllAccounts());
    }

    @GetMapping("/me")
    public ResponseEntity<String> getMyAccount() {
        String email = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        Account account = accountService.findMyAccount(email);
        return ResponseEntity.ok(email);
    }
}
