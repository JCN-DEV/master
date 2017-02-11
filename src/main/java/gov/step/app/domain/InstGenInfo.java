package gov.step.app.domain;

import gov.step.app.domain.enumeration.InstituteCategory;
import gov.step.app.domain.enumeration.InstituteType;
import gov.step.app.web.rest.util.AttachmentUtil;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import gov.step.app.domain.enumeration.instType;

/**
 * A InstGenInfo.
 */
@Entity
@Table(name = "inst_gen_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instgeninfo")
public class InstGenInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private InstituteType type;

    @Column(name = "village")
    private String village;

    @Column(name = "post_office")
    private String postOffice;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "land_phone")
    private String landPhone;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "alt_mobile_no")
    private String altMobileNo;

    @Column(name = "email")
    private String email;

    @Column(name = "cons_area")
    private String consArea;

    //comment added by galeb
    //status -1=rejected, 0=created, 1=approved, 2=edited, 2+ every edit will add 1 with previous status
    @Column(name = "status")
    private Integer status;

    @Column(name = "website")
    private String website;

    @Column(name = "faxnum")
    private String faxNum;

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

    @Column(name = "create_date")
    private LocalDate dateCrated;

    @Column(name = "update_date")
    private LocalDate dateModified;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;


    @OneToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    @ManyToOne
    @JoinColumn(name = "inst_category_id")
    private InstCategory instCategory;

    @ManyToOne
    @JoinColumn(name = "inst_level_id")
    private InstLevel instLevel;

    //@NotNull
//    @Enumerated(EnumType.STRING)
//    @Column(name = "category")
//    private InstituteCategory category;


    @Column(name = "submited_date")
    private Date submitedDate;

    @Column(name = "mpo_code")
    private String mpoCode;

    @Column(name = "reject_comments")
    private String rejectComments;

    @PrePersist
    protected void onCreate() {
        //this.setSubmitedDate(AttachmentUtil.getTodayDateWithTime());
        this.setSubmitedDate(new Date());

    }
    @PreUpdate
    protected void onUpdate() {
        //this.setSubmitedDate(AttachmentUtil.getTodayDateWithTime());
        this.setSubmitedDate(new Date());
    }

    public InstGenInfo(Long id,String code, String name, Date submitedDate, Integer status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.submitedDate = submitedDate;
        this.status = status == null ? 0: status;
    }

    public InstGenInfo() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public InstituteType getType() {
        return type;
    }

    public void setType(InstituteType type) {
        this.type = type;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getLandPhone() {
        return landPhone;
    }

    public void setLandPhone(String landPhone) {
        this.landPhone = landPhone;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAltMobileNo() {
        return altMobileNo;
    }

    public void setAltMobileNo(String altMobileNo) {
        this.altMobileNo = altMobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConsArea() {
        return consArea;
    }

    public void setConsArea(String consArea) {
        this.consArea = consArea;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Upazila getUpazila() {
        return upazila;
    }

    public void setUpazila(Upazila upazila) {
        this.upazila = upazila;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFaxNum() {
        return faxNum;
    }

    public void setFaxNum(String faxNum) {
        this.faxNum = faxNum;
    }

    public String getRejectComments() {
        return rejectComments;
    }

    public void setRejectComments(String rejectComments) {
        this.rejectComments = rejectComments;
    }

    /* public InstituteCategory getCategory() {
                return category;
            }

            public void setCategory(InstituteCategory category) {
                this.category = category;
            }
        */
    public Date getSubmitedDate() {
        return submitedDate;
    }

    public void setSubmitedDate(Date submitedDate) {
        this.submitedDate = submitedDate;
    }

    public String getMpoCode() {
        return mpoCode;
    }

    public void setMpoCode(String mpoCode) {
        this.mpoCode = mpoCode;
    }

    public LocalDate getDateCrated() {
        return dateCrated;
    }

    public void setDateCrated(LocalDate dateCrated) {
        this.dateCrated = dateCrated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstGenInfo instGenInfo = (InstGenInfo) o;

        if ( ! Objects.equals(id, instGenInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstGenInfo{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", publicationDate='" + publicationDate + "'" +
            ", type='" + type + "'" +
            ", village='" + village + "'" +
            ", postOffice='" + postOffice + "'" +
            ", postCode='" + postCode + "'" +
            ", landPhone='" + landPhone + "'" +
            ", mobileNo='" + mobileNo + "'" +
            ", email='" + email + "'" +
            ", consArea='" + consArea + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
