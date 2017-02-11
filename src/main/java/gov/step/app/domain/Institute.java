package gov.step.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.step.app.domain.enumeration.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Institute.
 */
@Entity
@Table(name = "institute")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "institute")
public class Institute extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_of_estb", nullable = false)
    private LocalDate dateOfEstablishment;

    @Column(name = "mpo_code")
    private String mpoCode;

    @Column(name = "post_office")
    private String postOffice;

    @Enumerated(EnumType.STRING)
    @Column(name = "location")
    private Location location;

    @Column(name = "is_joint")
    private Boolean isJoint;

    @Column(name = "multi_educational")
    private String multiEducational;

    @JsonIgnore
    @Column(name = "first_appr_edu_year", nullable = false)
    private LocalDate firstApprovedEducationalYear;

    @JsonIgnore
    @Column(name = "last_appr_edu_year", nullable = false)
    private LocalDate lastApprovedEducationalYear;

    @JsonIgnore
    @Column(name = "first_mpo_include_date", nullable = false)
    private LocalDate firstMpoIncludeDate;

    @JsonIgnore
    @Column(name = "last_mpo_exp_date", nullable = false)
    private LocalDate lastMpoExpireDate;

    @Column(name = "name_of_trade_subject", nullable = false)
    private LocalDate nameOfTradeSubject;

    @JsonIgnore
    @Column(name = "last_appr_sign_date", nullable = false)
    private LocalDate lastApprovedSignatureDate;

    @JsonIgnore
    @Column(name = "lat_cmt_aprv_date", nullable = false)
    private LocalDate lastCommitteeApprovedDate;

    @Lob
    @Column(name = "lst_cmt_aprv_file")
    private byte[] lastCommitteeApprovedFile;


    @Column(name = "lst_cmt_aprv_fl_cnt_type", nullable = false)
    private String lastCommitteeApprovedFileContentType;

    @JsonIgnore
    @Column(name = "lst_cmt_exp_date", nullable = false)
    private LocalDate lastCommitteeExpDate;

    @Lob
    @Column(name = "lst_cmt_mtng_file")
    private byte[] lastCommittee1stMeetingFile;


    @Column(name = "lst_cmt_mtng_fl_cnt_type", nullable = false)
    private String lastCommittee1stMeetingFileContentType;

    @Column(name = "lst_comite_expire_date", nullable = false)
    private LocalDate lastCommitteeExpireDate;

    @Column(name = "lst_mpo_memorial_date", nullable = false)
    private LocalDate lastMpoMemorialDate;

    @Column(name = "total_student")
    private Integer totalStudent;

    @Column(name = "length_of_library", precision = 10, scale = 2, nullable = false)
    private BigDecimal lengthOfLibrary;

    @Column(name = "width_of_library", precision = 10, scale = 2, nullable = false)
    private BigDecimal widthOfLibrary;

    @Column(name = "number_of_book")
    private Integer numberOfBook;

    @Column(name = "lst_mpo_inclde_exp_date", nullable = false)
    private LocalDate lastMpoIncludeExpireDate;

    @Column(name = "number_of_lab")
    private Integer numberOfLab;

    @Column(name = "lab_area")
    private String labArea;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "land_phone")
    private String landPhone;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "alt_mobile")
    private String altMobile;

    @Column(name = "email")
    private String email;

    @Column(name = "constituency_area")
    private String constituencyArea;

    @Column(name = "admin_counselor_name")
    private String adminCounselorName;

    @Column(name = "counselor_mobile_no")
    private String counselorMobileNo;

    @Column(name = "ins_head_name")
    private String insHeadName;

    @Column(name = "ins_head_mobile_no")
    private String insHeadMobileNo;

    @Column(name = "deo_name")
    private String deoName;

    @Column(name = "deo_mobile_no")
    private String deoMobileNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private InstituteType type;

    //@NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private InstituteCategory category;

    @Column(name = "counselor_name")
    private String counselorName;

    @Enumerated(EnumType.STRING)
    @Column(name = "curriculum")
    private Curriculum curriculum;

    @Column(name = "total_tech_trade_no")
    private Integer totalTechTradeNo;

    @Column(name = "trade_tech_details")
    private String tradeTechDetails;


    @Column(name = "eiin" ,unique = true)
    private String eiin;

    @Column(name = "center_code")
    private String centerCode;

    @Column(name = "mpo_enlisted")
    private Boolean mpoEnlisted;

    @Column(name = "date_of_mpo")
    private LocalDate dateOfMpo;

    @Column(name = "first_recognition_date")
    private LocalDate firstRecognitionDate;

    @Column(name = "first_affiliation_date")
    private LocalDate firstAffiliationDate;

    @Column(name = "last_exp_dt_of_affiliation")
    private LocalDate lastExpireDateOfAffiliation;

    @Column(name = "locality")
    private int locality;

    @Column(name = "owner_type")
    private Boolean ownerType;

    //added by galeb
    //this field to check general vacancy role assigned or not.
    // value is 'false' before providing mpo code by admin.
    @Column(name = "vacancy_assigned")
    private Boolean vacancyAssigned;

    @Column(name = "info_edit_status")
    private String infoEditStatus;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;


    @ManyToOne
    @JoinColumn(name = "inst_category_id")
    private InstCategory instCategory;

    @ManyToOne
    @JoinColumn(name = "inst_level_id")
    private InstLevel instLevel;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "institute_course",
        joinColumns = @JoinColumn(name = "institutes_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "courses_id", referencedColumnName = "ID"))
    private Set<Course> courses = new HashSet<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.draft;

    public Institute() {
        super();
    }

    public Institute(Long id) {
        super();
        this.id = id;
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

    public LocalDate getDateOfEstablishment() {
        return dateOfEstablishment;
    }

    public void setDateOfEstablishment(LocalDate dateOfEstablishment) {
        this.dateOfEstablishment = dateOfEstablishment;
    }

    public String getMpoCode() {
        return mpoCode;
    }

    public void setMpoCode(String mpoCode) {
        this.mpoCode = mpoCode;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Boolean getIsJoint() {
        return isJoint;
    }

    public void setIsJoint(Boolean isJoint) {
        this.isJoint = isJoint;
    }

    public String getMultiEducational() {
        return multiEducational;
    }

    public void setMultiEducational(String multiEducational) {
        this.multiEducational = multiEducational;
    }

    public LocalDate getFirstApprovedEducationalYear() {
        return firstApprovedEducationalYear;
    }

    public void setFirstApprovedEducationalYear(LocalDate firstApprovedEducationalYear) {
        this.firstApprovedEducationalYear = firstApprovedEducationalYear;
    }

    public LocalDate getLastApprovedEducationalYear() {
        return lastApprovedEducationalYear;
    }

    public void setLastApprovedEducationalYear(LocalDate lastApprovedEducationalYear) {
        this.lastApprovedEducationalYear = lastApprovedEducationalYear;
    }

    public LocalDate getFirstMpoIncludeDate() {
        return firstMpoIncludeDate;
    }

    public void setFirstMpoIncludeDate(LocalDate firstMpoIncludeDate) {
        this.firstMpoIncludeDate = firstMpoIncludeDate;
    }

    public LocalDate getLastMpoExpireDate() {
        return lastMpoExpireDate;
    }

    public void setLastMpoExpireDate(LocalDate lastMpoExpireDate) {
        this.lastMpoExpireDate = lastMpoExpireDate;
    }

    public LocalDate getNameOfTradeSubject() {
        return nameOfTradeSubject;
    }

    public void setNameOfTradeSubject(LocalDate nameOfTradeSubject) {
        this.nameOfTradeSubject = nameOfTradeSubject;
    }

    public LocalDate getLastApprovedSignatureDate() {
        return lastApprovedSignatureDate;
    }

    public void setLastApprovedSignatureDate(LocalDate lastApprovedSignatureDate) {
        this.lastApprovedSignatureDate = lastApprovedSignatureDate;
    }

    public LocalDate getLastCommitteeApprovedDate() {
        return lastCommitteeApprovedDate;
    }

    public void setLastCommitteeApprovedDate(LocalDate lastCommitteeApprovedDate) {
        this.lastCommitteeApprovedDate = lastCommitteeApprovedDate;
    }

    public byte[] getLastCommitteeApprovedFile() {
        return lastCommitteeApprovedFile;
    }

    public void setLastCommitteeApprovedFile(byte[] lastCommitteeApprovedFile) {
        this.lastCommitteeApprovedFile = lastCommitteeApprovedFile;
    }

    public String getLastCommitteeApprovedFileContentType() {
        return lastCommitteeApprovedFileContentType;
    }

    public void setLastCommitteeApprovedFileContentType(String lastCommitteeApprovedFileContentType) {
        this.lastCommitteeApprovedFileContentType = lastCommitteeApprovedFileContentType;
    }

    public LocalDate getLastCommitteeExpDate() {
        return lastCommitteeExpDate;
    }

    public void setLastCommitteeExpDate(LocalDate lastCommitteeExpDate) {
        this.lastCommitteeExpDate = lastCommitteeExpDate;
    }

    public byte[] getLastCommittee1stMeetingFile() {
        return lastCommittee1stMeetingFile;
    }

    public void setLastCommittee1stMeetingFile(byte[] lastCommittee1stMeetingFile) {
        this.lastCommittee1stMeetingFile = lastCommittee1stMeetingFile;
    }

    public String getLastCommittee1stMeetingFileContentType() {
        return lastCommittee1stMeetingFileContentType;
    }

    public void setLastCommittee1stMeetingFileContentType(String lastCommittee1stMeetingFileContentType) {
        this.lastCommittee1stMeetingFileContentType = lastCommittee1stMeetingFileContentType;
    }

    public LocalDate getLastCommitteeExpireDate() {
        return lastCommitteeExpireDate;
    }

    public void setLastCommitteeExpireDate(LocalDate lastCommitteeExpireDate) {
        this.lastCommitteeExpireDate = lastCommitteeExpireDate;
    }

    public LocalDate getLastMpoMemorialDate() {
        return lastMpoMemorialDate;
    }

    public void setLastMpoMemorialDate(LocalDate lastMpoMemorialDate) {
        this.lastMpoMemorialDate = lastMpoMemorialDate;
    }

    public Integer getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(Integer totalStudent) {
        this.totalStudent = totalStudent;
    }

    public BigDecimal getLengthOfLibrary() {
        return lengthOfLibrary;
    }

    public void setLengthOfLibrary(BigDecimal lengthOfLibrary) {
        this.lengthOfLibrary = lengthOfLibrary;
    }

    public BigDecimal getWidthOfLibrary() {
        return widthOfLibrary;
    }

    public void setWidthOfLibrary(BigDecimal widthOfLibrary) {
        this.widthOfLibrary = widthOfLibrary;
    }

    public Integer getNumberOfBook() {
        return numberOfBook;
    }

    public void setNumberOfBook(Integer numberOfBook) {
        this.numberOfBook = numberOfBook;
    }

    public LocalDate getLastMpoIncludeExpireDate() {
        return lastMpoIncludeExpireDate;
    }

    public void setLastMpoIncludeExpireDate(LocalDate lastMpoIncludeExpireDate) {
        this.lastMpoIncludeExpireDate = lastMpoIncludeExpireDate;
    }

    public Integer getNumberOfLab() {
        return numberOfLab;
    }

    public void setNumberOfLab(Integer numberOfLab) {
        this.numberOfLab = numberOfLab;
    }

    public String getLabArea() {
        return labArea;
    }

    public void setLabArea(String labArea) {
        this.labArea = labArea;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLandPhone() {
        return landPhone;
    }

    public void setLandPhone(String landPhone) {
        this.landPhone = landPhone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAltMobile() {
        return altMobile;
    }

    public void setAltMobile(String altMobile) {
        this.altMobile = altMobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConstituencyArea() {
        return constituencyArea;
    }

    public void setConstituencyArea(String constituencyArea) {
        this.constituencyArea = constituencyArea;
    }

    public String getAdminCounselorName() {
        return adminCounselorName;
    }

    public void setAdminCounselorName(String adminCounselorName) {
        this.adminCounselorName = adminCounselorName;
    }

    public String getCounselorMobileNo() {
        return counselorMobileNo;
    }

    public void setCounselorMobileNo(String counselorMobileNo) {
        this.counselorMobileNo = counselorMobileNo;
    }

    public String getInsHeadName() {
        return insHeadName;
    }

    public void setInsHeadName(String insHeadName) {
        this.insHeadName = insHeadName;
    }

    public String getInsHeadMobileNo() {
        return insHeadMobileNo;
    }

    public void setInsHeadMobileNo(String insHeadMobileNo) {
        this.insHeadMobileNo = insHeadMobileNo;
    }

    public String getDeoName() {
        return deoName;
    }

    public void setDeoName(String deoName) {
        this.deoName = deoName;
    }

    public String getDeoMobileNo() {
        return deoMobileNo;
    }

    public void setDeoMobileNo(String deoMobileNo) {
        this.deoMobileNo = deoMobileNo;
    }

    public InstituteType getType() {
        return type;
    }

    public void setType(InstituteType type) {
        this.type = type;
    }

    public InstituteCategory getCategory() {
        return category;
    }

    public void setCategory(InstituteCategory category) {
        this.category = category;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public Integer getTotalTechTradeNo() {
        return totalTechTradeNo;
    }

    public void setTotalTechTradeNo(Integer totalTechTradeNo) {
        this.totalTechTradeNo = totalTechTradeNo;
    }

    public String getTradeTechDetails() {
        return tradeTechDetails;
    }

    public void setTradeTechDetails(String tradeTechDetails) {
        this.tradeTechDetails = tradeTechDetails;
    }

    public String getEiin() {
        return eiin;
    }

    public void setEiin(String eiin) {
        this.eiin = eiin;
    }

    public String getCenterCode() {
        return centerCode;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    public Boolean getMpoEnlisted() {
        return mpoEnlisted;
    }

    public void setMpoEnlisted(Boolean mpoEnlisted) {
        this.mpoEnlisted = mpoEnlisted;
    }

    public LocalDate getDateOfMpo() {
        return dateOfMpo;
    }

    public void setDateOfMpo(LocalDate dateOfMpo) {
        this.dateOfMpo = dateOfMpo;
    }

    public LocalDate getFirstRecognitionDate() {
        return firstRecognitionDate;
    }

    public void setFirstRecognitionDate(LocalDate firstRecognitionDate) {
        this.firstRecognitionDate = firstRecognitionDate;
    }

    public LocalDate getFirstAffiliationDate() {
        return firstAffiliationDate;
    }

    public void setFirstAffiliationDate(LocalDate firstAffiliationDate) {
        this.firstAffiliationDate = firstAffiliationDate;
    }

    public LocalDate getLastExpireDateOfAffiliation() {
        return lastExpireDateOfAffiliation;
    }

    public void setLastExpireDateOfAffiliation(LocalDate lastExpireDateOfAffiliation) {
        this.lastExpireDateOfAffiliation = lastExpireDateOfAffiliation;
    }

    public int getLocality() {
        return locality;
    }

    public void setLocality(int locality) {
        this.locality = locality;
    }

    public Boolean getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Boolean ownerType) {
        this.ownerType = ownerType;
    }

    public Boolean getVacancyAssigned() {
        return vacancyAssigned;
    }

    public void setVacancyAssigned(Boolean vacancyAssigned) {
        this.vacancyAssigned = vacancyAssigned;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Upazila getUpazila() {
        return upazila;
    }

    public void setUpazila(Upazila upazila) {
        this.upazila = upazila;
    }

    public InstCategory getInstCategory() {
        return instCategory;
    }

    public void setInstCategory(InstCategory instCategory) {
        this.instCategory = instCategory;
    }

    public InstLevel getInstLevel() {
        return instLevel;
    }

    public void setInstLevel(InstLevel instLevel) {
        this.instLevel = instLevel;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public String getInfoEditStatus() {
        return infoEditStatus;
    }

    public void setInfoEditStatus(String infoEditStatus) {
        this.infoEditStatus = infoEditStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Institute institute = (Institute) o;

        if (!Objects.equals(id, institute.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Institute{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", dateOfEstablishment='" + dateOfEstablishment + "'" +
            ", mpoCode='" + mpoCode + "'" +
            ", postOffice='" + postOffice + "'" +
            ", location='" + location + "'" +
            ", isJoint='" + isJoint + "'" +
            ", multiEducational='" + multiEducational + "'" +
            ", firstApprovedEducationalYear='" + firstApprovedEducationalYear + "'" +
            ", lastApprovedEducationalYear='" + lastApprovedEducationalYear + "'" +
            ", firstMpoIncludeDate='" + firstMpoIncludeDate + "'" +
            ", lastMpoExpireDate='" + lastMpoExpireDate + "'" +
            ", nameOfTradeSubject='" + nameOfTradeSubject + "'" +
            ", lastApprovedSignatureDate='" + lastApprovedSignatureDate + "'" +
            ", lastCommitteeApprovedDate='" + lastCommitteeApprovedDate + "'" +
            ", lastCommitteeApprovedFile='" + lastCommitteeApprovedFile + "'" +
            ", lastCommitteeApprovedFileContentType='" + lastCommitteeApprovedFileContentType + "'" +
            ", lastCommitteeExpDate='" + lastCommitteeExpDate + "'" +
            ", lastCommittee1stMeetingFile='" + lastCommittee1stMeetingFile + "'" +
            ", lastCommittee1stMeetingFileContentType='" + lastCommittee1stMeetingFileContentType + "'" +
            ", lastCommitteeExpireDate='" + lastCommitteeExpireDate + "'" +
            ", lastMpoMemorialDate='" + lastMpoMemorialDate + "'" +
            ", totalStudent='" + totalStudent + "'" +
            ", lengthOfLibrary='" + lengthOfLibrary + "'" +
            ", widthOfLibrary='" + widthOfLibrary + "'" +
            ", numberOfBook='" + numberOfBook + "'" +
            ", lastMpoIncludeExpireDate='" + lastMpoIncludeExpireDate + "'" +
            ", numberOfLab='" + numberOfLab + "'" +
            ", labArea='" + labArea + "'" +
            ", code='" + code + "'" +
            ", landPhone='" + landPhone + "'" +
            ", mobile='" + mobile + "'" +
            ", email='" + email + "'" +
            ", constituencyArea='" + constituencyArea + "'" +
            ", adminCounselorName='" + adminCounselorName + "'" +
            ", counselorMobileNo='" + counselorMobileNo + "'" +
            ", insHeadName='" + insHeadName + "'" +
            ", insHeadMobileNo='" + insHeadMobileNo + "'" +
            ", deoName='" + deoName + "'" +
            ", deoMobileNo='" + deoMobileNo + "'" +
            ", type='" + type + "'" +
            ", category='" + category + "'" +
            ", infoEditStatus='" + infoEditStatus + "'" +
            '}';
    }
}
