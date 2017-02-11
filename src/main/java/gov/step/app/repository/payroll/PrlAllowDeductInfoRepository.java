package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlAllowDeductInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the PrlAllowDeductInfo entity.
 */
public interface PrlAllowDeductInfoRepository extends JpaRepository<PrlAllowDeductInfo,Long> {

    @Query("select distinct prlAllowDeductInfo from PrlAllowDeductInfo prlAllowDeductInfo left join fetch prlAllowDeductInfo.gradeInfos")
    List<PrlAllowDeductInfo> findAllWithEagerRelationships();

    @Query("select prlAllowDeductInfo from PrlAllowDeductInfo prlAllowDeductInfo left join fetch prlAllowDeductInfo.gradeInfos where prlAllowDeductInfo.id =:id")
    PrlAllowDeductInfo findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select distinct prlAllowDeductInfo from PrlAllowDeductInfo prlAllowDeductInfo join fetch prlAllowDeductInfo.gradeInfos")
    List<PrlAllowDeductInfo> findAllWithSelecteedGrade();

    @Query("select modelInfo from PrlAllowDeductInfo modelInfo where lower(modelInfo.allowDeducType) = lower(:type) AND lower(modelInfo.name) = lower(:name) ")
    Optional<PrlAllowDeductInfo> findOneByTypeAndName(@Param("type") String type, @Param("name") String name);

    @Query("select modelInfo from PrlAllowDeductInfo modelInfo where lower(modelInfo.allowDeducType) = lower(:type) ORDER BY name ")
    List<PrlAllowDeductInfo> findAllByType(@Param("type") String type);

    @Query("select modelInfo from PrlAllowDeductInfo modelInfo where lower(modelInfo.allowDeducCode) = lower(:code)")
    PrlAllowDeductInfo findAllowanceDeductionByCode(@Param("code") String code);

    @Query("select modelInfo from PrlAllowDeductInfo modelInfo WHERE allowDeducCode IN :codeList order by allowDeducCode")
    List<PrlAllowDeductInfo> findAllowanceDeductionByCodes(@Param("codeList") List<String> codeList);

    @Query("select modelInfo from PrlAllowDeductInfo modelInfo WHERE lower(allowCategory)= lower(:category)")
    List<PrlAllowDeductInfo> findAllowanceByCategory(@Param("category") String category);

    @Query("select modelInfo from PrlAllowDeductInfo modelInfo WHERE lower(allowCategory)= lower(:category) AND id != :alid ")
    List<PrlAllowDeductInfo> findAllowanceByCategoryAndID(@Param("category") String category, @Param("alid") Long alid);
}
