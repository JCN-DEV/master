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
 * A EducationalQualification.
 */
@Entity
@Table(name = "educational_qualification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "educationalqualification")
public class EducationalQualification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "level", nullable = false)
    private String level;

    @NotNull
    @Size(min = 2)
    @Column(name = "degree", nullable = false)
    private String degree;

    @NotNull
    @Size(min = 2)
    @Column(name = "major", nullable = false)
    private String major;

    @NotNull
    @Size(min = 3)
    @Column(name = "result", nullable = false)
    private String result;

    @NotNull
    @Column(name = "passing_year", nullable = false)
    private LocalDate passingYear;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private User manager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDate getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(LocalDate passingYear) {
        this.passingYear = passingYear;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

        EducationalQualification educationalQualification = (EducationalQualification) o;

        if (!Objects.equals(id, educationalQualification.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EducationalQualification{" +
            "id=" + id +
            ", level='" + level + "'" +
            ", degree='" + degree + "'" +
            ", major='" + major + "'" +
            ", result='" + result + "'" +
            ", passingYear='" + passingYear + "'" +
            ", duration='" + duration + "'" +
            '}';
    }
}
