package com.ejbank.payload.accounts;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SummariesAccountPayload {
    private final List<SummaryAccount> accounts;
    private final String error;

    public SummariesAccountPayload(List<SummaryAccount> accounts, String error) {
        this.accounts = accounts;
        this.error = error;
    }

    public List<SummaryAccount> getAccounts() {
        return accounts;
    }

    public String getError() {
        return error;
    }
}
