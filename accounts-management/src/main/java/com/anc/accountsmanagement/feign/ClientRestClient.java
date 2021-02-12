package com.anc.accountsmanagement.feign;

import com.anc.accountsmanagement.models.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CLIENTS-MANAGEMENTS")
public interface ClientRestClient {
    @GetMapping(path = "/clients/{id}")
    Client getClientById(@PathVariable(name = "id") Long id);
}
