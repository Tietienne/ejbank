package com.ejbank.payload.accounts;

import java.util.ArrayList;
import java.util.Objects;

public class AttachedAccountPayload {
    private final ArrayList<AttachedAccount> accounts;
    private final String error;

    public AttachedAccountPayload(ArrayList<AttachedAccount> accounts, String error) {
        this.accounts = accounts;
        this.error = error;
    }

    public ArrayList<AttachedAccount> getAccounts() {
        return accounts;
    }

    public String getError() {
        return error;
    }
}
