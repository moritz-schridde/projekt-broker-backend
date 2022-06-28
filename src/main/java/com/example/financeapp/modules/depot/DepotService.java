package com.example.financeapp.modules.depot;

import com.example.financeapp.modules.depot.responses.DepotPerformanceResponse;
import com.example.financeapp.modules.depot.responses.DepotResponse;

import java.util.ArrayList;
import java.util.List;

public interface DepotService {
    ArrayList<DepotPerformanceResponse> getDepotPerformance() throws Exception;
    DepotResponse getUserDepotShareList() throws Exception;
}