package com.ejbank.payload.transactions;

import java.util.List;

public class AllTransactionsPayload {
    private final Integer total;
    private final List<TransactionContent> transactions;
    private final String error;

    public AllTransactionsPayload(List<TransactionContent> transactions, String error) {
        this.transactions = transactions;
        this.total = transactions == null ? 0 : transactions.size();
        this.error = error;
    }
    /**
     * Method to get the list all transactions
     * @return Lit<TransactionContent>
     * */
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
