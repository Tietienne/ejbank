package com.ejbank.api.routes;

import com.ejbank.beans.AccountsBeanLocal;
import com.ejbank.payload.accounts.*;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Accounts {
    @EJB
    private AccountsBeanLocal accountsBeanLocal;
    @GET
    @Path("/{user_id}")
    public SummariesAccountPayload summariesAccountPayloadReponse(@PathParam("user_id") Integer user_id) {
        return accountsBeanLocal.getCustomerAccounts(user_id);
    }

    @GET
    @Path("/attached/{user_id}")
    public AttachedAccountPayload attachedAccountPayloadReponse(@PathParam("user_id") Integer user_id) {
        var accounts = new ArrayList<AttachedAccount>();
        accounts.add(new AttachedAccount("287621192", "Eienne ALEXANDRE", "Label du compte (courant)", 3200f, 2));
        return new AttachedAccountPayload(accounts);
    }

    @GET
    @Path("/all/{user_id}")
    public AllAccountPayload allAccountPayloadReponse(@PathParam("user_id") Integer user_id) {
        return accountsBeanLocal.getAllAccounts(user_id);
    }
}
