package com.example.axoncqrsbankingdemo.query.query;

import lombok.Data;

@Data
public class FindAccountByIdQuery {
    private String accountId;

    public FindAccountByIdQuery(String accountId) {
        this.accountId = accountId;
    }
}