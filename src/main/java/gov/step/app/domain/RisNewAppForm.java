package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import org.hibernate.annotations.Nationalized;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import gov.step.app.domain.enumeration.religion;

/**
 * A RisNewAppForm.
 */
@Entity
@Table(name = "ris_new_app_form")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "risnewappform")
public class RisNewAppForm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "designation", nullable = false)
    private String designation;

    @Lob
    @Column(name = "applicant_img")
    private byte[] applicantImg;

    @Column(name = "applicant_img_content_type")
    private String applicantImgContentType;

    @Column(name = "applicant_img_name")
    private String applicantImgName;

    @NotNull
    @Column(name = "circular_no", nullable = false)
    private String circularNo;

    @NotNull
    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @NotNull
    @Nationalized
    @Column(name = "applicants_name_bn", nullable = false)
    private String applicantsNameBn;

    @NotNull
    @Column(name = "reg_no", nullable = false)
    private String regNo;

    @NotNull
    @Column(name = "applicants_name_en", nullable = false)
    private String applicantsNameEn;

    @NotNull
    @Column(name = "national_id", nullable = false)
    private Long nationalId;

    @NotNull
    @Column(name = "birth_certificate_no", nullable = false)
    private Long birthCertificateNo;

    @NotNull
    @Column(name = "application_status", nullable = false)
    private Long applicationStatus;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotNull
    @Column(name = "age", nullable = false)
    private String age;

    @NotNull
    @Column(name = "fathers_name", nullable = false)
    private String fathersName;

    @Nationalized
    @Column(name = "qouta")
    private String qouta;

    @NotNull
    @Column(name = "mothers_name", nullable = false)
    private String mothersName;

    @NotNull
    @Nationalized
    @Column(name = "holding_name_bn_present", nullable = false)
    private String holdingNameBnPresent;

    @NotNull
    @Nationalized
    @Column(name = "village_bn_present", nullable = false)
    private String villageBnPresent;

    @NotNull
    @Nationalized
    @Column(name = "union_bn_present", nullable = false)
    private String unionBnPresent;

    @NotNull
    @Nationalized
    @Column(name = "po_bn_present", nullable = false)
    private String poBnPresent;

    @NotNull
    @Nationalized
    @Column(name = "po_code_bn_present", nullable = false)
    private String poCodeBnPresent;

    @NotNull
    @Nationalized
    @Column(name = "holding_name_bn_permanent", nullable = false)
    private String holdingNameBnPermanent;

    @NotNull
    @Nationalized
    @Column(name = "village_bn_permanent", nullable = false)
    private String villageBnPermanent;

    @NotNull
    @Nationalized
    @Column(name = "union_bn_permanent", nullable = false)
    private String unionBnPermanent;

    @NotNull
    @Nationalized
    @Column(name = "po_bn_permanent", nullable = false)
    private String poBnPermanent;

    @NotNull
    @Nationalized
    @Column(name = "po_code_bn_permanent", nullable = false)
    private String poCodeBnPermanent;

    @NotNull
    @Column(name = "contact_phone", nullable = false)
    private String contactPhone;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @NotNull
    @Column(name = "profession", nullable = false)
    private String profession;

    @NotNull
    @Nationalized
    @Enumerated(EnumType.STRING)
    @Column(name = "religion", nullable = false)
    private religion religion;

    @NotNull
    @Column(name = "holding_name_en_present", nullable = false)
    private String holdingNameEnPresent;

    @NotNull
    @Column(name = "village_en_present", nullable = false)
    private String villageEnPresent;

    @NotNull
    @Column(name = "union_en_present", nullable = false)
    private String unionEnPresent;

    @NotNull
    @Column(name = "po_en_present", nullable = false)
    private String poEnPresent;

    @NotNull
    @Column(name = "po_code_en_present", nullable = false)
    private String poCodeEnPresent;

    @NotNull
    @Column(name = "holding_name_en_permanent", nullable = false)
    private String holdingNameEnPermanent;

    @NotNull
    @Column(name = "village_en_permanent", nullable = false)
    private String villageEnPermanent;

    @NotNull
    @Column(name = "union_en_permanent", nullable = false)
    private String unionEnPermanent;

    @NotNull
    @Column(name = "po_en_permanent", nullable = false)
    private String poEnPermanent;

    @NotNull
    @Column(name = "po_code_en_permanent", nullable = false)
    private String poCodeEnPermanent;



    @Column(name = "written_exam", nullable = false)
    private Integer written_exam;

    @Column(name = "viva_exma", nullable = false)
    private Integer viva_exam;



    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCircularNo() {
        return circularNo;
    }

    public void setCircularNo(String circularNo) {
        this.circularNo = circularNo;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getApplicantsNameBn() {
        return applicantsNameBn;
    }

    public void setApplicantsNameBn(String applicantsNameBn) {
        this.applicantsNameBn = applicantsNameBn;
    }

    public String getApplicantsNameEn() {
        return applicantsNameEn;
    }

    public void setApplicantsNameEn(String applicantsNameEn) {
        this.applicantsNameEn = applicantsNameEn;
    }

    public Long getNationalId() {
        return nationalId;
    }

    public void setNationalId(Long nationalId) {
        this.nationalId = nationalId;
    }

    public Long getBirthCertificateNo() {
        return birthCertificateNo;
    }

    public void setBirthCertificateNo(Long birthCertificateNo) {
        this.birthCertificateNo = birthCertificateNo;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getWritten_exam() {
        return written_exam;
    }

    public void setWritten_exam(Integer written_exam) {
        this.written_exam = written_exam;
    }

    public Integer getViva_exam() {
        return viva_exam;
    }

    public void setViva_exam(Integer viva_exam) {
        this.viva_exam = viva_exam;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getQouta() {
        return this.qouta;
    }

    public void setQouta(String qouta) {
        this.qouta = qouta;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public String getHoldingNameBnPresent() {
        return holdingNameBnPresent;
    }

    public void setHoldingNameBnPresent(String holdingNameBnPresent) {
        this.holdingNameBnPresent = holdingNameBnPresent;
    }

    public String getVillageBnPresent() {
        return villageBnPresent;
    }

    public void setVillageBnPresent(String villageBnPresent) {
        this.villageBnPresent = villageBnPresent;
    }

    public String getUnionBnPresent() {
        return unionBnPresent;
    }

    public void setUnionBnPresent(String unionBnPresent) {
        this.unionBnPresent = unionBnPresent;
    }

    public String getPoBnPresent() {
        return poBnPresent;
    }

    public void setPoBnPresent(String poBnPresent) {
        this.poBnPresent = poBnPresent;
    }

    public String getPoCodeBnPresent() {
        return poCodeBnPresent;
    }

    public void setPoCodeBnPresent(String poCodeBnPresent) {
        this.poCodeBnPresent = poCodeBnPresent;
    }

    public String getHoldingNameBnPermanent() {
        return holdingNameBnPermanent;
    }

    public void setHoldingNameBnPermanent(String holdingNameBnPermanent) {
        this.holdingNameBnPermanent = holdingNameBnPermanent;
    }

    public String getVillageBnPermanent() {
        return villageBnPermanent;
    }

    public void setVillageBnPermanent(String villageBnPermanent) {
        this.villageBnPermanent = villageBnPermanent;
    }

    public String getUnionBnPermanent() {
        return unionBnPermanent;
    }

    public void setUnionBnPermanent(String unionBnPermanent) {
        this.unionBnPermanent = unionBnPermanent;
    }

    public String getPoBnPermanent() {
        return poBnPermanent;
    }

    public void setPoBnPermanent(String poBnPermanent) {
        this.poBnPermanent = poBnPermanent;
    }

    public String getPoCodeBnPermanent() {
        return poCodeBnPermanent;
    }

    public void setPoCodeBnPermanent(String poCodeBnPermanent) {
        this.poCodeBnPermanent = poCodeBnPermanent;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public religion getReligion() {
        return religion;
    }

    public void setReligion(religion religion) {
        this.religion = religion;
    }

    public String getHoldingNameEnPresent() {
        return holdingNameEnPresent;
    }

    public void setHoldingNameEnPresent(String holdingNameEnPresent) {
        this.holdingNameEnPresent = holdingNameEnPresent;
    }

    public String getVillageEnPresent() {
        return villageEnPresent;
    }

    public void setVillageEnPresent(String villageEnPresent) {
        this.villageEnPresent = villageEnPresent;
    }

    public String getUnionEnPresent() {
        return unionEnPresent;
    }

    public void setUnionEnPresent(String unionEnPresent) {
        this.unionEnPresent = unionEnPresent;
    }

    public String getPoEnPresent() {
        return poEnPresent;
    }

    public void setPoEnPresent(String poEnPresent) {
        this.poEnPresent = poEnPresent;
    }

    public String getPoCodeEnPresent() {
        return poCodeEnPresent;
    }

    public void setPoCodeEnPresent(String poCodeEnPresent) {
        this.poCodeEnPresent = poCodeEnPresent;
    }

    public String getHoldingNameEnPermanent() {
        return holdingNameEnPermanent;
    }

    public void setHoldingNameEnPermanent(String holdingNameEnPermanent) {
        this.holdingNameEnPermanent = holdingNameEnPermanent;
    }

    public String getVillageEnPermanent() {
        return villageEnPermanent;
    }

    public void setVillageEnPermanent(String villageEnPermanent) {
        this.villageEnPermanent = villageEnPermanent;
    }

    public String getUnionEnPermanent() {
        return unionEnPermanent;
    }

    public void setUnionEnPermanent(String unionEnPermanent) {
        this.unionEnPermanent = unionEnPermanent;
    }

    public String getPoEnPermanent() {
        return poEnPermanent;
    }

    public void setPoEnPermanent(String poEnPermanent) {
        this.poEnPermanent = poEnPermanent;
    }

    public String getPoCodeEnPermanent() {
        return poCodeEnPermanent;
    }

    public void setPoCodeEnPermanent(String poCodeEnPermanent) {
        this.poCodeEnPermanent = poCodeEnPermanent;
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

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Upazila getUpazila() {
        return upazila;
    }

    public void setUpazila(Upazila upazila) {
        this.upazila = upazila;
    }

    public byte[] getApplicantImg() {
        return this.applicantImg;
    }

    public void setApplicantImg(byte [] applicantImg) {
        this.applicantImg = applicantImg;
    }

    public String getApplicantImgContentType() {
        return this.applicantImgContentType;
    }

    public void setApplicantImgContentType(String applicantImgContentType) {
        this.applicantImgContentType = applicantImgContentType;
    }

    public String getApplicantImgName() {
        return this.applicantImgName;
    }

    public void setApplicantImgName(String applicantImgName) {
        this.applicantImgName = applicantImgName;
    }

    public String getRegNo() {
        return this.regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public Long getApplicationStatus() {
        return this.applicationStatus;
    }

    public void setApplicationStatus(Long applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RisNewAppForm risNewAppForm = (RisNewAppForm) o;

        if ( ! Objects.equals(id, risNewAppForm.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RisNewAppForm{" +
            "id=" + id +
            ", designation='" + designation + "'" +
            ", circularNo='" + circularNo + "'" +
            ", applicationDate='" + applicationDate + "'" +
            ", applicantsNameBn='" + applicantsNameBn + "'" +
            ", applicantsNameEn='" + applicantsNameEn + "'" +
            ", nationalId='" + nationalId + "'" +
            ", birthCertificateNo='" + birthCertificateNo + "'" +
            ", birthDate='" + birthDate + "'" +
            ", age='" + age + "'" +
            ", fathersName='" + fathersName + "'" +
            ", mothersName='" + mothersName + "'" +
            ", holdingNameBnPresent='" + holdingNameBnPresent + "'" +
            ", villageBnPresent='" + villageBnPresent + "'" +
            ", unionBnPresent='" + unionBnPresent + "'" +
            ", poBnPresent='" + poBnPresent + "'" +
            ", poCodeBnPresent='" + poCodeBnPresent + "'" +
            ", holdingNameBnPermanent='" + holdingNameBnPermanent + "'" +
            ", villageBnPermanent='" + villageBnPermanent + "'" +
            ", unionBnPermanent='" + unionBnPermanent + "'" +
            ", poBnPermanent='" + poBnPermanent + "'" +
            ", poCodeBnPermanent='" + poCodeBnPermanent + "'" +
            ", contactPhone='" + contactPhone + "'" +
            ", email='" + email + "'" +
            ", nationality='" + nationality + "'" +
            ", profession='" + profession + "'" +
            ", religion='" + religion + "'" +
            ", holdingNameEnPresent='" + holdingNameEnPresent + "'" +
            ", villageEnPresent='" + villageEnPresent + "'" +
            ", unionEnPresent='" + unionEnPresent + "'" +
            ", applicationStatus='" + applicationStatus + "'" +
            ", poEnPresent='" + poEnPresent + "'" +
            ", poCodeEnPresent='" + poCodeEnPresent + "'" +
            ", holdingNameEnPermanent='" + holdingNameEnPermanent + "'" +
            ", villageEnPermanent='" + villageEnPermanent + "'" +
            ", unionEnPermanent='" + unionEnPermanent + "'" +
            ", poEnPermanent='" + poEnPermanent + "'" +
            ", poCodeEnPermanent='" + poCodeEnPermanent + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", regNo='" + regNo + "'" +
            ", written_exam='" + written_exam + "'" +
            ", viva_exam='" + viva_exam + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }


}
