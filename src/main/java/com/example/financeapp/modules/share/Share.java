package com.example.financeapp.modules.share;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;



@Entity
@Getter
@Setter
public class Share {

    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    private String name;

    private String wkn;

    private byte iconId;

    private double price;

    private String category;

    public Share(String name, String wkn, byte iconId, double price, String category) {
        this.name = name;
        this.wkn = wkn;
        this.iconId = iconId;
        this.price = price;
        this.category = category;
    }

    public Share(@NotNull Long id, String name, String wkn, double price, String category) {
        this.id = id;
        this.name = name;
        this.wkn = wkn;
        this.price = price;
        this.category = category;
    }

    public Share(String name, String wkn, double price, String category) {
        this.id = id;
        this.name = name;
        this.wkn = wkn;
        this.price = price;
        this.category = category;
    }

    public Share () {

    }
}
