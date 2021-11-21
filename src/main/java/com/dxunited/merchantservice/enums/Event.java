package com.dxunited.merchantservice.enums;

public enum Event {
    CREATE("CREATE_MERCHANT");


    private String topic;

    Event(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}