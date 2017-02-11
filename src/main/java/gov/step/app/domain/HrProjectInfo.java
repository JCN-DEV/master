package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A HrProjectInfo.
 */
@Entity
@Table(name = "hr_project_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrprojectinfo")
public class HrProjectInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "project_detail")
    private String projectDetail;

    @NotNull
    @Column(name = "director_name", nullable = false)
    private String directorName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Column(name = "project_value", precision=10, scale=2, nullable = false)
    private BigDecimal projectValue;

    @NotNull
    @Column(name = "project_status", nullable = false)
    private String projectStatus;

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
    @JoinColumn(name = "fund_source_id")
    private MiscTypeSetup fundSource;

    /* Newly Added */
    @Column(name = "joining_date_pd")
    private LocalDate joiningDatePd;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "project_location")
    private String projectLocation;

    @Column(name = "est_total_cost_original")
    private BigDecimal estTotalCostOriginal;

    @Column(name = "est_gob_cost_original")
    private BigDecimal estGobCostOriginal;

    @Column(name = "est_pa_cost_original")
    private BigDecimal estPaCostOriginal;

    @Column(name = "est_total_cost_revised")
    private BigDecimal estTotalCostRevised;

    @Column(name = "est_gob_cost_revised")
    private BigDecimal estGobCostRevised;

    @Column(name = "est_pa_cost_revised")
    private BigDecimal estPaCostRevised;

    @Column(name = "cumul_progress_last_june")
    private BigDecimal cumulProgressLastJune;

    @Column(name = "current_year_total_alloc")
    private BigDecimal currentYearTotalAlloc;

    @Column(name = "current_year_gob_alloc")
    private BigDecimal currentYearGobAlloc;

    @Column(name = "current_year_pa_alloc")
    private BigDecimal currentYearPaAlloc;

    @Column(name = "current_month_total_prog")
    private BigDecimal currentMonthTotalProg;

    @Column(name = "current_month_gob_prog")
    private BigDecimal currentMonthGobProg;

    @Column(name = "current_month_pa_prog")
    private BigDecimal currentMonthPaProg;

    @Column(name = "current_year_progress")
    private BigDecimal currentYearProgress;

    @Column(name = "current_year_release")
    private BigDecimal currentYearRelease;

    @Column(name = "project_progress_total")
    private BigDecimal projectProgressTotal;

    @Column(name = "project_progress_revenue")
    private BigDecimal projectProgressRevenue;

    @Column(name = "project_progress_capital")
    private BigDecimal projectProgressCapital;

    @Column(name = "project_manpower")
    private BigDecimal projectManpower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(String projectDetail) {
        this.projectDetail = projectDetail;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getProjectValue() {
        return projectValue;
    }

    public void setProjectValue(BigDecimal projectValue) {
        this.projectValue = projectValue;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
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

    public MiscTypeSetup getFundSource() {
        return fundSource;
    }

    public void setFundSource(MiscTypeSetup MiscTypeSetup) {
        this.fundSource = MiscTypeSetup;
    }

    public LocalDate getJoiningDatePd() {
        return joiningDatePd;
    }

    public void setJoiningDatePd(LocalDate joiningDatePd) {
        this.joiningDatePd = joiningDatePd;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }

    public BigDecimal getEstTotalCostOriginal() {
        return estTotalCostOriginal;
    }

    public void setEstTotalCostOriginal(BigDecimal estTotalCostOriginal) {
        this.estTotalCostOriginal = estTotalCostOriginal;
    }

    public BigDecimal getEstGobCostOriginal() {
        return estGobCostOriginal;
    }

    public void setEstGobCostOriginal(BigDecimal estGobCostOriginal) {
        this.estGobCostOriginal = estGobCostOriginal;
    }

    public BigDecimal getEstPaCostOriginal() {
        return estPaCostOriginal;
    }

    public void setEstPaCostOriginal(BigDecimal estPaCostOriginal) {
        this.estPaCostOriginal = estPaCostOriginal;
    }

    public BigDecimal getEstTotalCostRevised() {
        return estTotalCostRevised;
    }

    public void setEstTotalCostRevised(BigDecimal estTotalCostRevised) {
        this.estTotalCostRevised = estTotalCostRevised;
    }

    public BigDecimal getEstGobCostRevised() {
        return estGobCostRevised;
    }

    public void setEstGobCostRevised(BigDecimal estGobCostRevised) {
        this.estGobCostRevised = estGobCostRevised;
    }

    public BigDecimal getEstPaCostRevised() {
        return estPaCostRevised;
    }

    public void setEstPaCostRevised(BigDecimal estPaCostRevised) {
        this.estPaCostRevised = estPaCostRevised;
    }

    public BigDecimal getCumulProgressLastJune() {
        return cumulProgressLastJune;
    }

    public void setCumulProgressLastJune(BigDecimal cumulProgressLastJune) {
        this.cumulProgressLastJune = cumulProgressLastJune;
    }

    public BigDecimal getCurrentYearTotalAlloc() {
        return currentYearTotalAlloc;
    }

    public void setCurrentYearTotalAlloc(BigDecimal currentYearTotalAlloc) {
        this.currentYearTotalAlloc = currentYearTotalAlloc;
    }

    public BigDecimal getCurrentYearGobAlloc() {
        return currentYearGobAlloc;
    }

    public void setCurrentYearGobAlloc(BigDecimal currentYearGobAlloc) {
        this.currentYearGobAlloc = currentYearGobAlloc;
    }

    public BigDecimal getCurrentYearPaAlloc() {
        return currentYearPaAlloc;
    }

    public void setCurrentYearPaAlloc(BigDecimal currentYearPaAlloc) {
        this.currentYearPaAlloc = currentYearPaAlloc;
    }

    public BigDecimal getCurrentMonthTotalProg() {
        return currentMonthTotalProg;
    }

    public void setCurrentMonthTotalProg(BigDecimal currentMonthTotalProg) {
        this.currentMonthTotalProg = currentMonthTotalProg;
    }

    public BigDecimal getCurrentMonthGobProg() {
        return currentMonthGobProg;
    }

    public void setCurrentMonthGobProg(BigDecimal currentMonthGobProg) {
        this.currentMonthGobProg = currentMonthGobProg;
    }

    public BigDecimal getCurrentMonthPaProg() {
        return currentMonthPaProg;
    }

    public void setCurrentMonthPaProg(BigDecimal currentMonthPaProg) {
        this.currentMonthPaProg = currentMonthPaProg;
    }

    public BigDecimal getCurrentYearProgress() {
        return currentYearProgress;
    }

    public void setCurrentYearProgress(BigDecimal currentYearProgress) {
        this.currentYearProgress = currentYearProgress;
    }

    public BigDecimal getCurrentYearRelease() {
        return currentYearRelease;
    }

    public void setCurrentYearRelease(BigDecimal currentYearRelease) {
        this.currentYearRelease = currentYearRelease;
    }

    public BigDecimal getProjectProgressTotal() {
        return projectProgressTotal;
    }

    public void setProjectProgressTotal(BigDecimal projectProgressTotal) {
        this.projectProgressTotal = projectProgressTotal;
    }

    public BigDecimal getProjectProgressRevenue() {
        return projectProgressRevenue;
    }

    public void setProjectProgressRevenue(BigDecimal projectProgressRevenue) {
        this.projectProgressRevenue = projectProgressRevenue;
    }

    public BigDecimal getProjectProgressCapital() {
        return projectProgressCapital;
    }

    public void setProjectProgressCapital(BigDecimal projectProgressCapital) {
        this.projectProgressCapital = projectProgressCapital;
    }

    public BigDecimal getProjectManpower() {
        return projectManpower;
    }

    public void setProjectManpower(BigDecimal projectManpower) {
        this.projectManpower = projectManpower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrProjectInfo hrProjectInfo = (HrProjectInfo) o;
        return Objects.equals(id, hrProjectInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrProjectInfo{" +
            "id=" + id +
            ", projectName='" + projectName + "'" +
            ", projectDetail='" + projectDetail + "'" +
            ", directorName='" + directorName + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", projectValue='" + projectValue + "'" +
            ", projectStatus='" + projectStatus + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
