package com.ejbank.beans;

import com.ejbank.payload.accounts.SummariesAccountPayload;

import javax.ejb.Local;

@Local
public interface AccountsBeanLocal {
    SummariesAccountPayload getCustomerAccounts(Integer user_id);
}
