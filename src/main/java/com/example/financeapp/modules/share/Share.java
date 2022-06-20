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
    private byte iconId;

    @Getter
    @Setter
    private double price;

    @Getter
    @Setter
    private String category;

    public Share(@NotNull Long id, String name, byte iconId, double price, String category) {
        this.id = id;
        this.name = name;
        this.iconId = iconId;
        this.price = price;
        this.category = category;
    }

    public Share () {

    }
}
