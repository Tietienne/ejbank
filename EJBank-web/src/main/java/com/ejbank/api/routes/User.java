package com.ejbank.api.routes;

import com.ejbank.api.payload.others.UserPayload;
import com.ejbank.beans.UserBeanLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class User {

    @EJB
    private UserBeanLocal userBean;

    @GET
    @Path("/{user_id}")
    public UserPayload userPayloadResponse(@PathParam("user_id") Integer user_id) {
        var user = userBean.getUser(user_id);
        return new UserPayload(user.getFirstname(), user.getLastname());
    }
}
