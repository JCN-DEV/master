package gov.step.app.domain;

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

/**
 * A HrWingHeadSetup.
 */
@Entity
@Table(name = "hr_wing_head_setup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrwingheadsetup")
public class HrWingHeadSetup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Column(name = "active_head", nullable = false)
    private Boolean activeHead;

    @NotNull
    @Column(name = "active_status", nullable = false)
    private Boolean activeStatus;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "wing_info_id")
    private HrWingSetup wingInfo;

    @ManyToOne
    @JoinColumn(name = "wing_head_info_id")
    private HrEmployeeInfo wingHeadInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getActiveHead() {
        return activeHead;
    }

    public void setActiveHead(Boolean activeHead) {
        this.activeHead = activeHead;
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

    public HrWingSetup getWingInfo() {
        return wingInfo;
    }

    public void setWingInfo(HrWingSetup HrWingSetup) {
        this.wingInfo = HrWingSetup;
    }

    public HrEmployeeInfo getWingHeadInfo() {
        return wingHeadInfo;
    }

    public void setWingHeadInfo(HrEmployeeInfo HrEmployeeInfo) {
        this.wingHeadInfo = HrEmployeeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HrWingHeadSetup hrWingHeadSetup = (HrWingHeadSetup) o;

        if ( ! Objects.equals(id, hrWingHeadSetup.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrWingHeadSetup{" +
            "id=" + id +
            ", joinDate='" + joinDate + "'" +
            ", endDate='" + endDate + "'" +
            ", activeHead='" + activeHead + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
