package com.ejbank.api.content;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;

public class TransactionContent {
    private final BigInteger id;
    private final LocalDateTime date;
    private final String source;
    private final String destination;
    private final String destination_user;
    private final Float amount;
    private final String author;
    private final String comment;
    private final String state;

    public TransactionContent(BigInteger id, LocalDateTime date, String source, String destination, String destination_user, Float amount, String author, String comment, String state) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(date);
        Objects.requireNonNull(source);
        Objects.requireNonNull(destination);
        Objects.requireNonNull(destination_user);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(author);
        Objects.requireNonNull(comment);
        Objects.requireNonNull(state);
        this.id = id;
        this.date = date;
        this.source = source;
        this.destination = destination;
        this.destination_user = destination_user;
        this.amount = amount;
        this.author = author;
        this.comment = comment;
        this.state = state;
    }

    public BigInteger getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getDestination_user() {
        return destination_user;
    }

    public Float getAmount() {
        return amount;
    }

    public String getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public String getState() {
        return state;
    }
}
