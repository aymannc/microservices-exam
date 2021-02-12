package com.anc.accountsmanagement.service;

import com.anc.accountsmanagement.entities.Account;
import com.anc.accountsmanagement.entities.AccountStates;
import com.anc.accountsmanagement.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountOperations {
    Account addAccount(Account account,Long clientId);

    Operation addDeposit(Float amount, Long accountId);

    Operation addWithdraw(Float amount, Long accountId);

    List<Operation> addTransaction(Float amount, Long fromAccountId, Long toAccountId);

    Page<Operation> getAllOperations(Long accountId, Pageable pageable);

    Account getAccount(Long accountId);

    Account activateOrSuspend(Long accountId, AccountStates state);

}
