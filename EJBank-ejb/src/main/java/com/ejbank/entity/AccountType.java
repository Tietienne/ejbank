package com.ejbank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ejbank_account_type")
public class AccountType {
    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private Float rate;
    @Column
    private Integer overdraft;

    public AccountType() {
    }

    public AccountType(Integer id, String name, Float rate, Integer overdraft) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.overdraft = overdraft;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getRate() {
        return rate;
    }

    public Integer getOverdraft() {
        return overdraft;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public void setOverdraft(Integer overdraft) {
        this.overdraft = overdraft;
    }

    @Override
    public String toString() {
        return name;
    }
}
