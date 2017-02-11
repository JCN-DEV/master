package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A JobPlacementInfo.
 */
@Entity
@Table(name = "job_placement_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jobplacementinfo")
public class JobPlacementInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "job_id")
    private String jobId;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "designation")
    private String designation;

    @Column(name = "department")
    private String department;

    @Column(name = "responsibility")
    private String responsibility;

    @Column(name = "work_from")
    private LocalDate workFrom;

    @Column(name = "work_to")
    private LocalDate workTo;

    @Column(name = "curr_work")
    private Boolean currWork;

    @Column(name = "experience")
    private String experience;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public LocalDate getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(LocalDate workFrom) {
        this.workFrom = workFrom;
    }

    public LocalDate getWorkTo() {
        return workTo;
    }

    public void setWorkTo(LocalDate workTo) {
        this.workTo = workTo;
    }

    public Boolean getCurrWork() {
        return currWork;
    }

    public void setCurrWork(Boolean currWork) {
        this.currWork = currWork;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobPlacementInfo jobPlacementInfo = (JobPlacementInfo) o;

        if ( ! Objects.equals(id, jobPlacementInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobPlacementInfo{" +
            "id=" + id +
            ", jobId='" + jobId + "'" +
            ", orgName='" + orgName + "'" +
            ", description='" + description + "'" +
            ", address='" + address + "'" +
            ", designation='" + designation + "'" +
            ", department='" + department + "'" +
            ", responsibility='" + responsibility + "'" +
            ", workFrom='" + workFrom + "'" +
            ", workTo='" + workTo + "'" +
            ", currWork='" + currWork + "'" +
            ", experience='" + experience + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateBy='" + updateBy + "'" +
            ", updateDate='" + updateDate + "'" +
            '}';
    }
}
