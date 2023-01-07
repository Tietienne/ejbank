package com.ejbank.beans;

import com.ejbank.entity.User;
import com.ejbank.payload.others.UserPayload;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UserBean implements UserBeanLocal {
    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    @Override
    public UserPayload getUser(Integer user_id) {
        var result = em.find(User.class, user_id);
        return new UserPayload(result.getFirstname(), result.getLastname());
    }
}
