package gov.step.app.repository;

import gov.step.app.domain.Country;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Country entity.
 */
public interface CountryRepository extends JpaRepository<Country,Long> {
    @Query("SELECT country from Country country where lower(country.name) = :name")
    Country findOneByName(@Param("name") String name);

    @Query("SELECT country from Country country order by country.name")
    List<Country> findAllOrderByName();

    @Query("SELECT country from Country country where country.callingCode = :callingCode")
    Country findOneByCallingCode(@Param("callingCode") String callingCode);

    @Query("SELECT country from Country country where lower(country.isoCode3) = :isoCode")
    Country findOneByIsoCode(@Param("isoCode") String isoCode);

    @Query("SELECT country from Country country where lower(country.capital) = :capital")
    Country findOneByCapital(@Param("capital") String capital);



}
