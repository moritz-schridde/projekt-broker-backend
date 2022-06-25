package com.example.financeapp.modules.depot.responses;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

public class DepotPerformanceResponse {
    @Getter
    @Setter
    private double value;

    @Getter
    @Setter
    private Timestamp timestamp;
}
