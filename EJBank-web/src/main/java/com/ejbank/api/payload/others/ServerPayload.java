package com.ejbank.api.payload.others;

public class ServerPayload {

    private final boolean result;

    public ServerPayload(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
