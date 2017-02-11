package gov.step.app.repository;

import gov.step.app.domain.DlContUpld;
import gov.step.app.domain.DlFileType;

import gov.step.app.domain.DlSourceSetUp;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DlFileType entity.
 */
public interface DlFileTypeRepository extends JpaRepository<DlFileType,Long> {

    @Query("select dlFileType from DlFileType dlFileType where dlFileType.fileType = :fileType")
    Optional<DlContUpld> findOneByfileType(@Param("fileType") String fileType);

    @Query("select dlFileType from DlFileType dlFileType where dlFileType.pStatus = 1 ")
    Page<DlFileType> activeFileType(org.springframework.data.domain.Pageable pageable);


}
