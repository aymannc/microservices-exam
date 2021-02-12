package com.anc.accountsmanagement.controllers;

import com.anc.accountsmanagement.entities.Account;
import com.anc.accountsmanagement.entities.AccountStates;
import com.anc.accountsmanagement.entities.Operation;
import com.anc.accountsmanagement.service.AccountOperations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
public class API {
    private final AccountOperations accountOperations;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public API(AccountOperations accountOperations, KafkaTemplate<String, Object> kafkaTemplate) {
        this.accountOperations = accountOperations;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/account/clients/{accountId}")
    public Account addAccount(@RequestBody Account account, @PathVariable final Long accountId) {
        //Breakpoint
        account = accountOperations.addAccount(account, accountId);
        return account;
    }

    @GetMapping("/account/addDeposit/{accountId}")
    public ResponseEntity<Operation> addDeposit(@PathVariable final Long accountId, @QueryParam("amount") Float amount) {
        Operation operation = accountOperations.addDeposit(amount, accountId);
        if (operation != null)
            kafkaTemplate.send("DEPOSIT", operation);

        return new ResponseEntity<>(operation, operation != null ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping("/account/addWithdraw/{accountId}")
    public ResponseEntity<Operation> addWithdraw(@PathVariable final Long accountId, @QueryParam("amount") Float amount) {
        Operation operation = accountOperations.addWithdraw(amount, accountId);
        if (operation != null)
            kafkaTemplate.send("WITHDRAW", operation);
        return new ResponseEntity<>(operation, operation != null ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping("/account/addTransaction/")
    public ResponseEntity<List<Operation>> addTransaction(@QueryParam("amount") Float amount, @QueryParam("fromAccountId") Long fromAccountId,
                                                          @QueryParam("toAccountId") Long toAccountId) {
        List<Operation> operations = accountOperations.addTransaction(amount, fromAccountId, toAccountId);
        if (operations.get(0) != null)
            kafkaTemplate.send("WITHDRAW", operations.get(0));
        else if (operations.get(1) != null) {
            kafkaTemplate.send("DEPOSIT", operations.get(1));
        }
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }

    @GetMapping("/account/getAllOperations/{accountId}")
    public Page<Operation> getAllOperations(@PathVariable final Long accountId, Pageable pageable) {
        return accountOperations.getAllOperations(accountId, pageable);
    }

    @GetMapping("/account/{accountId}")
    public Account getAccount(@PathVariable final Long accountId) {
        return accountOperations.getAccount(accountId);
    }

    @GetMapping("/account/activateOrSuspend/{accountId}")
    public Account activateOrSuspend(@PathVariable final Long accountId, @QueryParam("state") AccountStates state) {
        return accountOperations.activateOrSuspend(accountId, state);
    }
}
