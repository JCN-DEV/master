package gov.step.app.domain;

import gov.step.app.domain.enumeration.EmployeeType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Vacancy.
 */
@Entity
@Table(name = "vacancy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "vacancy")
public class Vacancy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "prom_gbresol_date", nullable = false)
    private LocalDate promotionGBResolutionDate;

    @Column(name = "total_service_tenure")
    private Integer totalServiceTenure;

    @Column(name = "current_job_duration")
    private Integer currentJobDuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EmployeeType status;

    @Column(name = "remarks")
    private String remarks;

    @OneToOne
    private Employee employee;

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

    public LocalDate getPromotionGBResolutionDate() {
        return promotionGBResolutionDate;
    }

    public void setPromotionGBResolutionDate(LocalDate promotionGBResolutionDate) {
        this.promotionGBResolutionDate = promotionGBResolutionDate;
    }

    public Integer getTotalServiceTenure() {
        return totalServiceTenure;
    }

    public void setTotalServiceTenure(Integer totalServiceTenure) {
        this.totalServiceTenure = totalServiceTenure;
    }

    public Integer getCurrentJobDuration() {
        return currentJobDuration;
    }

    public void setCurrentJobDuration(Integer currentJobDuration) {
        this.currentJobDuration = currentJobDuration;
    }

    public EmployeeType getStatus() {
        return status;
    }

    public void setStatus(EmployeeType status) {
        this.status = status;
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

        Vacancy vacancy = (Vacancy) o;

        if (!Objects.equals(id, vacancy.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Vacancy{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", promotionGBResolutionDate='" + promotionGBResolutionDate + "'" +
            ", totalServiceTenure='" + totalServiceTenure + "'" +
            ", currentJobDuration='" + currentJobDuration + "'" +
            ", status='" + status + "'" +
            ", remarks='" + remarks + "'" +
            '}';
    }
}
