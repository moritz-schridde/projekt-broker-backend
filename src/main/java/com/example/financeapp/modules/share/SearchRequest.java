package com.example.financeapp.modules.share;

import lombok.Getter;
import lombok.Setter;

public class SearchRequest {

    @Getter
    @Setter
    public String category;

    @Getter
    @Setter
    public String name;

    public SearchRequest(String category, String name) {
        this.category = category;
        this.name = name;
    }
}
