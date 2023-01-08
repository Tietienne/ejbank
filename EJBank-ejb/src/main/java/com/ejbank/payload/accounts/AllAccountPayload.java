package com.ejbank.payload.accounts;

import java.util.List;

public class AllAccountPayload {
    private final List<AllAccount> accounts;
    private final String error;

    public AllAccountPayload(List<AllAccount> accounts, String error) {
        this.accounts = accounts;
        this.error = error;
    }

    /**
     * Method to get the list all accounts
     * @return Lit<AllAccounts>
     * */
    public List<AllAccount> getAccounts() {
        return accounts;
    }
}
