package com.example.axoncqrsbankingdemo.command.service;

import com.example.axoncqrsbankingdemo.command.command.CreateAccountCommand;
import com.example.axoncqrsbankingdemo.command.command.DepositMoneyCommand;
import com.example.axoncqrsbankingdemo.command.command.WithdrawMoneyCommand;
import com.example.axoncqrsbankingdemo.command.dto.CreateAccountRequest;
import com.example.axoncqrsbankingdemo.command.dto.WithdrawalRequest;
import com.example.axoncqrsbankingdemo.command.dto.DepositRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountCommandService {

    //this is where the commands come from
    private final CommandGateway commandGateway;

    public AccountCommandService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    //completableFuture means it doesnt have to wait for the command to fully execute
    //it just returns the account id that we generated why? its how the axon implemented it
    public CompletableFuture<String> createAccount(CreateAccountRequest createAccountRequest) {
        //this is where we create and send command to command gateway then is calls the command in accountAggregate
        return commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                createAccountRequest.getStartingBalance())
        );
    }

    public CompletableFuture<String> depositToAccount(DepositRequest depositRequest) {
        return commandGateway.send(new DepositMoneyCommand(
                depositRequest.getAccountId(),
                depositRequest.getAmount()
        ));
    }

    public CompletableFuture<String> withdrawFromAccount(WithdrawalRequest withdrawalRequest) {
        return commandGateway.send(new WithdrawMoneyCommand(
                withdrawalRequest.getAccountId(),
                withdrawalRequest.getAmount()
        ));
    }
}