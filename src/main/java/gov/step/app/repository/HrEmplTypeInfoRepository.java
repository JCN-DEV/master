package gov.step.app.repository;

import gov.step.app.domain.HrEmplTypeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HrEmplTypeInfo entity.
 */
public interface HrEmplTypeInfoRepository extends JpaRepository<HrEmplTypeInfo,Long>
{
    @Query("select modelInfo from HrEmplTypeInfo modelInfo where lower(modelInfo.typeCode) = :typeCode ")
    Optional<HrEmplTypeInfo> findOneByTypeCode(@Param("typeCode") String typeCode);

    @Query("select modelInfo from HrEmplTypeInfo modelInfo where lower(modelInfo.typeName) = :typeName ")
    Optional<HrEmplTypeInfo> findOneByTypeName(@Param("typeName") String typeName);

    @Query("select modelInfo from HrEmplTypeInfo modelInfo where activeStatus =:activeStatus order by typeName")
    Page<HrEmplTypeInfo> findAllByActiveStatus(Pageable pageable, @Param("activeStatus") boolean activeStatus);

    @Query("select modelInfo from HrEmplTypeInfo modelInfo where activeStatus =:activeStatus order by typeName")
    List<HrEmplTypeInfo> findAllByActiveStatus(@Param("activeStatus") boolean activeStatus);
}
