package gov.step.app.domain;

import gov.step.app.domain.enumeration.designationType;
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
 * A HrDesignationHeadSetup.
 */
@Entity
@Table(name = "hr_designation_head_setup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrdesignationheadsetup")
public class HrDesignationHeadSetup implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "designation_code")
    private String designationCode;

    @Column(name = "designation_name")
    private String designationName;

    @Column(name = "designation_detail")
    private String designationDetail;

    @Column(name = "designation_level")
    private int designationLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "desig_type")
    private designationType desigType;

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

    public String getDesignationCode() {
        return designationCode;
    }

    public void setDesignationCode(String designationCode) {
        this.designationCode = designationCode;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getDesignationDetail() {
        return designationDetail;
    }

    public void setDesignationDetail(String designationDetail) {
        this.designationDetail = designationDetail;
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

    public designationType getDesigType() {return desigType;}

    public void setDesigType(designationType desigType) {this.desigType = desigType;}

    public int getDesignationLevel() {return designationLevel;}

    public void setDesignationLevel(int designationLevel) {this.designationLevel = designationLevel;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrDesignationHeadSetup hrDesignationHeadSetup = (HrDesignationHeadSetup) o;
        return Objects.equals(id, hrDesignationHeadSetup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrDesignationHeadSetup{" +
            "id=" + id +
            ", designationCode='" + designationCode + "'" +
            ", designationName='" + designationName + "'" +
            ", designationDetail='" + designationDetail + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
