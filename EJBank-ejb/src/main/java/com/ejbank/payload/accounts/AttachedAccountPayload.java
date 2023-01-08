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
    /**
     * Method to get the list all accounts
     * @return Lit<AllAccounts>
     * */
    public ArrayList<AttachedAccount> getAccounts() {
        return accounts;
    }

    public String getError() {
        return error;
    }
}
