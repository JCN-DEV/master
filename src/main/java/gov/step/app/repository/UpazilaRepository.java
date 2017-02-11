package gov.step.app.repository;

import gov.step.app.domain.District;
import gov.step.app.domain.Upazila;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Upazila entity.
 */
public interface UpazilaRepository extends JpaRepository<Upazila, Long> {


    @Query("select upazila from Upazila upazila where upazila.district.id = :id order by upazila.name")
    Page<Upazila> findUpazilasByDistrictIdOrderByName(Pageable pageable, @Param("id") Long id);

}
