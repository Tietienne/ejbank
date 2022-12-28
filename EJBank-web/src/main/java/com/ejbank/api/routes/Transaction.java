package com.ejbank.api.routes;

import com.ejbank.payload.transactions.*;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Transaction {
    @GET
    @Path("/list/{account_id}/{offset}/{user_id}")
    public AllTransactionsPayload AllTransactions() {
        var transactions = new ArrayList<TransactionContent>();
        transactions.add(new TransactionContent(new BigInteger("271077732"), LocalDateTime.now(), "Label du compte source", "Label du compte destination", "Florian", 125.65f, "Etienne ALEXANDRE", "Cadeau pour Noël", "APPLYED"));
        return new AllTransactionsPayload(transactions);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/preview")
    public AnswerPreviewPayload previewRequest(PreviewPayload payload) {
        return new AnswerPreviewPayload(true, 456f, 350f, "Oups...");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/apply")
    public AnswerApplyPayload applyRequest(ApplyPayload payload) {
        return new AnswerApplyPayload(false, "Oups...");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/validation")
    public AnswerValidationPayload validationRequest(ValidationPayload payload) {
        return new AnswerValidationPayload(false, "Retour du serveur");
    }

    @GET
    @Path("/validation/notification/{user_id}")
    public Integer validationNotif(@PathParam("user_id") Integer user_id) {
        return 3;
    }
}
