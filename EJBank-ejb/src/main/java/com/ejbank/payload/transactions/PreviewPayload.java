package com.ejbank.payload.transactions;

import java.util.Objects;

public class PreviewPayload {
    private final String source;
    private final String destination;
    private final String amount;
    private final String author;

    public PreviewPayload(String source, String destination, String amount, String author) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(destination);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(author);
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getAmount() {
        return amount;
    }

    public String getAuthor() {
        return author;
    }
}
