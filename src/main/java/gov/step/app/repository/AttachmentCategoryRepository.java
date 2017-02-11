package gov.step.app.repository;

import gov.step.app.domain.Attachment;
import gov.step.app.domain.AttachmentCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the AttachmentCategory entity.
 */
public interface AttachmentCategoryRepository extends JpaRepository<AttachmentCategory,Long> {

    @Query("select attachmentCategory from AttachmentCategory attachmentCategory where attachmentCategory.module.id = :id")
    List<AttachmentCategory> findByModule(@Param("id") Long id);

    @Query("select attachmentCategory from AttachmentCategory attachmentCategory where attachmentCategory.applicationName = :applicationName")
    List<AttachmentCategory> findByApplicationName(@Param("applicationName") String applicationName);

    @Query("select attachmentCategory from AttachmentCategory attachmentCategory where attachmentCategory.applicationName = :applicationName and attachmentCategory.designationSetup.id = :id")
    List<AttachmentCategory> findByApplicationNameAndDesignation(@Param("applicationName") String applicationName, @Param("id") Long id);

}
