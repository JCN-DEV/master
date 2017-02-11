package gov.step.app.repository;

import gov.step.app.domain.DlExistBookInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DlExistBookInfo entity.
 */
public interface DlExistBookInfoRepository extends JpaRepository<DlExistBookInfo,Long> {

}
