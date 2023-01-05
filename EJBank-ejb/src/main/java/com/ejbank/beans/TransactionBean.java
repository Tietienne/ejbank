package com.ejbank.beans;

import com.ejbank.entity.*;
import com.ejbank.payload.transactions.*;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
            return new AnswerPreviewPayload(false, source.getBalance(), source.getBalance() - preview.getAmount(), "The transaction is not valid.", null);
        }
        String message;
        if (preview.getAmount() > 1000 && user instanceof Customer) {
            message = "The transaction is valid but it needs to be validated by your Advisor!";
        } else {
            message = "The transaction is valid.";
        }
        return new AnswerPreviewPayload(true, source.getBalance() - preview.getAmount(), dest.getBalance() + preview.getAmount(), message, null);
    }

    @Transactional
    public AnswerApplyPayload apply(ApplyPayload preview) {
        var source = em.find(Account.class, preview.getSource());
        var dest = em.find(Account.class, preview.getDestination());
        var user = em.find(User.class, Integer.parseInt(preview.getAuthor()));
        if (preview.getAmount() <= 0 || source.getBalance() - preview.getAmount() < - source.getAccountType().getOverdraft() ) {
            new AnswerApplyPayload(false,"Transaction failed");
        }
        boolean applied;
        String message;
        if (preview.getAmount() > 1000 && user instanceof Customer) {
            message = "Transaction added but it needs to be validated by your Advisor!";
            applied = false;
        } else {
            message = "Transaction added.";
            applied = true;
        }
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        em.persist(new Transaction(preview.getDestination(),
                preview.getSource(),
                preview.getAuthor(),preview.getAmount(),preview.getComment(),applied,now));
        if (applied) {
            source.setBalance(source.getBalance()-preview.getAmount());
            dest.setBalance(dest.getBalance()+preview.getAmount());
        }
        em.flush();
        return new AnswerApplyPayload(true,message);
    }

    @Override
    public AllTransactionsPayload getAllTransactionsOf(Integer accountId, Integer offset, Integer userId) {


        return null;

    }
}
