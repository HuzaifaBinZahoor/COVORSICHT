package scs.covid.covorsicht.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Subscriber {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	private String email;

	@OneToMany(mappedBy = "subscriber", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<SubCities> userSubCities = new HashSet<SubCities>();

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<SubCities> getUserSubCities() {
		return userSubCities;
	}

	public void setUserSubCities(Set<SubCities> userSubCities) {
		this.userSubCities = userSubCities;
	}


}
