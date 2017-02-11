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
 * A HrSpouseInfoLog.
 */
@Entity
@Table(name = "hr_spouse_info_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrspouseinfolog")
public class HrSpouseInfoLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "spouse_name")
    private String spouseName;

    @Column(name = "spouse_name_bn")
    private String spouseNameBn;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

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

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "log_status")
    private Long logStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "action_date")
    private LocalDate actionDate;

    @Column(name = "action_by")
    private Long actionBy;

    @Column(name = "action_comments")
    private String actionComments;

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Long logStatus) {
        this.logStatus = logStatus;
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

    public LocalDate getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
    }

    public Long getActionBy() {
        return actionBy;
    }

    public void setActionBy(Long actionBy) {
        this.actionBy = actionBy;
    }

    public String getActionComments() {
        return actionComments;
    }

    public void setActionComments(String actionComments) {
        this.actionComments = actionComments;
    }

    public HrEmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(HrEmployeeInfo HrEmployeeInfo) {
        this.employeeInfo = HrEmployeeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrSpouseInfoLog hrSpouseInfoLog = (HrSpouseInfoLog) o;
        return Objects.equals(id, hrSpouseInfoLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrSpouseInfoLog{" +
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
            ", parentId='" + parentId + "'" +
            ", logStatus='" + logStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", actionDate='" + actionDate + "'" +
            ", actionBy='" + actionBy + "'" +
            ", actionComments='" + actionComments + "'" +
            '}';
    }
}
