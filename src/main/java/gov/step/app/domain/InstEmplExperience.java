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
 * A InstEmplExperience.
 */
@Entity
@Table(name = "inst_empl_experience")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instemplexperience")
public class InstEmplExperience implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 50)
    @Column(name = "institute_name", length = 50)
    private String instituteName;

    @Size(max = 50)
    @Column(name = "index_no", length = 50)
    private String indexNo;

    @Size(max = 512)
    @Column(name = "address", length = 512)
    private String address;

    @Size(max = 512)
    @Column(name = "designation", length = 512)
    private String designation;

    @Size(max = 512)
    @Column(name = "subject", length = 512)
    private String subject;

    @Size(max = 50)
    @Column(name = "salary_code", length = 50)
    private String salaryCode;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "mpo_enlisting_date")
    private LocalDate mpoEnlistingDate;

    @Column(name = "resign_date")
    private LocalDate resignDate;

    @Column(name = "payment_received_date")
    private LocalDate dateOfPaymentReceived;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "vacation_from")
    private LocalDate vacationFrom;

    @Column(name = "vacation_to")
    private LocalDate vacationTo;

    @Column(name = "total_exp_from")
    private LocalDate totalExpFrom;

    @Column(name = "total_exp_to")
    private LocalDate totalExpTo;

    @Column(name = "current_emp")
    private Boolean current;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;


    @Column(name = "attachment_cnt_type")
    private String attachmentContentType;

    @Column(name = "attachment_name")
    private String attachmentName;

    @Column(name = "create_date")
    private LocalDate dateCrated;

    @Column(name = "update_date")
    private LocalDate dateModified;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;


    @ManyToOne
    @JoinColumn(name = "inst_employee_id")
    private InstEmployee instEmployee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSalaryCode() {
        return salaryCode;
    }

    public void setSalaryCode(String salaryCode) {
        this.salaryCode = salaryCode;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public LocalDate getMpoEnlistingDate() {
        return mpoEnlistingDate;
    }

    public void setMpoEnlistingDate(LocalDate mpoEnlistingDate) {
        this.mpoEnlistingDate = mpoEnlistingDate;
    }

    public LocalDate getResignDate() {
        return resignDate;
    }

    public void setResignDate(LocalDate resignDate) {
        this.resignDate = resignDate;
    }

    public LocalDate getDateOfPaymentReceived() {
        return dateOfPaymentReceived;
    }

    public void setDateOfPaymentReceived(LocalDate dateOfPaymentReceived) {
        this.dateOfPaymentReceived = dateOfPaymentReceived;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getVacationFrom() {
        return vacationFrom;
    }

    public void setVacationFrom(LocalDate vacationFrom) {
        this.vacationFrom = vacationFrom;
    }

    public LocalDate getVacationTo() {
        return vacationTo;
    }

    public void setVacationTo(LocalDate vacationTo) {
        this.vacationTo = vacationTo;
    }

    public LocalDate getTotalExpFrom() {
        return totalExpFrom;
    }

    public void setTotalExpFrom(LocalDate totalExpFrom) {
        this.totalExpFrom = totalExpFrom;
    }

    public LocalDate getTotalExpTo() {
        return totalExpTo;
    }

    public void setTotalExpTo(LocalDate totalExpTo) {
        this.totalExpTo = totalExpTo;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
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

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstEmplExperience instEmplExperience = (InstEmplExperience) o;

        if ( ! Objects.equals(id, instEmplExperience.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstEmplExperience{" +
            "id=" + id +
            ", instituteName='" + instituteName + "'" +
            ", indexNo='" + indexNo + "'" +
            ", address='" + address + "'" +
            ", designation='" + designation + "'" +
            ", subject='" + subject + "'" +
            ", salaryCode='" + salaryCode + "'" +
            ", joiningDate='" + joiningDate + "'" +
            ", mpoEnlistingDate='" + mpoEnlistingDate + "'" +
            ", resignDate='" + resignDate + "'" +
            ", dateOfPaymentReceived='" + dateOfPaymentReceived + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", vacationFrom='" + vacationFrom + "'" +
            ", vacationTo='" + vacationTo + "'" +
            ", totalExpFrom='" + totalExpFrom + "'" +
            ", totalExpTo='" + totalExpTo + "'" +
            ", current='" + current + "'" +
            ", attachment='" + attachment + "'" +
            ", attachmentContentType='" + attachmentContentType + "'" +
            '}';
    }
}
