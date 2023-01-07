package com.ejbank.beans;

import com.ejbank.entity.Account;
import com.ejbank.entity.Transaction;
import com.ejbank.entity.User;

import com.ejbank.entity.*;

import com.ejbank.payload.transactions.*;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;


import javax.transaction.Transactional;

import java.util.Calendar;
import java.util.Date;

@Stateless
@LocalBean
public class TransactionBean implements TransactionBeanLocal {
    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    /**
     * Verification of a transaction and returning the appropriate answer
     * @param preview PreviewPayload (source, destination, amount, author) : information of a transaction
     * @return AnswerPreviewPayload (result, before, after, message)
     */
    @Override
    public AnswerPreviewPayload getAnswerPreview(PreviewPayload preview) {
        var source = em.find(Account.class, preview.getSource());
        var dest = em.find(Account.class, preview.getDestination());
        var user = em.find(User.class, Integer.parseInt(preview.getAuthor()));
        if (preview.getAmount() <= 0 || source.getBalance() - preview.getAmount() < - source.getAccountType().getOverdraft() ) {
            return new AnswerPreviewPayload(false, source.getBalance(), source.getBalance() - preview.getAmount(), "La transaction n'est pas valide.", null);
        }
        String message;
        if (preview.getAmount() > 1000 && user instanceof Customer) {
            message = "La transaction est valide mais nécessitera la validation de votre conseiller!";
        } else {
            message = "La transaction est valide.";
        }
        return new AnswerPreviewPayload(true, source.getBalance() - preview.getAmount(), dest.getBalance() + preview.getAmount(), message, null);
    }

    /**
     * Apply a transaction, verify it's correct, and return answer if it was applied (or "to approve")
     * @param preview ApplyPayload (source, destination, amount, comment, author) : information of a transaction
     * @return AnswerApplyPayload (result, message)
     */
    @Transactional()
    public AnswerApplyPayload apply(ApplyPayload preview) {
        var source = em.find(Account.class, preview.getSource());
        var dest = em.find(Account.class, preview.getDestination());
        var user = em.find(User.class, Integer.parseInt(preview.getAuthor()));
        if (preview.getAmount() <= 0 || source.getBalance() - preview.getAmount() < - source.getAccountType().getOverdraft() ) {
            new AnswerApplyPayload(false,"Transaction échouée");
        }
        boolean applied;
        String message;
        if (preview.getAmount() > 1000 && user instanceof Customer) {
            message = "Transaction ajoutée mais nécessite la validation de votre conseiller!";
            applied = false;
        } else {
            message = "Transaction ajoutée.";
            applied = true;
        }
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        var transaction = new Transaction(preview.getDestination(),
                preview.getSource(),
                preview.getAuthor(),preview.getAmount(),preview.getComment(),applied,now);
        em.persist(transaction);
        em.merge(transaction);

        if (applied) {
            source.setBalance(source.getBalance()-preview.getAmount());
            dest.setBalance(dest.getBalance()+preview.getAmount());
        }

        return new AnswerApplyPayload(true,message);
    }

    /**
     * Get all transactions from a specific account as a user (not showing any result if not allowed). Skipping all results from 0 to the offset given.
     * @param accountId Account id as Integer : the account searched
     * @param offset Offset as Integer : skipping every result from 0 to this offset
     * @param userId User id as Integer : the user asking
     * @return AllTransactionsPayload : List of TransactionContent (id, date, source, destination, destination_user, amount, author, comment, state)
     */
    @Override
    public AllTransactionsPayload getAllTransactionsOf(Integer accountId, Integer offset, Integer userId) {

        var user  = em.find(User.class, userId);
        var account = em.find(Account.class, accountId);

        if(user == null) {
            return new AllTransactionsPayload(null,"Utilisateur introuvable");
        } else {
            if (user instanceof Customer customer) {
                if (!account.getCustomer_id().equals(customer.getId())) {
                    return new AllTransactionsPayload(null,"Vous n'êtes pas autorisé à visualiser les informations de ce compte!");
                }
            }
            if (user instanceof Advisor advisor) {
                var accountUser = em.find(User.class, account.getCustomer_id());
                if (accountUser == null) {
                    return new AllTransactionsPayload(null,"Utilisateur introuvable");
                }
                if (accountUser instanceof Customer customer) {
                    if (!advisor.getId().equals(customer.getAdvisor().getId())) {
                        return new AllTransactionsPayload(null,"Vous n'êtes pas autorisé à visualiser les informations de ce compte!");
                    }
                }
            }
        }

        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Transaction.class);
        var root = cq.from(Transaction.class);

