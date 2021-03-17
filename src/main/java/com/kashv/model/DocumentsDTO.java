package com.kashv.model;

import javax.validation.constraints.NotNull;


public class DocumentsDTO {

    private Long docId;

    @NotNull
    private Long docUserId;

    public Long getDocId() {
        return docId;
    }

    public void setDocId(final Long docId) {
        this.docId = docId;
    }

    public Long getDocUserId() {
        return docUserId;
    }

    public void setDocUserId(final Long docUserId) {
        this.docUserId = docUserId;
    }

}