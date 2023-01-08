package com.ejbank.payload.others;

public class DetailsAccountPayload {
    private final String owner;
    private final String advisor;
    private final Float rate;
    private final Float interest;
    private final Float amount;
    private final String error;

    public DetailsAccountPayload(String owner, String advisor, Float rate, Float interest, Float amount, String error) {
        this.owner = owner;
        this.advisor = advisor;
        this.rate = rate;
        this.interest = interest;
        this.amount = amount;
        this.error = error;
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
