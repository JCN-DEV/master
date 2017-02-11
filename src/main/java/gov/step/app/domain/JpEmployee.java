package gov.step.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import gov.step.app.domain.enumeration.EmployeeGender;

import gov.step.app.domain.enumeration.maritialStatus;

import gov.step.app.domain.enumeration.Nationality;

import gov.step.app.domain.enumeration.EmployeeAvailability;

/**
 * A JpEmployee.
 */
@Entity
@Table(name = "jp_employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jpemployee")
public class JpEmployee implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="jp_employee_seq")
    @SequenceGenerator(name="jp_employee_seq", sequenceName="jp_employee_seq")
    private Long id;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @Size(max = 50)
    @Column(name = "father_name", length = 50)
    private String fatherName;

    @Size(max = 50)
    @Column(name = "mother_name", length = 50)
    private String motherName;

    @Column(name = "present_address")
    private String presentAddress;

    @Column(name = "permanent_address")
    private String permanentAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private EmployeeGender gender;

    @Column(name = "dob")
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "maritial_status")
    private maritialStatus maritialStatus;

    @Size(max = 50)
    @Column(name = "nid_no", length = 50)
    private String nidNo;


    @Column(name = "nationality")
    private String nationality;

    @Column(name = "current_location")
    private String currentLocation;

    @Column(name = "mailing_address")
    private String mailingAddress;

    @Size(max = 30)
    @Column(name = "home_phone", length = 30)
    private String homePhone;

    @Size(max = 30)
    @Column(name = "mobile_no", length = 30)
    private String mobileNo;

    @Size(max = 30)
    @Column(name = "office_phone", length = 30)
    private String officePhone;

    @Size(max = 30)
    @Column(name = "email", length = 30)
    private String email;

    @Size(max = 30)
    @Column(name = "alternative_mail", length = 30)
    private String alternativeMail;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_name")
    private String pictureName;

    @Column(name = "picture_type")
    private String pictureType;

    @Lob
    @Column(name = "cv")
    private byte[] cv;

    @Column(name = "cv_name")
    private String cvName;

    @Column(name = "cv_content_type")
    private String cvContentType;

    @Size(max = 200)
    @Column(name = "objective", length = 200)
    private String objective;

    @Column(name = "present_salary", precision=10, scale=2)
    private BigDecimal presentSalary;

    @Column(name = "expected_salary", precision=10, scale=2)
    private BigDecimal expectedSalary;

    @Column(name = "total_experience", precision=10, scale=2 )
    private Double totalExperience = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "availibility_type")
    private EmployeeAvailability availibilityType;


    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "religion_id")
    private Religion religion;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @OneToMany(mappedBy = "jpEmployee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JpAcademicQualification> jpAcademicQualifications = new HashSet<>();

    @OneToMany(mappedBy = "jpEmployee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JpEmployeeExperience> jpEmployeeExperiences = new HashSet<>();

    @OneToMany(mappedBy = "jpEmployee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JpEmploymentHistory> jpEmploymentHistorys = new HashSet<>();

    @OneToMany(mappedBy = "jpEmployee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JpEmployeeReference> jpEmployeeReferences = new HashSet<>();

    @OneToMany(mappedBy = "jpEmployee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JpLanguageProficiency> jpLanguageProficiencys = new HashSet<>();

    @OneToMany(mappedBy = "jpEmployee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JpEmployeeTraining> jpEmployeeTrainings = new HashSet<>();

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

    public EmployeeGender getGender() {
        return gender;
    }

    public void setGender(EmployeeGender gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public maritialStatus getMaritialStatus() {
        return maritialStatus;
    }

    public void setMaritialStatus(maritialStatus maritialStatus) {
        this.maritialStatus = maritialStatus;
    }

    public String getNidNo() {
        return nidNo;
    }

    public void setNidNo(String nidNo) {
        this.nidNo = nidNo;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlternativeMail() {
        return alternativeMail;
    }

    public void setAlternativeMail(String alternativeMail) {
        this.alternativeMail = alternativeMail;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public byte[] getCv() {
        return cv;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public String getCvName() {
        return cvName;
    }

    public void setCvName(String cvName) {
        this.cvName = cvName;
    }

    public String getCvContentType() {
        return cvContentType;
    }

    public void setCvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public BigDecimal getPresentSalary() {
        return presentSalary;
    }

    public void setPresentSalary(BigDecimal presentSalary) {
        this.presentSalary = presentSalary;
    }

    public BigDecimal getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(BigDecimal expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public Double getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(Double totalExperience) {
        this.totalExperience = totalExperience;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public EmployeeAvailability getAvailibilityType() {
        return availibilityType;
    }

    public void setAvailibilityType(EmployeeAvailability availibilityType) {
        this.availibilityType = availibilityType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Religion getReligion() {
        return religion;
    }


    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public Set<JpAcademicQualification> getJpAcademicQualifications() {
        return jpAcademicQualifications;
    }

    public void setJpAcademicQualifications(Set<JpAcademicQualification> jpAcademicQualifications) {
        this.jpAcademicQualifications = jpAcademicQualifications;
    }

    public Set<JpEmployeeExperience> getJpEmployeeExperiences() {
        return jpEmployeeExperiences;
    }

    public void setJpEmployeeExperiences(Set<JpEmployeeExperience> jpEmployeeExperiences) {
        this.jpEmployeeExperiences = jpEmployeeExperiences;
    }

    public Set<JpEmploymentHistory> getJpEmploymentHistorys() {
        return jpEmploymentHistorys;
    }

    public void setJpEmploymentHistorys(Set<JpEmploymentHistory> jpEmploymentHistorys) {
        this.jpEmploymentHistorys = jpEmploymentHistorys;
    }

    public Set<JpEmployeeReference> getJpEmployeeReferences() {
        return jpEmployeeReferences;
    }

    public void setJpEmployeeReferences(Set<JpEmployeeReference> jpEmployeeReferences) {
        this.jpEmployeeReferences = jpEmployeeReferences;
    }

    public Set<JpLanguageProficiency> getJpLanguageProficiencys() {
        return jpLanguageProficiencys;
    }

    public void setJpLanguageProficiencys(Set<JpLanguageProficiency> jpLanguageProficiencys) {
        this.jpLanguageProficiencys = jpLanguageProficiencys;
    }

    public Set<JpEmployeeTraining> getJpEmployeeTrainings() {
        return jpEmployeeTrainings;
    }

    public void setJpEmployeeTrainings(Set<JpEmployeeTraining> jpEmployeeTrainings) {
        this.jpEmployeeTrainings = jpEmployeeTrainings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JpEmployee jpEmployee = (JpEmployee) o;

        if ( ! Objects.equals(id, jpEmployee.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JpEmployee{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", fatherName='" + fatherName + "'" +
            ", motherName='" + motherName + "'" +
            ", presentAddress='" + presentAddress + "'" +
            ", permanentAddress='" + permanentAddress + "'" +
            ", gender='" + gender + "'" +
            ", dob='" + dob + "'" +
            ", maritialStatus='" + maritialStatus + "'" +
            ", nidNo='" + nidNo + "'" +
            ", nationality='" + nationality + "'" +
            ", currentLocation='" + currentLocation + "'" +
            ", mailingAddress='" + mailingAddress + "'" +
            ", homePhone='" + homePhone + "'" +
            ", mobileNo='" + mobileNo + "'" +
            ", officePhone='" + officePhone + "'" +
            ", email='" + email + "'" +
            ", alternativeMail='" + alternativeMail + "'" +
            ", cv='" + cv + "'" +
            ", cvContentType='" + cvContentType + "'" +
            ", objective='" + objective + "'" +
            ", presentSalary='" + presentSalary + "'" +
            ", expectedSalary='" + expectedSalary + "'" +
            ", availibilityType='" + availibilityType + "'" +
            '}';
    }
}
