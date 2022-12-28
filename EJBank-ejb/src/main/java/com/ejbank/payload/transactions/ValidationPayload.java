package com.ejbank.payload.transactions;

import java.math.BigInteger;
import java.util.Objects;

public class ValidationPayload {
    private BigInteger transaction;
    private Boolean approve;
    private String author;

    public BigInteger getTransaction() {
        return transaction;
    }

    public Boolean getApprove() {
        return approve;
    }

    public String getAuthor() {
        return author;
    }
}
