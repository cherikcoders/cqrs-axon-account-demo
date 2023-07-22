package com.example.axoncqrsbankingdemo.command.aggregate;


import com.example.axoncqrsbankingdemo.command.command.CreateAccountCommand;
import com.example.axoncqrsbankingdemo.command.command.DepositMoneyCommand;
import com.example.axoncqrsbankingdemo.command.command.WithdrawMoneyCommand;
import com.example.axoncqrsbankingdemo.common.event.AccountActivatedEvent;
import com.example.axoncqrsbankingdemo.common.event.AccountCreatedEvent;
import com.example.axoncqrsbankingdemo.common.event.AccountCreditedEvent;
import com.example.axoncqrsbankingdemo.common.event.AccountDebitedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

@Aggregate /// this is my main aggregate class , aggregate is an object that keeps the state of application
    @Slf4j //easier logging
    public class AccountAggregate {

        @AggregateIdentifier
        private String accountId;
        private BigDecimal balance;
        private String status;

        //axon need this no args constructor to apply events to account-aggregate
        //not having this throws exception
        public AccountAggregate() {
        }

        /**
         * first we write the code to handel create account command and then we come back
         * */

        //when we receive a createAccount command we need to apply an account-created-event
        @CommandHandler // let axon know that this constructor is "for handling create account command" next we need to handle the event too
        public AccountAggregate(CreateAccountCommand createAccountCommand) {
            log.info("CreateAccountCommand received.");
            /**
             * by using AggregateLifecycle.apply the event presistes in event-store and event-store dispatches the event
             * to query-side for saving the account  into database
             */
            AggregateLifecycle.apply(new AccountCreatedEvent(
                    createAccountCommand.getId(),
                    createAccountCommand.getBalance()));
        }

        //to handle the account create event
        @EventSourcingHandler
        public void on(AccountCreatedEvent accountCreatedEvent) {
            log.info("An AccountCreatedEvent occurred.");
            //setting  the current state of aggregate
            this.accountId = accountCreatedEvent.getId();
            this.balance = accountCreatedEvent.getBalance();
            this.status = "CREATED";

            /**
             * after create , the account needs to be activated
             * normaly the bank requires you to put some money into the account to gets activated
             * we want the account to activate instantly*/

            AggregateLifecycle.apply(new AccountActivatedEvent(this.accountId, "ACTIVATED"));
        }



        @EventSourcingHandler
        public void on(AccountActivatedEvent accountActivatedEvent) {
            log.info("An AccountActivatedEvent occurred.");
            this.status = accountActivatedEvent.getStatus();
        }

        @CommandHandler
        public void on(DepositMoneyCommand depositMoneyCommand) {
            log.info("DepositMoneyCommand received.");
            AggregateLifecycle.apply(new AccountCreditedEvent(
                    depositMoneyCommand.getId(),
                    depositMoneyCommand.getAmount()));
        }

        @EventSourcingHandler
        public void on(AccountCreditedEvent accountCreditedEvent) {
            log.info("An AccountCreditedEvent occurred.");
            this.balance = this.balance.add(accountCreditedEvent.getAmount());
        }

        @CommandHandler
        public void on(WithdrawMoneyCommand withdrawMoneyCommand) {
            log.info("WithdrawMoneyCommand received.");
            AggregateLifecycle.apply(new AccountDebitedEvent(
                    withdrawMoneyCommand.getId(),
                    withdrawMoneyCommand.getAmount()));
        }

        @EventSourcingHandler
        public void on(AccountDebitedEvent accountDebitedEvent) {
            log.info("An AccountDebitedEvent occurred.");
            this.balance = this.balance.subtract(accountDebitedEvent.getAmount());
        }
    }

