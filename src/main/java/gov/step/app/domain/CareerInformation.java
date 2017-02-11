package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CareerInformation.
 */
@Entity
@Table(name = "career_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "careerinformation")
public class CareerInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "objectives", nullable = false)
    private String objectives;

    @NotNull
    @Size(min = 2)
    @Column(name = "keyword", nullable = false)
    private String keyword;

    @Column(name = "present_salary", precision = 10, scale = 2, nullable = false)
    private BigDecimal presentSalary;

    @Column(name = "expected_salary", precision = 10, scale = 2, nullable = false)
    private BigDecimal expectedSalary;

    @NotNull
    @Size(min = 3)
    @Column(name = "look_for_nature", nullable = false)
    private String lookForNature;

    @Column(name = "available_from", nullable = false)
    private LocalDate availableFrom;

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

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public BigDecimal getPresentSalary() {
        return presentSalary;
    }

    public void setPresentSalary(BigDecimal presentSalary) {
        this.presentSalary = presentSalary;
    }

    public BigDecimal getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(BigDecimal expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public String getLookForNature() {
        return lookForNature;
    }

    public void setLookForNature(String lookForNature) {
        this.lookForNature = lookForNature;
    }

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
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

        CareerInformation careerInformation = (CareerInformation) o;

        if (!Objects.equals(id, careerInformation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CareerInformation{" +
            "id=" + id +
            ", objectives='" + objectives + "'" +
            ", keyword='" + keyword + "'" +
            ", presentSalary='" + presentSalary + "'" +
            ", expectedSalary='" + expectedSalary + "'" +
            ", lookForNature='" + lookForNature + "'" +
            ", availableFrom='" + availableFrom + "'" +
            '}';
    }
}
