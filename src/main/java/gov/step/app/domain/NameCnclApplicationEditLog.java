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
 * A NameCnclApplicationEditLog.
 */
@Entity
@Table(name = "name_cncl_app_edit_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "namecnclapplicationeditlog")
public class NameCnclApplicationEditLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status")
    private Integer status;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_date")
    private LocalDate created_date;

    @ManyToOne
    @JoinColumn(name = "NAME_CNCL_APP_ID")
    private NameCnclApplication nameCnclApplication;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDate created_date) {
        this.created_date = created_date;
    }

    public NameCnclApplication getNameCnclApplication() {
        return nameCnclApplication;
    }

    public void setNameCnclApplication(NameCnclApplication nameCnclApplication) {
        this.nameCnclApplication = nameCnclApplication;
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

        NameCnclApplicationEditLog nameCnclApplicationEditLog = (NameCnclApplicationEditLog) o;

        if ( ! Objects.equals(id, nameCnclApplicationEditLog.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NameCnclApplicationEditLog{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", remarks='" + remarks + "'" +
            ", created_date='" + created_date + "'" +
            '}';
    }
}
