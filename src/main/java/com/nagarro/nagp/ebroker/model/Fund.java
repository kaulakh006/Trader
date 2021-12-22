package com.nagarro.nagp.ebroker.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="fund")
public class Fund implements Serializable{

	public Fund(double amount, User owner) {
		super();
		this.amount = amount;
		this.owner = owner;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name= "amount")
	private double amount;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User owner;
	
	public Fund() {
		super();
	}

	public Fund(double fund) {
		this.amount = fund;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	
}
