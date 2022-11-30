package com.ejbank.payload.accounts;

import java.util.ArrayList;
import java.util.Objects;

public class AttachedAccountPayload {
    private final ArrayList<AttachedAccount> accounts;
    private final String error;

    public AttachedAccountPayload(ArrayList<AttachedAccount> accounts) {
        Objects.requireNonNull(accounts);
        this.accounts = accounts;
        this.error = null;
    }

    public ArrayList<AttachedAccount> getAccounts() {
        return accounts;
    }

    public String getError() {
        return error;
    }
}
