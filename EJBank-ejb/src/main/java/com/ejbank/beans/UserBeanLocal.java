package com.ejbank.beans;

import com.ejbank.payload.others.UserPayload;

import javax.ejb.Local;

@Local
public interface UserBeanLocal {
    UserPayload getUser(Integer user_id);
}
