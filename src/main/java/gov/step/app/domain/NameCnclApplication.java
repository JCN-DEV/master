package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A NameCnclApplication.
 */
@Entity
@Table(name = "name_cncl_application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "namecnclapplication")
public class NameCnclApplication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_date")
    private LocalDate created_date;

    @Column(name = "status")
    private Integer status;

    @Column(name = "resign_date")
    private Date resignDate;

    @Column(name = "cause")
    private String cause;

    @Column(name = "create_date")
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;

    @ManyToOne
    @JoinColumn(name = "INST_EMPLOYEE_ID")
    private InstEmployee instEmployee;

    @PrePersist
    protected void onCreate() {
        this.setCreated_date(LocalDate.now());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDate created_date) {
        this.created_date = created_date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getResignDate() {
        return resignDate;
    }

    public void setResignDate(Date resignDate) {
        this.resignDate = resignDate;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NameCnclApplication nameCnclApplication = (NameCnclApplication) o;

        if ( ! Objects.equals(id, nameCnclApplication.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NameCnclApplication{" +
            "id=" + id +
            ", created_date=" + created_date +
            ", status=" + status +
            ", resignDate=" + resignDate +
            ", cause='" + cause + '\'' +
            ", createDate=" + createDate +
            ", createBy=" + createBy +
            ", updateDate=" + updateDate +
            ", updateBy=" + updateBy +
            ", instEmployee=" + instEmployee +
            '}';
    }
}
