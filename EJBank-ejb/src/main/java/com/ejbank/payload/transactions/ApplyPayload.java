package com.ejbank.payload.transactions;

import java.util.Objects;

public class ApplyPayload {
    private final String source;
    private final String destination;
    private final Float amount;
    private final String comment;
    private final String author;

    public ApplyPayload(String source, String destination, Float amount, String comment, String author) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(destination);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(comment);
        Objects.requireNonNull(author);
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.comment = comment;
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Float getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public String getAuthor() {
        return author;
    }
}
