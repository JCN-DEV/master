package gov.step.app.repository;

import gov.step.app.domain.MpoVacancyRoleDesgnations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MpoVacancyRoleDesgnations entity.
 */
public interface MpoVacancyRoleDesgnationsRepository extends JpaRepository<MpoVacancyRoleDesgnations,Long> {

    @Query("select mpoVacancyRoleDesgnations from MpoVacancyRoleDesgnations mpoVacancyRoleDesgnations where mpoVacancyRoleDesgnations.createBy.login = ?#{principal.username}")
    List<MpoVacancyRoleDesgnations> findByCreateByIsCurrentUser();

    @Query("select mpoVacancyRoleDesgnations from MpoVacancyRoleDesgnations mpoVacancyRoleDesgnations where mpoVacancyRoleDesgnations.mpoVacancyRole.id =:id")
    Page<MpoVacancyRoleDesgnations> findByMpoVacancyRole(Pageable pageable, @Param("id") Long id);

    @Query("select mpoVacancyRoleDesgnations from MpoVacancyRoleDesgnations mpoVacancyRoleDesgnations where mpoVacancyRoleDesgnations.mpoVacancyRole.id =:id")
    List<MpoVacancyRoleDesgnations> findByMpoVacancyRole(@Param("id") Long id);

}
