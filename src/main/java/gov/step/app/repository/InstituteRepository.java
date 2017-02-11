package gov.step.app.repository;

import gov.step.app.domain.Institute;
import gov.step.app.domain.enumeration.InstituteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Institute entity.
 */
public interface InstituteRepository extends JpaRepository<Institute, Long> {

    @Query("select distinct institute from Institute institute left join fetch institute.courses")
    List<Institute> findAllWithEagerRelationships();

    @Query("select institute from Institute institute left join fetch institute.courses where institute.id =:id")
    Institute findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select institute from Institute institute where institute.user.login = ?#{principal.username}")
    Institute findOneByUserIsCurrentUser();

    @Query("select institute.id from Institute institute where institute.user.login = ?#{principal.username}")
    Long findOneByUserIsCurrentUserID();

    @Query("select institute from Institute institute where institute.upazila.id = :upazilaId")
    List<Institute> findInstitutesByUpazilla(@Param("upazilaId") Long upazilaId);

    @Query("select institute from Institute institute where institute.instCategory.id = :catId")
    List<Institute> findInstitutesByCategory(@Param("catId") Long catId);

    @Query("select institute from Institute institute where institute.type = :instType")
    List<Institute> findInstitutesByType(@Param("instType") InstituteType instType);

    @Query("SELECT institute from Institute institute where lower(institute.name) = :name")
    Institute findOneByName(@Param("name") String name);

    @Query("select institute from Institute institute where institute.instLevel.name = :name")
    List<Institute> findInstitutesByInstLevel(@Param("name") String name);

    @Query("select institute from Institute institute where institute.mpoEnlisted = :value order by institute.lastModifiedDate desc")
    List<Institute> findInstitutesByMpoEnlisting(@Param("value") Boolean value);

    @Query("select institute from Institute institute where institute.user.login = ?#{principal.username}")
    Page<Institute> findByUserIsCurrentUser(Pageable pageable);
}
