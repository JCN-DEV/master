package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ApplicantEducation.
 */
@Entity
@Table(name = "applicant_education")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "applicanteducation")
public class ApplicantEducation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 512)
    @Column(name = "name", length = 512, nullable = false)
    private String name;

    @Column(name = "applicant_group")
    private String applicantGroup;

    @Column(name = "gpa")
    private String gpa;

    @Column(name = "exam_year", nullable = false)
    private LocalDate examYear;

    @Column(name = "exam_result_date", nullable = false)
    private LocalDate examResultDate;

    @Size(max = 1024)
    @Column(name = "remarks", length = 1024)
    private String remarks;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Institute institute;

    @ManyToOne
    private User manager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplicantGroup() {
        return applicantGroup;
    }

    public void setApplicantGroup(String applicantGroup) {
        this.applicantGroup = applicantGroup;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public LocalDate getExamYear() {
        return examYear;
    }

    public void setExamYear(LocalDate examYear) {
        this.examYear = examYear;
    }

    public LocalDate getExamResultDate() {
        return examResultDate;
    }

    public void setExamResultDate(LocalDate examResultDate) {
        this.examResultDate = examResultDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User user) {
        this.manager = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicantEducation applicantEducation = (ApplicantEducation) o;

        if (!Objects.equals(id, applicantEducation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApplicantEducation{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", applicantGroup='" + applicantGroup + "'" +
            ", gpa='" + gpa + "'" +
            ", examYear='" + examYear + "'" +
            ", examResultDate='" + examResultDate + "'" +
            ", remarks='" + remarks + "'" +
            '}';
    }
}
