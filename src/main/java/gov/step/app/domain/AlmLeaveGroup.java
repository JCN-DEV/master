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
 * A AlmLeaveGroup.
 */
@Entity
@Table(name = "alm_leave_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "almleavegroup")
public class AlmLeaveGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "leave_group_name", nullable = false)
    private String leaveGroupName;

    @Column(name = "description")
    private String description;

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

    public String getLeaveGroupName() {
        return leaveGroupName;
    }

    public void setLeaveGroupName(String leaveGroupName) {
        this.leaveGroupName = leaveGroupName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlmLeaveGroup almLeaveGroup = (AlmLeaveGroup) o;

        if ( ! Objects.equals(id, almLeaveGroup.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlmLeaveGroup{" +
            "id=" + id +
            ", leaveGroupName='" + leaveGroupName + "'" +
            ", description='" + description + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
