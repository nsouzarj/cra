package br.adv.cra.entity;

import java.util.List;

public class ProcessoJSON {
	
	
	
	private String id;
	private String originAreaId;
	private String responsibleAreaId;
	private String legalDepartmentAreaId;
	
	private String  requesterId;
	private String natureId;
	private String  folder;
	private String  courtId;
	private String  courtName;
	private String  actionTypeId;
	private String  actionTypeName;
	private String  identifierNumber;
	private String  oldNumber;
	private String  statusId;
	private String  countryId;
	private String  stateId;
	private String  cityId;
	private String  individual;
	private String  individualName;
	private String  company;
	private String  companyName;
	private List<OutraParteJSON> otherParty;
	private String  name1;
	private String  otherPartyName;
	private List<ClienteJSON> customer;       
	private String  name2;
	private String  customerName;
	
	
	
	public ProcessoJSON() {
		super();
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getOriginAreaId() {
		return originAreaId;
	}



	public void setOriginAreaId(String originAreaId) {
		this.originAreaId = originAreaId;
	}



	public String getResponsibleAreaId() {
		return responsibleAreaId;
	}



	public void setResponsibleAreaId(String responsibleAreaId) {
		this.responsibleAreaId = responsibleAreaId;
	}



	public String getLegalDepartmentAreaId() {
		return legalDepartmentAreaId;
	}



	public void setLegalDepartmentAreaId(String legalDepartmentAreaId) {
		this.legalDepartmentAreaId = legalDepartmentAreaId;
	}



	public String getRequesterId() {
		return requesterId;
	}



	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}



	public String getNatureId() {
		return natureId;
	}



	public void setNatureId(String natureId) {
		this.natureId = natureId;
	}



	public String getFolder() {
		return folder;
	}



	public void setFolder(String folder) {
		this.folder = folder;
	}



	public String getCourtId() {
		return courtId;
	}



	public void setCourtId(String courtId) {
		this.courtId = courtId;
	}



	public String getCourtName() {
		return courtName;
	}



	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}



	public String getActionTypeId() {
		return actionTypeId;
	}



	public void setActionTypeId(String actionTypeId) {
		this.actionTypeId = actionTypeId;
	}



	public String getActionTypeName() {
		return actionTypeName;
	}



	public void setActionTypeName(String actionTypeName) {
		this.actionTypeName = actionTypeName;
	}



	public String getIdentifierNumber() {
		return identifierNumber;
	}



	public void setIdentifierNumber(String identifierNumber) {
		this.identifierNumber = identifierNumber;
	}



	public String getOldNumber() {
		return oldNumber;
	}



	public void setOldNumber(String oldNumber) {
		this.oldNumber = oldNumber;
	}



	public String getStatusId() {
		return statusId;
	}



	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}



	public String getCountryId() {
		return countryId;
	}



	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}



	public String getStateId() {
		return stateId;
	}



	public void setStateId(String stateId) {
		this.stateId = stateId;
	}



	public String getCityId() {
		return cityId;
	}



	public void setCityId(String cityId) {
		this.cityId = cityId;
	}



	public String getIndividual() {
		return individual;
	}



	public void setIndividual(String individual) {
		this.individual = individual;
	}



	public String getIndividualName() {
		return individualName;
	}



	public void setIndividualName(String individualName) {
		this.individualName = individualName;
	}



	public String getCompany() {
		return company;
	}



	public void setCompany(String company) {
		this.company = company;
	}



	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public List<OutraParteJSON> getOtherParty() {
		return otherParty;
	}



	public void setOtherParty(List<OutraParteJSON> otherParty) {
		this.otherParty = otherParty;
	}



	public String getName1() {
		return name1;
	}



	public void setName1(String name1) {
		this.name1 = name1;
	}



	public String getOtherPartyName() {
		return otherPartyName;
	}



	public void setOtherPartyName(String otherPartyName) {
		this.otherPartyName = otherPartyName;
	}



	public List<ClienteJSON> getCustomer() {
		return customer;
	}



	public void setCustomer(List<ClienteJSON> customer) {
		this.customer = customer;
	}



	public String getName2() {
		return name2;
	}



	public void setName2(String name2) {
		this.name2 = name2;
	}



	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	@Override
	public String toString() {
		return "ProcessoJSON [id=" + id + ", originAreaId=" + originAreaId + ", responsibleAreaId=" + responsibleAreaId
				+ ", legalDepartmentAreaId=" + legalDepartmentAreaId + ", requesterId=" + requesterId + ", natureId="
				+ natureId + ", folder=" + folder + ", courtId=" + courtId + ", courtName=" + courtName
				+ ", actionTypeId=" + actionTypeId + ", actionTypeName=" + actionTypeName + ", identifierNumber="
				+ identifierNumber + ", oldNumber=" + oldNumber + ", statusId=" + statusId + ", countryId=" + countryId
				+ ", stateId=" + stateId + ", cityId=" + cityId + ", individual=" + individual + ", individualName="
				+ individualName + ", company=" + company + ", companyName=" + companyName + ", otherParty="
				+ otherParty + ", name1=" + name1 + ", otherPartyName=" + otherPartyName + ", customer=" + customer
				+ ", name2=" + name2 + ", customerName=" + customerName + "]";
	}
	
	
	
	

}
