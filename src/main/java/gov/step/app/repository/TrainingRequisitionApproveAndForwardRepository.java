package gov.step.app.repository;

import gov.step.app.domain.TrainingRequisitionApproveAndForward;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TrainingRequisitionApproveAndForward entity.
 */
public interface TrainingRequisitionApproveAndForwardRepository extends JpaRepository<TrainingRequisitionApproveAndForward,Long> {

    @Query("select modelInfo from TrainingRequisitionApproveAndForward modelInfo where" +
                                 " modelInfo.approveStatus =:approveStatus and modelInfo.trainingRequisitionForm.id =:requisitionId and modelInfo.status = true")
    TrainingRequisitionApproveAndForward findByRequisitionAndApproveStatus(@Param("approveStatus") Integer approveStatus,@Param("requisitionId") Long requisitionId);
}
