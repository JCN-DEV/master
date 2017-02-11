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
 * A TrainerInformation.
 */
@Entity
@Table(name = "tis_trainer_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "trainerinformation")
public class TrainerInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "trainer_id")
    private String trainerId;

    @Column(name = "trainer_name")
    private String trainerName;

    @NotNull
    @Column(name = "trainer_type", nullable = false)
    private String trainerType;

    @Column(name = "address")
    private String address;

    @Column(name = "designation")
    private String designation;

    @Column(name = "department")
    private String department;

    @Column(name = "organization")
    private String organization;

    @Column(name = "mobile_number")
    private Long mobileNumber;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "special_skills")
    private String specialSkills;

    @Column(name = "training_assign_status")
    private Boolean trainingAssignStatus;

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
    @JoinColumn(name = "initialization_info_id")
    private TrainingInitializationInfo trainingInitializationInfo;

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo hrEmployeeInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTrainerType() {
        return trainerType;
    }

    public void setTrainerType(String trainerType) {
        this.trainerType = trainerType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getSpecialSkills() {
        return specialSkills;
    }

    public void setSpecialSkills(String specialSkills) {
        this.specialSkills = specialSkills;
    }

    public Boolean getTrainingAssignStatus() {
        return trainingAssignStatus;
    }

    public void setTrainingAssignStatus(Boolean trainingAssignStatus) {
        this.trainingAssignStatus = trainingAssignStatus;
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

    public TrainingInitializationInfo getTrainingInitializationInfo() {
        return trainingInitializationInfo;
    }

    public void setTrainingInitializationInfo(TrainingInitializationInfo trainingInitializationInfo) {
        this.trainingInitializationInfo = trainingInitializationInfo;
    }

    public HrEmployeeInfo getHrEmployeeInfo() {
        return hrEmployeeInfo;
    }

    public void setHrEmployeeInfo(HrEmployeeInfo hrEmployeeInfo) {
        this.hrEmployeeInfo = hrEmployeeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrainerInformation trainerInformation = (TrainerInformation) o;

        if ( ! Objects.equals(id, trainerInformation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TrainerInformation{" +
            "id=" + id +
            ", trainerId='" + trainerId + "'" +
            ", trainerName='" + trainerName + "'" +
            ", trainerType='" + trainerType + "'" +
            ", address='" + address + "'" +
            ", designation='" + designation + "'" +
            ", department='" + department + "'" +
            ", organization='" + organization + "'" +
            ", mobileNumber='" + mobileNumber + "'" +
            ", emailId='" + emailId + "'" +
            ", specialSkills='" + specialSkills + "'" +
            ", trainingAssignStatus='" + trainingAssignStatus + "'" +
            ", status='" + status + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
