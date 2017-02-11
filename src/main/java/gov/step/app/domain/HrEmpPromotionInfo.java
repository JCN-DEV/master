package gov.step.app.domain;

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

/**
 * A HrEmpPromotionInfo.
 */
@Entity
@Table(name = "hr_emp_promotion_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hremppromotioninfo")
public class HrEmpPromotionInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "institute_from", nullable = false)
    private String instituteFrom;

    @NotNull
    @Column(name = "institute_to", nullable = false)
    private String instituteTo;

    @NotNull
    @Column(name = "department_from", nullable = false)
    private String departmentFrom;

    @NotNull
    @Column(name = "department_to", nullable = false)
    private String departmentTo;

    @NotNull
    @Column(name = "designation_from", nullable = false)
    private String designationFrom;

    @NotNull
    @Column(name = "designation_to", nullable = false)
    private String designationTo;

    @NotNull
    @Column(name = "payscale_from", precision=10, scale=2, nullable = false)
    private BigDecimal payscaleFrom;

    @NotNull
    @Column(name = "payscale_to", precision=10, scale=2, nullable = false)
    private BigDecimal payscaleTo;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "promotion_type")
    private String promotionType;

    @Column(name = "office_order_no")
    private String officeOrderNo;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "go_date")
    private LocalDate goDate;

    @Lob
    @Column(name = "go_doc")
    private byte[] goDoc;

    @Column(name = "go_doc_content_type")
    private String goDocContentType;

    @Column(name = "go_doc_name")
    private String goDocName;

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
    @JoinColumn(name = "job_category_id")
    private MiscTypeSetup jobCategory;

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

    public String getInstituteFrom() {
        return instituteFrom;
    }

    public void setInstituteFrom(String instituteFrom) {
        this.instituteFrom = instituteFrom;
    }

    public String getInstituteTo() {
        return instituteTo;
    }

    public void setInstituteTo(String instituteTo) {
        this.instituteTo = instituteTo;
    }

    public String getDepartmentFrom() {
        return departmentFrom;
    }

    public void setDepartmentFrom(String departmentFrom) {
        this.departmentFrom = departmentFrom;
    }

    public String getDepartmentTo() {
        return departmentTo;
    }

    public void setDepartmentTo(String departmentTo) {
        this.departmentTo = departmentTo;
    }

    public String getDesignationFrom() {
        return designationFrom;
    }

    public void setDesignationFrom(String designationFrom) {
        this.designationFrom = designationFrom;
    }

    public String getDesignationTo() {
        return designationTo;
    }

    public void setDesignationTo(String designationTo) {
        this.designationTo = designationTo;
    }

    public BigDecimal getPayscaleFrom() {
        return payscaleFrom;
    }

    public void setPayscaleFrom(BigDecimal payscaleFrom) {
        this.payscaleFrom = payscaleFrom;
    }

    public BigDecimal getPayscaleTo() {
        return payscaleTo;
    }

    public void setPayscaleTo(BigDecimal payscaleTo) {
        this.payscaleTo = payscaleTo;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getOfficeOrderNo() {
        return officeOrderNo;
    }

    public void setOfficeOrderNo(String officeOrderNo) {
        this.officeOrderNo = officeOrderNo;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getGoDate() {
        return goDate;
    }

    public void setGoDate(LocalDate goDate) {
        this.goDate = goDate;
    }

    public byte[] getGoDoc() {
        return goDoc;
    }

    public void setGoDoc(byte[] goDoc) {
        this.goDoc = goDoc;
    }

    public String getGoDocContentType() {
        return goDocContentType;
    }

    public void setGoDocContentType(String goDocContentType) {
        this.goDocContentType = goDocContentType;
    }

    public String getGoDocName() {
        return goDocName;
    }

    public void setGoDocName(String goDocName) {
        this.goDocName = goDocName;
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

    public MiscTypeSetup getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(MiscTypeSetup MiscTypeSetup) {
        this.jobCategory = MiscTypeSetup;
    }

    public Long getLogId() { return logId;}

    public void setLogId(Long logId) {this.logId = logId;}

    public Long getLogStatus() {return logStatus;}

    public void setLogStatus(Long logStatus) {this.logStatus = logStatus;}

    public String getLogComments() {return logComments;}

    public void setLogComments(String logComments) {this.logComments = logComments;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEmpPromotionInfo hrEmpPromotionInfo = (HrEmpPromotionInfo) o;
        return Objects.equals(id, hrEmpPromotionInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpPromotionInfo{" +
            "id=" + id +
            ", instituteFrom='" + instituteFrom + "'" +
            ", instituteTo='" + instituteTo + "'" +
            ", departmentFrom='" + departmentFrom + "'" +
            ", departmentTo='" + departmentTo + "'" +
            ", designationFrom='" + designationFrom + "'" +
            ", designationTo='" + designationTo + "'" +
            ", payscaleFrom='" + payscaleFrom + "'" +
            ", payscaleTo='" + payscaleTo + "'" +
            ", joiningDate='" + joiningDate + "'" +
            ", promotionType='" + promotionType + "'" +
            ", officeOrderNo='" + officeOrderNo + "'" +
            ", orderDate='" + orderDate + "'" +
            ", goDate='" + goDate + "'" +
            ", goDoc='" + goDoc + "'" +
            ", goDocContentType='" + goDocContentType + "'" +
            ", goDocName='" + goDocName + "'" +
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
