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
 * A TraineeEvaluationInfo.
 */
@Entity
@Table(name = "tis_trainee_evaluation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "traineeevaluationinfo")
public class TraineeEvaluationInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(name = "session_year")
//    private String sessionYear;

    @NotNull
    @Size(max = 50)
    @Column(name = "performance", length = 50, nullable = false)
    private String performance;

    @NotNull
    @Column(name = "mark", nullable = false)
    private String mark;

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


    @ManyToOne
    @JoinColumn(name = "traineeinfo_id")
    private TraineeInformation traineeInformation;

//    @ManyToOne
//    private TrainingHeadSetup trainingHeadSetup;

//    @ManyToOne
//    private HrEmployeeInfo hrEmployeeInfo;

    @ManyToOne
    private TrainingInitializationInfo trainingInitializationInfo;

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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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

    public TraineeInformation getTraineeInformation() {
        return traineeInformation;
    }

    public void setTraineeInformation(TraineeInformation traineeInformation) {
        this.traineeInformation = traineeInformation;
    }

//    public TrainingHeadSetup getTrainingHeadSetup() {
//        return trainingHeadSetup;
//    }
//
//    public void setTrainingHeadSetup(TrainingHeadSetup trainingHeadSetup) {
//        this.trainingHeadSetup = trainingHeadSetup;
//    }
//
//    public HrEmployeeInfo getHrEmployeeInfo() {
//        return hrEmployeeInfo;
//    }
//
//    public void setHrEmployeeInfo(HrEmployeeInfo hrEmployeeInfo) {
//        this.hrEmployeeInfo = hrEmployeeInfo;
//    }

    public TrainingInitializationInfo getTrainingInitializationInfo() {
        return trainingInitializationInfo;
    }

    public void setTrainingInitializationInfo(TrainingInitializationInfo trainingInitializationInfo) {
        this.trainingInitializationInfo = trainingInitializationInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TraineeEvaluationInfo traineeEvaluationInfo = (TraineeEvaluationInfo) o;

        if ( ! Objects.equals(id, traineeEvaluationInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraineeEvaluationInfo{" +
            "id=" + id +
//            ", sessionYear='" + sessionYear + "'" +
            ", performance='" + performance + "'" +
            ", mark='" + mark + "'" +
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
