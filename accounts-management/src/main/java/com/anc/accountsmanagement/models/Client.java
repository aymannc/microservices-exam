package com.anc.accountsmanagement.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class Client {
    private Long code;
    private String name;
    private String email;
}
