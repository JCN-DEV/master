package gov.step.app.domain;

import gov.step.app.domain.enumeration.designationType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A HrDesignationSetup.
 */
@Entity
@Table(name = "hr_designation_setup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrdesignationsetup")
public class HrDesignationSetup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "elocatted_position")
    private Long elocattedPosition;

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

    @Column(name = "organization_type")
    private String organizationType;

    @ManyToOne
    @JoinColumn(name = "org_category_id")
    private MiscTypeSetup organizationCategory;

    @ManyToOne
    @JoinColumn(name = "org_info_id")
    private HrEmpWorkAreaDtlInfo organizationInfo;

    @ManyToOne
    @JoinColumn(name = "grade_info_id")
    private HrGradeSetup gradeInfo;

    @ManyToOne
    @JoinColumn(name = "designation_info_id")
    private HrDesignationHeadSetup designationInfo;

    @ManyToOne
    @JoinColumn(name = "inst_category_id")
    private InstCategory instCategory;

    @ManyToOne
    @JoinColumn(name = "inst_level_id")
    private InstLevel instLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "desig_type")
    private designationType desigType;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getElocattedPosition() {
        return elocattedPosition;
    }

    public void setElocattedPosition(Long elocattedPosition) {
        this.elocattedPosition = elocattedPosition;
    }

    public MiscTypeSetup getOrganizationCategory() {return organizationCategory;}

    public void setOrganizationCategory(MiscTypeSetup organizationCategory) {this.organizationCategory = organizationCategory;}

    public HrEmpWorkAreaDtlInfo getOrganizationInfo() {return organizationInfo;}

    public void setOrganizationInfo(HrEmpWorkAreaDtlInfo organizationInfo) {this.organizationInfo = organizationInfo;}

    public HrGradeSetup getGradeInfo() {return gradeInfo;}

    public void setGradeInfo(HrGradeSetup gradeInfo) {this.gradeInfo = gradeInfo;}

    public HrDesignationHeadSetup getDesignationInfo() {return designationInfo;}

    public void setDesignationInfo(HrDesignationHeadSetup designationInfo) {this.designationInfo = designationInfo;}

    public String getOrganizationType() {return organizationType;}

    public void setOrganizationType(String organizationType) {this.organizationType = organizationType;}

    public InstCategory getInstCategory() {return instCategory;}

    public void setInstCategory(InstCategory instCategory) {this.instCategory = instCategory;}

    public Institute getInstitute() {return institute;}

    public void setInstitute(Institute institute) {this.institute = institute;}

    public InstLevel getInstLevel() {return instLevel;}

    public void setInstLevel(InstLevel instLevel) {this.instLevel = instLevel;}

    public designationType getDesigType() {return desigType;}

    public void setDesigType(designationType desigType) {this.desigType = desigType;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrDesignationSetup hrDesignationSetup = (HrDesignationSetup) o;
        return Objects.equals(id, hrDesignationSetup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrDesignationSetup{" +
            "id=" + id +
            ", activeStatus='" + activeStatus + "'" +
            ", allocatedPosition= "+ elocattedPosition +", "+
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
