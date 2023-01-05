package com.ejbank.payload.transactions;

public class PreviewPayload {
    private Integer source;
    private Integer destination;
    private Float amount;
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

    public void setAuthor(String author) {
        this.author = author;
    }
}
