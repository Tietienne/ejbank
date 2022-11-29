package com.ejbank.api.routes;

import com.ejbank.api.content.TransactionContent;
import com.ejbank.api.payload.AllTransactionsPayload;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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

    @GET
    @Path("/validation/notification/{user_id}")
    public Integer validationNotif(@PathParam("user_id") Integer user_id) {
        return 3;
    }
}
