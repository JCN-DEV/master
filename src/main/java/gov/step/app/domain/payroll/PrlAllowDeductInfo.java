package gov.step.app.domain.payroll;

import gov.step.app.domain.HrGradeSetup;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import gov.step.app.domain.enumeration.AllowanceDeductionType;

/**
 * A PrlAllowDeductInfo.
 */
@Entity
@Table(name = "prl_allow_deduct_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prlallowdeductinfo")
public class PrlAllowDeductInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 6)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "allow_deduc_type", nullable = false)
    private AllowanceDeductionType allowDeducType;

    @Column(name = "all_dd_code")
    private String allowDeducCode;

    @Column(name = "allow_category")
    private String allowCategory;

    @Column(name = "description")
    private String description;

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "prl_allowdeduct_grade_map",
               joinColumns = @JoinColumn(name="prl_allow_deduct_infos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="grade_infos_id", referencedColumnName="ID"))
    private Set<HrGradeSetup> gradeInfos = new HashSet<>();


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

    public AllowanceDeductionType getAllowDeducType() {
        return allowDeducType;
    }

    public void setAllowDeducType(AllowanceDeductionType allowDeducType) {
        this.allowDeducType = allowDeducType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<HrGradeSetup> getGradeInfos() {
        return gradeInfos;
    }

    public void setGradeInfos(Set<HrGradeSetup> HrGradeSetups) {
        this.gradeInfos = HrGradeSetups;
    }

    public String getAllowCategory() {return allowCategory;}

    public void setAllowCategory(String allowCategory) {this.allowCategory = allowCategory;}

    public String getAllowDeducCode() {return allowDeducCode;}

    public void setAllowDeducCode(String allowDeducCode) {this.allowDeducCode = allowDeducCode;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrlAllowDeductInfo prlAllowDeductInfo = (PrlAllowDeductInfo) o;
        return Objects.equals(id, prlAllowDeductInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrlAllowDeductInfo{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", allowDeducType='" + allowDeducType + "'" +
            ", allowCategory='" + allowCategory + "'" +
            ", allowDeducCode='" + allowDeducCode + "'" +
            ", description='" + description + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
