package gov.step.app.repository;

import gov.step.app.domain.JobPlacementInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the JobPlacementInfo entity.
 */
public interface JobPlacementInfoRepository extends JpaRepository<JobPlacementInfo,Long> {

    public Page<JobPlacementInfo> findAllByOrderByIdDesc(Pageable pageable);

}
