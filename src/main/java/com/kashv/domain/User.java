package com.kashv.domain;

import com.kashv.model.UserType;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


@Entity
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(unique = true)
    private Long phoneNumber;

    @Column
    private String name;

    @Column
    private Long referenceId;

    @Column
    private Boolean block;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column
    private String password;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @OneToOne(
            mappedBy = "userId",
            fetch = FetchType.LAZY,
            optional = false
    )
    private Profile userId;
    
    @OneToOne(
            mappedBy = "creditUserId",
            fetch = FetchType.LAZY,
            optional = false
    )
    private CreditDecision creditUserId;
    
    @OneToOne(
            mappedBy = "docUserId",
            fetch = FetchType.LAZY,
            optional = false
    )
    private Documents docUserId;
    
    @PrePersist
    public void prePersist() {
        dateCreated = OffsetDateTime.now();
        lastUpdated = dateCreated;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(final Long referenceId) {
        this.referenceId = referenceId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(final UserType userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
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

    public Boolean getBlock() {
        return block;
    }

    public void setBlock(final Boolean block) {
        this.block = block;
    }
    
    public Profile getUserId() {
        return userId;
    }

    public void setUserId(final Profile userId) {
        this.userId = userId;
    }
    public CreditDecision getCreditUserId() {
        return creditUserId;
    }

    public void setCreditUserId(final CreditDecision creditUserId) {
        this.creditUserId = creditUserId;
    }
    public Documents getDocUserId() {
        return docUserId;
    }

    public void setDocUserId(final Documents docUserId) {
        this.docUserId = docUserId;
    }

}
 