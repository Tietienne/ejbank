package com.ejbank.api;

import com.ejbank.api.payload.UserPayload;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Account {
    @GET
    @Path("/{user_id}")
    public UserPayload testPayloadReponse(@PathParam("user_id") Integer user_id) {
        return new UserPayload("Jean", "Dupont");
    }
}
