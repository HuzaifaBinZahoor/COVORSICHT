package scs.covid.covorsicht.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class SubCities {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@ManyToOne
	@JoinColumn(name = "sub_subid", nullable = false)
	@JsonBackReference
	private Subscriber subscriber;

	@OneToOne
	private City city;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
