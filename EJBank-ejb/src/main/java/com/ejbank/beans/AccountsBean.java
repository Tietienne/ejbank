package com.ejbank.beans;

import com.ejbank.entity.Account;
import com.ejbank.entity.Advisor;
import com.ejbank.entity.Customer;
import com.ejbank.entity.User;
import com.ejbank.payload.accounts.SummariesAccountPayload;
import com.ejbank.payload.accounts.SummaryAccount;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Local
public class AccountsBean implements AccountsBeanLocal {
    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    @Override
    public SummariesAccountPayload getCustomerAccounts(Integer user_id) {
        var user = em.find(User.class, user_id);
        if(user == null) {
            return new SummariesAccountPayload(null,"User not here");
        } else if(user instanceof Advisor) {
            return new SummariesAccountPayload(null,"Vous n'êtes pas un client, vous ne pouvez pas consulter vos comptes.");
        } else {
            var customer = (Customer)user;
            var shortAccount = customer.getAccounts().stream().map(e -> new SummaryAccount(e.getId().toString(),e.getAccountType().toString(),e.getBalance()));
            return new SummariesAccountPayload(shortAccount.toList(),null);
        }

    }
}
