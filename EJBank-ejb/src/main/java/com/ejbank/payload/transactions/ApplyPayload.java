package com.ejbank.payload.transactions;

import java.util.Objects;

public class ApplyPayload {
    private String source;
    private String destination;
    private Float amount;
    private String comment;
    private String author;

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
