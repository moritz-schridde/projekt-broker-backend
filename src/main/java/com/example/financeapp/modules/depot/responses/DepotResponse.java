package com.example.financeapp.modules.depot.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class DepotResponse {
    @Getter
    @Setter
    //Restliche freies Geld auf dem Konto
    private double availableMoney;

    @Getter
    @Setter
    private ArrayList<DepotShareInfoModel> shares;
}
