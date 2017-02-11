package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlLocalitySetInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the PrlLocalitySetInfo entity.
 */
public interface PrlLocalitySetInfoRepository extends JpaRepository<PrlLocalitySetInfo,Long> {

    @Query("select distinct prlLocalitySetInfo from PrlLocalitySetInfo prlLocalitySetInfo left join fetch prlLocalitySetInfo.localityInfos")
    List<PrlLocalitySetInfo> findAllWithEagerRelationships();

    @Query("select prlLocalitySetInfo from PrlLocalitySetInfo prlLocalitySetInfo left join fetch prlLocalitySetInfo.localityInfos where prlLocalitySetInfo.id =:id")
    PrlLocalitySetInfo findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select modelInfo from PrlLocalitySetInfo modelInfo where lower(modelInfo.name) = lower(:name) ")
    Optional<PrlLocalitySetInfo> findOneByName(@Param("name") String name);

}
