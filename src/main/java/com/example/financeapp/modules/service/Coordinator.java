package com.example.financeapp.modules.service;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

public class Coordinator {

    @Getter
    @Setter
    @NotNull
//    ENUM
    private String state;

    public String register() {
        return "";
    }
    public String login() {
        return "";
    }
    public String order() {
        return "";
    }
    public String validate() {
        return "";
    }
}
