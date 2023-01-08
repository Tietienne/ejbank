package com.ejbank.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ejbank_customer")
@DiscriminatorValue("customer")
public class Customer extends User {
    @ManyToOne(targetEntity = Advisor.class)
    private Advisor advisor;

    @OneToMany( targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private List<Account> accounts;


    public Customer() {}

    public Customer(String login, String password, String email, String firstname, String lastname, Advisor advisor) {
        super(login, password, email, firstname, lastname, "customer");
        this.advisor = advisor;
    }


    public Advisor getAdvisor() {
        return advisor;
    }


    public List<Account> getAccounts() {
        return accounts;
    }
}

