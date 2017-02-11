package gov.step.app.repository;

import gov.step.app.domain.District;
import gov.step.app.domain.Jobapplication;
import gov.step.app.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the District entity.
 */
public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query("select user from User user where user.district.id = :id")
    User findUserByDistrictId(@Param("id") Long id);

    @Query("select district from District district order by district.name")
    List<District> findDistrictsOrderByName();

    @Query("select district from District district where district.division.id = :id order by district.name")
    Page<District> findDistrictsByDivisionIdOrderByName(Pageable pageable, @Param("id") Long id);

}
