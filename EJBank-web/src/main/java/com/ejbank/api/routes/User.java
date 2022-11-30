package com.ejbank.api.routes;

import com.ejbank.api.payload.others.UserPayload;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class User {
    @GET
    @Path("/{user_id}")
    public UserPayload userPayloadResponse(@PathParam("user_id") Integer user_id) {
        return new UserPayload("Etienne", "Alexandre");
    }
}