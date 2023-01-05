package com.ejbank.payload.transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllTransactionsPayload {
    private final Integer total;
    private final List<TransactionContent> transactions;
    private final String error;

    public AllTransactionsPayload(List<TransactionContent> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
        this.total = transactions.size();
        this.error = null;
    }

    public List<TransactionContent> getTransactions() {
        return transactions;
    }

    public Integer getTotal() {
        return total;
    }

    public String getError() {
        return error;
    }
}
