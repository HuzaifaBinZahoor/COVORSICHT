package scs.covid.covorsicht.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class CityCases {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	private int newCases;
	private boolean alert;
	private String date;
	private int totalCases;

	@OneToOne

	private City city;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}


	public int getNewCases() {
		return newCases;
	}

	public void setNewCases(int newCases) {
		this.newCases = newCases;
	}

	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}


	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}
}
