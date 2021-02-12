package com.anc.accountsmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AccountsManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsManagementApplication.class, args);
    }

}
