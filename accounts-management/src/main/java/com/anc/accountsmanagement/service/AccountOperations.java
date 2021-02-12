package com.anc.accountsmanagement.service;

import com.anc.accountsmanagement.entities.Account;
import com.anc.accountsmanagement.entities.AccountStates;
import com.anc.accountsmanagement.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountOperations {
    Account addAccount(Account account,Long clientId);

    boolean addDeposit(Float amount, Long accountId);

    boolean addWithdraw(Float amount, Long accountId);

    boolean addTransaction(Float amount, Long fromAccountId, Long toAccountId);

    Page<Operation> getAllOperations(Long accountId, Pageable pageable);

    Account getAccount(Long accountId);

    Account activateOrSuspend(Long accountId, AccountStates state);

}
