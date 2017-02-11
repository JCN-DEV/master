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
 * A HrEmpActingDutyInfoLog.
 */
@Entity
@Table(name = "hr_emp_acting_duty_info_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrempactingdutyinfolog")
public class HrEmpActingDutyInfoLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "to_institution")
    private String toInstitution;

    @Column(name = "designation")
    private String designation;

    @Column(name = "to_department")
    private String toDepartment;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "office_order_number")
    private String officeOrderNumber;

    @Column(name = "office_order_date")
    private LocalDate officeOrderDate;

    @Column(name = "work_on_acting_duty")
    private String workOnActingDuty;

    @Column(name = "comments")
    private String comments;

    @Column(name = "go_date")
    private LocalDate goDate;

    @Lob
    @Column(name = "go_doc")
    private byte[] goDoc;

    @Column(name = "go_doc_content_type")
    private String goDocContentType;

    @Column(name = "go_doc_name")
    private String goDocName;

    @Column(name = "go_number")
    private String goNumber;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToInstitution() {
        return toInstitution;
    }

    public void setToInstitution(String toInstitution) {
        this.toInstitution = toInstitution;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getToDepartment() {
        return toDepartment;
    }

    public void setToDepartment(String toDepartment) {
        this.toDepartment = toDepartment;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getOfficeOrderNumber() {
        return officeOrderNumber;
    }

    public void setOfficeOrderNumber(String officeOrderNumber) {
        this.officeOrderNumber = officeOrderNumber;
    }

    public LocalDate getOfficeOrderDate() {
        return officeOrderDate;
    }

    public void setOfficeOrderDate(LocalDate officeOrderDate) {
        this.officeOrderDate = officeOrderDate;
    }

    public String getWorkOnActingDuty() {
        return workOnActingDuty;
    }

    public void setWorkOnActingDuty(String workOnActingDuty) {
        this.workOnActingDuty = workOnActingDuty;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public String getGoNumber() {return goNumber;}

    public void setGoNumber(String goNumber) {this.goNumber = goNumber;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEmpActingDutyInfoLog hrEmpActingDutyInfoLog = (HrEmpActingDutyInfoLog) o;
        return Objects.equals(id, hrEmpActingDutyInfoLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpActingDutyInfoLog{" +
            "id=" + id +
            ", toInstitution='" + toInstitution + "'" +
            ", designation='" + designation + "'" +
            ", toDepartment='" + toDepartment + "'" +
            ", fromDate='" + fromDate + "'" +
            ", toDate='" + toDate + "'" +
            ", officeOrderNumber='" + officeOrderNumber + "'" +
            ", officeOrderDate='" + officeOrderDate + "'" +
            ", workOnActingDuty='" + workOnActingDuty + "'" +
            ", comments='" + comments + "'" +
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
