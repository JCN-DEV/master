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
 * A HrSpouseInfo.
 */
@Entity
@Table(name = "hr_spouse_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrspouseinfo")
public class HrSpouseInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "spouse_name", length = 100, nullable = false)
    private String spouseName;

    @Column(name = "spouse_name_bn")
    private String spouseNameBn;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Column(name = "relationship")
    private String relationship;

    @Column(name = "is_nominee")
    private Boolean isNominee;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "organization")
    private String organization;

    @Column(name = "designation")
    private String designation;

    @Column(name = "national_id")
    private String nationalId;

    @NotNull
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @NotNull
    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

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

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getSpouseNameBn() {
        return spouseNameBn;
    }

    public void setSpouseNameBn(String spouseNameBn) {
        this.spouseNameBn = spouseNameBn;
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

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Boolean getIsNominee() {
        return isNominee;
    }

    public void setIsNominee(Boolean isNominee) {
        this.isNominee = isNominee;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Long logStatus) {
        this.logStatus = logStatus;
    }

    public String getLogComments() {
        return logComments;
    }

    public void setLogComments(String logComments) {this.logComments = logComments;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrSpouseInfo hrSpouseInfo = (HrSpouseInfo) o;
        return Objects.equals(id, hrSpouseInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrSpouseInfo{" +
            "id=" + id +
            ", spouseName='" + spouseName + "'" +
            ", spouseNameBn='" + spouseNameBn + "'" +
            ", birthDate='" + birthDate + "'" +
            ", gender='" + gender + "'" +
            ", relationship='" + relationship + "'" +
            ", isNominee='" + isNominee + "'" +
            ", occupation='" + occupation + "'" +
            ", organization='" + organization + "'" +
            ", designation='" + designation + "'" +
            ", nationalId='" + nationalId + "'" +
            ", emailAddress='" + emailAddress + "'" +
            ", contactNumber='" + contactNumber + "'" +
            ", address='" + address + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
