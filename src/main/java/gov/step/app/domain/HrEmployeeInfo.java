package gov.step.app.domain;

import gov.step.app.domain.enumeration.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import org.hibernate.annotations.Nationalized;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HrEmployeeInfo.
 */
@Entity
@Table(name = "hr_employee_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hremployeeinfo")
public class HrEmployeeInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @Nationalized
    @Column(name = "full_name_bn")
    private String fullNameBn;

    @Column(name = "father_name")
    private String fatherName;

    @Nationalized
    @Column(name = "father_name_bn")
    private String fatherNameBn;

    @Column(name = "mother_name")
    private String motherName;

    @Nationalized
    @Column(name = "mother_name_bn")
    private String motherNameBn;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotNull
    @Column(name = "apointment_go_date", nullable = false)
    private LocalDate apointmentGoDate;

    @Column(name = "present_id")
    private String presentId;

    @Size(min = 13, max = 17)
    @Column(name = "national_id", length = 17)
    private String nationalId;

    @NotNull
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @NotNull
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "any_disease")
    private String anyDisease;

    @Column(name = "officer_stuff")
    private String officerStuff;

    @Column(name = "tin_number")
    private String tinNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private maritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private bloodGroup bloodGroup;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "quota")
    private jobQuota quota;

    @Column(name = "birth_certificate_no")
    private String birthCertificateNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "religion")
    private Religions religion;

    @Lob
    @Column(name = "emp_photo")
    private byte[] empPhoto;

    @Column(name = "emp_photo_content_type")
    private String empPhotoContentType;

    @Column(name = "image_name")
    private String imageName;

    @Lob
    @Column(name = "quota_cert")
    private byte[] quotaCert;

    @Column(name = "quota_cert_content_type")
    private String quotaCertContentType;

    @Column(name = "quota_cert_name")
    private String quotaCertName;

    @Column(name = "employee_id")
    private String employeeId;

    @NotNull
    @Column(name = "date_of_joining", nullable = false)
    private LocalDate dateOfJoining;

    @Column(name = "prl_date")
    private LocalDate prlDate;

    @Column(name = "retirement_date")
    private LocalDate retirementDate;

    @Column(name = "last_working_day")
    private LocalDate lastWorkingDay;

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
    @JoinColumn(name = "department_info_id")
    private HrDepartmentSetup departmentInfo;

    @ManyToOne
    @JoinColumn(name = "designation_info_id")
    private HrDesignationSetup designationInfo;

    @Column(name = "organization_type")
    private String organizationType;

    @ManyToOne
    @JoinColumn(name = "work_area_id")
    private MiscTypeSetup workArea;

    @ManyToOne
    @JoinColumn(name = "work_area_dtl_id")
    private HrEmpWorkAreaDtlInfo workAreaDtl;

    @ManyToOne
    @JoinColumn(name = "encadrement_id")
    private MiscTypeSetup encadrement;

    @ManyToOne
    @JoinColumn(name = "inst_category_id")
    private InstCategory instCategory;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type")
    private designationType employeeType;

    @ManyToOne
    @JoinColumn(name = "grade_info_id")
    private HrGradeSetup gradeInfo;

    @ManyToOne
    @JoinColumn(name = "employement_type_id")
    private HrEmplTypeInfo employementType;

    @ManyToOne
    @JoinColumn(name = "inst_employee_id")
    private InstEmployee instEmployee;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "active_account")
    private Boolean activeAccount = true;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameBn() {
        return fullNameBn;
    }

    public void setFullNameBn(String fullNameBn) {
        this.fullNameBn = fullNameBn;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherNameBn() {
        return fatherNameBn;
    }

    public void setFatherNameBn(String fatherNameBn) {
        this.fatherNameBn = fatherNameBn;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherNameBn() {
        return motherNameBn;
    }

    public void setMotherNameBn(String motherNameBn) {
        this.motherNameBn = motherNameBn;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getApointmentGoDate() {
        return apointmentGoDate;
    }

    public void setApointmentGoDate(LocalDate apointmentGoDate) {
        this.apointmentGoDate = apointmentGoDate;
    }

    public String getPresentId() {
        return presentId;
    }

    public void setPresentId(String presentId) {
        this.presentId = presentId;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getAnyDisease() {
        return anyDisease;
    }

    public void setAnyDisease(String anyDisease) {
        this.anyDisease = anyDisease;
    }

    public String getOfficerStuff() {
        return officerStuff;
    }

    public void setOfficerStuff(String officerStuff) {
        this.officerStuff = officerStuff;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public maritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(maritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public bloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(bloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public jobQuota getQuota() {
        return quota;
    }

    public void setQuota(jobQuota quota) {
        this.quota = quota;
    }

    public String getBirthCertificateNo() {
        return birthCertificateNo;
    }

    public void setBirthCertificateNo(String birthCertificateNo) {
        this.birthCertificateNo = birthCertificateNo;
    }

    public Religions getReligion() {
        return religion;
    }

    public void setReligion(Religions religion) {
        this.religion = religion;
    }

    public byte[] getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(byte[] empPhoto) {
        this.empPhoto = empPhoto;
    }

    public String getEmpPhotoContentType() {
        return empPhotoContentType;
    }

    public void setEmpPhotoContentType(String empPhotoContentType) {
        this.empPhotoContentType = empPhotoContentType;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getQuotaCert() {
        return quotaCert;
    }

    public void setQuotaCert(byte[] quotaCert) {
        this.quotaCert = quotaCert;
    }

    public String getQuotaCertContentType() {
        return quotaCertContentType;
    }

    public void setQuotaCertContentType(String quotaCertContentType) {this.quotaCertContentType = quotaCertContentType;}

    public String getQuotaCertName() {
        return quotaCertName;
    }

    public void setQuotaCertName(String quotaCertName) {
        this.quotaCertName = quotaCertName;
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

    public HrDepartmentSetup getDepartmentInfo() {
        return departmentInfo;
    }

    public void setDepartmentInfo(HrDepartmentSetup HrDepartmentSetup) {
        this.departmentInfo = HrDepartmentSetup;
    }

    public HrDesignationSetup getDesignationInfo() {
        return designationInfo;
    }

    public void setDesignationInfo(HrDesignationSetup HrDesignationSetup) {
        this.designationInfo = HrDesignationSetup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User User) {
        this.user = User;
    }

    public Long getLogId() { return logId;}

    public void setLogId(Long logId) {this.logId = logId;}

    public Long getLogStatus() {return logStatus;}

    public void setLogStatus(Long logStatus) {this.logStatus = logStatus;}

    public String getLogComments() {return logComments;}

    public void setLogComments(String logComments) {this.logComments = logComments;}

    public MiscTypeSetup getWorkArea() {return workArea;}

    public void setWorkArea(MiscTypeSetup workArea) {this.workArea = workArea;}

    public String getEmployeeId() {return employeeId;}

    public void setEmployeeId(String employeeId) {this.employeeId = employeeId;}

    public LocalDate getDateOfJoining() {return dateOfJoining;}

    public void setDateOfJoining(LocalDate dateOfJoining) {this.dateOfJoining = dateOfJoining;}

    public LocalDate getPrlDate() {return prlDate;}

    public void setPrlDate(LocalDate prlDate) {this.prlDate = prlDate;}

    public HrGradeSetup getGradeInfo() {return gradeInfo;}

    public void setGradeInfo(HrGradeSetup gradeInfo) {this.gradeInfo = gradeInfo;}

    public HrEmplTypeInfo getEmployementType() {return employementType;}

    public void setEmployementType(HrEmplTypeInfo employementType) {this.employementType = employementType;}

    public HrEmpWorkAreaDtlInfo getWorkAreaDtl() {return workAreaDtl;}

    public void setWorkAreaDtl(HrEmpWorkAreaDtlInfo workAreaDtl) {this.workAreaDtl = workAreaDtl;}

    public String getOrganizationType() {return organizationType;}

    public void setOrganizationType(String organizationType) {this.organizationType = organizationType;}

    public InstCategory getInstCategory() {return instCategory;}

    public void setInstCategory(InstCategory instCategory) {this.instCategory = instCategory;}

    public Institute getInstitute() {return institute;}

    public void setInstitute(Institute institute) {this.institute = institute;}

    public MiscTypeSetup getEncadrement() {return encadrement;}

    public void setEncadrement(MiscTypeSetup encadrement) {this.encadrement = encadrement;}

    public LocalDate getRetirementDate() {return retirementDate;}

    public void setRetirementDate(LocalDate retirementDate) {this.retirementDate = retirementDate;}

    public designationType getEmployeeType() {return employeeType;}

    public void setEmployeeType(designationType employeeType) {this.employeeType = employeeType;}

    public InstEmployee getInstEmployee() {return instEmployee;}

    public void setInstEmployee(InstEmployee instEmployee) {this.instEmployee = instEmployee;}

    public Boolean getActiveAccount() {return activeAccount;}

    public void setActiveAccount(Boolean activeAccount) {this.activeAccount = activeAccount;}

    public LocalDate getLastWorkingDay() {return lastWorkingDay;}

    public void setLastWorkingDay(LocalDate lastWorkingDay) {this.lastWorkingDay = lastWorkingDay;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEmployeeInfo hrEmployeeInfo = (HrEmployeeInfo) o;
        return Objects.equals(id, hrEmployeeInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmployeeInfo{" +
            "id=" + id +
            ", empId='" + employeeId + "'" +
            ", fullName='" + fullName + "'" +
            ", fullNameBn='" + fullNameBn + "'" +
            ", fatherName='" + fatherName + "'" +
            ", fatherNameBn='" + fatherNameBn + "'" +
            ", motherName='" + motherName + "'" +
            ", motherNameBn='" + motherNameBn + "'" +
            ", birthDate='" + birthDate + "'" +
            ", prlDate='" + prlDate + "'" +
            ", retirementDate='" + retirementDate + "'" +
            ", lastWorkingDay='" + lastWorkingDay + "'" +
            ", apointmentGoDate='" + apointmentGoDate + "'" +
            ", presentId='" + presentId + "'" +
            ", nationalId='" + nationalId + "'" +
            ", emailAddress='" + emailAddress + "'" +
            ", mobileNumber='" + mobileNumber + "'" +
            ", gender='" + gender + "'" +
            ", birthPlace='" + birthPlace + "'" +
            ", anyDisease='" + anyDisease + "'" +
            ", officerStuff='" + officerStuff + "'" +
            ", tinNumber='" + tinNumber + "'" +
            ", maritalStatus='" + maritalStatus + "'" +
            ", bloodGroup='" + bloodGroup + "'" +
            ", nationality='" + nationality + "'" +
            ", quota='" + quota + "'" +
            ", birthCertificateNo='" + birthCertificateNo + "'" +
            ", religion='" + religion + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
