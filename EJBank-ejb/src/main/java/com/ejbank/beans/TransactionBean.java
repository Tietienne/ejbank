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

        return new AnswerApplyPayload(applied,message);
    }

    @Override
    public AllTransactionsPayload getAllTransactionsOf(Integer accountId, Integer offset, Integer userId) {

        var user  = em.find(User.class, userId);

         if(user == null) {
            return new AllTransactionsPayload(null,"something went wrong");
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

    @Override
    public Integer getNotificationPayload(Integer user_id) {
        em.clear();
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

    //TODO : Tests
    @Override
    public AnswerValidationPayload validate(ValidationPayload preview) {
        var transaction = em.find(Transaction.class, preview.getTransaction().intValue());
        var user = em.find(User.class, Integer.parseInt(preview.getAuthor()));
        var source = em.find(Account.class, transaction.getAccount_id_from());
        var dest = em.find(Account.class, transaction.getAccount_id_to());
        if (user instanceof Advisor) {
            if (transaction.getAmount() <= 0 || source.getBalance() - transaction.getAmount() < - source.getAccountType().getOverdraft() ) {
                new AnswerValidationPayload(false,"Transaction échouée", null);
            }
            transaction.setApplied(true);
            source.setBalance(source.getBalance()-transaction.getAmount());
            dest.setBalance(dest.getBalance()+transaction.getAmount());
            em.flush();
            return new AnswerValidationPayload(true, "Transaction validée", null);
        }
        return new AnswerValidationPayload(false, null, "Vous n'êtes pas autorisé à valider cette transaction");
    }
}
