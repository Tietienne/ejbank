package com.ejbank.payload.accounts;

public class AllAccount {

    private final String id;
    private final String user;
    private final String type;
    private final Float amount;

    public AllAccount(String id, String user, String type, Float amount) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getType() {
        return type;
    }

    public Float getAmount() {
        return amount;
    }
}
