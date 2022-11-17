package com.streamit.others.jdbc;

import lombok.Data;

@Data
public class StateValues {


    private String type;
    private String key;
    private String condition;
    private Object value;

    public StateValues() {
    }

    public StateValues(String type, String key, String condition, Object value) {
        this.type = type;
        this.key = key;
        this.condition = condition;
        this.value = value;
    }
}
