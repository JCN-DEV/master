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
 * A HrEmpForeignTourInfo.
 */
@Entity
@Table(name = "hr_emp_foreign_tour_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrempforeigntourinfo")
public class HrEmpForeignTourInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "purpose", nullable = false)
    private String purpose;

    @NotNull
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @NotNull
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @NotNull
    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(name = "office_order_number")
    private String officeOrderNumber;

    @Column(name = "office_order_date")
    private LocalDate officeOrderDate;

    @Column(name = "fund_source")
    private String fundSource;

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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getOfficeOrderNumber() {
        return officeOrderNumber;
    }

    public void setOfficeOrderNumber(String officeOrderNumber) {
        this.officeOrderNumber = officeOrderNumber;
    }

    public String getFundSource() {
        return fundSource;
    }

    public void setFundSource(String fundSource) {
        this.fundSource = fundSource;
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

    public LocalDate getOfficeOrderDate() {return officeOrderDate;}

    public void setOfficeOrderDate(LocalDate officeOrderDate) {this.officeOrderDate = officeOrderDate;}

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
        HrEmpForeignTourInfo hrEmpForeignTourInfo = (HrEmpForeignTourInfo) o;
        return Objects.equals(id, hrEmpForeignTourInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpForeignTourInfo{" +
            "id=" + id +
            ", purpose='" + purpose + "'" +
            ", fromDate='" + fromDate + "'" +
            ", toDate='" + toDate + "'" +
            ", countryName='" + countryName + "'" +
            ", officeOrderNumber='" + officeOrderNumber + "'" +
            ", fundSource='" + fundSource + "'" +
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
