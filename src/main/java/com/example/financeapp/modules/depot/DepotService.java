package com.example.financeapp.modules.depot;

import com.example.financeapp.modules.depot.responses.DepotPerformanceResponse;

import java.util.List;

public interface DepotService {
    List<DepotPerformanceResponse> getDepotPerformance() throws Exception;
    Depot getUserDepot() throws Exception;
}
