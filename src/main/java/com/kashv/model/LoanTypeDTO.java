package com.kashv.model;

import javax.validation.constraints.Size;


public class LoanTypeDTO {

    private Long loanTypeId;

    @Size(max = 255)
    private String typeName;

    public Long getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(final Long loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

}
 