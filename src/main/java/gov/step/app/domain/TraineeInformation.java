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
 * A TraineeInformation.
 */
@Entity
@Table(name = "tis_trainee_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "traineeinformation")
public class TraineeInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 50)
    @Column(name = "trainee_id", length = 50)
    private String traineeId;

//    @NotNull
    @Size(max = 50)
    @Column(name = "trainee_name", length = 50)
    private String traineeName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "organization")
    private String organization;


    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @Column(name = "contact_number")
    private Long contactNumber;

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
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo hrEmployeeInfo;

    @ManyToOne
    @JoinColumn(name="institute_employee_id")
    private InstEmployee instEmployee;

    @ManyToOne
    @JoinColumn(name = "training_requisition_id")
    private TrainingRequisitionForm trainingRequisitionForm;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    public String getTraineeName() {
        return traineeName;
    }

    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
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

    public HrEmployeeInfo getHrEmployeeInfo() {
        return hrEmployeeInfo;
    }

    public void setHrEmployeeInfo(HrEmployeeInfo hrEmployeeInfo) {
        this.hrEmployeeInfo = hrEmployeeInfo;
    }

    public TrainingRequisitionForm getTrainingRequisitionForm() {
        return trainingRequisitionForm;
    }

    public void setTrainingRequisitionForm(TrainingRequisitionForm trainingRequisitionForm) {
        this.trainingRequisitionForm = trainingRequisitionForm;
    }

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TraineeInformation traineeInformation = (TraineeInformation) o;

        if ( ! Objects.equals(id, traineeInformation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraineeInformation{" +
            "id=" + id +
            ", traineeId='" + traineeId + "'" +
            ", traineeName='" + traineeName + "'" +
            ", gender='" + gender + "'" +
            ", organization='" + organization + "'" +
            ", address='" + address + "'" +
            ", division='" + division + "'" +
            ", district='" + district + "'" +
            ", contactNumber='" + contactNumber + "'" +
            ", status='" + status + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
