package gov.step.app.domain.payroll;

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
 * A PrlSalaryGenerationLog.
 */
@Entity
@Table(name = "prl_salary_generation_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prlsalarygenerationlog")
public class PrlSalaryGenerationLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "generate_date")
    private LocalDate generateDate;

    @Column(name = "comments")
    private String comments;

    @Column(name = "action_status")
    private String actionStatus;

    @Column(name = "generate_by")
    private String generateBy;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @ManyToOne
    @JoinColumn(name = "generated_salary_info_id")
    private PrlGeneratedSalaryInfo generatedSalaryInfo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(LocalDate generateDate) {
        this.generateDate = generateDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public String getGenerateBy() {
        return generateBy;
    }

    public void setGenerateBy(String generateBy) {
        this.generateBy = generateBy;
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

    public PrlGeneratedSalaryInfo getGeneratedSalaryInfo() {
        return generatedSalaryInfo;
    }

    public void setGeneratedSalaryInfo(PrlGeneratedSalaryInfo PrlGeneratedSalaryInfo) {
        this.generatedSalaryInfo = PrlGeneratedSalaryInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrlSalaryGenerationLog prlSalaryGenerationLog = (PrlSalaryGenerationLog) o;
        return Objects.equals(id, prlSalaryGenerationLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrlSalaryGenerationLog{" +
            "id=" + id +
            ", generateDate='" + generateDate + "'" +
            ", comments='" + comments + "'" +
            ", actionStatus='" + actionStatus + "'" +
            ", generateBy='" + generateBy + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            '}';
    }
}
