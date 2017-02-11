package gov.step.app.domain;

import gov.step.app.web.rest.util.AttachmentUtil;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

/**
 * A TimeScaleApplication.
 */
@Entity
@Table(name = "time_scale_application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "timescaleapplication")
public class TimeScaleApplication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_date")
    private Date date;

    @Size(max = 20)
    @Column(name = "index_no", length = 20)
    private String indexNo;

    //status maintaining by MpoApplicationStatusType.java class
    @Column(name = "status")
    private Integer status;

    @Column(name = "resulation_date")
    private LocalDate resulationDate;

    @Column(name = "agenda_number")
    private Integer agendaNumber;

    @Column(name = "working_break")
    private Boolean workingBreak;

    @Column(name = "working_break_start")
    private LocalDate workingBreakStart;

    @Column(name = "working_break_end")
    private LocalDate workingBreakEnd;

    @Column(name = "disciplinary_action")
    private Boolean disciplinaryAction;

    @Column(name = "dis_action_case_no")
    private String disActionCaseNo;

    @Column(name = "dis_action_file_name")
    private String disActionFileName;

    @Column(name = "dis_action_file")
    private byte[] disActionFile;

    //application forwarded from additional director
    @Column(name = "ad_forwarded")
    private Boolean adForwarded;

    //this for director's action. 0 = no action, 1 = approved, 2 = rejected
    @Column(name = "dir_action")
    private Integer directorAction = 0;

    @Column(name = "director_comment")
    private String directorComment;

    //this status will be true when finally approved by dg
    @Column(name = "dg_final_approval")
    private Boolean dgFinalApproval = false;

    @Column(name = "dg_representative")
    private Boolean dgRepresentative = Boolean.FALSE;

    @Column(name = "gov_order")
    private Boolean govOrder =Boolean.FALSE;

    @Column(name = "memo_no")
    private String memoNo;

    @Column(name = "create_date")
    private LocalDate dateCrated;

    @Column(name = "update_date")
    private LocalDate dateModified;

    @Column(name = "date_cmt_approved")
    private LocalDate committeeApproveDate;

    @Column(name = "date_deo_approved")
    private LocalDate deoApproveDate;

    @ManyToOne
    @JoinColumn(name = "inst_employee_id")
    private InstEmployee instEmployee;


    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;

    @PrePersist
    protected void onCreate() {
        this.setDate(AttachmentUtil.getTodayDateWithTime());

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getResulationDate() {
        return resulationDate;
    }

    public void setResulationDate(LocalDate resulationDate) {
        this.resulationDate = resulationDate;
    }

    public Integer getAgendaNumber() {
        return agendaNumber;
    }

    public void setAgendaNumber(Integer agendaNumber) {
        this.agendaNumber = agendaNumber;
    }

    public Boolean getWorkingBreak() {
        return workingBreak;
    }

    public void setWorkingBreak(Boolean workingBreak) {
        this.workingBreak = workingBreak;
    }

    public LocalDate getWorkingBreakStart() {
        return workingBreakStart;
    }

    public void setWorkingBreakStart(LocalDate workingBreakStart) {
        this.workingBreakStart = workingBreakStart;
    }

    public LocalDate getWorkingBreakEnd() {
        return workingBreakEnd;
    }

    public void setWorkingBreakEnd(LocalDate workingBreakEnd) {
        this.workingBreakEnd = workingBreakEnd;
    }

    public Boolean getDisciplinaryAction() {
        return disciplinaryAction;
    }

    public void setDisciplinaryAction(Boolean disciplinaryAction) {
        this.disciplinaryAction = disciplinaryAction;
    }

    public String getDisActionCaseNo() {
        return disActionCaseNo;
    }

    public void setDisActionCaseNo(String disActionCaseNo) {
        this.disActionCaseNo = disActionCaseNo;
    }

    public String getDisActionFileName() {
        return disActionFileName;
    }

    public void setDisActionFileName(String disActionFileName) {
        this.disActionFileName = disActionFileName;
    }

    public byte[] getDisActionFile() {
        return disActionFile;
    }

    public void setDisActionFile(byte[] disActionFile) {
        this.disActionFile = disActionFile;
    }

    public Boolean getAdForwarded() {
        return adForwarded;
    }

    public void setAdForwarded(Boolean adForwarded) {
        this.adForwarded = adForwarded;
    }

    public Integer getDirectorAction() {
        return directorAction;
    }

    public void setDirectorAction(Integer directorAction) {
        this.directorAction = directorAction;
    }

    public String getDirectorComment() {
        return directorComment;
    }

    public void setDirectorComment(String directorComment) {
        this.directorComment = directorComment;
    }

    public Boolean getDgFinalApproval() {
        return dgFinalApproval;
    }

    public void setDgFinalApproval(Boolean dgFinalApproval) {
        this.dgFinalApproval = dgFinalApproval;
    }

    public Boolean getDgRepresentative() {
        return dgRepresentative;
    }

    public void setDgRepresentative(Boolean dgRepresentative) {
        this.dgRepresentative = dgRepresentative;
    }

    public Boolean getGovOrder() {
        return govOrder;
    }

    public void setGovOrder(Boolean govOrder) {
        this.govOrder = govOrder;
    }

    public String getMemoNo() {
        return memoNo;
    }

    public void setMemoNo(String memoNo) {
        this.memoNo = memoNo;
    }

    public LocalDate getDateCrated() {
        return dateCrated;
    }

    public void setDateCrated(LocalDate dateCrated) {
        this.dateCrated = dateCrated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public LocalDate getCommitteeApproveDate() {
        return committeeApproveDate;
    }

    public void setCommitteeApproveDate(LocalDate committeeApproveDate) {
        this.committeeApproveDate = committeeApproveDate;
    }

    public LocalDate getDeoApproveDate() {
        return deoApproveDate;
    }

    public void setDeoApproveDate(LocalDate deoApproveDate) {
        this.deoApproveDate = deoApproveDate;
    }

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TimeScaleApplication timeScaleApplication = (TimeScaleApplication) o;

        if ( ! Objects.equals(id, timeScaleApplication.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TimeScaleApplication{" +
            "id=" + id +
            ", date=" + date +
            ", indexNo='" + indexNo + '\'' +
            ", status=" + status +
            ", resulationDate=" + resulationDate +
            ", agendaNumber=" + agendaNumber +
            ", workingBreak=" + workingBreak +
            ", workingBreakStart=" + workingBreakStart +
            ", workingBreakEnd=" + workingBreakEnd +
            ", disciplinaryAction=" + disciplinaryAction +
            ", disActionCaseNo='" + disActionCaseNo + '\'' +
            ", disActionFileName='" + disActionFileName + '\'' +
            ", disActionFile=" + Arrays.toString(disActionFile) +
            ", instEmployee=" + instEmployee +
            '}';
    }
}
