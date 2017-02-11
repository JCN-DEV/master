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
 * A DlBookIssue.
 */
@Entity
@Table(name = "dl_book_issue")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dlbookissue")
public class DlBookIssue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "isbn_no")
    private String isbnNo;



    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "edition")
    private String edition;



    @Column(name = "no_of_copies")
    private Integer noOfCopies;

    @Column(name = "return_date")
    private LocalDate returnDate;




    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "requisition_date")
    private LocalDate requisitionDate;

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

    @Column(name = "inst_employee_code")
    private String instEmployeeCode;

    @Column(name = "expec_recv_date")
    private LocalDate expecRecvDate;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "p_status")
    private Boolean pStatus;


    @ManyToOne
    @JoinColumn(name = "sis_student_info_id")
    private SisStudentInfo sisStudentInfo;

    @ManyToOne
    private InstEmployee instEmployee;
    @ManyToOne
    @JoinColumn(name = "dlBookInfo_id")
    private DlBookInfo dlBookInfo;
    @ManyToOne
    private DlContTypeSet dlContTypeSet;

    @ManyToOne
    private DlContCatSet dlContCatSet;

    @ManyToOne
    private DlContSCatSet dlContSCatSet;

    @ManyToOne
    private DlBookReturn dlBookReturn;


    @ManyToOne
    @JoinColumn(name = "dlBookEdition_id")
    private DlBookEdition dlBookEdition;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbnNo() {
        return isbnNo;
    }

    public void setIsbnNo(String isbnNo) {
        this.isbnNo = isbnNo;
    }

    public Integer getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(Integer noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
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

    public LocalDate getRequisitioneDate() {
        return requisitionDate;
    }

    public void setRequisitioneDate(LocalDate requisitioneDate) {
        this.requisitionDate = requisitioneDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getExpecRecvDate() {
        return expecRecvDate;
    }

    public void setExpecRecvDate(LocalDate expecRecvDate) {
        this.expecRecvDate = expecRecvDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getpStatus() {
        return pStatus;
    }

    public void setpStatus(Boolean pStatus) {
        this.pStatus = pStatus;
    }

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    public DlContTypeSet getDlContTypeSet() {
        return dlContTypeSet;
    }

    public void setDlContTypeSet(DlContTypeSet dlContTypeSet) {
        this.dlContTypeSet = dlContTypeSet;
    }

    public DlContCatSet getDlContCatSet() {
        return dlContCatSet;
    }

    public void setDlContCatSet(DlContCatSet dlContCatSet) {
        this.dlContCatSet = dlContCatSet;
    }

    public DlContSCatSet getDlContSCatSet() {
        return dlContSCatSet;
    }

    public void setDlContSCatSet(DlContSCatSet dlContSCatSet) {
        this.dlContSCatSet = dlContSCatSet;
    }

    public DlBookReturn getDlBookReturn() {
        return dlBookReturn;
    }

    public void setDlBookReturn(DlBookReturn DlBookReturn) {
        this.dlBookReturn = DlBookReturn;
    }

    public String getInstEmployeeCode() {
        return this.instEmployeeCode;
    }

    public void setInstEmployeeCode(String instEmployeeCode) {
        this.instEmployeeCode = instEmployeeCode;
    }

    public SisStudentInfo getSisStudentInfo() {
        return sisStudentInfo;
    }

    public void setSisStudentInfo(SisStudentInfo sisStudentInfo) {
        this.sisStudentInfo = sisStudentInfo;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public DlBookInfo getDlBookInfo() {
        return dlBookInfo;
    }

    public void setDlBookInfo(DlBookInfo dlBookInfo) {
        this.dlBookInfo = dlBookInfo;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
    public DlBookEdition getDlBookEdition() {
        return dlBookEdition;
    }

    public void setDlBookEdition(DlBookEdition dlBookEdition) {
        this.dlBookEdition = dlBookEdition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DlBookIssue dlBookIssue = (DlBookIssue) o;

        if ( ! Objects.equals(id, dlBookIssue.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DlBookIssue{" +
            "id=" + id +
            ", isbnNo='" + isbnNo + "'" +
            ", noOfCopies='" + noOfCopies + "'" +
            ", returnDate='" + returnDate + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            ", status='" + status + "'" +
            ", pStatus='" + pStatus + "'" +
            '}';
    }
}
