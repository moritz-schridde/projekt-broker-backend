package com.example.financeapp.modules.depot.responses;

import com.example.financeapp.modules.share.Share;
import lombok.Getter;
import lombok.Setter;

public class DepotShareInfoModel {
    @Getter
    @Setter
    private double amount;

    @Getter
    @Setter
    //Durchschnittspreis
    private double purchasePrice;

    @Getter
    @Setter
    private Share share;
}
