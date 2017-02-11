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
 * A AttachmentCategory.
 */
@Entity
@Table(name = "attachment_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "attachmentcategory")
public class AttachmentCategory implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="attachment_category_seq")
    @SequenceGenerator(name="attachment_category_seq", sequenceName="attachment_category_seq")
    private Long id;

    @NotNull
    @Column(name = "application_name", nullable = false)
    private String applicationName;

    @NotNull
    @Column(name = "attachment_name", nullable = false)
    private String attachmentName;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private Boolean status;

    //@NotNull
    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;

    @ManyToOne
    private Module module;

//    @ManyToOne
//    @JoinColumn(name="inst_empl_designation_id")
//    private InstEmplDesignation instEmplDesignation;

    @ManyToOne
    @JoinColumn(name = "hr_designation_setup")
    private HrDesignationSetup designationSetup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    /*public LocalDate getDate() {
        return dateCreated;
    }

    public void setDate(LocalDate date) {
        this.dateCreated = date;
    }*/

//    public InstEmplDesignation getInstEmplDesignation() {
//        return instEmplDesignation;
//    }
//
//    public void setInstEmplDesignation(InstEmplDesignation instEmplDesignation) {
//        this.instEmplDesignation = instEmplDesignation;
//    }


    public HrDesignationSetup getDesignationSetup() {
        return designationSetup;
    }

    public void setDesignationSetup(HrDesignationSetup designationSetup) {
        this.designationSetup = designationSetup;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttachmentCategory attachmentCategory = (AttachmentCategory) o;

        if ( ! Objects.equals(id, attachmentCategory.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AttachmentCategory{" +
            "id=" + id +
            ", applicationName='" + applicationName + "'" +
            ", attachmentName='" + attachmentName + "'" +
            ", date='" + dateCreated + "'" +
            ",designationSetup='"+designationSetup.getDesignationInfo().getDesignationName()+"'"+
            '}';
    }
}
