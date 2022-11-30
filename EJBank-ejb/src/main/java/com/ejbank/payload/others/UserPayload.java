package com.ejbank.payload.others;

import java.util.Objects;

public class UserPayload {

    private final String firstname;
    private final String lastname;

    public UserPayload(String firstname, String lastname) {
        Objects.requireNonNull(firstname);
        Objects.requireNonNull(lastname);
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
