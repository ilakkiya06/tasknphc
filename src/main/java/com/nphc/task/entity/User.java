package com.nphc.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	  @Id
	  @Column(name = "id")
	  private String id;

	  @Column(name = "login")
	  private String login;

	  @Column(name = "name")
	  private String name;

	  @Column(name = "salary")
	  private double salary;
	  
	  @Column(name = "startdate")
	  private String startDate;
	  


	public User() {
		
	}

	public User(String id, String login, String name, double salary, String startDate) {
		this.id = id;
		this.login = login;
		this.name = name;
		this.salary = salary;
		this.startDate = startDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	@Override
	  public String toString() {
	    return "User [id=" + id + ", login=" + login + ", name=" + name + ", salary=" + salary + ", startDate=" + startDate + "]";
	  }
	
}
