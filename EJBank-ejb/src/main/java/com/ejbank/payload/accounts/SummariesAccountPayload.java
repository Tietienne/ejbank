package com.ejbank.payload.accounts;



import java.util.List;


public class SummariesAccountPayload {
    private final List<SummaryAccount> accounts;
    private final String error;

    public SummariesAccountPayload(List<SummaryAccount> accounts, String error) {
        this.accounts = accounts;
        this.error = error;
    }
    /**
     * Method to get the list all accounts
     * @return Lit<AllAccounts>
     * */
    public List<SummaryAccount> getAccounts() {
        return accounts;
    }

    public String getError() {
        return error;
    }
}
