package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import gov.step.app.domain.enumeration.JobApplicationStatus;

import gov.step.app.domain.enumeration.CvType;

/**
 * A Jobapplication.
 */
@Entity
@Table(name = "jobapplication")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jobapplication")


/*@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "job_emp_dtl", procedureName = "job_emp_dtl", resultClasses = Jobapplication.class,parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_location", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.OUT, name = "in_location", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_age_frm", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.OUT, name = "in_age_frm", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_age_to", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.OUT, name = "in_age_to", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_applicant_name", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.OUT, name = "in_applicant_name", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_gender", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.OUT, name = "in_gender", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_exprience", type = Double.class),
            @StoredProcedureParameter(mode = ParameterMode.OUT, name = "in_exprience", type = Double.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_salary_from", type = Double.class),
            @StoredProcedureParameter(mode = ParameterMode.OUT, name = "in_salary_from", type = Double.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_salary_to", type = Double.class),
            @StoredProcedureParameter(mode = ParameterMode.OUT, name = "in_salary_to", type = Double.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_usercode", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.OUT, name = "in_usercode", type = String.class)
        }),

})*/
public class Jobapplication implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="job_application_seq")
    @SequenceGenerator(name="job_application_seq", sequenceName="job_application_seq")
    private Long id;

    @Column(name = "note")
    private String note;

    @Lob
    @Column(name = "cv")
    private byte[] cv;


    @Column(name = "cv_content_type")
    private String cvContentType;

    @Column(name = "expected_salary", precision=10, scale=2)
    private BigDecimal expectedSalary;

    @Enumerated(EnumType.STRING)
    @Column(name = "applicant_status")
    private JobApplicationStatus applicantStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "cv_type")
    private CvType cvType;

    @Column(name = "applied_date")
    private LocalDate appliedDate;

    @Column(name = "cv_viewed")
    private Boolean cvViewed = Boolean.FALSE;

    /*@ManyToOne
    private User user;*/


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

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getCv() {
        return cv;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public String getCvContentType() {
        return cvContentType;
    }

    public void setCvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
    }

    public BigDecimal getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(BigDecimal expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public JobApplicationStatus getApplicantStatus() {
        return applicantStatus;
    }

    public void setApplicantStatus(JobApplicationStatus applicantStatus) {
        this.applicantStatus = applicantStatus;
    }

    public CvType getCvType() {
        return cvType;
    }

    public void setCvType(CvType cvType) {
        this.cvType = cvType;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }

    public Boolean getCvViewed() {
        return cvViewed;
    }

    public void setCvViewed(Boolean cvViewed) {
        this.cvViewed = cvViewed;
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

    public void setJpEmployee(JpEmployee jpEmployee) {
        this.jpEmployee = jpEmployee;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Jobapplication jobapplication = (Jobapplication) o;

        if ( ! Objects.equals(id, jobapplication.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Jobapplication{" +
            "id=" + id +
            ", note='" + note + "'" +
            ", cv='" + cv + "'" +
            ", cvContentType='" + cvContentType + "'" +
            ", expectedSalary='" + expectedSalary + "'" +
            ", applicantStatus='" + applicantStatus + "'" +
            ", cvType='" + cvType + "'" +
            ", appliedDate='" + appliedDate + "'" +
            '}';
    }
}
