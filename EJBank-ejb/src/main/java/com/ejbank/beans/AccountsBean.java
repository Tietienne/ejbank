package com.ejbank.beans;

import com.ejbank.payload.accounts.SummariesAccountPayload;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local
public class AccountsBean implements AccountsBeanLocal {
    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    @Override
    public SummariesAccountPayload getCustomerAccounts(Integer user_id) {
        em.createQuery("SELECT account FROM Account WHERE id=user_id");
        return null;
    }
}
