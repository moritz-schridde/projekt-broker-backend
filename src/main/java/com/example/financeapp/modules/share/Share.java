package com.example.financeapp.modules.share;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;



@Entity
public class Share {
    @Getter
    @Setter
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String wkn;

    @Getter
    @Setter
    private double price;

    @Getter
    @Setter
    private double checkInterest;


    public Share(@NotNull Long id, String name, String wkn, double price) {
        this.id = id;
        this.name = name;
        this.wkn = wkn;
        this.price = price;
    }

    public Share(String name, String wkn, double price) {
        this.id = id;
        this.name = name;
        this.wkn = wkn;
        this.price = price;
    }

    public Share() {

    }
}
