package com.example.financeapp.modules.depot;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Depot {

    @Id
    @GeneratedValue
    @NotNull
    //@Column(name = "depot_id")
    private Long id;

    @Setter
    @NotNull
    private int totalNumberShares;

    @Setter
    @OneToMany(mappedBy = "share")
    private List<DepoShareAmount> mySharesAmount;

    @Setter
    @NotNull
    private int totalValue; // in € Cent

    @Setter
    private int totalCash; // in € Cent

    public Depot() {

    }

    /*public String getShareInformation() {
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
    }*/
}

