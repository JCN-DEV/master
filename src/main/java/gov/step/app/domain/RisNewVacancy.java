package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RisNewVacancy.
 */
@Entity
@Table(name = "ris_new_vacancy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "risnewvacancy")
public class RisNewVacancy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "vacancy_no")
    private Integer vacancyNo;

    @Column(name = "educational_qualification")
    private String educationalQualification;

    @Column(name = "other_qualification")
    private String otherQualification;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "application_date")
    private LocalDate applicationDate;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;


    @Column(name = "attachment_content_type")
    private String attachmentContentType;
    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    private HrDepartmentSetup department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVacancyNo() {
        return vacancyNo;
    }

    public void setVacancyNo(Integer vacancyNo) {
        this.vacancyNo = vacancyNo;
    }

    public String getEducationalQualification() {
        return educationalQualification;
    }

    public void setEducationalQualification(String educationalQualification) {
        this.educationalQualification = educationalQualification;
    }

    public String getOtherQualification() {
        return otherQualification;
    }

    public void setOtherQualification(String otherQualification) {
        this.otherQualification = otherQualification;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public HrDepartmentSetup getDepartment() {
        return department;
    }

    public void setDepartment(HrDepartmentSetup HrDepartmentSetup) {
        this.department = HrDepartmentSetup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RisNewVacancy risNewVacancy = (RisNewVacancy) o;

        if ( ! Objects.equals(id, risNewVacancy.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RisNewVacancy{" +
            "id=" + id +
            ", vacancyNo='" + vacancyNo + "'" +
            ", educationalQualification='" + educationalQualification + "'" +
            ", otherQualification='" + otherQualification + "'" +
            ", remarks='" + remarks + "'" +
            ", publishDate='" + publishDate + "'" +
            ", applicationDate='" + applicationDate + "'" +
            ", attachment='" + attachment + "'" +
            ", attachmentContentType='" + attachmentContentType + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
