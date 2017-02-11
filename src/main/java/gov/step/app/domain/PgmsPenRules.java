package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import gov.step.app.domain.enumeration.penQuota;

/**
 * A PgmsPenRules.
 */
@Entity
@Table(name = "pgms_pen_rules")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pgmspenrules")
public class PgmsPenRules implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "quota_type")
    private penQuota quotaType;

    @NotNull
    @Column(name = "min_age_limit", nullable = false)
    private Long minAgeLimit;

    @NotNull
    @Column(name = "max_age_limit", nullable = false)
    private Long maxAgeLimit;

    @NotNull
    @Column(name = "min_work_duration", nullable = false)
    private Long minWorkDuration;

    @Column(name = "disable")
    private Boolean disable;

    @Column(name = "senile")
    private Boolean senile;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public penQuota getQuotaType() {
        return quotaType;
    }

    public void setQuotaType(penQuota quotaType) {
        this.quotaType = quotaType;
    }

    public Long getMinAgeLimit() {
        return minAgeLimit;
    }

    public void setMinAgeLimit(Long minAgeLimit) {
        this.minAgeLimit = minAgeLimit;
    }

    public Long getMaxAgeLimit() {
        return maxAgeLimit;
    }

    public void setMaxAgeLimit(Long maxAgeLimit) {
        this.maxAgeLimit = maxAgeLimit;
    }

    public Long getMinWorkDuration() {
        return minWorkDuration;
    }

    public void setMinWorkDuration(Long minWorkDuration) {
        this.minWorkDuration = minWorkDuration;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Boolean getSenile() {
        return senile;
    }

    public void setSenile(Boolean senile) {
        this.senile = senile;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
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

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
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

        PgmsPenRules pgmsPenRules = (PgmsPenRules) o;

        if ( ! Objects.equals(id, pgmsPenRules.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PgmsPenRules{" +
            "id=" + id +
            ", quotaType='" + quotaType + "'" +
            ", minAgeLimit='" + minAgeLimit + "'" +
            ", maxAgeLimit='" + maxAgeLimit + "'" +
            ", minWorkDuration='" + minWorkDuration + "'" +
            ", disable='" + disable + "'" +
            ", senile='" + senile + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
