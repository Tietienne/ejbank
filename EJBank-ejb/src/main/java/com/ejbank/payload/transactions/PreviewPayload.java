package com.ejbank.payload.transactions;

public class PreviewPayload {
    private String source;
    private String destination;
    private String amount;
    private String author;

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

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
