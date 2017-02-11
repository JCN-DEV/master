package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TrainerEvaluationInfo.
 */
@Entity
@Table(name = "tis_trainer_evaluation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "trainerevaluationinfo")
public class TrainerEvaluationInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(name = "session_year")
//    private String sessionYear;

    @NotNull
    @Size(max = 50)
    @Column(name = "performance", length = 50, nullable = false)
    private String performance;

    @Column(name = "remarks")
    private String remarks;

    @NotNull
    @Column(name = "evaluation_date", nullable = false)
    private LocalDate evaluationDate;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

//    @ManyToOne
//    @JoinColumn(name="headsetup_id")
//    private TrainingHeadSetup trainingHeadSetup;

    @ManyToOne
    @JoinColumn(name="initialization_info_id")
    private TrainingInitializationInfo trainingInitializationInfo;

    @ManyToOne
    @JoinColumn(name="trainee_info_id")
    private TraineeInformation traineeInformation;

    @ManyToOne
    @JoinColumn(name="trainer_info_id")
    private TrainerInformation trainerInformation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getSessionYear() {
//        return sessionYear;
//    }
//
//    public void setSessionYear(String sessionYear) {
//        this.sessionYear = sessionYear;
//    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

//    public TrainingHeadSetup getTrainingHeadSetup() {
//        return trainingHeadSetup;
//    }
//
//    public void setTrainingHeadSetup(TrainingHeadSetup trainingHeadSetup) {
//        this.trainingHeadSetup = trainingHeadSetup;
//    }

    public TrainingInitializationInfo getTrainingInitializationInfo() {
        return trainingInitializationInfo;
    }

    public void setTrainingInitializationInfo(TrainingInitializationInfo trainingInitializationInfo) {
        this.trainingInitializationInfo = trainingInitializationInfo;
    }

    public TraineeInformation getTraineeInformation() {
        return traineeInformation;
    }

    public void setTraineeInformation(TraineeInformation traineeInformation) {
        this.traineeInformation = traineeInformation;
    }

    public TrainerInformation getTrainerInformation() {
        return trainerInformation;
    }

    public void setTrainerInformation(TrainerInformation trainerInformation) {
        this.trainerInformation = trainerInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrainerEvaluationInfo trainerEvaluationInfo = (TrainerEvaluationInfo) o;

        if ( ! Objects.equals(id, trainerEvaluationInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TrainerEvaluationInfo{" +
            "id=" + id +
           // ", sessionYear='" + sessionYear + "'" +
            ", performance='" + performance + "'" +
            ", remarks='" + remarks + "'" +
            ", evaluationDate='" + evaluationDate + "'" +
            ", status='" + status + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
