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
 * A TrainingRequisitionForm.
 */
@Entity
@Table(name = "tis_requisition_form")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "trainingrequisitionform")
public class TrainingRequisitionForm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 50)
    @Column(name = "requisition_code", length = 50)
    private String requisitionCode;

    @NotNull
    @Column(name = "training_type", nullable = false)
    private String trainingType;

    @Column(name = "session_year")
    private String session;

    @NotNull
    @Column(name = "apply_date", nullable = false)
    private LocalDate applyDate;

    @Size(max = 50)
    @Column(name = "reason", length = 50)
    private String reason;

    @Column(name = "file_name")
    private String fileName;

    @Lob
    @Column(name = "file_content")
    private byte[] file;


    @Column(name = "file_content_type")
    private String fileContentType;

    @Column(name = "file_content_name")
    private String fileContentName;

    @Column(name = "apply_by")
    private String applyBy;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "CATEGORYSETUP_ID")
    private TrainingCategorySetup trainingCategorySetup;

    @ManyToOne
    @JoinColumn(name = "TRAININGHEADSETUP_ID")
    private TrainingHeadSetup trainingHeadSetup;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "HREMPLOYEEINFO_ID")
    private HrEmployeeInfo hrEmployeeInfo;

    @Column(name="approve_status")
    private Integer approveStatus;

    @Column(name="approved_date")
    private LocalDate approvedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequisitionCode() {
        return requisitionCode;
    }

    public void setRequisitionCode(String requisitionCode) {
        this.requisitionCode = requisitionCode;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public LocalDate getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileContentName() {
        return fileContentName;
    }

    public void setFileContentName(String fileContentName) {
        this.fileContentName = fileContentName;
    }


    public String getApplyBy() {
        return applyBy;
    }

    public void setApplyBy(String applyBy) {
        this.applyBy = applyBy;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public TrainingCategorySetup getTrainingCategorySetup() {
        return trainingCategorySetup;
    }

    public void setTrainingCategorySetup(TrainingCategorySetup trainingCategorySetup) {
        this.trainingCategorySetup = trainingCategorySetup;
    }

    public TrainingHeadSetup getTrainingHeadSetup() {
        return trainingHeadSetup;
    }

    public void setTrainingHeadSetup(TrainingHeadSetup trainingHeadSetup) {
        this.trainingHeadSetup = trainingHeadSetup;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public HrEmployeeInfo getHrEmployeeInfo() {
        return hrEmployeeInfo;
    }

    public void setHrEmployeeInfo(HrEmployeeInfo hrEmployeeInfo) {
        this.hrEmployeeInfo = hrEmployeeInfo;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public LocalDate getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDate approvedDate) {
        this.approvedDate = approvedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrainingRequisitionForm trainingRequisitionForm = (TrainingRequisitionForm) o;

        if ( ! Objects.equals(id, trainingRequisitionForm.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TrainingRequisitionForm{" +
            "id=" + id +
            ", requisitionCode='" + requisitionCode + "'" +
            ", trainingType='" + trainingType + "'" +
            ", session='" + session + "'" +
            ", applyDate='" + applyDate + "'" +
            ", reason='" + reason + "'" +
            ", fileName='" + fileName + "'" +
            ", file='" + file + "'" +
            ", fileContentType='" + fileContentType + "'" +
            ", fileContentName='" + fileContentName + "'" +
            ", applyBy='" + applyBy + "'" +
            ", institute='" + institute+ "'" +
            ", status='" + status + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
