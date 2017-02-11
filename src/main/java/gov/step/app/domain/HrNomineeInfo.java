package gov.step.app.domain;

import gov.step.app.domain.enumeration.Gender;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A HrNomineeInfo.
 */
@Entity
@Table(name = "hr_nominee_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrnomineeinfo")
public class HrNomineeInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nominee_name", length = 100, nullable = false)
    private String nomineeName;

    @Column(name = "nominee_name_bn")
    private String nomineeNameBn;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "nominee_relationship_id")
    private MiscTypeSetup nomineeRelationship;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "designation")
    private String designation;

    @Size(min = 13, max = 17)
    @Column(name = "national_id", length = 17)
    private String nationalId;

    @NotNull
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "active_status", nullable = false)
    private Boolean activeStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @Column(name = "log_id")
    private Long logId;

    @Column(name = "log_status")
    private Long logStatus;

    @Column(name = "log_comments")
    private String logComments;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getNomineeNameBn() {
        return nomineeNameBn;
    }

    public void setNomineeNameBn(String nomineeNameBn) {
        this.nomineeNameBn = nomineeNameBn;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
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

    public HrEmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(HrEmployeeInfo HrEmployeeInfo) {
        this.employeeInfo = HrEmployeeInfo;
    }

    public Long getLogId() { return logId;}

    public void setLogId(Long logId) {this.logId = logId;}

    public Long getLogStatus() {return logStatus;}

    public void setLogStatus(Long logStatus) {this.logStatus = logStatus;}

    public String getLogComments() {return logComments;}

    public void setLogComments(String logComments) {this.logComments = logComments;}

    public MiscTypeSetup getNomineeRelationship() {return nomineeRelationship;}

    public void setNomineeRelationship(MiscTypeSetup nomineeRelationship) {this.nomineeRelationship = nomineeRelationship;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrNomineeInfo hrNomineeInfo = (HrNomineeInfo) o;
        return Objects.equals(id, hrNomineeInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrNomineeInfo{" +
            "id=" + id +
            ", nomineeName='" + nomineeName + "'" +
            ", nomineeNameBn='" + nomineeNameBn + "'" +
            ", birthDate='" + birthDate + "'" +
            ", gender='" + gender + "'" +
            ", occupation='" + occupation + "'" +
            ", designation='" + designation + "'" +
            ", nationalId='" + nationalId + "'" +
            ", mobileNumber='" + mobileNumber + "'" +
            ", address='" + address + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
