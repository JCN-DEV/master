package gov.step.app.repository;

import gov.step.app.domain.CmsTrade;
import gov.step.app.domain.MpoTrade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MpoTrade entity.
 */
public interface MpoTradeRepository extends JpaRepository<MpoTrade,Long> {

    @Query("select mpoTrade from MpoTrade mpoTrade where mpoTrade.createBy.login = ?#{principal.username}")
    List<MpoTrade> findByCreateByIsCurrentUser();

    @Query("select mpoTrade from MpoTrade mpoTrade where mpoTrade.cmsTrade.id = :id")
    MpoTrade findOneByTrade(@Param("id") Long id);

    @Query("select mpoTrade.cmsTrade from MpoTrade mpoTrade")
    Page<CmsTrade> findAllCmsTrades(Pageable pageable);

    @Query("select mpoTrade.cmsTrade from MpoTrade mpoTrade where mpoTrade.cmsTrade.cmsCurriculum.id = :id")
    Page<CmsTrade> findAllCmsTradesByCurriculum(Pageable pageable, @Param("id") Long id);



}
