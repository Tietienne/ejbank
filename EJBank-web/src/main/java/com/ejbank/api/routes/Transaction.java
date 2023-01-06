package com.ejbank.api.routes;

import com.ejbank.beans.AccountsBeanLocal;
import com.ejbank.beans.TransactionBeanLocal;
import com.ejbank.payload.transactions.*;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.transaction.SystemException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Transaction {

    @EJB
    private TransactionBeanLocal transactionBeanLocal;
    @GET
    @Path("/list/{account_id}/{offset}/{user_id}")
    public AllTransactionsPayload AllTransactions(@PathParam("account_id")Integer account_id, @PathParam("offset") Integer offset, @PathParam("user_id") Integer user_id) {
        //TODO : send all transactions of an account (asked by an user ?) depending on an offset
        return transactionBeanLocal.getAllTransactionsOf(account_id,offset,user_id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/preview")
    public AnswerPreviewPayload previewRequest(PreviewPayload payload) {
        return transactionBeanLocal.getAnswerPreview(payload);
    }




    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/apply")
    public AnswerApplyPayload applyRequest(ApplyPayload payload) throws SystemException {
        return transactionBeanLocal.apply(payload);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/validation")
    public AnswerValidationPayload validationRequest(ValidationPayload payload) {
        return transactionBeanLocal.validate(payload);
    }

    @GET
    @Path("/validation/notification/{user_id}")
    public Integer validationNotif(@PathParam("user_id") Integer user_id) {
        return transactionBeanLocal.getNotificationPayload(user_id);
    }
}
