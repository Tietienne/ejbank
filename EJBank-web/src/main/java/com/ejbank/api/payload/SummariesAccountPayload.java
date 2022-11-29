package com.ejbank.api.payload;

import com.ejbank.api.content.SummaryAccount;

import java.util.ArrayList;
import java.util.Objects;

public class SummariesAccountPayload {
    private final ArrayList<SummaryAccount> accounts;
    private final String error;

    public SummariesAccountPayload(ArrayList<SummaryAccount> accounts) {
        Objects.requireNonNull(accounts);
        this.accounts = accounts;
        this.error = null;
    }

    public ArrayList<SummaryAccount> getAccounts() {
        return accounts;
    }

    public String getError() {
        return error;
    }
}
