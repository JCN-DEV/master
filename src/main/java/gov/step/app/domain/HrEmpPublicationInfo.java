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
 * A HrEmpPublicationInfo.
 */
@Entity
@Table(name = "hr_emp_publication_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hremppublicationinfo")
public class HrEmpPublicationInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "publication_title", nullable = false)
    private String publicationTitle;

    @NotNull
    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "publication_link")
    private String publicationLink;

    @Lob
    @Column(name = "publication_doc")
    private byte[] publicationDoc;

    @Column(name = "publication_doc_content_type")
    private String publicationDocContentType;

    @Column(name = "publication_doc_name")
    private String publicationDocName;

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
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @ManyToOne
    @JoinColumn(name = "publication_type_id")
    private MiscTypeSetup publicationType;

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

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public void setPublicationTitle(String publicationTitle) {
        this.publicationTitle = publicationTitle;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public byte[] getPublicationDoc() {
        return publicationDoc;
    }

    public void setPublicationDoc(byte[] publicationDoc) {
        this.publicationDoc = publicationDoc;
    }

    public String getPublicationDocContentType() {
        return publicationDocContentType;
    }

    public void setPublicationDocContentType(String publicationDocContentType) {this.publicationDocContentType = publicationDocContentType;}

    public String getPublicationDocName() {
        return publicationDocName;
    }

    public void setPublicationDocName(String publicationDocName) {
        this.publicationDocName = publicationDocName;
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

    public HrEmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(HrEmployeeInfo HrEmployeeInfo) {
        this.employeeInfo = HrEmployeeInfo;
    }

    public Long getLogId() { return logId;}

    public void setLogId(Long logId) {this.logId = logId;}

    public Long getLogStatus() {return logStatus;}

    public void setLogStatus(Long logStatus) {this.logStatus = logStatus;}

    public String getLogComments() {return logComments;}

    public void setLogComments(String logComments) {this.logComments = logComments;}

    public String getPublicationLink() {return publicationLink;}

    public void setPublicationLink(String publicationLink) {this.publicationLink = publicationLink;}

    public MiscTypeSetup getPublicationType() {return publicationType;}

    public void setPublicationType(MiscTypeSetup publicationType) {this.publicationType = publicationType;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEmpPublicationInfo hrEmpPublicationInfo = (HrEmpPublicationInfo) o;
        return Objects.equals(id, hrEmpPublicationInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpPublicationInfo{" +
            "id=" + id +
            ", publicationTitle='" + publicationTitle + "'" +
            ", publicationDate='" + publicationDate + "'" +
            ", remarks='" + remarks + "'" +
            ", publicationDoc='" + publicationDoc + "'" +
            ", publicationDocContentType='" + publicationDocContentType + "'" +
            ", publicationDocName='" + publicationDocName + "'" +
            ", logId='" + logId + "'" +
            ", logStatus='" + logStatus + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
