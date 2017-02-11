package gov.step.app.repository;

import gov.step.app.domain.VclDriver;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VclDriver entity.
 */
public interface VclDriverRepository extends JpaRepository<VclDriver,Long> {

}
