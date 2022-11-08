package com.streamit.others.jdbc;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DataBo implements Serializable{    
    private ResultBO resultBO = new ResultBO();
    
    public ResultBO getResultBO() {
        return resultBO;
    }
    public void setResultBO(ResultBO resultBO) {
        this.resultBO = resultBO;
    }
}
