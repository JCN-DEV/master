package gov.step.app.repository;

import gov.step.app.domain.PersonalPay;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonalPay entity.
 */
public interface PersonalPayRepository extends JpaRepository<PersonalPay,Long> {

    @Query("select personalPay from PersonalPay personalPay where personalPay.createdBy.login = ?#{principal.username}")
    List<PersonalPay> findByCreatedByIsCurrentUser();

    @Query("select personalPay from PersonalPay personalPay where personalPay.updatedBy.login = ?#{principal.username}")
    List<PersonalPay> findByUpdatedByIsCurrentUser();

}
