package com.ejbank.payload.others;

import java.util.Objects;

public class DetailsAccountPayload {
    private final String owner;
    private final String advisor;
    private final Float rate;
    private final Float interest;
    private final Float amount;
    private final String error;

    public DetailsAccountPayload(String owner, String advisor, Float rate, Float interest, Float amount) {
        Objects.requireNonNull(owner);
        Objects.requireNonNull(advisor);
        Objects.requireNonNull(rate);
        Objects.requireNonNull(interest);
        Objects.requireNonNull(amount);
        this.owner = owner;
        this.advisor = advisor;
        this.rate = rate;
        this.interest = interest;
        this.amount = amount;
        this.error = null;
    }

    public String getOwner() {
        return owner;
    }

    public String getAdvisor() {
        return advisor;
    }

    public Float getRate() {
        return rate;
    }

    public Float getInterest() {
        return interest;
    }

    public Float getAmount() {
        return amount;
    }
}
