package gov.step.app.repository;

import gov.step.app.domain.AlmLeaveRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the AlmLeaveRule entity.
 */
public interface AlmLeaveRuleRepository extends JpaRepository<AlmLeaveRule,Long> {
    @Query("select almLeaveRule from AlmLeaveRule almLeaveRule where almLeaveRule.almLeaveGroup.id = :groupId and almLeaveRule.almLeaveType.id = :leaveTypeId")
    Optional<AlmLeaveRule> findOneByAlmLeaveGroupAndAlmLeaveType(@Param("groupId") Long groupId, @Param("leaveTypeId") Long leaveTypeId);
}
