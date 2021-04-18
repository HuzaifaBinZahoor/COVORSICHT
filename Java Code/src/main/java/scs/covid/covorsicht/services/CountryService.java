package scs.covid.covorsicht.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scs.covid.covorsicht.models.Country;
import scs.covid.covorsicht.repositories.CountryRepository;

@Service
@Transactional
public class CountryService {

	@Autowired
	CountryRepository countryRepository;

	public Country save(Country country) {

		return countryRepository.save(country);
	}

	public Country update(Country country) {

		return countryRepository.saveAndFlush(country);
	}

	public Country get(Long id) {
		return countryRepository.findById(id).orElse(null);
	}

	public List<Country> getAll() {
		return countryRepository.findAll();
	}

	public void delete(Country c) {
		countryRepository.delete(c);
	}

}
