package com.ejbank.entity;

import javax.persistence.*;

@Entity
@Table(name = "ejbank_account")
public class Account {
    @Id
    private Integer id;
    @Column
    private Integer customer_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_type_id", referencedColumnName = "id")
    private AccountType accountType;
    @Column
    private Float balance;

    public Account() {
    }

    public Account(Integer id, Integer customer_id, AccountType accountType, Float balance) {
        this.id = id;
        this.customer_id = customer_id;
        this.accountType = accountType;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Float getBalance() {
        return balance;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
