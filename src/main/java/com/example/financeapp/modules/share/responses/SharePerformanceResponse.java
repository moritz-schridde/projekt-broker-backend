package com.example.financeapp.modules.share.responses;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

public class SharePerformanceResponse {

    @Getter
    @Setter
    private double currentValue;

    @Getter
    @Setter
    private Timestamp timestamp;
}
