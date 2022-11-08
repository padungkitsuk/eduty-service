package com.streamit.others.constant;

import com.streamit.others.jdbc.Pagging;

public class PaggingInqueryDTO<T> {
    private Object data;
    private Pagging pagging;

    public PaggingInqueryDTO() {
    }

    public PaggingInqueryDTO(Object data, Pagging pagging) {
        this.data = data;
        this.pagging = pagging;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Pagging getPagging() {
        return pagging;
    }

    public void setPagging(Pagging pagging) {
        this.pagging = pagging;
    }
}
