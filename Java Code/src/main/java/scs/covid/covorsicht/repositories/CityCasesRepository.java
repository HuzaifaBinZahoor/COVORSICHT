package scs.covid.covorsicht.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import scs.covid.covorsicht.models.City;
import scs.covid.covorsicht.models.CityCases;

public interface CityCasesRepository extends JpaRepository<CityCases, Long> {

	@Query("select cc from CityCases cc")
	public List<CityCases> getCurrent(long id, Pageable pageable);

	public CityCases findTopByCityOrderByDateDesc(City c);
	
	public List<CityCases> findByCityOrderByDateDesc(City c);

	public CityCases findTopByCityAndAlertOrderByDateDesc(City c, boolean alert);

}
