package com.example.financeapp.modules.user;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

public class Bankaccount {

    @Getter
    @Setter
    @NotNull
    private String iban;

    public String deposit() {
        return "";
    }
    public String withdraw() {
        return "";
    }

}
