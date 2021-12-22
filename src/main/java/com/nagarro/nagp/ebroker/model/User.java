package com.nagarro.nagp.ebroker.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "user")
public class User {
	public User() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column
	private boolean verified;

	@Column
	private String phoneNumber;

	@OneToMany
	private Set<Fund> funds;

	@OneToMany
	private Set<UserStock> equities;
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<Fund> getFunds() {
		return this.funds;
	}

	public void setFundss(Set<Fund> funds) {
		this.funds = funds;
	}

	@Override
	public String toString() {
		return "{" + " id='" + getId() + "'" + ", email='" + getEmail() + "'" + ", password='" + getPassword() + "'"
				+ ", firstName='" + getFirstName() + "'" + ", lastName='" + getLastName() + "'" + ", phoneNumber='"
				+ getPhoneNumber() + "'" + ", transactions='" + getFunds() + "'" + "}";
	}

	/**
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @param verified the verified to set
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public Set<UserStock> getEquities() {
		return equities;
	}

	public void setEquities(Set<UserStock> equities) {
		this.equities = equities;
	}

}