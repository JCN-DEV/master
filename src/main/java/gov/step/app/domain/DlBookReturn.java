package gov.step.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A DlBookReturn.
 */
@Entity
@Table(name = "dl_book_return")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dlbookreturn")
public class DlBookReturn implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "issue_id")
    private Long issueId;



    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;



    @Column(name = "received_status")
    private String receivedStatus;



    @Column(name = "total_fine")
    private String totalFine;

    @Column(name = "fine_deposit")
    private String fineDeposit;


    @Column(name = "remission_status")
    private Boolean remissionStatus;


    @Column(name = "compensation")
    private String compensation;

    @Column(name = "compensation_deposit")
    private String compensationDeposit;

    @Column(name = "compensation_fine_deposit")
    private String compensationFineDeposit;



    @Column(name = "total_fine_compensation")
    private String totalFineCompensation;


    @Column(name = "remission_compensation_status")
    private Boolean remissionCompensationStatus;




    @Column(name = "cf_fine_status")
    private Boolean cfFineStatus;

    @Column(name = "cf_compensation_status")
    private Boolean cfCompensationStatus;




    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @ManyToOne
    @JoinColumn(name = "dlBookIssue_id")
    private DlBookIssue dlBookIssue;

    @OneToMany(mappedBy = "dlBookReturn")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DlBookIssue> dlBookIssues = new HashSet<>();

    @ManyToOne
    private InstEmployee instEmployee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<DlBookIssue> getDlBookIssues() {
        return dlBookIssues;
    }

    public void setDlBookIssues(Set<DlBookIssue> DlBookIssues) {
        this.dlBookIssues = DlBookIssues;
    }

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }


    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public String getReceivedStatus() {
        return receivedStatus;
    }

    public void setReceivedStatus(String receivedStatus) {
        this.receivedStatus = receivedStatus;
    }

    public String getTotalFine() {
        return totalFine;
    }

    public void setTotalFine(String totalFine) {
        this.totalFine = totalFine;
    }

    public String getFineDeposit() {
        return fineDeposit;
    }

    public void setFineDeposit(String fineDeposit) {
        this.fineDeposit = fineDeposit;
    }

    public Boolean getRemissionStatus() {
        return remissionStatus;
    }

    public void setRemissionStatus(Boolean remissionStatus) {
        this.remissionStatus = remissionStatus;
    }

    public String getCompensation() {
        return compensation;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }

    public String getCompensationDeposit() {
        return compensationDeposit;
    }

    public void setCompensationDeposit(String compensationDeposit) {
        this.compensationDeposit = compensationDeposit;
    }

    public Boolean getRemissionCompensationStatus() {
        return remissionCompensationStatus;
    }

    public void setRemissionCompensationStatus(Boolean remissionCompensationStatus) {
        this.remissionCompensationStatus = remissionCompensationStatus;
    }

    public DlBookIssue getDlBookIssue() {
        return dlBookIssue;
    }

    public void setDlBookIssue(DlBookIssue dlBookIssue) {
        this.dlBookIssue = dlBookIssue;
    }

    public String getCompensationFineDeposit() {
        return compensationFineDeposit;
    }

    public void setCompensationFineDeposit(String compensationFineDeposit) {
        this.compensationFineDeposit = compensationFineDeposit;
    }
    public String getTotalFineCompensation() {
        return totalFineCompensation;
    }

    public void setTotalFineCompensation(String totalFineCompensation) {
        this.totalFineCompensation = totalFineCompensation;
    }

    public Boolean getCfFineStatus() {
        return cfFineStatus;
    }

    public void setCfFineStatus(Boolean cfFineStatus) {
        this.cfFineStatus = cfFineStatus;
    }

    public Boolean getCfCompensationStatus() {
        return cfCompensationStatus;
    }

    public void setCfCompensationStatus(Boolean cfCompensationStatus) {
        this.cfCompensationStatus = cfCompensationStatus;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DlBookReturn dlBookReturn = (DlBookReturn) o;

        if ( ! Objects.equals(id, dlBookReturn.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DlBookReturn{" +
            "id=" + id +
            ", issueId='" + issueId + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
