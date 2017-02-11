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
 * A SisStudentInfo.
 */
@Entity
@Table(name = "sis_student_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sisstudentinfo")
public class SisStudentInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "stu_picture")
    private byte[] stuPicture;


    @Column(name = "stu_picture_content_type", nullable = false)
    private String stuPictureContentType;

    @Column(name = "institute_name")
    private String instituteName;

    @Column(name = "trade_technology")
    private String TradeTechnology;

//    @ManyToOne
//    private CmsTrade techNature;

//    @Column(name = "tech")
//    private String tech;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "present_address")
    private String presentAddress;

    @Column(name = "village_house")
    private String villageHouse;

    @Column(name = "village_housePer")
    private String villageHousePer;

    @Column(name = "permanent_address")
    private String permanentAddress;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "national_id_no")
    private String nationalIdNo;

    @Column(name = "birth_certificate_no")
    private String birthCertificateNo;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "contact_no_home")
    private String contactNoHome;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "gender")
    private String gender;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "religion")
    private String religion;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "curriculum")
    private String curriculum;

    @Column(name = "application_id")
    private Long applicationId;



    @Column(name = "shift")
    private String shift;

    @Column(name = "quota_reg")
    private String quotaReg;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;


    @ManyToOne
    @JoinColumn(name = "division_present_id")
    private Division divisionPresent;

    @ManyToOne
    @JoinColumn(name = "division_permanent_id")
    private Division divisionPermanent;

    @ManyToOne
    @JoinColumn(name = "district_present_id")
    private District districtPresent;

    @ManyToOne
    @JoinColumn(name = "upazila_present_id")
    private Upazila thanaUpozila;

    @ManyToOne
    @JoinColumn(name = "district_permanent_id")
    private District districtPermanent;

    @ManyToOne
    @JoinColumn(name = "upazila_permanent_id")
    private Upazila thanaUpozilaPermanent;

    @ManyToOne
    @JoinColumn(name = "country_present_id")
    private Country countryPresent;

    @ManyToOne
    @JoinColumn(name = "country_permanent_id")
    private Country countryPermanent;

    @ManyToOne
    @JoinColumn(name = "quota_id")
    private SisQuota quota;

    public Long getId() {
        return id;
    }


//    public CmsTrade getTechNature() {
//        return techNature;
//    }
//
//    public void setTechNature(CmsTrade techNature) {
//        this.techNature = techNature;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getStuPicture() {
        return stuPicture;
    }

    public void setStuPicture(byte[] stuPicture) {
        this.stuPicture = stuPicture;
    }

    public String getStuPictureContentType() {
        return stuPictureContentType;
    }

    public void setStuPictureContentType(String stuPictureContentType) {
        this.stuPictureContentType = stuPictureContentType;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getTradeTechnology() {
        return TradeTechnology;
    }

    public void setTradeTechnology(String TradeTechnology) {
        this.TradeTechnology = TradeTechnology;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getVillageHouse() {
        return villageHouse;
    }

    public void setVillageHouse(String villageHouse) {
        this.villageHouse = villageHouse;
    }

    public String getVillageHousePer() {
        return villageHousePer;
    }

    public void setVillageHousePer(String villageHousePer) {
        this.villageHousePer = villageHousePer;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationalIdNo() {
        return nationalIdNo;
    }

    public void setNationalIdNo(String nationalIdNo) {
        this.nationalIdNo = nationalIdNo;
    }

    public String getBirthCertificateNo() {
        return birthCertificateNo;
    }

    public void setBirthCertificateNo(String birthCertificateNo) {
        this.birthCertificateNo = birthCertificateNo;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
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

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Division getDivisionPresent() {
        return divisionPresent;
    }

    public void setDivisionPresent(Division Division) {
        this.divisionPresent = Division;
    }

    public Division getDivisionPermanent() {
        return divisionPermanent;
    }

    public void setDivisionPermanent(Division Division) {
        this.divisionPermanent = Division;
    }

    public District getDistrictPresent() {
        return districtPresent;
    }

    public void setDistrictPresent(District District) {
        this.districtPresent = District;
    }

    public District getDistrictPermanent() {
        return districtPermanent;
    }

    public void setDistrictPermanent(District District) {
        this.districtPermanent = District;
    }

    public Upazila getThanaUpozila() {
        return thanaUpozila;
    }

    public void setThanaUpozila(Upazila thanaUpozila) {
        this.thanaUpozila = thanaUpozila;
    }

    public Upazila getThanaUpozilaPermanent() {
        return thanaUpozilaPermanent;
    }

    public void setThanaUpozilaPermanent(Upazila thanaUpozilaPermanent) {
        this.thanaUpozilaPermanent = thanaUpozilaPermanent;
    }

    public Country getCountryPresent() {
        return countryPresent;
    }

    public void setCountryPresent(Country Country) {
        this.countryPresent = Country;
    }

    public Country getCountryPermanent() {
        return countryPermanent;
    }

    public void setCountryPermanent(Country Country) {
        this.countryPermanent = Country;
    }

    public SisQuota getQuota() {
        return quota;
    }

    public void setQuota(SisQuota SisQuota) {
        this.quota = SisQuota;
    }


    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getQuotaReg() {
        return quotaReg;
    }

    public void setQuotaReg(String quotaReg) {
        this.quotaReg = quotaReg;
    }

//    public String getTech() {
//        return tech;
//    }
//
//    public void setTech(String tech) {
//        this.tech = tech;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SisStudentInfo sisStudentInfo = (SisStudentInfo) o;

        if ( ! Objects.equals(id, sisStudentInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SisStudentInfo{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", stuPicture='" + stuPicture + "'" +
            ", stuPictureContentType='" + stuPictureContentType + "'" +
            ", instituteName='" + instituteName + "'" +
            ", TradeTechnology='" + TradeTechnology + "'" +
            ", studentName='" + studentName + "'" +
            ", fatherName='" + fatherName + "'" +
            ", motherName='" + motherName + "'" +
            ", dateOfBirth='" + dateOfBirth + "'" +
            ", presentAddress='" + presentAddress + "'" +
            ", permanentAddress='" + permanentAddress + "'" +
            ", nationality='" + nationality + "'" +
            ", nationalIdNo='" + nationalIdNo + "'" +
            ", birthCertificateNo='" + birthCertificateNo + "'" +
            ", mobileNo='" + mobileNo + "'" +
            ", contactNoHome='" + contactNoHome + "'" +
            ", emailAddress='" + emailAddress + "'" +
            ", gender='" + gender + "'" +
            ", maritalStatus='" + maritalStatus + "'" +
            ", bloodGroup='" + bloodGroup + "'" +
            ", religion='" + religion + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            ", curriculum='" + curriculum + "'" +
            ", applicationId='" + applicationId + "'" +
            '}';
    }
}
