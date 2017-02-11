package gov.step.app.repository;

import gov.step.app.domain.TrainingRequisitionForm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TrainingRequisitionForm entity.
 */
public interface TrainingRequisitionFormRepository extends JpaRepository<TrainingRequisitionForm,Long> {

    public Page<TrainingRequisitionForm> findAllByOrderByIdDesc(Pageable pageable);

    @Query("select trainingRequisittion from TrainingRequisitionForm trainingRequisittion where trainingRequisittion.requisitionCode =:trainingReqcode")
    TrainingRequisitionForm findByRequisitionCode(@Param("trainingReqcode") String trainingReqcode);

    @Query("select trainingRequisittion from TrainingRequisitionForm trainingRequisittion where trainingRequisittion.approveStatus =:approveStatus and trainingRequisittion.status = true")
    List<TrainingRequisitionForm> findByApproveStatus(@Param("approveStatus") Integer approveStatus);

    @Query("select trainingRequisittion from TrainingRequisitionForm trainingRequisittion where trainingRequisittion.hrEmployeeInfo.id =:employeeInfoId and trainingRequisittion.status = true")
    List<TrainingRequisitionForm> findByCurrentUser(@Param("employeeInfoId") Long employeeInfoId);

    @Query("select trainingRequisittion from TrainingRequisitionForm trainingRequisittion where trainingRequisittion.institute.id =:instituteId and trainingRequisittion.status = true")
    List<TrainingRequisitionForm> findByCurrentInstitute(@Param("instituteId") Long instituteId);



}
