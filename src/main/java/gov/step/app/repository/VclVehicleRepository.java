package gov.step.app.repository;

import gov.step.app.domain.VclVehicle;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VclVehicle entity.
 */
public interface VclVehicleRepository extends JpaRepository<VclVehicle,Long> {

}
