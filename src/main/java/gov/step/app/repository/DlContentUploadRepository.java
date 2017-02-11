package gov.step.app.repository;

import gov.step.app.domain.DlContentUpload;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DlContentUpload entity.
 */
public interface DlContentUploadRepository extends JpaRepository<DlContentUpload,Long> {

}
