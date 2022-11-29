package com.ejbank.api.payload.transactions;

import java.math.BigInteger;
import java.util.Objects;

public class ValidationPayload {
    private final BigInteger transaction;
    private final Boolean approve;
    private final String author;

    public ValidationPayload(BigInteger transaction, Boolean approve, String author) {
        Objects.requireNonNull(transaction);
        Objects.requireNonNull(approve);
        Objects.requireNonNull(author);
        this.transaction = transaction;
        this.approve = approve;
        this.author = author;
    }

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
