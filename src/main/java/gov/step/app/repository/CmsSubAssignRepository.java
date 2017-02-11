/*
package gov.step.app.repository;

import gov.step.app.domain.CmsSubAssign;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

*/
/**
 * Spring Data JPA repository for the CmsSubAssign entity.
 *//*

public interface CmsSubAssignRepository extends JpaRepository<CmsSubAssign,Long> {
    @Query("select cmsSubAssign from CmsSubAssign cmsSubAssign where cmsSubAssign.cmsTrade.id = :id")
    Page<CmsSubAssign> findAllByTrade(@Param("id") Long id, Pageable pageable);


}
*/


package gov.step.app.repository;

import gov.step.app.domain.CmsSubAssign;

import gov.step.app.domain.CmsSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CmsSubAssign entity.
 */
public interface CmsSubAssignRepository extends JpaRepository<CmsSubAssign,Long> {

    @Query("select cmsSubAssign.cmsSubject from CmsSubAssign cmsSubAssign where cmsSubAssign.cmsTrade.id =:cmsTradeId")
    List<CmsSubject> findCmsSubjectByTrade(@Param("cmsTradeId") Long cmsTradeId);

    @Query("select cmsSubAssign from CmsSubAssign cmsSubAssign where cmsSubAssign.cmsTrade.id = :id")
    Page<CmsSubAssign> findAllByTrade(@Param("id") Long id, Pageable pageable);


}
