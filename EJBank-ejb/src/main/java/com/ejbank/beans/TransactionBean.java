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
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
                    preview.getAuthor(),preview.getAmount(),preview.getComment(),0,now));

        source.setBalance(source.getBalance()-preview.getAmount());
        dest.setBalance(source.getBalance()+preview.getAmount());
        em.flush();

        return new AnswerApplyPayload(true,"Transaction added");
    }

    @Override
    public AllTransactionsPayload getAllTransactionsOf(Integer accountId, Integer offset, Integer userId) {
        var destUser = em.find(User.class, userId);
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Transaction.class);
        var root = cq.from(Transaction.class);
        cq.select(root);
        cq.where(cb.equal(root.get("account_id_from"), accountId));
        var query = em.createQuery(cq);
        query.setFirstResult(offset);
        query.setMaxResults(10);


        var transactionContents = query.getResultList().stream().map(t -> {
            var instant = t.getDate().toInstant();
            var ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            var srcAccount = em.find(Account.class, t.getAccount_id_from());
            var srcCustomer = em.find(User.class,srcAccount.getCustomer_id());

            var destAccount = em.find(Account.class, t.getAccount_id_to());
            var destCustomer = em.find(User.class,destAccount.getCustomer_id());

            var auther = em.find(User.class,Integer.parseInt(t.getAuthor()));

            return new TransactionContent(
                 BigInteger.valueOf(t.getId()),
                ldt,
               srcAccount.getAccountType().getName().concat("(").concat(srcCustomer.getFirstname().concat(" ").concat(srcCustomer.getLastname()).concat(")")),
               destAccount.getAccountType().getName(), destCustomer.getFirstname().concat(" ").concat(destCustomer.getLastname()),
                t.getAmount(), auther.getFirstname().concat(" ").concat(auther.getLastname()),
                t.getComment() == null? "NO COMMENT" : t.getComment(),
                t.getApplied().toString());
        }).toList();

       // var transactions = new ArrayList<TransactionContent>();
       // transactions.add(new TransactionContent(new BigInteger(String.valueOf(transactionContents.size())), LocalDateTime.now(), "Label du compte source", "Label du compte destination", "Florian", 125.65f, "Etienne ALEXANDRE", "Cadeau pour Noël", "APPLYED"));
        return new AllTransactionsPayload(transactionContents);
    }
}
