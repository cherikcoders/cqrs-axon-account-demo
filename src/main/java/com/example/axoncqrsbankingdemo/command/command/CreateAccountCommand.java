package com.example.axoncqrsbankingdemo.command.command;

import java.math.BigDecimal;


public class CreateAccountCommand extends BaseCommand<String> {
/**
 * just has account id and starting balance*/
    private final BigDecimal balance;

    public CreateAccountCommand(String id, BigDecimal balance) {
        super(id);
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
