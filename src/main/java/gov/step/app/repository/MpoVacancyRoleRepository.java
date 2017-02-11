package gov.step.app.repository;

import gov.step.app.domain.CmsTrade;
import gov.step.app.domain.MpoVacancyRole;

import gov.step.app.domain.enumeration.VacancyRoleType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MpoVacancyRole entity.
 */
public interface MpoVacancyRoleRepository extends JpaRepository<MpoVacancyRole,Long> {

    @Query("select mpoVacancyRole from MpoVacancyRole mpoVacancyRole where mpoVacancyRole.createBy.login = ?#{principal.username}")
    List<MpoVacancyRole> findByCreateByIsCurrentUser();

    @Query("select mpoVacancyRole from MpoVacancyRole mpoVacancyRole where mpoVacancyRole.vacancyRoleType =:roleType and mpoVacancyRole.cmsCurriculum.id = :id")
    List<MpoVacancyRole> findGeneralRolesByCurriculum(@Param("id") Long id, @Param("roleType") VacancyRoleType roleType);

    @Query("select distinct mpoVacancyRole from MpoVacancyRole mpoVacancyRole join fetch mpoVacancyRole.mpoVacancyRoleTrades mpoVacancyRoleTrade where mpoVacancyRoleTrade.cmsTrade in :trades")
    List<MpoVacancyRole> findSpecialRolesByTrades(@Param("trades") List<CmsTrade> trades);


}
