package com.anc.accountsmanagement.service;

import com.anc.accountsmanagement.entities.Account;
import com.anc.accountsmanagement.entities.AccountStates;
import com.anc.accountsmanagement.entities.Operation;
import com.anc.accountsmanagement.entities.OperationTypes;
import com.anc.accountsmanagement.feign.ClientRestClient;
import com.anc.accountsmanagement.models.Client;
import com.anc.accountsmanagement.repositories.AccountRepository;
import com.anc.accountsmanagement.repositories.OperationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountOperationsImpl implements AccountOperations {
    ClientRestClient clientRestClient;
    AccountRepository accountRepository;
    OperationRepository operationRepository;

    public AccountOperationsImpl(ClientRestClient clientRestClient, AccountRepository accountRepository, OperationRepository operationRepository) {
        this.clientRestClient = clientRestClient;
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public Account addAccount(Account account, Long clientId) {
        Client client = clientRestClient.getClientById(clientId);
        account.setClient(client);
        return accountRepository.save(account);
    }

    @Override
    public Operation addDeposit(Float amount, Long accountId) {
        Account account = accountRepository.findById(accountId).get();

        if (account.getState() != AccountStates.SUSPENDED) {
            Operation operation = new Operation(null, new Date(), amount, OperationTypes.DEBIT, account);
            operation = operationRepository.save(operation);

            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);
            return operation;
        }
        return null;
    }

    @Override
    public Operation addWithdraw(Float amount, Long accountId) {

        Account account = accountRepository.findById(accountId).get();
        if (account.getState() != AccountStates.SUSPENDED) {
            Operation operation = new Operation(null, new Date(), amount, OperationTypes.CREIDT, account);
            operation = operationRepository.save(operation);

            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);
            return operation;
        }
        return null;
    }

    @Override
    public List<Operation> addTransaction(Float amount, Long fromAccountId, Long toAccountId) {
        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(addWithdraw(amount, fromAccountId));
        operations.add(addDeposit(amount, toAccountId));

        return operations;
    }

    @Override
    public Page<Operation> getAllOperations(Long accountId, Pageable pageable) {
        return operationRepository.findOperationByAccount_Code(accountId, pageable);
    }

    @Override
    public Account getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        account.setOperation(operationRepository.findOperationByAccount_Code(accountId));
        account.setClient(clientRestClient.getClientById(accountId));
        return account;
    }

    @Override
    public Account activateOrSuspend(Long accountId, AccountStates state) {
        Account account = accountRepository.findById(accountId).get();
        account.setState(state);
        return accountRepository.save(account);
    }
}
