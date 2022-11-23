package com.ejbank.api.content;

import java.util.Objects;

public class SummaryAccount {

    private final String id;
    private final String type;
    private final Float amount;

    public SummaryAccount(String id, String type, Float amount) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Float getAmount() {
        return amount;
    }
}
