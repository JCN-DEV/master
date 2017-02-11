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
 * A HrEmpActingDutyInfo.
 */
@Entity
@Table(name = "hr_emp_acting_duty_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrempactingdutyinfo")
public class HrEmpActingDutyInfo implements Serializable {

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

    @Column(name = "active_status")
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
        HrEmpActingDutyInfo hrEmpActingDutyInfo = (HrEmpActingDutyInfo) o;
        return Objects.equals(id, hrEmpActingDutyInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpActingDutyInfo{" +
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
