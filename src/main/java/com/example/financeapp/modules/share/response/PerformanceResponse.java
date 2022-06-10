package com.example.financeapp.modules.share.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

public class PerformanceResponse {

    @Getter
    @Setter
    private double currentValue;

    @Getter
    @Setter
    private Timestamp timestamp;
}
