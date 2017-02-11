package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SisStudentReg.
 */
@Entity
@Table(name = "sis_student_reg")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sisstudentreg")
public class SisStudentReg implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "inst_category")
    private String instCategory;

    @Column(name = "institute_name")
    private String instituteName;

    @Column(name = "curriculum")
    private String curriculum;

    @Column(name = "trade_technology")
    private String tradeTechnology;

    @Column(name = "subject1")
    private String subject1;

    @Column(name = "subject2")
    private String subject2;

    @Column(name = "subject3")
    private String subject3;

    @Column(name = "subject4")
    private String subject4;

    @Column(name = "subject5")
    private String subject5;

    @Column(name = "optional")
    private String optional;

    @Column(name = "shift")
    private String shift;

    @Column(name = "semester")
    private String semester;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "religion")
    private String religion;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "quota")
    private String quota;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "contact_no_home")
    private String contactNoHome;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "present_address")
    private String presentAddress;

    @Column(name = "permanent_address")
    private String permanentAddress;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "marital_status")
    private String maritalStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getInstCategory() {
        return instCategory;
    }

    public void setInstCategory(String instCategory) {
        this.instCategory = instCategory;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

   /* public String getTradeTechnology() {
        return TradeTechnology;
    }

    public void setTradeTechnology(String TradeTechnology) {
        this.TradeTechnology = TradeTechnology;
    }*/

    public String getTradeTechnology() {
        return tradeTechnology;
    }

    public void setTradeTechnology(String tradeTechnology) {
        this.tradeTechnology = tradeTechnology;
    }

    public String getSubject1() {
        return subject1;
    }

    public void setSubject1(String subject1) {
        this.subject1 = subject1;
    }

    public String getSubject2() {
        return subject2;
    }

    public void setSubject2(String subject2) {
        this.subject2 = subject2;
    }

    public String getSubject3() {
        return subject3;
    }

    public void setSubject3(String subject3) {
        this.subject3 = subject3;
    }

    public String getSubject4() {
        return subject4;
    }

    public void setSubject4(String subject4) {
        this.subject4 = subject4;
    }

    public String getSubject5() {
        return subject5;
    }

    public void setSubject5(String subject5) {
        this.subject5 = subject5;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getContactNoHome() {
        return contactNoHome;
    }

    public void setContactNoHome(String contactNoHome) {
        this.contactNoHome = contactNoHome;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
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

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SisStudentReg sisStudentReg = (SisStudentReg) o;

        if ( ! Objects.equals(id, sisStudentReg.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SisStudentReg{" +
            "id=" + id +
            ", applicationId='" + applicationId + "'" +
            ", instCategory='" + instCategory + "'" +
            ", instituteName='" + instituteName + "'" +
            ", curriculum='" + curriculum + "'" +
            ", tradeTechnology='" + tradeTechnology + "'" +
            ", subject1='" + subject1 + "'" +
            ", subject2='" + subject2 + "'" +
            ", subject3='" + subject3 + "'" +
            ", subject4='" + subject4 + "'" +
            ", subject5='" + subject5 + "'" +
            ", optional='" + optional + "'" +
            ", shift='" + shift + "'" +
            ", semester='" + semester + "'" +
            ", studentName='" + studentName + "'" +
            ", fatherName='" + fatherName + "'" +
            ", motherName='" + motherName + "'" +
            ", dateOfBirth='" + dateOfBirth + "'" +
            ", gender='" + gender + "'" +
            ", religion='" + religion + "'" +
            ", bloodGroup='" + bloodGroup + "'" +
            ", quota='" + quota + "'" +
            ", nationality='" + nationality + "'" +
            ", mobileNo='" + mobileNo + "'" +
            ", contactNoHome='" + contactNoHome + "'" +
            ", emailAddress='" + emailAddress + "'" +
            ", presentAddress='" + presentAddress + "'" +
            ", permanentAddress='" + permanentAddress + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            ", maritalStatus='" + maritalStatus + "'" +
            '}';
    }
}
