package com.kashv.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreditDecisionDTO {

	private Long creditId;

	@Size(max = 255)
	private String jobType;

	private Integer salary;

	@Size(max = 255)
	private String bankName;

	@Size(max = 500)
	private String bankAddress;

	@Size(max = 255)
	private String questionAnswer;

	@NotNull
	private Long typeId;

	@NotNull
	private Long creditUserId;

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

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(final Long typeId) {
		this.typeId = typeId;
	}

	public Long getCreditUserId() {
		return creditUserId;
	}

	public void setCreditUserId(final Long creditUserId) {
		this.creditUserId = creditUserId;
	}

}
