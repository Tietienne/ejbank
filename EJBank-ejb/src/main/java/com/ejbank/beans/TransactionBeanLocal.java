package com.ejbank.beans;

import com.ejbank.payload.transactions.*;

import javax.ejb.Local;

@Local
public interface TransactionBeanLocal {
    AnswerPreviewPayload getAnswerPreview(PreviewPayload preview);
    AnswerApplyPayload apply(ApplyPayload preview);
    AllTransactionsPayload getAllTransactionsOf(Integer accountId, Integer offset, Integer userId);
    Integer getNotificationPayload(Integer user_id);
    AnswerValidationPayload validate(ValidationPayload preview);
}
