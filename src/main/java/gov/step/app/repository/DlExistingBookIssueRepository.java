package gov.step.app.repository;

import gov.step.app.domain.DlExistingBookIssue;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DlExistingBookIssue entity.
 */
public interface DlExistingBookIssueRepository extends JpaRepository<DlExistingBookIssue,Long> {

}
