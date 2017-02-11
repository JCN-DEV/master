package gov.step.app.domain;

import gov.step.app.domain.enumeration.jobQuota;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import gov.step.app.domain.enumeration.EmpType;

import gov.step.app.domain.enumeration.maritalStatus;

import gov.step.app.domain.enumeration.bloodGroup;

/**
 * A InstEmployee.
 */
@Entity
@Table(name = "inst_employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instemployee")
public class InstEmployee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "contact_no", nullable = true)
    private String contactNo;

    @Column(name = "alt_contactNo", nullable = true)
    private String altContactNo;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private EmpType category;

    @Column(name = "gender")
    private String gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private maritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private bloodGroup bloodGroup;

    @Column(name = "tin")
    private String tin;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_name")
    private String imageName;


    @Column(name = "image_content_type", nullable = false)
    private String imageContentType;
    @Column(name = "nationality")
    private String nationality;

    @Column(name = "nid")
    private String nid;

    @Lob
    @Column(name = "nid_image")
    private byte[] nidImage;

    @Column(name = "nid_image_name")
    private String nidImageName;


    @Column(name = "nid_image_content_type", nullable = false)
    private String nidImageContentType;

    @Column(name = "birth_cert_no")
    private String birthCertNo;

    @Column(name = "birth_cert_no_name")
    private String birthCertNoName;

    @Lob
    @Column(name = "birth_cert_image")
    private byte[] birthCertImage;


    @Column(name = "birth_cert_image_content_type", nullable = false)
    private String birthCertImageContentType;
    @Column(name = "con_per_name")
    private String conPerName;

    @Column(name = "con_per_mobile")
    private String conPerMobile;

    @Column(name = "con_per_address")
    private String conPerAddress;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "mpo_app_status")
    private  Integer mpoAppStatus = 0;

    @Column(name = "timescale_app_status")
    private  Integer timescaleAppStatus= 0;

    @Column(name = "bed_app_status")
    private  Integer bEDAppStatus= 0;

    @Column(name = "ap_app_status")
    private  Integer aPAppStatus= 0;

    @Column(name = "principle_app_status")
    private  Integer principleAppStatus = 0;

    @Column(name = "name_cncl_app_status")
    private  Integer nameCnclAppStatus = 0;

    @Column(name = "index_no", unique=true)
    private  String indexNo;

    @Column(name = "withheld_ammount")
    private  Double withheldAmount;

    @Column(name = "withheld_start_date")
    private  LocalDate withheldStartDate;

    @Column(name = "withheld_end_date")
    private  LocalDate withheldEndDate;

    @Column(name = "pay_scale_changed_date")
    private  LocalDate payScaleChangedDate;

    @Column(name = "withheld_active")
    private  Boolean withheldActive;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "teacher_level", nullable = true)
    private String level;

    @Column(name = "teacher_quota")
    private String quota;

    @Column(name = "apply_date", nullable = true)
    private LocalDate applyDate;

    @Column(name = "mpo_active_date")
    private LocalDate mpoActiveDate;


    /*@ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "inst_employee_pay_scale",
               joinColumns = @JoinColumn(name="inst_employees_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="pay_scales_id", referencedColumnName="ID"))
    private Set<PayScale> payScales = new HashSet<>();*/



    @ManyToOne
    @JoinColumn(name = "religion_id")
    private Religion religion;

    @Column(name = "status")
    private  Integer status = 0;

    @Column(name = "remarks", nullable = false)
    private String remarks;

    @Column(name = "type_of_position")
    private String typeOfPosition;

    @Column(name = "registered_Certificate_Sub")
    private String registeredCertificateSubject;

    @Column(name = "registration_exam_year")
    private LocalDate registrationExamYear;

    //new field for merging with hrm
    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "registered_Certificate_no")
    private String registeredCertificateNo;

    @Column(name = "mpo_active")
    private Boolean mpoActive;

    @Column(name = "is_jp_admin")
    private Boolean isJPAdmin;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_quota")
    private jobQuota jobQuota;

    @Lob
    @Column(name = "quota_cert")
    private byte[] quotaCert;

    @Column(name = "quota_cert_content_type")
    private String quotaCertContentType;

    @Column(name = "quota_cert_name")
    private String quotaCertName;


    @ManyToOne
    @JoinColumn(name = "iis_course_info_id")
    private IisCourseInfo iisCourseInfo;

    @ManyToOne
    @JoinColumn(name = "inst_level_id")
    private InstLevel instLevel;


    @ManyToOne
    @JoinColumn(name = "cms_sub_assign_id")
    private CmsSubAssign cmsSubAssign;


    @ManyToOne
    @JoinColumn(name = "pay_scale_id")
    private PayScale payScale;

    //TODO: it should remove. this is using only for previous designation which is using in general teacher module
    @ManyToOne
    @JoinColumn(name = "inst_empl_designation_id")
    private InstEmplDesignation instEmplDesignation;

    //common for teacher and hrm
    @ManyToOne
    @JoinColumn(name = "designation_setup_id")
    private HrDesignationSetup designationSetup;

    //common for teacher and hrm
    @ManyToOne
    @JoinColumn(name = "employee_type_id")
    private HrEmplTypeInfo employeeType;


    @ManyToOne
    @JoinColumn(name = "course_sub_id")
    private CourseSub courseSub;

    @PrePersist
    protected void onCreate() {
        //this.setSubmitedDate(AttachmentUtil.getTodayDateWithTime());
        this.setMpoAppStatus(0);

    }

    // fields not using
    //courseSub
    //iisCourseInfo


    public gov.step.app.domain.enumeration.jobQuota getJobQuota() {
        return jobQuota;
    }

    public void setJobQuota(gov.step.app.domain.enumeration.jobQuota jobQuota) {
        this.jobQuota = jobQuota;
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

    public void setQuotaCertContentType(String quotaCertContentType) {
        this.quotaCertContentType = quotaCertContentType;
    }

    public String getQuotaCertName() {
        return quotaCertName;
    }

    public void setQuotaCertName(String quotaCertName) {
        this.quotaCertName = quotaCertName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAltContactNo() {
        return altContactNo;
    }

    public void setAltContactNo(String altContactNo) {
        this.altContactNo = altContactNo;
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public EmpType getCategory() {
        return category;
    }

    public void setCategory(EmpType category) {
        this.category = category;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNidImageName() {
        return nidImageName;
    }

    public void setNidImageName(String nidImageName) {
        this.nidImageName = nidImageName;
    }

    public byte[] getNidImage() {
        return nidImage;
    }

    public void setNidImage(byte[] nidImage) {
        this.nidImage = nidImage;
    }

    public String getNidImageContentType() {
        return nidImageContentType;
    }

    public void setNidImageContentType(String nidImageContentType) {
        this.nidImageContentType = nidImageContentType;
    }

    public String getBirthCertNo() {
        return birthCertNo;
    }

    public void setBirthCertNo(String birthCertNo) {
        this.birthCertNo = birthCertNo;
    }

    public String getBirthCertNoName() {
        return birthCertNoName;
    }

    public void setBirthCertNoName(String birthCertNoName) {
        this.birthCertNoName = birthCertNoName;
    }

    public byte[] getBirthCertImage() {
        return birthCertImage;
    }

    public void setBirthCertImage(byte[] birthCertImage) {
        this.birthCertImage = birthCertImage;
    }

    public String getBirthCertImageContentType() {
        return birthCertImageContentType;
    }

    public void setBirthCertImageContentType(String birthCertImageContentType) {
        this.birthCertImageContentType = birthCertImageContentType;
    }

    public String getConPerName() {
        return conPerName;
    }

    public void setConPerName(String conPerName) {
        this.conPerName = conPerName;
    }

    public String getConPerMobile() {
        return conPerMobile;
    }

    public void setConPerMobile(String conPerMobile) {
        this.conPerMobile = conPerMobile;
    }

    public String getConPerAddress() {
        return conPerAddress;
    }

    public void setConPerAddress(String conPerAddress) {
        this.conPerAddress = conPerAddress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMpoAppStatus() {
        return mpoAppStatus;
    }

    public void setMpoAppStatus(Integer mpoAppStatus) {
        this.mpoAppStatus = mpoAppStatus;
    }

    public Integer getTimescaleAppStatus() {
        return timescaleAppStatus;
    }

    public void setTimescaleAppStatus(Integer timescaleAppStatus) {
        this.timescaleAppStatus = timescaleAppStatus;
    }

    public Integer getbEDAppStatus() {
        return bEDAppStatus;
    }

    public void setbEDAppStatus(Integer bEDAppStatus) {
        this.bEDAppStatus = bEDAppStatus;
    }

    public Integer getaPAppStatus() {
        return aPAppStatus;
    }

    public void setaPAppStatus(Integer aPAppStatus) {
        this.aPAppStatus = aPAppStatus;
    }

    public Integer getPrincipleAppStatus() {
        return principleAppStatus;
    }

    public void setPrincipleAppStatus(Integer principleAppStatus) {
        this.principleAppStatus = principleAppStatus;
    }

    public Integer getNameCnclAppStatus() {
        return nameCnclAppStatus;
    }

    public void setNameCnclAppStatus(Integer nameCnclAppStatus) {
        this.nameCnclAppStatus = nameCnclAppStatus;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public Double getWithheldAmount() {
        return withheldAmount;
    }

    public void setWithheldAmount(Double withheldAmount) {
        this.withheldAmount = withheldAmount;
    }

    public LocalDate getWithheldStartDate() {
        return withheldStartDate;
    }

    public void setWithheldStartDate(LocalDate withheldStartDate) {
        this.withheldStartDate = withheldStartDate;
    }

    public LocalDate getWithheldEndDate() {
        return withheldEndDate;
    }

    public void setWithheldEndDate(LocalDate withheldEndDate) {
        this.withheldEndDate = withheldEndDate;
    }

    public LocalDate getPayScaleChangedDate() {
        return payScaleChangedDate;
    }

    public void setPayScaleChangedDate(LocalDate payScaleChangedDate) {
        this.payScaleChangedDate = payScaleChangedDate;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public HrEmplTypeInfo getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(HrEmplTypeInfo employeeType) {
        this.employeeType = employeeType;
    }

    public Boolean getWithheldActive() {
        return withheldActive;
    }

    public void setWithheldActive(Boolean withheldActive) {
        this.withheldActive = withheldActive;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public PayScale getPayScale() {
        return payScale;
    }

    public void setPayScale(PayScale payScale) {
        this.payScale = payScale;
    }

    public InstEmplDesignation getInstEmplDesignation() {
        return instEmplDesignation;
    }

    public void setInstEmplDesignation(InstEmplDesignation instEmplDesignation) {
        this.instEmplDesignation = instEmplDesignation;
    }

    public HrDesignationSetup getDesignationSetup() {
        return designationSetup;
    }

    public void setDesignationSetup(HrDesignationSetup designationSetup) {
        this.designationSetup = designationSetup;
    }

    public CourseSub getCourseSub() {
        return courseSub;
    }

    public void setCourseSub(CourseSub courseSub) {
        this.courseSub = courseSub;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTypeOfPosition() {
        return typeOfPosition;
    }

    public void setTypeOfPosition(String typeOfPosition) {
        this.typeOfPosition = typeOfPosition;
    }

    public String getRegisteredCertificateSubject() {
        return registeredCertificateSubject;
    }

    public void setRegisteredCertificateSubject(String registeredCertificateSubject) {
        this.registeredCertificateSubject = registeredCertificateSubject;
    }

    public LocalDate getRegistrationExamYear() {
        return registrationExamYear;
    }

    public LocalDate getMpoActiveDate() {
        return mpoActiveDate;
    }

    public void setMpoActiveDate(LocalDate mpoActiveDate) {
        this.mpoActiveDate = mpoActiveDate;
    }

    public void setRegistrationExamYear(LocalDate registrationExamYear) {
        this.registrationExamYear = registrationExamYear;
    }

    public String getRegisteredCertificateNo() {
        return registeredCertificateNo;
    }

    public void setRegisteredCertificateNo(String registeredCertificateNo) {
        this.registeredCertificateNo = registeredCertificateNo;
    }

    public Boolean getMpoActive() {
        return mpoActive;
    }

    public void setMpoActive(Boolean mpoActive) {
        this.mpoActive = mpoActive;
    }

    public Boolean getIsJPAdmin() {
        return isJPAdmin;
    }

    public void setIsJPAdmin(Boolean isJPAdmin) {
        this.isJPAdmin = isJPAdmin;
    }
    public LocalDate getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public IisCourseInfo getIisCourseInfo() {
        return iisCourseInfo;
    }

    public void setIisCourseInfo(IisCourseInfo iisCourseInfo) {
        this.iisCourseInfo = iisCourseInfo;
    }

    public InstLevel getInstLevel() {
        return instLevel;
    }

    public void setInstLevel(InstLevel instLevel) {
        this.instLevel = instLevel;
    }

    public CmsSubAssign getCmsSubAssign() {
        return cmsSubAssign;
    }

    public void setCmsSubAssign(CmsSubAssign cmsSubAssign) {
        this.cmsSubAssign = cmsSubAssign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstEmployee instEmployee = (InstEmployee) o;

        if ( ! Objects.equals(id, instEmployee.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstEmployee{" +
            "mpoActive=" + mpoActive +
            ", registeredCertificateNo='" + registeredCertificateNo + '\'' +
            ", registrationExamYear=" + registrationExamYear +
            ", registeredCertificateSubject='" + registeredCertificateSubject + '\'' +
            ", typeOfPosition='" + typeOfPosition + '\'' +
            ", remarks='" + remarks + '\'' +
            ", status=" + status +
            ", religion=" + religion +
            ", courseSub=" + courseSub +
            ", instEmplDesignation=" + instEmplDesignation +
            ", payScale=" + payScale +
            ", quota='" + quota + '\'' +
            ", level='" + level + '\'' +
            ", user=" + user +
            ", institute=" + institute +
            ", indexNo=" + indexNo +
            ", mpoAppStatus=" + mpoAppStatus +
            ", code='" + code + '\'' +
            ", conPerAddress='" + conPerAddress + '\'' +
            ", conPerMobile=" + conPerMobile +
            ", conPerName='" + conPerName + '\'' +
            ", birthCertImageContentType='" + birthCertImageContentType + '\'' +
            ", birthCertNoName='" + birthCertNoName + '\'' +
            ", birthCertNo='" + birthCertNo + '\'' +
            ", nidImageContentType='" + nidImageContentType + '\'' +
            ", nidImageName='" + nidImageName + '\'' +
            ", nid='" + nid + '\'' +
            ", nationality='" + nationality + '\'' +
            ", imageContentType='" + imageContentType + '\'' +
            ", imageName='" + imageName + '\'' +
            ", tin='" + tin + '\'' +
            ", bloodGroup=" + bloodGroup +
            ", maritalStatus=" + maritalStatus +
            ", gender='" + gender + '\'' +
            ", category=" + category +
            ", dob=" + dob +
            ", motherName='" + motherName + '\'' +
            ", fatherName='" + fatherName + '\'' +
            ", contactNo='" + contactNo + '\'' +
            ", email='" + email + '\'' +
            ", name='" + name + '\'' +
            ", id=" + id +
            '}';
    }
}
