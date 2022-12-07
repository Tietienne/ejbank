package com.ejbank.api.routes;

import com.ejbank.api.payload.accounts.AllAccount;
import com.ejbank.api.payload.accounts.AttachedAccount;
import com.ejbank.api.payload.accounts.SummaryAccount;
import com.ejbank.api.payload.accounts.AllAccountPayload;
import com.ejbank.api.payload.accounts.AttachedAccountPayload;
import com.ejbank.api.payload.accounts.SummariesAccountPayload;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Accounts {
    @GET
    @Path("/{user_id}")
    public SummariesAccountPayload summariesAccountPayloadReponse(@PathParam("user_id") Integer user_id) {
        var accounts = new ArrayList<SummaryAccount>();
        accounts.add(new SummaryAccount("287621192","Label du compte (courant)", 3200f));
        return new SummariesAccountPayload(accounts, null);
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
        var accounts = new ArrayList<AllAccount>();
        accounts.add(new AllAccount("287621192", "Eienne ALEXANDRE", "Label du compte (courant)", 3200f));
        accounts.add(new AllAccount("287621193", "Eienne ALEXANDRE", "Label du compte (Livret A)", 4000f));
        return new AllAccountPayload(accounts);
    }
}
