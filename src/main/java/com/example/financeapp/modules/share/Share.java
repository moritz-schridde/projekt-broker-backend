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


    @Getter
    @Setter
    private String shortname;

    @Getter
    @Setter
    private String wkn;


    private byte iconId;

    private double price;

    private String category;

    public Share(String name, String shortname, byte iconId, double price, String category, String wkn) {
        this.name = name;
        this.shortname = shortname;
        this.iconId = iconId;
        this.price = price;
        this.category = category;
        this.wkn = wkn;
    }

    public Share(String name, String shortname, byte iconId, double price, String category) {
        this.name = name;
        this.shortname = shortname;
        this.iconId = iconId;
        this.price = price;
        this.category = category;
    }
   


    public Share () {

    }
}
