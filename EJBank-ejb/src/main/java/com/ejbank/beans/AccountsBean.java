package com.ejbank.beans;

import com.ejbank.entity.Account;
import com.ejbank.entity.Advisor;
import com.ejbank.entity.Customer;
import com.ejbank.entity.User;
import com.ejbank.payload.accounts.*;
import com.ejbank.payload.others.DetailsAccountPayload;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@Stateless
@Local
public class AccountsBean implements AccountsBeanLocal {
    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    @Override
    public SummariesAccountPayload getCustomerAccounts(Integer user_id) {
        var user = em.find(User.class, user_id);
        if(user == null) {
            return new SummariesAccountPayload(null,"Utilisateur introuvable");
        } else if(user instanceof Advisor) {
            return new SummariesAccountPayload(null,"Vous n'êtes pas un client, vous ne pouvez pas consulter vos comptes.");
        } else {
            var customer = (Customer)user;
            var shortAccount = customer.getAccounts().stream().map(e -> new SummaryAccount(e.getId().toString(),e.getAccountType().toString(),e.getBalance()));
            return new SummariesAccountPayload(shortAccount.toList(),null);
        }
    }

    @Override
    public AllAccountPayload getAllAccounts(Integer user_id) {
        var user = em.find(User.class, user_id);
        if(user == null) {
            return new AllAccountPayload(null,"Utilisateur introuvable");
        } else if(user instanceof Advisor) {
            var advisor = (Advisor) user;
            var allAccounts = new ArrayList<AllAccount>();
            for (var customer : advisor.getCustomers()) {
                allAccounts.addAll(customer.getAccounts().stream().map(e -> new AllAccount(e.getId().toString(),e.getCustomer_id().toString(), e.getAccountType().toString(),e.getBalance())).toList());
            }
            return new AllAccountPayload(allAccounts,null);
        } else {
            var customer = (Customer) user;
            var allAccount = customer.getAccounts().stream().map(e -> new AllAccount(e.getId().toString(),e.getCustomer_id().toString(), e.getAccountType().toString(),e.getBalance()));
            return new AllAccountPayload(allAccount.toList(),null);
        }
    }

    @Override
    public AttachedAccountPayload getAllAttachedAccount(Integer advisor_id) {
        var user = em.find(User.class, advisor_id);
        if(user == null) {
            return new AttachedAccountPayload(null, "Utilisateur introuvable");
        } else if(user instanceof Advisor) {
            var advisor = (Advisor) user;
            var allAccounts = new ArrayList<AttachedAccount>();
            for (var customer : advisor.getCustomers()) {
                for(var acc : customer.getAccounts()) {
                    var notification = 0;
                    for (var transaction : acc.getTransactions()) {
                        if (!transaction.getApplied()) {
                            notification++;
                        }
                    }
                    var custo = em.find(User.class, acc.getCustomer_id());
                    allAccounts.add(new AttachedAccount(acc.getId().toString(),custo.getFirstname().concat(" ".concat(custo.getLastname())), acc.getAccountType().toString(),acc.getBalance(),notification));
                }
            }
            return new AttachedAccountPayload(allAccounts, null);
        } else {
            return new AttachedAccountPayload(null, "Vous n'êtes pas un conseiller, vous n'avez pas de comptes rattachés.");
        }
    }

    @Override
    public DetailsAccountPayload getDetailsAccount(Integer account_id, Integer user_id) {
        var user = em.find(User.class, user_id);
        var account = em.find(Account.class, account_id);
        if (user == null) {
            return new DetailsAccountPayload(null, null, null, null, null, "Utilisateur introuvable");
        } else {
            if (user instanceof Customer customer) {
                if (!account.getCustomer_id().equals(customer.getId())) {
                    return new DetailsAccountPayload(null, null, null, null, null, "Vous n'êtes pas autorisé à visualiser les informations de ce compte!");
                }
                return new DetailsAccountPayload(customer.getFirstname() + " " + customer.getLastname(), customer.getAdvisor().getFirstname() + " " + customer.getAdvisor().getLastname(),
                        account.getAccountType().getRate(), account.getBalance()*account.getAccountType().getRate()/100, account.getBalance(), null);
            }
            if (user instanceof Advisor advisor) {
                var accountUser = em.find(User.class, account.getCustomer_id());
                if (accountUser == null) {
                    return new DetailsAccountPayload(null, null, null, null, null, "Utilisateur introuvable");
                }
                if (accountUser instanceof Customer customer) {
                    if (!advisor.getId().equals(customer.getAdvisor().getId())) {
                        return new DetailsAccountPayload(null, null, null, null, null, "Vous n'êtes pas autorisé à visualiser les informations de ce compte!");
                    }
                    return new DetailsAccountPayload(customer.getFirstname() + " " + customer.getLastname(), accountUser.getFirstname() + " " + accountUser.getLastname(),
                            account.getAccountType().getRate(), account.getBalance()*account.getAccountType().getRate()/100, account.getBalance(), null);

                }
            }
        }
        return new DetailsAccountPayload(null, null, null, null, null, "Impossible de vérifier les informations de ce compte, informer votre administrateur!");
    }
}
