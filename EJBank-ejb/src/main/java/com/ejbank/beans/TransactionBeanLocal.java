package com.ejbank.beans;

import com.ejbank.payload.others.UserPayload;
import com.ejbank.payload.transactions.*;

import javax.ejb.Local;
import javax.transaction.SystemException;

@Local
public interface TransactionBeanLocal {
    AnswerPreviewPayload getAnswerPreview(PreviewPayload preview);
    AnswerApplyPayload apply(ApplyPayload preview) throws SystemException;

    AllTransactionsPayload getAllTransactionsOf(Integer accountId, Integer offset, Integer userId);
}
