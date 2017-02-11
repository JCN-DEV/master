package gov.step.app.repository;

import gov.step.app.domain.VclVehicleDriverAssign;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VclVehicleDriverAssign entity.
 */
public interface VclVehicleDriverAssignRepository extends JpaRepository<VclVehicleDriverAssign,Long> {

}
