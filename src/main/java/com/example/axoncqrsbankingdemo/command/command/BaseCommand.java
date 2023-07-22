package com.example.axoncqrsbankingdemo.command.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class BaseCommand<T> {
//can use long or string ids
    @TargetAggregateIdentifier//letting axon know this is the identifier for account-aggregate that the command targets
    private final T id;

    public BaseCommand(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}