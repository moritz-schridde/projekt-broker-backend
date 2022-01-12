package com.example.financeapp.modules.user;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

public class Depot {

    @Getter
    @Setter
    @NotNull
    private int totalShares;

    @Getter
    @Setter
    private List<UUID> listOfShares;

    @Getter
    @Setter
    @NotNull
    private double totalValue;

    public String getShareInformation() {
        return "";
    }
    public String buy() {
        return "";
    }
    public String sell() {
        return "";
    }
    public String getPerformance() {
        return "";
    }
}

