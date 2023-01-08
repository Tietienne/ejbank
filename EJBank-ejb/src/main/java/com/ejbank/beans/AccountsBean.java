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

    /**
     * Method to get the summaries accounts for a specific user.
     * @param user_id User id as Integer : the user asking
     * @return SummariesAccountPayload : List of SummaryAccount (id, type, amount)
     */
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

    /**
     * Get AllAccount linked to a user : if it's an advisor, all accounts of every managed customer
     * @param user_id User id as Integer : the user asking
     * @return AllAccountPayload : List of AllAccount (id, user, type, amount)
     */
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

    /**
     * Get all AttachedAccount for an advisor
     * @param advisor_id Advisor id as Integer : the advisor asking
     * @return AttachedAccountPayload : List of AttachedAccount (id, user, type, amount, number of notification of transactions to validate)
     */
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

    /**
     * Get the details of an account, as a user. If user is not allowed : not owning the account or not the advisor of this account, he won't get any details
     * @param account_id Account id as Integer : account searched
     * @param user_id User id as Integer : user asking for details
     * @return DetailsAccountPayload (owner, advisor, rate, interest, amount)
     */
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
                    return new DetailsAccountPayload(customer.getFirstname() + " " + customer.getLastname(), advisor.getFirstname() + " " + advisor.getLastname(),
                            account.getAccountType().getRate(), account.getBalance()*account.getAccountType().getRate()/100, account.getBalance(), null);

                }
            }
        }
        return new DetailsAccountPayload(null, null, null, null, null, "Impossible de vérifier les informations de ce compte, informer votre administrateur!");
    }
}
