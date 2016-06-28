package com.webProject;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Details {
	@PrimaryKey
	@Persistent
	private String Mail;
	@Persistent
	private String Name;
	@Persistent
	private String Phone;
	@Persistent
	private String Qualification;
	@Persistent
	private String Type;
	@Persistent
	private String Month;
	@Persistent
	private String EmpId;
	public String getEmpId() {
		return EmpId;
	}
	public void setEmpId(String empId) {
		EmpId = empId;
	}
	public String getMail() {
		return Mail;
	}
	public void setMail(String mail) {
		Mail = mail;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getQualification() {
		return Qualification;
	}
	public void setQualification(String qualification) {
		Qualification = qualification;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getMonth() {
		return Month;
	}
	public void setMonth(String month) {
		Month = month;
	}
	
}
