package com.example.axoncqrsbankingdemo.query.controller;

import com.example.axoncqrsbankingdemo.query.entity.Account;
import com.example.axoncqrsbankingdemo.query.query.FindAccountByIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.GenericQueryMessage;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/manage-account")
public class ManageAccountController {

    private final QueryBus queryGateway;

    public ManageAccountController(QueryBus queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/get-account")
    public ResponseEntity<Account> getAccount(@RequestParam String id) {
        GenericQueryMessage<FindAccountByIdQuery,  Account> query =
                new GenericQueryMessage<>(new FindAccountByIdQuery(id), ResponseTypes.instanceOf(Account.class));
        queryGateway.query(
                query
        );

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

/*    @GetMapping("/get-account")
    public ResponseEntity<List<Account>> getAccount1(@RequestParam String id) {
        List<Account> account = queryGateway.query(
                new FindAccountByIdQuery(id), Account.class
        ).join();

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }*/
}