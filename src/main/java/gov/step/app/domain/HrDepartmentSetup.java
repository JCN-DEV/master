package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A HrDepartmentSetup.
 */
@Entity
@Table(name = "hr_department_setup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrdepartmentsetup")
public class HrDepartmentSetup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    @JoinColumn(name = "inst_category_id")
    private InstCategory instCategory;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @ManyToOne
    @JoinColumn(name = "department_info_id")
    private HrDepartmentHeadSetup departmentInfo;

    @ManyToOne
    @JoinColumn(name = "wing_info_id")
    private HrWingSetup wingInfo;

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

    public MiscTypeSetup getOrganizationCategory() {return organizationCategory;}

    public void setOrganizationCategory(MiscTypeSetup organizationCategory) {this.organizationCategory = organizationCategory;}

    public HrEmpWorkAreaDtlInfo getOrganizationInfo() {return organizationInfo;}

    public void setOrganizationInfo(HrEmpWorkAreaDtlInfo organizationInfo) {this.organizationInfo = organizationInfo;}

    public HrDepartmentHeadSetup getDepartmentInfo() {return departmentInfo;}

    public void setDepartmentInfo(HrDepartmentHeadSetup departmentInfo) {this.departmentInfo = departmentInfo;}

    public String getOrganizationType() {return organizationType;}

    public void setOrganizationType(String organizationType) {this.organizationType = organizationType;}

    public InstCategory getInstCategory() {return instCategory;}

    public void setInstCategory(InstCategory instCategory) {this.instCategory = instCategory;}

    public Institute getInstitute() {return institute;}

    public void setInstitute(Institute institute) {this.institute = institute;}

    public HrWingSetup getWingInfo() {return wingInfo;}

    public void setWingInfo(HrWingSetup wingInfo) {this.wingInfo = wingInfo;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrDepartmentSetup hrDepartmentSetup = (HrDepartmentSetup) o;
        return Objects.equals(id, hrDepartmentSetup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrDepartmentSetup{" +
            "id=" + id +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
