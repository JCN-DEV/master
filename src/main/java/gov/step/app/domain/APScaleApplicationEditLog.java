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
 * A APScaleApplicationEditLog.
 */
@Entity
@Table(name = "ap_scale_app_edit_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "apscaleapplicationeditlog")
public class APScaleApplicationEditLog implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="ap_scale_application_seq")
    @SequenceGenerator(name="ap_scale_application_seq", sequenceName="ap_scale_application_seq")
    private Long id;

    @Column(name = "status")
    private Integer status;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_date")
    private LocalDate date;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;


    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;

    @ManyToOne
    @JoinColumn(name = "ap_scale_application_id")
    private APScaleApplication apScaleApplication;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public APScaleApplication getApScaleApplication() {
        return apScaleApplication;
    }

    public void setApScaleApplication(APScaleApplication apScaleApplication) {
        this.apScaleApplication = apScaleApplication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        APScaleApplicationEditLog aPScaleApplicationEditLog = (APScaleApplicationEditLog) o;

        if ( ! Objects.equals(id, aPScaleApplicationEditLog.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "APScaleApplicationEditLog{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", remarks='" + remarks + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
