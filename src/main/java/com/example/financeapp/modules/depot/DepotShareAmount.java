package com.example.financeapp.modules.depot;

import com.example.financeapp.modules.depot.Depot;
import com.example.financeapp.modules.share.Share;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class DepotShareAmount {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @ManyToOne
    @JoinColumn(name="share_id")
    @Setter
    private Share share;

    @ManyToOne
    @JoinColumn(name="depot_id")
    @Setter
    private Depot depot;

    @Setter
    private int amount;

    public DepotShareAmount(Share share, Depot depot, int amount) {
        this.share = share;
        this.depot = depot;
        this.amount = amount;
    }

    public DepotShareAmount() {

    }
}
