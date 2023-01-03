package com.ejbank.beans;

import com.ejbank.payload.accounts.AllAccountPayload;
import com.ejbank.payload.accounts.AttachedAccountPayload;
import com.ejbank.payload.accounts.SummariesAccountPayload;

import javax.ejb.Local;

@Local
public interface AccountsBeanLocal {
    SummariesAccountPayload getCustomerAccounts(Integer user_id);

    AllAccountPayload getAllAccounts(Integer user_id);

    AttachedAccountPayload getAllAttachedAccount(Integer advisor_id);


}
