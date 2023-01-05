package com.ejbank.payload.transactions;

import java.util.Objects;

public class ApplyPayload {
    private Integer source;
    private Integer destination;
    private Float amount;
    private String comment;
    private String author;

    public Integer getSource() {
        return source;
    }

    public Integer getDestination() {
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

    public void setSource(Integer source) {
        this.source = source;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
