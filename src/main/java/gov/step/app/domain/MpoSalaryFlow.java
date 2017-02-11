package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MpoSalaryFlow.
 */
@Entity
@Table(name = "mpo_salary_flow")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mposalaryflow")
public class MpoSalaryFlow implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "report_name")
    private String reportName;

    @Column(name = "urls")
    private String urls;

    @Column(name = "forward_to")
    private Long forwardTo;

    @Column(name = "forward_from")
    private Long forwardFrom;

    @Column(name = "forward_to_role")
    private String forwardToRole;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "approve_status")
    private String approveStatus;

    @Column(name = "user_lock")
    private Boolean userLock;

    @Column(name = "levels")
    private Long levels;

    @Column(name = "dte_status")
    private String dteStatus;

    @Column(name = "dte_id")
    private Long dteId;

    @Column(name = "dte_approve_date")
    private LocalDate dteApproveDate;

    @Column(name = "ministry_status")
    private String ministryStatus;

    @Column(name = "ministry_id")
    private Long ministryId;

    @Column(name = "ministry_approve_date")
    private LocalDate ministryApproveDate;

    @Column(name = "ag_status")
    private String agStatus;

    @Column(name = "ag_id")
    private Long agId;

    @Column(name = "ag_approve_date")
    private LocalDate agApproveDate;

    @Column(name = "dte_comments")
    private String dteComments;

    @Column(name = "ministry_comments")
    private String ministryComments;

    @Column(name = "ag_comments")
    private String agComments;

    @Column(name = "comments")
    private String comments;

    @Column(name = "report_month")
    private String reportMonth;

    @Column(name = "report_year")
    private String reportYear;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public Long getForwardTo() {
        return forwardTo;
    }

    public void setForwardTo(Long forwardTo) {
        this.forwardTo = forwardTo;
    }

    public Long getForwardFrom() {
        return forwardFrom;
    }

    public void setForwardFrom(Long forwardFrom) {
        this.forwardFrom = forwardFrom;
    }

    public String getForwardToRole() {
        return forwardToRole;
    }

    public void setForwardToRole(String forwardToRole) {
        this.forwardToRole = forwardToRole;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Boolean getUserLock() {
        return userLock;
    }

    public void setUserLock(Boolean userLock) {
        this.userLock = userLock;
    }

    public Long getLevels() {
        return levels;
    }

    public void setLevels(Long levels) {
        this.levels = levels;
    }

    public String getDteStatus() {
        return dteStatus;
    }

    public void setDteStatus(String dteStatus) {
        this.dteStatus = dteStatus;
    }

    public Long getDteId() {
        return dteId;
    }

    public void setDteId(Long dteId) {
        this.dteId = dteId;
    }

    public LocalDate getDteApproveDate() {
        return dteApproveDate;
    }

    public void setDteApproveDate(LocalDate dteApproveDate) {
        this.dteApproveDate = dteApproveDate;
    }

    public String getMinistryStatus() {
        return ministryStatus;
    }

    public void setMinistryStatus(String ministryStatus) {
        this.ministryStatus = ministryStatus;
    }

    public Long getMinistryId() {
        return ministryId;
    }

    public void setMinistryId(Long ministryId) {
        this.ministryId = ministryId;
    }

    public LocalDate getMinistryApproveDate() {
        return ministryApproveDate;
    }

    public void setMinistryApproveDate(LocalDate ministryApproveDate) {
        this.ministryApproveDate = ministryApproveDate;
    }

    public String getAgStatus() {
        return agStatus;
    }

    public void setAgStatus(String agStatus) {
        this.agStatus = agStatus;
    }

    public Long getAgId() {
        return agId;
    }

    public void setAgId(Long agId) {
        this.agId = agId;
    }

    public LocalDate getAgApproveDate() {
        return agApproveDate;
    }

    public void setAgApproveDate(LocalDate agApproveDate) {
        this.agApproveDate = agApproveDate;
    }

    public String getDteComments() {
        return dteComments;
    }

    public void setDteComments(String dteComments) {
        this.dteComments = dteComments;
    }

    public String getMinistryComments() {
        return ministryComments;
    }

    public void setMinistryComments(String ministryComments) {
        this.ministryComments = ministryComments;
    }

    public String getAgComments() {
        return agComments;
    }

    public void setAgComments(String agComments) {
        this.agComments = agComments;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReportMonth() {
        return reportMonth;
    }

    public void setReportMonth(String reportMonth) {
        this.reportMonth = reportMonth;
    }

    public String getReportYear() {
        return reportYear;
    }

    public void setReportYear(String reportYear) {
        this.reportYear = reportYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MpoSalaryFlow mpoSalaryFlow = (MpoSalaryFlow) o;

        if ( ! Objects.equals(id, mpoSalaryFlow.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MpoSalaryFlow{" +
            "id=" + id +
            ", reportName='" + reportName + "'" +
            ", urls='" + urls + "'" +
            ", forwardTo='" + forwardTo + "'" +
            ", forwardFrom='" + forwardFrom + "'" +
            ", forwardToRole='" + forwardToRole + "'" +
            ", status='" + status + "'" +
            ", approveStatus='" + approveStatus + "'" +
            ", userLock='" + userLock + "'" +
            ", levels='" + levels + "'" +
            ", dteStatus='" + dteStatus + "'" +
            ", dteId='" + dteId + "'" +
            ", dteApproveDate='" + dteApproveDate + "'" +
            ", ministryStatus='" + ministryStatus + "'" +
            ", ministryId='" + ministryId + "'" +
            ", ministryApproveDate='" + ministryApproveDate + "'" +
            ", agStatus='" + agStatus + "'" +
            ", agId='" + agId + "'" +
            ", agApproveDate='" + agApproveDate + "'" +
            ", dteComments='" + dteComments + "'" +
            ", ministryComments='" + ministryComments + "'" +
            ", agComments='" + agComments + "'" +
            ", comments='" + comments + "'" +
            '}';
    }
}
