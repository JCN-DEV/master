package gov.step.app.repository;

import gov.step.app.domain.Attachment;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Attachment entity.
 */
public interface AttachmentRepository extends JpaRepository<Attachment,Long> {

    @Query("select attachment from Attachment attachment where attachment.instEmployee.id = :id")
    List<Attachment> findByEmployee(@Param("id") Long id);

    @Query("select attachment from Attachment attachment where attachment.instEmployee.id = :id and attachment.attachmentCategory.applicationName = :applicationName")
    List<Attachment> findByEmployeeAndApplicationName(@Param("id") Long id, @Param("applicationName") String applicationName);
}
