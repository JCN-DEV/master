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
 * A HrEmpPublicationInfoLog.
 */
@Entity
@Table(name = "hr_emp_publication_info_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hremppublicationinfolog")
public class HrEmpPublicationInfoLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "publication_title")
    private String publicationTitle;

    @Column(name = "publication_date")
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

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "log_status")
    private Long logStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "action_date")
    private LocalDate actionDate;

    @Column(name = "action_by")
    private Long actionBy;

    @Column(name = "action_comments")
    private String actionComments;

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @ManyToOne
    @JoinColumn(name = "publication_type_id")
    private MiscTypeSetup publicationType;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Long logStatus) {
        this.logStatus = logStatus;
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

    public LocalDate getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
    }

    public Long getActionBy() {
        return actionBy;
    }

    public void setActionBy(Long actionBy) {
        this.actionBy = actionBy;
    }

    public String getActionComments() {
        return actionComments;
    }

    public void setActionComments(String actionComments) {
        this.actionComments = actionComments;
    }

    public HrEmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(HrEmployeeInfo HrEmployeeInfo) {
        this.employeeInfo = HrEmployeeInfo;
    }

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
        HrEmpPublicationInfoLog hrEmpPublicationInfoLog = (HrEmpPublicationInfoLog) o;
        return Objects.equals(id, hrEmpPublicationInfoLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpPublicationInfoLog{" +
            "id=" + id +
            ", publicationTitle='" + publicationTitle + "'" +
            ", publicationDate='" + publicationDate + "'" +
            ", remarks='" + remarks + "'" +
            ", publicationDoc='" + publicationDoc + "'" +
            ", publicationDocContentType='" + publicationDocContentType + "'" +
            ", publicationDocName='" + publicationDocName + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", parentId='" + parentId + "'" +
            ", logStatus='" + logStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", actionDate='" + actionDate + "'" +
            ", actionBy='" + actionBy + "'" +
            ", actionComments='" + actionComments + "'" +
            '}';
    }
}
