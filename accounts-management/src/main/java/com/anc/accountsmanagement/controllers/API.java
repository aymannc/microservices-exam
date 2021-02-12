package com.anc.accountsmanagement.controllers;

import com.anc.accountsmanagement.entities.Account;
import com.anc.accountsmanagement.entities.AccountStates;
import com.anc.accountsmanagement.entities.Operation;
import com.anc.accountsmanagement.service.AccountOperations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
public class API {
    private final AccountOperations accountOperations;

    public API(AccountOperations accountOperations) {
        this.accountOperations = accountOperations;
    }

    @PostMapping("/account/clients/{accountId}")
    public Account addAccount(@RequestBody Account account, @PathVariable final Long accountId) {
        //Breakpoint
        account = accountOperations.addAccount(account, accountId);
        return account;
    }

    @GetMapping("/account/addDeposit/{accountId}")
    public ResponseEntity<Boolean> addDeposit(@PathVariable final Long accountId, @QueryParam("amount") Float amount) {
        Boolean response = accountOperations.addDeposit(amount, accountId);
        return new ResponseEntity<>(response, response ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping("/account/addWithdraw/{accountId}")
    public ResponseEntity<Boolean> addWithdraw(@PathVariable final Long accountId, @QueryParam("amount") Float amount) {
        Boolean response = accountOperations.addWithdraw(amount, accountId);
        return new ResponseEntity<>(response, response ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping("/account/addTransaction/")
    public ResponseEntity<Boolean> addTransaction(@QueryParam("amount") Float amount, @QueryParam("fromAccountId") Long fromAccountId,
                                                  @QueryParam("toAccountId") Long toAccountId) {
        Boolean response = accountOperations.addTransaction(amount, fromAccountId, toAccountId);
        return new ResponseEntity<>(response, response ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED);
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
