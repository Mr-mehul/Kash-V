package com.kashv.domain;

import java.time.OffsetDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


@Entity
public class Documents {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId;

    @OneToOne
    @JoinColumn(name = "doc_user_id_id", nullable = false)
    private User docUserId;

    @OneToMany(mappedBy = "aadharCard")
    private Set<FileData> aadharCardFileDatas;

    @OneToMany(mappedBy = "drivingLicense")
    private Set<FileData> drivingLicenseFileDatas;

    @OneToMany(mappedBy = "currentAddressProof")
    private Set<FileData> currentAddressProofFileDatas;

    @OneToMany(mappedBy = "permanentAddressProof")
    private Set<FileData> permanentAddressProofFileDatas;

    @OneToMany(mappedBy = "bankStatement")
    private Set<FileData> bankStatementFileDatas;

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

    public Long getDocId() {
        return docId;
    }

    public void setDocId(final Long docId) {
        this.docId = docId;
    }

    public User getDocUserId() {
        return docUserId;
    }

    public void setDocUserId(final User docUserId) {
        this.docUserId = docUserId;
    }

    public Set<FileData> getAadharCardFileDatas() {
        return aadharCardFileDatas;
    }

    public void setAadharCardFileDatas(final Set<FileData> aadharCardFileDatas) {
        this.aadharCardFileDatas = aadharCardFileDatas;
    }

    public Set<FileData> getDrivingLicenseFileDatas() {
        return drivingLicenseFileDatas;
    }

    public void setDrivingLicenseFileDatas(final Set<FileData> drivingLicenseFileDatas) {
        this.drivingLicenseFileDatas = drivingLicenseFileDatas;
    }

    public Set<FileData> getCurrentAddressProofFileDatas() {
        return currentAddressProofFileDatas;
    }

    public void setCurrentAddressProofFileDatas(final Set<FileData> currentAddressProofFileDatas) {
        this.currentAddressProofFileDatas = currentAddressProofFileDatas;
    }

    public Set<FileData> getPermanentAddressProofFileDatas() {
        return permanentAddressProofFileDatas;
    }

    public void setPermanentAddressProofFileDatas(
            final Set<FileData> permanentAddressProofFileDatas) {
        this.permanentAddressProofFileDatas = permanentAddressProofFileDatas;
    }

    public Set<FileData> getBankStatementFileDatas() {
        return bankStatementFileDatas;
    }

    public void setBankStatementFileDatas(final Set<FileData> bankStatementFileDatas) {
        this.bankStatementFileDatas = bankStatementFileDatas;
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