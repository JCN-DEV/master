package gov.step.app.repository;

import gov.step.app.domain.AlmLeaveType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the AlmLeaveType entity.
 */
public interface AlmLeaveTypeRepository extends JpaRepository<AlmLeaveType,Long> {
    @Query("select modelInfo from AlmLeaveType modelInfo where lower(modelInfo.leaveTypeName) = :leaveTypeName ")
    Optional<AlmLeaveType> findOneByLeaveTypeName(@Param("leaveTypeName") String leaveTypeName);

    Page<AlmLeaveType> findAllByActiveStatus(Pageable pageable, boolean activeStatus);
}
