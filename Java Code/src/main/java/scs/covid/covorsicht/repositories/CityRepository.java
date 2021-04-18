package scs.covid.covorsicht.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import scs.covid.covorsicht.models.City;

public interface CityRepository extends JpaRepository<City, Long> {

//	@Query("select c from City c inner join CityCases cc on c.id=cc.city.id ORDER BY c.cc.date asc")
	// public List<City> getCurrent();
//
//    @Query("SELECT t FROM City t WHERE t.name = ?1 ")
//    public City findByName(String name);

}
