package gov.step.app.repository;

import gov.step.app.domain.CmsTrade;
import gov.step.app.domain.MpoVacancyRoleDesgnations;
import gov.step.app.domain.MpoVacancyRoleTrade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MpoVacancyRoleTrade entity.
 */
public interface MpoVacancyRoleTradeRepository extends JpaRepository<MpoVacancyRoleTrade,Long> {

    @Query("select mpoVacancyRoleTrade from MpoVacancyRoleTrade mpoVacancyRoleTrade where mpoVacancyRoleTrade.createBy.login = ?#{principal.username}")
    List<MpoVacancyRoleTrade> findByCreateByIsCurrentUser();

    @Query("select mpoVacancyRoleTrade from MpoVacancyRoleTrade mpoVacancyRoleTrade where mpoVacancyRoleTrade.mpoVacancyRole.id =:id")
    Page<MpoVacancyRoleTrade> findByMpoVacancyRole(Pageable pageable, @Param("id") Long id);

    @Query("select mpoVacancyRoleTrade.cmsTrade from MpoVacancyRoleTrade mpoVacancyRoleTrade where mpoVacancyRoleTrade.mpoVacancyRole.id =:id")
    List<CmsTrade> findTradesByMpoVacancyRole(@Param("id") Long id);

    @Query("select mpoVacancyRoleTrade from MpoVacancyRoleTrade mpoVacancyRoleTrade where mpoVacancyRoleTrade.cmsTrade in :trades ")
    List<MpoVacancyRoleTrade> findByCmstrades(@Param("trades") List<CmsTrade> trades);

}





