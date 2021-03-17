package com.kashv.model;

import javax.validation.constraints.Size;


public class FileDataDTO {

	private Long id;

    @Size(max = 255)
    private String orignalFileName;

    @Size(max = 255)
    private String key;

    @Size(max = 1000)
    private String publicUrl;

    private Long profileId;

    private Long aadharCard;

    private Long panCard;

    private Long drivingLicense;

    private Long currentAddressProof;

    private Long permanentAddressProof;

    private Long bankStatement;
    
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

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(final Long profileId) {
        this.profileId = profileId;
    }


    public Long getAadharCard() {
        return aadharCard;
    }

    public void setAadharCard(final Long aadharCard) {
        this.aadharCard = aadharCard;
    }

    public Long getPanCard() {
        return panCard;
    }

    public void setPanCard(final Long panCard) {
        this.panCard = panCard;
    }

    public Long getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(final Long drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public Long getCurrentAddressProof() {
        return currentAddressProof;
    }

    public void setCurrentAddressProof(final Long currentAddressProof) {
        this.currentAddressProof = currentAddressProof;
    }

    public Long getPermanentAddressProof() {
        return permanentAddressProof;
    }

    public void setPermanentAddressProof(final Long permanentAddressProof) {
        this.permanentAddressProof = permanentAddressProof;
    }

    public Long getBankStatement() {
        return bankStatement;
    }

    public void setBankStatement(final Long bankStatement) {
        this.bankStatement = bankStatement;
    }
    
}