package com.ejbank.payload.accounts;

import java.util.Objects;

public class AttachedAccount {

    private final String id;
    private final String user;
    private final String type;
    private final Float amount;
    private final Integer validation;

    public AttachedAccount(String id, String user, String type, Float amount, Integer validation) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(user);
        Objects.requireNonNull(type);
        Objects.requireNonNull(validation);
        this.id = id;
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.validation = validation;
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

    public Integer getValidation() {
        return validation;
    }
}
