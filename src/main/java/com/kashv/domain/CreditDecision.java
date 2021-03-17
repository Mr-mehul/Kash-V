package com.kashv.domain;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


@Entity
public class CreditDecision {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditId;

    @Column
    private String jobType;

    @Column
    private Integer salary;

    @Column
    private String bankName;

    @Column(length = 500)
    private String bankAddress;

    @Column
    private String questionAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id_id", nullable = false)
    private LoanType typeId;

    @OneToOne
    @JoinColumn(name = "credit_user_id_id", nullable = false)
    private User creditUserId;
    
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        dateCreated = OffsetDateTime.now();
        lastUpdated = dateCreated;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = OffsetDateTime.now();
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(final Long creditId) {
        this.creditId = creditId;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(final String jobType) {
        this.jobType = jobType;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(final Integer salary) {
        this.salary = salary;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(final String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(final String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public LoanType getTypeId() {
        return typeId;
    }

    public void setTypeId(final LoanType typeId) {
        this.typeId = typeId;
    }
    public User getCreditUserId() {
        return creditUserId;
    }

    public void setCreditUserId(final User creditUserId) {
        this.creditUserId = creditUserId;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(final OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
 