        cq.select(root);
        cq.where(cb.equal(root.get("account_id_from"), accountId));
        var orderByApplied = cb.asc(root.get("applied"));
        var orderByDate = cb.desc(root.get("date"));

        cq.orderBy(orderByApplied,orderByDate);
        var query = em.createQuery(cq);
        query.setFirstResult(offset);

        var transactionContents = query.getResultList().stream().map(t -> {
            var instant = t.getDate().toInstant();
            var ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            var srcAccount = em.find(Account.class, t.getAccount_id_from());
            var srcCustomer = em.find(User.class,srcAccount.getCustomer_id());

            var destAccount = em.find(Account.class, t.getAccount_id_to());
            var destCustomer = em.find(User.class,destAccount.getCustomer_id());

            var auther = em.find(User.class,Integer.parseInt(t.getAuthor()));

            String applied = "WAITING_APPROVE" ;

            if(user instanceof Advisor) {
                if(t.getApplied()) {
                    applied = "APPLYED";
                } else {
                    applied = "TO_APPROVE";
                }
            }
            return new TransactionContent(
                 BigInteger.valueOf(t.getId()),
                ldt,
               srcAccount.getAccountType().getName().concat("(").concat(srcCustomer.getFirstname().concat(" ").concat(srcCustomer.getLastname()).concat(")")),
               destAccount.getAccountType().getName(), destCustomer.getFirstname().concat(" ").concat(destCustomer.getLastname()),
                t.getAmount(), auther.getFirstname().concat(" ").concat(auther.getLastname()),
                t.getComment(),
                 applied);
        }).toList();

        return new AllTransactionsPayload(transactionContents, null);
    }

    /**
     * Return the number of notification of transaction to validate for a user (0 if not an advisor).
     * @param user_id User id as Integer : user asking for his number of notification
     * @return Integer : number of notification
     */
    @Override
    public Integer getNotificationPayload(Integer user_id) {
        em.getEntityManagerFactory().getCache().evictAll();
        var user = em.find(User.class, user_id);
        if(user instanceof Advisor advisor) {
            var notification = 0;
            for (var customer : advisor.getCustomers()) {
                for (var account : customer.getAccounts()) {
                    for (var transaction : account.getTransactions()) {
                        if (!transaction.getApplied()) {
                            notification++;
                        }
                    }
                }
            }
            return notification;
        }
        return 0;
    }

    /**
     * Varify if transaction is valid, then apply the transaction.
     * If approve is false : removing transaction from db.
     * If author is not allowed to validate this transaction, method do nothing.
     * @param preview ValidationPayload (transaction, approve, author)
     * @return AnswerValidationPayload (result, message)
     */
    @Override
    public AnswerValidationPayload validate(ValidationPayload preview) {
        var transaction = em.find(Transaction.class, preview.getTransaction().intValue());
        var user = em.find(User.class, Integer.parseInt(preview.getAuthor()));
        var source = em.find(Account.class, transaction.getAccount_id_from());
        var dest = em.find(Account.class, transaction.getAccount_id_to());
        if (user instanceof Advisor advisor) {
            if (transaction.getAmount() <= 0 || source.getBalance() - transaction.getAmount() < - source.getAccountType().getOverdraft() ) {
                new AnswerValidationPayload(false,"Transaction échouée", null);
            }
            if (preview.getApprove()) {
                transaction.setApplied(preview.getApprove());
                source.setBalance(source.getBalance()-transaction.getAmount());
                dest.setBalance(dest.getBalance()+transaction.getAmount());
                em.flush();
                return new AnswerValidationPayload(true, "Transaction validée", null);
            } else {
                em.remove(transaction);
                return new AnswerValidationPayload(true, "Transaction annulée", null);
            }
        }
        return new AnswerValidationPayload(false, null, "Vous n'êtes pas autorisé à valider cette transaction");
    }
}
