package scs.covid.covorsicht.models;

import javax.persistence.*;
import java.util.Date;

public class CityCasesModel {

	private String newCases;
	private String city;
	private String date;

	public String getNewCases() {
		return newCases;
	}

	public void setNewCases(String newCases) {
		this.newCases = newCases;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
