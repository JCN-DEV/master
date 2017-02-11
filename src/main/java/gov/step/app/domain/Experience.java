package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Experience.
 */
@Entity
@Table(name = "experience")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "experience")
public class Experience implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    @Column(name = "date_of_pay_recvd")
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

    @Column(name = "current")
    private Boolean current;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;


    @Column(name = "attachment_content_type")
    private String attachmentContentType;
    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Institute institute;

    @ManyToOne
    private User manager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User user) {
        this.manager = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Experience experience = (Experience) o;

        if (!Objects.equals(id, experience.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Experience{" +
            "id=" + id +
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
