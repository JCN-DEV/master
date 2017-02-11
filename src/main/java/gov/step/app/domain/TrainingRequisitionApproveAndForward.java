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
 * A TrainingRequisitionApproveAndForward.
 */
@Entity
@Table(name = "tis_approve_and_forward")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "trainingrequisitionapproveandforward")
public class TrainingRequisitionApproveAndForward implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "approve_by_authority")
    private String approveByAuthority;

    @Column(name = "forward_to_authority")
    private String forwardToAuthority;

    @Column(name = "approve_status")
    private Integer approveStatus;

    @Column(name = "comments")
    private String comments;

    @Column(name = "log_comments")
    private String logComments;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "requisition_form_id")
    private TrainingRequisitionForm trainingRequisitionForm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApproveByAuthority() {
        return approveByAuthority;
    }

    public void setApproveByAuthority(String approveByAuthority) {
        this.approveByAuthority = approveByAuthority;
    }

    public String getForwardToAuthority() {
        return forwardToAuthority;
    }

    public void setForwardToAuthority(String forwardToAuthority) {
        this.forwardToAuthority = forwardToAuthority;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getLogComments() {
        return logComments;
    }

    public void setLogComments(String logComments) {
        this.logComments = logComments;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public TrainingRequisitionForm getTrainingRequisitionForm() {
        return trainingRequisitionForm;
    }

    public void setTrainingRequisitionForm(TrainingRequisitionForm trainingRequisitionForm) {
        this.trainingRequisitionForm = trainingRequisitionForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrainingRequisitionApproveAndForward trainingRequisitionApproveAndForward = (TrainingRequisitionApproveAndForward) o;

        if ( ! Objects.equals(id, trainingRequisitionApproveAndForward.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TrainingRequisitionApproveAndForward{" +
            "id=" + id +
            ", approveStatus='" + approveStatus + "'" +
            ", approveByAuthority='" + approveByAuthority + "'" +
            ", forwardToAuthority='" + forwardToAuthority + "'" +
            ", approveStatus='" + approveStatus + "'" +
            ", logComments='" + logComments + "'" +
            ", status='" + status + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
