package com.anc.accountsmanagement.repositories;

import com.anc.accountsmanagement.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OperationRepository extends JpaRepository<Operation, Long> {
    Page<Operation> findOperationByAccount_Code(Long code, Pageable pageable);

    List<Operation> findOperationByAccount_Code(Long code);
}

