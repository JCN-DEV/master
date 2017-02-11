package gov.step.app.repository;

import gov.step.app.domain.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Relationship entity.
 */
public interface RelationshipRepository extends JpaRepository<Relationship,Long> {

}
