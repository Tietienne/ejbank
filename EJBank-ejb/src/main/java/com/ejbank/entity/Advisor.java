package com.ejbank.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ejbank_advisor")
@DiscriminatorValue("advisor")
public class Advisor extends User {

    @OneToMany(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "advisor_id")
    private List<Customer> customers;

    public List<Customer> getCustomers() {
        return customers;
    }

}
