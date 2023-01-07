package com.ejbank.api.routes;

import com.ejbank.beans.AccountsBeanLocal;
import com.ejbank.payload.others.DetailsAccountPayload;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Account {

    @EJB
    private AccountsBeanLocal accountsBeanLocal;

    @GET
    @Path("/{account_id}/{user_id}")
    public DetailsAccountPayload summariesAccountPayloadReponse(@PathParam("account_id") Integer account_id, @PathParam("user_id") Integer user_id) {
        return accountsBeanLocal.getDetailsAccount(account_id, user_id);
    }
}
