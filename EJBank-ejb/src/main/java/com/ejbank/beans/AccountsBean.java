package com.ejbank.beans;

import com.ejbank.entity.Account;
import com.ejbank.entity.Customer;
import com.ejbank.payload.accounts.SummariesAccountPayload;
import com.ejbank.payload.accounts.SummaryAccount;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local
public class AccountsBean implements AccountsBeanLocal {
    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    /*@Override
    public SummariesAccountPayload getCustomerAccounts(Integer user_id) {
        em.createQuery("SELECT account FROM Account WHERE id=user_id");
        return null;
    }*/

    @Override
    public SummariesAccountPayload getCustomerAccounts(Integer user_id) {
        var customer = em.find(Customer.class, user_id);
        var shortAccount = customer.getAccounts().stream().map(e -> new SummaryAccount(e.getId().toString(),e.getAccountType().toString(),e.getBalance()));
        return new SummariesAccountPayload(shortAccount.toList(),null);
    }
}
