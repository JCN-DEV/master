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
 * A EmployeeLoanAttachment.
 */
@Entity
@Table(name = "elms_loan_attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employeeloanattachment")
public class EmployeeLoanAttachment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_name")
    private String fileName;

    @Lob
    @Column(name = "file_content")
    private byte[] file;

    @Column(name = "file_content_name")
    private String fileContentName;

    @Column(name = "file_content_type")
    private String fileContentType;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    private HrEmployeeInfo hrEmployeeInfo;

    @ManyToOne
    private AttachmentCategory attachmentCategory;

    @ManyToOne
    @JoinColumn(name = "loan_requisition_id")
    private EmployeeLoanRequisitionForm employeeLoanRequisitionForm;

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentName() {
        return fileContentName;
    }

    public void setFileContentName(String fileContentName) {
        this.fileContentName = fileContentName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public HrEmployeeInfo getHrEmployeeInfo() {
        return hrEmployeeInfo;
    }

    public void setHrEmployeeInfo(HrEmployeeInfo hrEmployeeInfo) {
        this.hrEmployeeInfo = hrEmployeeInfo;
    }

    public AttachmentCategory getAttachmentCategory() {
        return attachmentCategory;
    }

    public void setAttachmentCategory(AttachmentCategory attachmentCategory) {
        this.attachmentCategory = attachmentCategory;
    }

    public EmployeeLoanRequisitionForm getEmployeeLoanRequisitionForm() {
        return employeeLoanRequisitionForm;
    }

    public void setEmployeeLoanRequisitionForm(EmployeeLoanRequisitionForm employeeLoanRequisitionForm) {
        this.employeeLoanRequisitionForm = employeeLoanRequisitionForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeLoanAttachment employeeLoanAttachment = (EmployeeLoanAttachment) o;

        if ( ! Objects.equals(id, employeeLoanAttachment.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmployeeLoanAttachment{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", fileName='" + fileName + "'" +
            ", file='" + file + "'" +
            ", fileContentName='" + fileContentName + "'" +
            ", fileContentType='" + fileContentType + "'" +
            ", remarks='" + remarks + "'" +
            ", status='" + status + "'" +
            ", createBy='" + createBy + "'" +
            ", createDate='" + createDate + "'" +
            ", updateBy='" + updateBy + "'" +
            ", updateDate='" + updateDate + "'" +
            '}';
    }
}
