package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A HrGazetteSetup.
 */
@Entity
@Table(name = "hr_gazette_setup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrgazettesetup")
public class HrGazetteSetup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "gazette_code")
    private String gazetteCode;

    @NotNull
    @Column(name = "gazette_name", nullable = false)
    private String gazetteName;

    @Column(name = "gazette_year")
    private String gazetteYear;

    @Column(name = "gazette_detail")
    private String gazetteDetail;

    @NotNull
    @Column(name = "active_status", nullable = false)
    private Boolean activeStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGazetteCode() {
        return gazetteCode;
    }

    public void setGazetteCode(String gazetteCode) {
        this.gazetteCode = gazetteCode;
    }

    public String getGazetteName() {
        return gazetteName;
    }

    public void setGazetteName(String gazetteName) {
        this.gazetteName = gazetteName;
    }

    public String getGazetteYear() {
        return gazetteYear;
    }

    public void setGazetteYear(String gazetteYear) {
        this.gazetteYear = gazetteYear;
    }

    public String getGazetteDetail() {
        return gazetteDetail;
    }

    public void setGazetteDetail(String gazetteDetail) {
        this.gazetteDetail = gazetteDetail;
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
        HrGazetteSetup hrGazetteSetup = (HrGazetteSetup) o;
        return Objects.equals(id, hrGazetteSetup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrGazetteSetup{" +
            "id=" + id +
            ", gazetteCode='" + gazetteCode + "'" +
            ", gazetteName='" + gazetteName + "'" +
            ", gazetteYear='" + gazetteYear + "'" +
            ", gazetteDetail='" + gazetteDetail + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
