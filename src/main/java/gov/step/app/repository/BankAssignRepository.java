package gov.step.app.repository;

import gov.step.app.domain.BankAssign;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BankAssign entity.
 */
public interface BankAssignRepository extends JpaRepository<BankAssign,Long> {

    @Query("select bankAssign from BankAssign bankAssign where bankAssign.createdBy.login = ?#{principal.username}")
    List<BankAssign> findByCreatedByIsCurrentUser();

    @Query("select bankAssign from BankAssign bankAssign where bankAssign.updatedBy.login = ?#{principal.username}")
    List<BankAssign> findByUpdatedByIsCurrentUser();

}
