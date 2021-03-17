package com.kashv.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.kashv.domain.FileData;

public class ProfileDTO {

	private Long id;

	private Date dob;

	private Gender gender;

	@Size(max = 255)
	private String address;

	private Integer pincode;

	@Size(max = 255)
	private String fullName;

	@Size(max = 255)
	private String email;
	
	@NotNull
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(final Date dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
    
	public void setGender(final Gender gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(final Integer pincode) {
		this.pincode = pincode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	// Mehul Code

	private Set<FileDataDTO> files;

	public Set<FileDataDTO> getFiles() {
		return files;
	}

	public void setFiles(Set<FileDataDTO> files) {
		this.files = files;
	}

}