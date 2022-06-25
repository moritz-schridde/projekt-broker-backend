package com.example.financeapp.modules.bank;

import com.example.financeapp.modules.user.User;
import com.example.financeapp.modules.user.communication.models.UserCreateCommunicationModel;
import com.example.financeapp.modules.user.communication.models.UserUpdateCommunicationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.example.financeapp.modules.user.UserServiceImplementation;
import com.example.financeapp.modules.user.UserRepository;
import com.example.financeapp.modules.user.UserService;

import java.util.List;
import java.util.UUID;

@Component
public class BankServiceImplementation implements BankService {

    private final BankRepository bankRepository;
    private final UserService userService;

    @Autowired
    public BankServiceImplementation(BankRepository bankRepository, UserService userService){
        this.bankRepository = bankRepository;
        this.userService = userService;
    }

    @Override
    public Boolean changeAmount(String iban, double amount, Bank.mode mode) throws Exception {

        int sign = (mode == Bank.mode.WITHDRAW ? 1 : -1);

        Bank bank2save = bankRepository.getBankByIban(iban);

        User user = userService.getUserByEmail("email@mail.com");

        if(user == bank2save.getUser()){
            bank2save.setAmount(amount * sign + bank2save.getAmount());
            bankRepository.save(bank2save);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Bank> getBank(){
        User user = userService.getUserByEmail("email@mail.com");
        return bankRepository.getBankByUser(user);
    }

    public String getCurrentUsersEmail() {
        return ((com.example.financeapp.auth.models.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
    }

}
