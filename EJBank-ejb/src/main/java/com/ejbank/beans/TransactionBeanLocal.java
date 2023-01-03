package com.ejbank.beans;

import com.ejbank.payload.others.UserPayload;
import com.ejbank.payload.transactions.AnswerPreviewPayload;
import com.ejbank.payload.transactions.PreviewPayload;

import javax.ejb.Local;

@Local
public interface TransactionBeanLocal {
    AnswerPreviewPayload getAnswerPreview(PreviewPayload preview);
}
