package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A JobPostingInfo.
 */
@Entity
@Table(name = "job_posting_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jobpostinginfo")
public class JobPostingInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "job_post_id")
    private String jobPostId;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "organizatin_name")
    private String organizatinName;

    @Column(name = "job_vacancy")
    private String jobVacancy;

    @Column(name = "salary_range")
    private String salaryRange;

    @Column(name = "job_source")
    private String jobSource;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(name = "application_date_line")
    private LocalDate applicationDateLine;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "job_file_name")
    private String jobFileName;

    @Lob
    @Column(name = "upload")
    private byte[] upload;


    @Column(name = "upload_content_type")
    private String uploadContentType;
    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    private JobType jobNature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(String jobPostId) {
        this.jobPostId = jobPostId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getOrganizatinName() {
        return organizatinName;
    }

    public void setOrganizatinName(String organizatinName) {
        this.organizatinName = organizatinName;
    }

    public String getJobVacancy() {
        return jobVacancy;
    }

    public void setJobVacancy(String jobVacancy) {
        this.jobVacancy = jobVacancy;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public String getJobSource() {
        return jobSource;
    }

    public void setJobSource(String jobSource) {
        this.jobSource = jobSource;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public LocalDate getApplicationDateLine() {
        return applicationDateLine;
    }

    public void setApplicationDateLine(LocalDate applicationDateLine) {
        this.applicationDateLine = applicationDateLine;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobFileName() {
        return jobFileName;
    }

    public void setJobFileName(String jobFileName) {
        this.jobFileName = jobFileName;
    }

    public byte[] getUpload() {
        return upload;
    }

    public void setUpload(byte[] upload) {
        this.upload = upload;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
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

    public JobType getJobNature() {
        return jobNature;
    }

    public void setJobNature(JobType jobType) {
        this.jobNature = jobType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobPostingInfo jobPostingInfo = (JobPostingInfo) o;

        if ( ! Objects.equals(id, jobPostingInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobPostingInfo{" +
            "id=" + id +
            ", jobPostId='" + jobPostId + "'" +
            ", jobTitle='" + jobTitle + "'" +
            ", organizatinName='" + organizatinName + "'" +
            ", jobVacancy='" + jobVacancy + "'" +
            ", salaryRange='" + salaryRange + "'" +
            ", jobSource='" + jobSource + "'" +
            ", publishedDate='" + publishedDate + "'" +
            ", applicationDateLine='" + applicationDateLine + "'" +
            ", jobDescription='" + jobDescription + "'" +
            ", jobFileName='" + jobFileName + "'" +
            ", upload='" + upload + "'" +
            ", uploadContentType='" + uploadContentType + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateBy='" + updateBy + "'" +
            ", updateDate='" + updateDate + "'" +
            '}';
    }
}
