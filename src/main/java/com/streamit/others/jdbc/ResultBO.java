package com.streamit.others.jdbc;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class ResultBO implements Serializable{
    private Exception exception = null;
    private boolean actionStatus = true;
    private String actionMessage = "success";
    private String errorMessage = "";
    private String responseMessage = "";
    private List<String> errorMessages;
    private List<String> infoMessage;
    
    public boolean isActionStatus() {
        return actionStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Exception getException() {
        return exception;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setActionStatus(boolean actionStatus) {
        this.actionStatus = actionStatus;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public List<String> getInfoMessage() {
		return infoMessage;
	}

	public void setInfoMessage(List<String> infoMessage) {
		this.infoMessage = infoMessage;
	}
    
}
