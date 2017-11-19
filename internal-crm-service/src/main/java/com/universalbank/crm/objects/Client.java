package com.universalbank.crm.objects;

public class Client {
	
	private String identificationNumber;
	
	private String identificationType;
	
	private ClientType clientType;
	
	private String names;
	
	private String lastNames;
	
	private String email;
	
	private String cellPhone;
	
	private String phone;	

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getLastNames() {
		return lastNames;
	}

	public void setLastNames(String lastNames) {
		this.lastNames = lastNames;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}
	
}
