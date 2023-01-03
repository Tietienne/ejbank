package com.ejbank.beans;

import com.ejbank.entity.Account;
import com.ejbank.payload.transactions.AnswerPreviewPayload;
import com.ejbank.payload.transactions.PreviewPayload;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class TransactionBean implements TransactionBeanLocal {
    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    @Override
    public AnswerPreviewPayload getAnswerPreview(PreviewPayload preview) {
        var source = em.find(Account.class, Integer.parseInt(preview.getSource()));

        if (source.getBalance() - Float.parseFloat(preview.getAmount()) < - source.getAccountType().getOverdraft() ) {
            return new AnswerPreviewPayload(false, source.getBalance(), source.getBalance() - Float.parseFloat(preview.getAmount()), "Solde du compte source insuffisant", null);
        }
        return new AnswerPreviewPayload(true, source.getBalance(), source.getBalance() - Float.parseFloat(preview.getAmount()), "Solde du compte suffisant", null);
    }
}
