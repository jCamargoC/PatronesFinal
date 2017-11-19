package com.universalbank.loan.objects;

public class Loan {
	
	private String identificationNumber;
	
	private String identificationType;
	
	private LoanType loanType;
	
	private Double amount;
	
	private Double payed;
	
	private Double quoteAmount;

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}	

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public Double getPayed() {
		return payed;
	}

	public void setPayed(Double payed) {
		this.payed = payed;
	}

	public Double getQuoteAmount() {
		return quoteAmount;
	}

	public void setQuoteAmount(Double quoteAmount) {
		this.quoteAmount = quoteAmount;
	}
	
	
}
