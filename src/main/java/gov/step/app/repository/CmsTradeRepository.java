/*
package gov.step.app.repository;

import gov.step.app.domain.CmsTrade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

*/
/**
 * Spring Data JPA repository for the CmsTrade entity.
 *//*

public interface CmsTradeRepository extends JpaRepository<CmsTrade,Long> {
    @Query("select cmsTrade from CmsTrade cmsTrade where cmsTrade.code = :code")
    Optional<CmsTrade> findOneByCode(@Param("code") String code);

    @Query("select cmsTrade from CmsTrade cmsTrade where cmsTrade.cmsCurriculum.id = :id")
    Page<CmsTrade> findAllByCurriculum(Pageable pageable, @Param("id") Long id);
}
*/


package gov.step.app.repository;

import gov.step.app.domain.CmsCurriculum;
import gov.step.app.domain.CmsTrade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the CmsTrade entity.
 */
public interface CmsTradeRepository extends JpaRepository<CmsTrade,Long> {
    @Query("select cmsTrade from CmsTrade cmsTrade where cmsTrade.code = :code")
    Optional<CmsTrade> findOneByCode(@Param("code") String code);

    @Query("select cmsTrade from CmsTrade cmsTrade where cmsTrade.name = :name")
    Optional<CmsTrade> findOneByName(@Param("name") String name);

    @Query("select cmsTrade from CmsTrade cmsTrade where cmsTrade.cmsCurriculum.id = :id and cmsTrade.status = true")
    List<CmsTrade> findAllByCurriculum(@Param("id") Long id);

    @Query("select cmsTrade from CmsTrade cmsTrade where cmsTrade.status=1 AND cmsTrade.cmsCurriculum.id = :id")
    Page<CmsTrade> findAllByCurriculum(Pageable pageable, @Param("id") Long id);


    @Query("select cmsTrade from CmsTrade cmsTrade where cmsTrade.status = 1 ")
    Page<CmsTrade> activecmsTrades(org.springframework.data.domain.Pageable pageable);
}
