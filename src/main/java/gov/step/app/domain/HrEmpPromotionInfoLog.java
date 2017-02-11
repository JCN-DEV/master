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
 * A HrEmpPromotionInfoLog.
 */
@Entity
@Table(name = "hr_emp_promotion_info_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hremppromotioninfolog")
public class HrEmpPromotionInfoLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "institute_from")
    private String instituteFrom;

    @NotNull
    @Column(name = "institute_to")
    private String instituteTo;

    @NotNull
    @Column(name = "department_from")
    private String departmentFrom;

    @NotNull
    @Column(name = "department_to")
    private String departmentTo;

    @NotNull
    @Column(name = "designation_from")
    private String designationFrom;

    @NotNull
    @Column(name = "designation_to")
    private String designationTo;

    @NotNull
    @Column(name = "payscale_from", precision=10, scale=2)
    private BigDecimal payscaleFrom;

    @NotNull
    @Column(name = "payscale_to", precision=10, scale=2)
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
    @JoinColumn(name = "job_category_id")
    private MiscTypeSetup jobCategory;


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

    public MiscTypeSetup getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(MiscTypeSetup MiscTypeSetup) {
        this.jobCategory = MiscTypeSetup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEmpPromotionInfoLog hrEmpPromotionInfoLog = (HrEmpPromotionInfoLog) o;
        return Objects.equals(id, hrEmpPromotionInfoLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpPromotionInfoLog{" +
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
