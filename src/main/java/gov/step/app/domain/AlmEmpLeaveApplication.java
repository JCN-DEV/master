package gov.step.app.domain;

import gov.step.app.domain.enumeration.applicationLeaveStatuses;
import gov.step.app.domain.enumeration.halfDayInfos;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AlmEmpLeaveApplication.
 */
@Entity
@Table(name = "alm_emp_leave_application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "almempleaveapplication")
public class AlmEmpLeaveApplication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_leave_status")
    private applicationLeaveStatuses applicationLeaveStatus;

    @Column(name = "leave_from_date", nullable = false)
    private LocalDate leaveFromDate;

    @Column(name = "leave_to_date", nullable = false)
    private LocalDate leaveToDate;

    @Column(name = "is_half_day_leave")
    private Boolean isHalfDayLeave;

    @Enumerated(EnumType.STRING)
    @Column(name = "half_day_leave_info")
    private halfDayInfos halfDayLeaveInfo;

    @Column(name = "leave_days")
    private Double leaveDays;

    @Column(name = "reason_of_leave")
    private String reasonOfLeave;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "is_with_certificate")
    private Boolean isWithCertificate;

    @Lob
    @Column(name = "leave_certificate")
    private byte[] leaveCertificate;

    @Column(name = "leave_cert_content_type")
    private String leaveCertContentType;

    @Column(name = "leave_certificate_name")
    private String leaveCertificateName;

    @Column(name = "assign_resposibilty")
    private String assignResposibilty;

    @Column(name = "responsible_emp_add")
    private String responsibleEmpAdd;

    @Column(name = "responsible_emp_cont")
    private String responsibleEmpCont;

    @Column(name = "emergency_contact_no")
    private String emergencyContactNo;

    @Column(name = "address_while_leave")
    private String addressWhileLeave;

    @Column(name = "is_with_ddo")
    private Boolean isWithDdo;

    @Column(name = "is_with_finance")
    private Boolean isWithFinance;

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
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;


    @ManyToOne
    @JoinColumn(name = "responsible_emp_id")
    private HrEmployeeInfo responsibleEmp;

    @ManyToOne
    @JoinColumn(name = "approver_name_id")
    private HrEmployeeInfo approverName;

    @ManyToOne
    @JoinColumn(name = "alm_leave_type_id")
    private AlmLeaveType almLeaveType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public applicationLeaveStatuses getApplicationLeaveStatus() {
        return applicationLeaveStatus;
    }

    public void setApplicationLeaveStatus(applicationLeaveStatuses applicationLeaveStatus) {
        this.applicationLeaveStatus = applicationLeaveStatus;
    }

    public LocalDate getLeaveFromDate() {
        return leaveFromDate;
    }

    public void setLeaveFromDate(LocalDate leaveFromDate) {
        this.leaveFromDate = leaveFromDate;
    }

    public LocalDate getLeaveToDate() {
        return leaveToDate;
    }

    public void setLeaveToDate(LocalDate leaveToDate) {
        this.leaveToDate = leaveToDate;
    }

    public Boolean getIsHalfDayLeave() {
        return isHalfDayLeave;
    }

    public void setIsHalfDayLeave(Boolean isHalfDayLeave) {
        this.isHalfDayLeave = isHalfDayLeave;
    }

    public halfDayInfos getHalfDayLeaveInfo() {
        return halfDayLeaveInfo;
    }

    public void setHalfDayLeaveInfo(halfDayInfos halfDayLeaveInfo) {
        this.halfDayLeaveInfo = halfDayLeaveInfo;
    }

    public Double getLeaveDays(){
        return leaveDays;
    }

    public void setLeaveDays(Double leaveDays){
        this.leaveDays = leaveDays;
    }

    public String getReasonOfLeave() {
        return reasonOfLeave;
    }

    public void setReasonOfLeave(String reasonOfLeave) {
        this.reasonOfLeave = reasonOfLeave;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Boolean getIsWithCertificate() {
        return isWithCertificate;
    }

    public void setIsWithCertificate(Boolean isWithCertificate) {
        this.isWithCertificate = isWithCertificate;
    }

    public Boolean getIsWithFinance() {
        return isWithFinance;
    }

    public void setIsWithFinance(Boolean isWithFinance) {
        this.isWithFinance = isWithFinance;
    }

    public Boolean getIsWithDdo() {
        return isWithDdo;
    }

    public void setIsWithDdo(Boolean isWithDdo) {
        this.isWithDdo = isWithDdo;
    }

    public byte[] getLeaveCertificate(){
        return leaveCertificate;
    }

    public void setLeaveCertificate(byte[] leaveCertificate){
        this.leaveCertificate = leaveCertificate;
    }


    public String getEmergencyContactNo() {
        return emergencyContactNo;
    }

    public void setEmergencyContactNo(String emergencyContactNo) {
        this.emergencyContactNo = emergencyContactNo;
    }

    public String getAddressWhileLeave() {
        return addressWhileLeave;
    }

    public void setAddressWhileLeave(String addressWhileLeave) {
        this.addressWhileLeave = addressWhileLeave;
    }

    public String getLeaveCertificateName() {
        return leaveCertificateName;
    }

    public void setLeaveCertificateName(String leaveCertificateName) {
        this.leaveCertificateName = leaveCertificateName;
    }

    public String getResponsibleEmpAdd(){
        return responsibleEmpAdd;
    }

    public void setResponsibleEmpAdd(String responsibleEmpAdd){
        this.responsibleEmpAdd = responsibleEmpAdd;
    }

    public  String getResponsibleEmpCont(){
        return responsibleEmpCont;
    }

    public void setResponsibleEmpCont(String responsibleEmpCont){
        this.responsibleEmpCont = responsibleEmpCont;
    }

    public String getLeaveCertContentType() {
        return leaveCertContentType;
    }

    public void setLeaveCertContentType(String leaveCertContentType) {
        this.leaveCertContentType = leaveCertContentType;
    }

    public String getAssignResposibilty() {
        return assignResposibilty;
    }

    public void setAssignResposibilty(String assignResposibilty) {
        this.assignResposibilty = assignResposibilty;
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

    public HrEmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(HrEmployeeInfo HrEmployeeInfo) {
        this.employeeInfo = HrEmployeeInfo;
    }

    public HrEmployeeInfo getResponsibleEmp() {
        return responsibleEmp;
    }

    public void setResponsibleEmp(HrEmployeeInfo HrEmployeeInfo) {
        this.responsibleEmp = HrEmployeeInfo;
    }

    public HrEmployeeInfo getApproverName() {
        return approverName;
    }

    public void setApproverName(HrEmployeeInfo HrEmployeeInfo) {
        this.approverName = HrEmployeeInfo;
    }

    public AlmLeaveType getAlmLeaveType() {
        return almLeaveType;
    }

    public void setAlmLeaveType(AlmLeaveType AlmLeaveType) {
        this.almLeaveType = AlmLeaveType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlmEmpLeaveApplication almEmpLeaveApplication = (AlmEmpLeaveApplication) o;

        if ( ! Objects.equals(id, almEmpLeaveApplication.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlmEmpLeaveApplication{" +
            "id=" + id +
            ", applicationDate='" + applicationDate + "'" +
            ", applicationLeaveStatus='" + applicationLeaveStatus + "'" +
            ", leaveFromDate='" + leaveFromDate + "'" +
            ", leaveToDate='" + leaveToDate + "'" +
            ", isHalfDayLeave='" + isHalfDayLeave + "'" +
            ", halfDayLeaveInfo='" + halfDayLeaveInfo + "'" +
            ", leaveDays='" + leaveDays + "'" +
            ", reasonOfLeave='" + reasonOfLeave + "'" +
            ", contactNo='" + contactNo + "'" +
            ", isWithCertificate='" + isWithCertificate + "'" +
            ", assignResposibilty='" + assignResposibilty + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
