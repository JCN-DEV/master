package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TrainingInitializationInfo.
 */
@Entity
@Table(name = "tis_initialization_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "traininginitializationinfo")
public class TrainingInitializationInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "training_code")
    private String trainingCode;

    @Column(name = "training_type")
    private String trainingType;

    @Column(name = "session_year")
    private String session;

    @Column(name = "venue_name")
    private String venueName;

    @Column(name = "number_of_trainee")
    private Long numberOfTrainee;

    @Column(name = "location")
    private String location;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    @ManyToOne
    @JoinColumn(name="district_id")
    private District district;

    @Column(name = "venue_description")
    private String venueDescription;

    @Column(name = "initialization_date")
    private LocalDate initializationDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "duration")
    private String duration;

    @Column(name = "hours")
    private Integer hours;

    @Column(name = "publish_status")
    private Boolean publishStatus;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    private TrainingHeadSetup trainingHeadSetup;

    @ManyToOne
    private TrainingRequisitionForm trainingRequisitionForm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainingCode() {
        return trainingCode;
    }

    public void setTrainingCode(String trainingCode) {
        this.trainingCode = trainingCode;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Long getNumberOfTrainee() {
        return numberOfTrainee;
    }

    public void setNumberOfTrainee(Long numberOfTrainee) {
        this.numberOfTrainee = numberOfTrainee;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getVenueDescription() {
        return venueDescription;
    }

    public void setVenueDescription(String venueDescription) {
        this.venueDescription = venueDescription;
    }

    public LocalDate getInitializationDate() {
        return initializationDate;
    }

    public void setInitializationDate(LocalDate initializationDate) {
        this.initializationDate = initializationDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Boolean getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Boolean publishStatus) {
        this.publishStatus = publishStatus;
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

    public TrainingHeadSetup getTrainingHeadSetup() {
        return trainingHeadSetup;
    }

    public void setTrainingHeadSetup(TrainingHeadSetup trainingHeadSetup) {
        this.trainingHeadSetup = trainingHeadSetup;
    }

    public TrainingRequisitionForm getTrainingRequisitionForm() {
        return trainingRequisitionForm;
    }

    public void setTrainingRequisitionForm(TrainingRequisitionForm trainingRequisitionForm) {
        this.trainingRequisitionForm = trainingRequisitionForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrainingInitializationInfo trainingInitializationInfo = (TrainingInitializationInfo) o;

        if ( ! Objects.equals(id, trainingInitializationInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TrainingInitializationInfo{" +
            "id=" + id +
            ", trainingCode='" + trainingCode + "'" +
            ", trainingType='" + trainingType + "'" +
            ", session='" + session + "'" +
            ", venueName='" + venueName + "'" +
            ", numberOfTrainee='" + numberOfTrainee + "'" +
            ", location='" + location + "'" +
            ", division='" + division + "'" +
            ", district='" + district + "'" +
            ", venueDescription='" + venueDescription + "'" +
            ", initializationDate='" + initializationDate + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", duration='" + duration + "'" +
            ", hours='" + hours + "'" +
            ", publishStatus='" + publishStatus + "'" +
            ", initializationDate='" + initializationDate + "'" +
            ", status='" + status + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
