package com.ejbank.payload.accounts;

import java.util.ArrayList;
import java.util.Objects;

public class SummariesAccountPayload {
    private final ArrayList<SummaryAccount> accounts;
    private final String error;

    public SummariesAccountPayload(ArrayList<SummaryAccount> accounts, String error) {
        Objects.requireNonNull(accounts);
        this.accounts = accounts;
        this.error = error;
    }

    public ArrayList<SummaryAccount> getAccounts() {
        return accounts;
    }

    public String getError() {
        return error;
    }
}
