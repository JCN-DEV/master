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
 * A JpEmploymentHistory.
 */
@Entity
@Table(name = "jp_employment_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jpemploymenthistory")
public class JpEmploymentHistory implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="jp_emp_hist_seq")
    @SequenceGenerator(name="jp_emp_hist_seq", sequenceName="jp_emp_hist_seq")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;

    //@Size(max = 200)
    @Column(name = "company_business")
    private String companyBusiness;

    //@Size(max = 200)
    @Column(name = "company_location")
    private String companyLocation;

    //@NotNull
    @Size(max = 100)
    @Column(name = "position_held")
    private String positionHeld;

   // @NotNull
    //@Size(max = 100)
    @Column(name = "department_name")
    private String departmentName;

    //@NotNull
   // @Size(max = 200)
    @Column(name = "responsibility")
    private String responsibility;

    @NotNull
    @Column(name = "start_from", nullable = false)
    private LocalDate startFrom;

    @Column(name = "end_to")
    private LocalDate endTo;

    @Column(name = "current_status")
    private Boolean currentStatus;


    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;


    @ManyToOne
    @JoinColumn(name = "jp_employee_id")
    private JpEmployee jpEmployee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyBusiness() {
        return companyBusiness;
    }

    public void setCompanyBusiness(String companyBusiness) {
        this.companyBusiness = companyBusiness;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getPositionHeld() {
        return positionHeld;
    }

    public void setPositionHeld(String positionHeld) {
        this.positionHeld = positionHeld;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public LocalDate getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(LocalDate startFrom) {
        this.startFrom = startFrom;
    }

    public LocalDate getEndTo() {
        return endTo;
    }

    public void setEndTo(LocalDate endTo) {
        this.endTo = endTo;
    }

    public Boolean getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Boolean currentStatus) {
        this.currentStatus = currentStatus;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public JpEmployee getJpEmployee() {
        return jpEmployee;
    }

    public void setJpEmployee(JpEmployee JpEmployee) {
        this.jpEmployee = JpEmployee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JpEmploymentHistory jpEmploymentHistory = (JpEmploymentHistory) o;

        if ( ! Objects.equals(id, jpEmploymentHistory.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JpEmploymentHistory{" +
            "id=" + id +
            ", companyName='" + companyName + "'" +
            ", companyBusiness='" + companyBusiness + "'" +
            ", companyLocation='" + companyLocation + "'" +
            ", positionHeld='" + positionHeld + "'" +
            ", departmentName='" + departmentName + "'" +
            ", responsibility='" + responsibility + "'" +
            ", startFrom='" + startFrom + "'" +
            ", endTo='" + endTo + "'" +
            ", currentStatus='" + currentStatus + "'" +
            '}';
    }
}
