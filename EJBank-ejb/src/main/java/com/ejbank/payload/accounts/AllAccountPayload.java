package com.ejbank.payload.accounts;

import java.util.ArrayList;
import java.util.Objects;

public class AllAccountPayload {
    private final ArrayList<AllAccount> accounts;
    private final String error;

    public AllAccountPayload(ArrayList<AllAccount> accounts) {
        Objects.requireNonNull(accounts);
        this.accounts = accounts;
        this.error = null;
    }

    public ArrayList<AllAccount> getAccounts() {
        return accounts;
    }
}
