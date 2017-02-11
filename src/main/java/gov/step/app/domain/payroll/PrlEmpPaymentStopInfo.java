package gov.step.app.domain.payroll;

import gov.step.app.domain.HrEmployeeInfo;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PrlEmpPaymentStopInfo.
 */
@Entity
@Table(name = "prl_emp_payment_stop_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prlemppaymentstopinfo")
public class PrlEmpPaymentStopInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "effected_date_from")
    private LocalDate effectedDateFrom;

    @Column(name = "effected_date_to")
    private LocalDate effectedDateTo;

    @Column(name = "stop_action_type")
    private String stopActionType;

    @Column(name = "comments")
    private String comments;

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

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEffectedDateFrom() {
        return effectedDateFrom;
    }

    public void setEffectedDateFrom(LocalDate effectedDateFrom) {
        this.effectedDateFrom = effectedDateFrom;
    }

    public LocalDate getEffectedDateTo() {
        return effectedDateTo;
    }

    public void setEffectedDateTo(LocalDate effectedDateTo) {
        this.effectedDateTo = effectedDateTo;
    }

    public String getStopActionType() {
        return stopActionType;
    }

    public void setStopActionType(String stopActionType) {
        this.stopActionType = stopActionType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public Boolean getActiveStatus() {return activeStatus;}

    public void setActiveStatus(Boolean activeStatus) {this.activeStatus = activeStatus;}

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public HrEmployeeInfo getEmployeeInfo() {return employeeInfo;}

    public void setEmployeeInfo(HrEmployeeInfo HrEmployeeInfo) {
        this.employeeInfo = HrEmployeeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrlEmpPaymentStopInfo prlEmpPaymentStopInfo = (PrlEmpPaymentStopInfo) o;
        return Objects.equals(id, prlEmpPaymentStopInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrlEmpPaymentStopInfo{" +
            "id=" + id +
            ", effectedDateFrom='" + effectedDateFrom + "'" +
            ", effectedDateTo='" + effectedDateTo + "'" +
            ", stopActionType='" + stopActionType + "'" +
            ", comments='" + comments + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
