package com.ejbank.beans;

import com.ejbank.entity.Account;
import com.ejbank.entity.Customer;
import com.ejbank.entity.Transaction;
import com.ejbank.entity.User;
import com.ejbank.payload.transactions.*;

import javax.transaction.*;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;

@Stateless
@LocalBean
public class TransactionBean implements TransactionBeanLocal {
    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    @Override
    public AnswerPreviewPayload getAnswerPreview(PreviewPayload preview) {
        var source = em.find(Account.class, Integer.parseInt(preview.getSource()));
        if (Integer.parseInt(preview.getAmount()) <= 0 || source.getBalance() - Float.parseFloat(preview.getAmount()) < - source.getAccountType().getOverdraft() ) {
            return new AnswerPreviewPayload(false, source.getBalance(), source.getBalance() - Float.parseFloat(preview.getAmount()), "The transaction is not valid", null);
        }
        return new AnswerPreviewPayload(true, source.getBalance(), source.getBalance() - Float.parseFloat(preview.getAmount()), "The transaction is valid", null);
    }
    @Transactional
    public AnswerApplyPayload apply(ApplyPayload preview) throws SystemException {
        var source = em.find(Account.class, Integer.parseInt(preview.getSource()));
        var dest = em.find(Account.class, Integer.parseInt(preview.getDestination()));

        if (preview.getAmount() <= 0 || source.getBalance() - preview.getAmount() < - source.getAccountType().getOverdraft() ) {
            new AnswerApplyPayload(false,"Transaction failed");
        }


        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        em.persist(new Transaction(Integer.parseInt(preview.getSource()),
                    Integer.parseInt(preview.getDestination()),
                    preview.getAuthor(),preview.getAmount(),preview.getComment(),true,now));

        source.setBalance(source.getBalance()-preview.getAmount());
        dest.setBalance(source.getBalance()+preview.getAmount());
        em.flush();

        return new AnswerApplyPayload(true,"Transaction added");
    }

    @Override
    public AllTransactionsPayload getAllTransactionsOf(Integer accountId, Integer offset, Integer userId) {


        return null;

    }
}
