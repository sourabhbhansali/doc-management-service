package com.dxunited.merchantservice.enums;

public enum Event {
    CREATE("CREATE_MERCHANT"),
    UPDATE("UPDATE_MERCHANT"),
    GETALL("GETALL_MERCHANT"),
    GET("GET_MERCHANT"),
    DELETE("DELETE_MERCHANT"),
    PATCH("PATCH_MERCHANT");

    private String topic;

    Event(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}