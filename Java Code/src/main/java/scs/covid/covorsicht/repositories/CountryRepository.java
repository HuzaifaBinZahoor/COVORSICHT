package scs.covid.covorsicht.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import scs.covid.covorsicht.models.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
