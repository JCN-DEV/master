package gov.step.app.repository;

import gov.step.app.domain.HrClassInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HrClassInfo entity.
 */
public interface HrClassInfoRepository extends JpaRepository<HrClassInfo,Long>
{
    @Query("select modelInfo from HrClassInfo modelInfo where lower(modelInfo.classCode) = lower(:classCode) ")
    List<HrClassInfo> findOneByClassCode(@Param("classCode") String classCode);

    @Query("select modelInfo from HrClassInfo modelInfo where lower(modelInfo.className) = lower(:className) ")
    List<HrClassInfo> findOneByClassName(@Param("className") String className);

    @Query("select modelInfo from HrClassInfo modelInfo where activeStatus =:activeStatus order by className")
    Page<HrClassInfo> findAllByActiveStatus(Pageable pageable, @Param("activeStatus") boolean activeStatus);
}
