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
 * A LecturerSeniority.
 */
@Entity
@Table(name = "lecturer_seniority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "lecturerseniority")
public class LecturerSeniority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "serial")
    private Integer serial;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "subject")
    private String subject;

    @Column(name = "fst_mpoenlist_date", nullable = false)
    private LocalDate firstMPOEnlistingDate;

    @Column(name = "join_date_as_lecturer", nullable = false)
    private LocalDate joiningDateAsLecturer;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Size(max = 1024)
    @Column(name = "remarks", length = 1024)
    private String remarks;

    @ManyToOne
    private Institute institute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getFirstMPOEnlistingDate() {
        return firstMPOEnlistingDate;
    }

    public void setFirstMPOEnlistingDate(LocalDate firstMPOEnlistingDate) {
        this.firstMPOEnlistingDate = firstMPOEnlistingDate;
    }

    public LocalDate getJoiningDateAsLecturer() {
        return joiningDateAsLecturer;
    }

    public void setJoiningDateAsLecturer(LocalDate joiningDateAsLecturer) {
        this.joiningDateAsLecturer = joiningDateAsLecturer;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LecturerSeniority lecturerSeniority = (LecturerSeniority) o;

        if (!Objects.equals(id, lecturerSeniority.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LecturerSeniority{" +
            "id=" + id +
            ", serial='" + serial + "'" +
            ", name='" + name + "'" +
            ", subject='" + subject + "'" +
            ", firstMPOEnlistingDate='" + firstMPOEnlistingDate + "'" +
            ", joiningDateAsLecturer='" + joiningDateAsLecturer + "'" +
            ", dob='" + dob + "'" +
            ", remarks='" + remarks + "'" +
            '}';
    }
}
