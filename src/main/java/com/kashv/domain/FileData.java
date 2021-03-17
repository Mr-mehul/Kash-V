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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


@Entity
public class FileData {

	  @Id
	    @Column(nullable = false, updatable = false)
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column
	    private String orignalFileName;

	    @Column(name = "\"key\"")
	    private String key;

	    @Column(length = 1000)
	    private String publicUrl;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "profile_id_id")
	    private Profile profileId;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "aadhar_card_id")
	    private Documents aadharCard;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "pan_card_id")
	    private CreditDecision panCard;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "driving_license_id")
	    private Documents drivingLicense;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "current_address_proof_id")
	    private Documents currentAddressProof;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "permanent_address_proof_id")
	    private Documents permanentAddressProof;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "bank_statement_id")
	    private Documents bankStatement;
	    
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

	    public Long getId() {
	        return id;
	    }

	    public void setId(final Long id) {
	        this.id = id;
	    }

	    public String getOrignalFileName() {
	        return orignalFileName;
	    }

	    public void setOrignalFileName(final String orignalFileName) {
	        this.orignalFileName = orignalFileName;
	    }

	    public String getKey() {
	        return key;
	    }

	    public void setKey(final String key) {
	        this.key = key;
	    }

	    public String getPublicUrl() {
	        return publicUrl;
	    }

	    public void setPublicUrl(final String publicUrl) {
	        this.publicUrl = publicUrl;
	    }

	    public Profile getProfileId() {
	        return profileId;
	    }

	    public void setProfileId(final Profile profileId) {
	        this.profileId = profileId;
	    }
	    public Documents getAadharCard() {
	        return aadharCard;
	    }

	    public void setAadharCard(final Documents aadharCard) {
	        this.aadharCard = aadharCard;
	    }

	    public CreditDecision getPanCard() {
	        return panCard;
	    }

	    public void setPanCard(final CreditDecision panCard) {
	        this.panCard = panCard;
	    }

	    public Documents getDrivingLicense() {
	        return drivingLicense;
	    }

	    public void setDrivingLicense(final Documents drivingLicense) {
	        this.drivingLicense = drivingLicense;
	    }

	    public Documents getCurrentAddressProof() {
	        return currentAddressProof;
	    }

	    public void setCurrentAddressProof(final Documents currentAddressProof) {
	        this.currentAddressProof = currentAddressProof;
	    }

	    public Documents getPermanentAddressProof() {
	        return permanentAddressProof;
	    }

	    public void setPermanentAddressProof(final Documents permanentAddressProof) {
	        this.permanentAddressProof = permanentAddressProof;
	    }

	    public Documents getBankStatement() {
	        return bankStatement;
	    }

	    public void setBankStatement(final Documents bankStatement) {
	        this.bankStatement = bankStatement;
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