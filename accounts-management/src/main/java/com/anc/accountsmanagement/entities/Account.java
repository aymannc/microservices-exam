package com.anc.accountsmanagement.entities;

import com.anc.accountsmanagement.models.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    private Float balance;
    @CreationTimestamp
    private Date creationDate;
    @Transient
    @OneToMany(mappedBy = "bill")
    private Collection<Operation> operation;
    @Enumerated(EnumType.STRING)
    private AccountTypes type;
    @Enumerated(EnumType.STRING)
    private AccountStates state;
    @Transient
    private Client client;
}
