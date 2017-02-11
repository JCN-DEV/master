package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PrincipalRequirement.
 */
@Entity
@Table(name = "principal_requirement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "principalrequirement")
public class PrincipalRequirement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_joining_date_as_lecturer")
    private LocalDate firstJoiningDateAsLecturer;

    @Column(name = "first_mpoenlisting_date_as_lecturer")
    private LocalDate firstMPOEnlistingDateAsLecturer;

    @Column(name = "first_joining_date_as_asst_prof")
    private LocalDate firstJoiningDateAsAsstProf;

    @Column(name = "first_mpoenlisting_date_asst_prof")
    private LocalDate firstMPOEnlistingDateAsstProf;

    @Column(name = "first_joining_date_as_vp")
    private LocalDate firstJoiningDateAsVP;

    @Column(name = "first_mpoenlisting_date_as_vp")
    private LocalDate firstMPOEnlistingDateAsVP;

    @ManyToOne
    private Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFirstJoiningDateAsLecturer() {
        return firstJoiningDateAsLecturer;
    }

    public void setFirstJoiningDateAsLecturer(LocalDate firstJoiningDateAsLecturer) {
        this.firstJoiningDateAsLecturer = firstJoiningDateAsLecturer;
    }

    public LocalDate getFirstMPOEnlistingDateAsLecturer() {
        return firstMPOEnlistingDateAsLecturer;
    }

    public void setFirstMPOEnlistingDateAsLecturer(LocalDate firstMPOEnlistingDateAsLecturer) {
        this.firstMPOEnlistingDateAsLecturer = firstMPOEnlistingDateAsLecturer;
    }

    public LocalDate getFirstJoiningDateAsAsstProf() {
        return firstJoiningDateAsAsstProf;
    }

    public void setFirstJoiningDateAsAsstProf(LocalDate firstJoiningDateAsAsstProf) {
        this.firstJoiningDateAsAsstProf = firstJoiningDateAsAsstProf;
    }

    public LocalDate getFirstMPOEnlistingDateAsstProf() {
        return firstMPOEnlistingDateAsstProf;
    }

    public void setFirstMPOEnlistingDateAsstProf(LocalDate firstMPOEnlistingDateAsstProf) {
        this.firstMPOEnlistingDateAsstProf = firstMPOEnlistingDateAsstProf;
    }

    public LocalDate getFirstJoiningDateAsVP() {
        return firstJoiningDateAsVP;
    }

    public void setFirstJoiningDateAsVP(LocalDate firstJoiningDateAsVP) {
        this.firstJoiningDateAsVP = firstJoiningDateAsVP;
    }

    public LocalDate getFirstMPOEnlistingDateAsVP() {
        return firstMPOEnlistingDateAsVP;
    }

    public void setFirstMPOEnlistingDateAsVP(LocalDate firstMPOEnlistingDateAsVP) {
        this.firstMPOEnlistingDateAsVP = firstMPOEnlistingDateAsVP;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrincipalRequirement principalRequirement = (PrincipalRequirement) o;

        if (!Objects.equals(id, principalRequirement.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrincipalRequirement{" +
            "id=" + id +
            ", firstJoiningDateAsLecturer='" + firstJoiningDateAsLecturer + "'" +
            ", firstMPOEnlistingDateAsLecturer='" + firstMPOEnlistingDateAsLecturer + "'" +
            ", firstJoiningDateAsAsstProf='" + firstJoiningDateAsAsstProf + "'" +
            ", firstMPOEnlistingDateAsstProf='" + firstMPOEnlistingDateAsstProf + "'" +
            ", firstJoiningDateAsVP='" + firstJoiningDateAsVP + "'" +
            ", firstMPOEnlistingDateAsVP='" + firstMPOEnlistingDateAsVP + "'" +
            '}';
    }
}
