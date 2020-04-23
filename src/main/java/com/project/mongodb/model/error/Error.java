package com.project.mongodb.model.error;

public class Error {
    private String companyId;
    private String errorMessage;

    public Error(String companyId, String message) {
        this.companyId = companyId;
        this.errorMessage = message;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
