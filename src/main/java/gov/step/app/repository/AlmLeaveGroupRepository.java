package gov.step.app.repository;

import gov.step.app.domain.AlmLeaveGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the AlmLeaveGroup entity.
 */
public interface AlmLeaveGroupRepository extends JpaRepository<AlmLeaveGroup,Long> {
    @Query("select modelInfo from AlmLeaveGroup modelInfo where lower(modelInfo.leaveGroupName) = :leaveGroupName ")
    Optional<AlmLeaveGroup> findOneByLeaveGroupName(@Param("leaveGroupName") String leaveGroupName);

    Page<AlmLeaveGroup> findAllByActiveStatus(Pageable pageable, boolean activeStatus);
}
