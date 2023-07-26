package com.nkxgen.spring.jdbc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "permissions")
public class Permission {

	@Id
	@Column(name = "role")
	private String role;

	@Column(name = "Accountedit")
	private boolean Accountedit;

	@Column(name = "Accountdel")
	private boolean Accountdel;

	@Column(name = "Accountactive")
	private boolean Accountactive;

	@Column(name = "transactions")
	private boolean transactions;

	@Column(name = "appforms")
	private boolean appforms;

	@Column(name = "security")
	private boolean security;

	@Column(name = "accprocessing")
	private boolean accprocessing;

	@Column(name = "cusedit")
	private boolean cusedit;

	@Column(name = "users")
	private boolean users;

	public boolean isTransactions() {
		return transactions;
	}

	public void setTransactions(boolean transactions) {
		this.transactions = transactions;
	}

	public boolean isAppforms() {
		return appforms;
	}

	public void setAppforms(boolean appforms) {
		this.appforms = appforms;
	}

	public boolean isSecurity() {
		return security;
	}

	public void setSecurity(boolean security) {
		this.security = security;
	}

	public boolean isAccprocessing() {
		return accprocessing;
	}

	public void setAccprocessing(boolean accprocessing) {
		this.accprocessing = accprocessing;
	}

	public boolean isCusedit() {
		return cusedit;
	}

	public void setCusedit(boolean cusedit) {
		this.cusedit = cusedit;
	}

	public boolean isUsers() {
		return users;
	}

	public void setUsers(boolean users) {
		this.users = users;
	}

	public boolean isAccountactive() {
		return Accountactive;
	}

	public void setAccountactive(boolean accountactive) {
		Accountactive = accountactive;
	}

	//
	public Permission() {
	}

	public boolean isAccountedit() {
		return Accountedit;
	}

	public void setAccountedit(boolean accountedit) {
		Accountedit = accountedit;
	}

	public boolean isAccountdel() {
		return Accountdel;
	}

	public void setAccountdel(boolean accountdel) {
		Accountdel = accountdel;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	// Getters and setters

}