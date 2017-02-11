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
 * A PgmsElpc.
 */
@Entity
@Table(name = "pgms_elpc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pgmselpc")
public class PgmsElpc implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "emp_code", nullable = false)
    private String empCode;

    @NotNull
    @Column(name = "inst_code", nullable = false)
    private String instCode;

//    @Column(name = "emp_index_no")
//    private String empIndexNo;

    @NotNull
    @Column(name = "emp_name", nullable = false)
    private String empName;

    @NotNull
    @Column(name = "inst_name", nullable = false)
    private String instName;

    @NotNull
    @Column(name = "desig_id", nullable = false)
    private Long desigId;

    @NotNull
    @Column(name = "designation", nullable = false)
    private String designation;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "join_date", nullable = false)
    private LocalDate joinDate;

    @NotNull
    @Column(name = "begin_date_of_retiremnt", nullable = false)
    private LocalDate beginDateOfRetiremnt;

    @NotNull
    @Column(name = "retiremnt_date", nullable = false)
    private LocalDate retirementDate;

    @NotNull
    @Column(name = "last_rcv_payscale", nullable = false)
    private Long lastRcvPayscale;

    @NotNull
    @Column(name = "incrs_dt_of_yrly_payment", nullable = false)
    private Long incrsDtOfYrlyPayment;

    @NotNull
    @Column(name = "gaining_leave", nullable = false)
    private Long gainingLeave;

    @NotNull
    @Column(name = "leave_type", nullable = false)
    private String leaveType;

    @NotNull
    @Column(name = "leave_total", nullable = false)
    private Long leaveTotal;

    @NotNull
    @Column(name = "app_retirement_date", nullable = false)
    private LocalDate appRetirementDate;

    @NotNull
    @Column(name = "main_payment", nullable = false)
    private Long mainPayment;

    @NotNull
    @Column(name = "incr_mon_rate_leaving", nullable = false)
    private Long incrMonRateLeaving;

    @NotNull
    @Column(name = "special_payment", nullable = false)
    private Long specialPayment;

    @NotNull
    @Column(name = "special_allowance", nullable = false)
    private Long specialAllowance;

    @NotNull
    @Column(name = "houserent_al", nullable = false)
    private Long houserentAl;

    @NotNull
    @Column(name = "treatment_al", nullable = false)
    private Long treatmentAl;

    @NotNull
    @Column(name = "dearness_al", nullable = false)
    private Long dearnessAl;

    @NotNull
    @Column(name = "travelling_al", nullable = false)
    private Long travellingAl;

    @NotNull
    @Column(name = "laundry_al", nullable = false)
    private Long laundryAl;

    @NotNull
    @Column(name = "personal_al", nullable = false)
    private Long personalAl;

    @NotNull
    @Column(name = "technical_al", nullable = false)
    private Long technicalAl;

    @NotNull
    @Column(name = "hospitality_al", nullable = false)
    private Long hospitalityAl;

    @NotNull
    @Column(name = "tiffin_al", nullable = false)
    private Long tiffinAl;

    @NotNull
    @Column(name = "adv_of_making_house", nullable = false)
    private Long advOfMakingHouse;

    @NotNull
    @Column(name = "vechile_status", nullable = false)
    private Long vechileStatus;

    @NotNull
    @Column(name = "adv_trav_al", nullable = false)
    private Long advTravAl;

    @NotNull
    @Column(name = "adv_salary", nullable = false)
    private Long advSalary;

    @NotNull
    @Column(name = "house_rent", nullable = false)
    private Long houseRent;

    @NotNull
    @Column(name = "car_rent", nullable = false)
    private Long carRent;

    @NotNull
    @Column(name = "gas_bill", nullable = false)
    private Long gasBill;

    @NotNull
    @Column(name = "santry_water_tax", nullable = false)
    private String santryWaterTax;

    @NotNull
    @Column(name = "bank_acc", nullable = false)
    private String bankAcc;

    @NotNull
    @Column(name = "acc_book_no", nullable = false)
    private String accBookNo;

    @NotNull
    @Column(name = "book_page_no", nullable = false)
    private String bookPageNo;

    @NotNull
    @Column(name = "bank_interest", nullable = false)
    private String bankInterest;

    @NotNull
    @Column(name = "monly_dep_rate_fr_salary", nullable = false)
    private String monlyDepRateFrSalary;

    @NotNull
    @Column(name = "expected_deposition", nullable = false)
    private String expectedDeposition;

    @NotNull
    @Column(name = "acc_date", nullable = false)
    private LocalDate accDate;

    @NotNull
    @Column(name = "app_no", nullable = false)
    private String appNo;

    @NotNull
    @Column(name = "app_date", nullable = false)
    private LocalDate appDate;

    @NotNull
    @Column(name = "app_type", nullable = false)
    private String appType;

    @NotNull
    @Column(name = "app_comments", nullable = false)
    private String appComments;


    @Column(name = "aprv_status")
    private String aprvStatus;


    @Column(name = "aprv_date")
    private LocalDate aprvDate;


    @Column(name = "aprv_comment")
    private String aprvComment;


    @Column(name = "aprv_by")
    private Long aprvBy;


    @Column(name = "notification_status")
    private Long notificationStatus;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "inst_emp_id_id")
    private HrEmployeeInfo instEmpId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public Long getDesigId() {
        return desigId;
    }

    public void setDesigId(Long desigId) {
        this.desigId = desigId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDate getBeginDateOfRetiremnt() {
        return beginDateOfRetiremnt;
    }

    public void setBeginDateOfRetiremnt(LocalDate beginDateOfRetiremnt) {
        this.beginDateOfRetiremnt = beginDateOfRetiremnt;
    }

    public LocalDate getRetirementDate() {
        return retirementDate;
    }

    public void setRetirementDate(LocalDate retirementDate) {
        this.retirementDate = retirementDate;
    }

    public Long getLastRcvPayscale() {
        return lastRcvPayscale;
    }

    public void setLastRcvPayscale(Long lastRcvPayscale) {
        this.lastRcvPayscale = lastRcvPayscale;
    }

    public Long getIncrsDtOfYrlyPayment() {
        return incrsDtOfYrlyPayment;
    }

    public void setIncrsDtOfYrlyPayment(Long incrsDtOfYrlyPayment) {
        this.incrsDtOfYrlyPayment = incrsDtOfYrlyPayment;
    }

    public Long getGainingLeave() {
        return gainingLeave;
    }

    public void setGainingLeave(Long gainingLeave) {
        this.gainingLeave = gainingLeave;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public Long getLeaveTotal() {
        return leaveTotal;
    }

    public void setLeaveTotal(Long leaveTotal) {
        this.leaveTotal = leaveTotal;
    }

    public LocalDate getAppRetirementDate() {
        return appRetirementDate;
    }

    public void setAppRetirementDate(LocalDate appRetirementDate) {
        this.appRetirementDate = appRetirementDate;
    }

    public Long getMainPayment() {
        return mainPayment;
    }

    public void setMainPayment(Long mainPayment) {
        this.mainPayment = mainPayment;
    }

    public Long getIncrMonRateLeaving() {
        return incrMonRateLeaving;
    }

    public void setIncrMonRateLeaving(Long incrMonRateLeaving) {
        this.incrMonRateLeaving = incrMonRateLeaving;
    }

    public Long getSpecialPayment() {
        return specialPayment;
    }

    public void setSpecialPayment(Long specialPayment) {
        this.specialPayment = specialPayment;
    }

    public Long getSpecialAllowance() {
        return specialAllowance;
    }

    public void setSpecialAllowance(Long specialAllowance) {
        this.specialAllowance = specialAllowance;
    }

    public Long getHouserentAl() {
        return houserentAl;
    }

    public void setHouserentAl(Long houserentAl) {
        this.houserentAl = houserentAl;
    }

    public Long getTreatmentAl() {
        return treatmentAl;
    }

    public void setTreatmentAl(Long treatmentAl) {
        this.treatmentAl = treatmentAl;
    }

    public Long getDearnessAl() {
        return dearnessAl;
    }

    public void setDearnessAl(Long dearnessAl) {
        this.dearnessAl = dearnessAl;
    }

    public Long getTravellingAl() {
        return travellingAl;
    }

    public void setTravellingAl(Long travellingAl) {
        this.travellingAl = travellingAl;
    }

    public Long getLaundryAl() {
        return laundryAl;
    }

    public void setLaundryAl(Long laundryAl) {
        this.laundryAl = laundryAl;
    }

    public Long getPersonalAl() {
        return personalAl;
    }

    public void setPersonalAl(Long personalAl) {
        this.personalAl = personalAl;
    }

    public Long getTechnicalAl() {
        return technicalAl;
    }

    public void setTechnicalAl(Long technicalAl) {
        this.technicalAl = technicalAl;
    }

    public Long getHospitalityAl() {
        return hospitalityAl;
    }

    public void setHospitalityAl(Long hospitalityAl) {
        this.hospitalityAl = hospitalityAl;
    }

    public Long getTiffinAl() {
        return tiffinAl;
    }

    public void setTiffinAl(Long tiffinAl) {
        this.tiffinAl = tiffinAl;
    }

    public Long getAdvOfMakingHouse() {
        return advOfMakingHouse;
    }

    public void setAdvOfMakingHouse(Long advOfMakingHouse) {
        this.advOfMakingHouse = advOfMakingHouse;
    }

    public Long getVechileStatus() {
        return vechileStatus;
    }

    public void setVechileStatus(Long vechileStatus) {
        this.vechileStatus = vechileStatus;
    }

    public Long getAdvTravAl() {
        return advTravAl;
    }

    public void setAdvTravAl(Long advTravAl) {
        this.advTravAl = advTravAl;
    }

    public Long getAdvSalary() {
        return advSalary;
    }

    public void setAdvSalary(Long advSalary) {
        this.advSalary = advSalary;
    }

    public Long getHouseRent() {
        return houseRent;
    }

    public void setHouseRent(Long houseRent) {
        this.houseRent = houseRent;
    }

    public Long getCarRent() {
        return carRent;
    }

    public void setCarRent(Long carRent) {
        this.carRent = carRent;
    }

    public Long getGasBill() {
        return gasBill;
    }

    public void setGasBill(Long gasBill) {
        this.gasBill = gasBill;
    }

    public String getSantryWaterTax() {
        return santryWaterTax;
    }

    public void setSantryWaterTax(String santryWaterTax) {
        this.santryWaterTax = santryWaterTax;
    }

    public String getBankAcc() {
        return bankAcc;
    }

    public void setBankAcc(String bankAcc) {
        this.bankAcc = bankAcc;
    }

    public String getAccBookNo() {
        return accBookNo;
    }

    public void setAccBookNo(String accBookNo) {
        this.accBookNo = accBookNo;
    }

    public String getBookPageNo() {
        return bookPageNo;
    }

    public void setBookPageNo(String bookPageNo) {
        this.bookPageNo = bookPageNo;
    }

    public String getBankInterest() {
        return bankInterest;
    }

    public void setBankInterest(String bankInterest) {
        this.bankInterest = bankInterest;
    }

    public String getMonlyDepRateFrSalary() {
        return monlyDepRateFrSalary;
    }

    public void setMonlyDepRateFrSalary(String monlyDepRateFrSalary) {
        this.monlyDepRateFrSalary = monlyDepRateFrSalary;
    }

    public String getExpectedDeposition() {
        return expectedDeposition;
    }

    public void setExpectedDeposition(String expectedDeposition) {
        this.expectedDeposition = expectedDeposition;
    }

    public LocalDate getAccDate() {
        return accDate;
    }

    public void setAccDate(LocalDate accDate) {
        this.accDate = accDate;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public LocalDate getAppDate() {
        return appDate;
    }

    public void setAppDate(LocalDate appDate) {
        this.appDate = appDate;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppComments() {
        return appComments;
    }

    public void setAppComments(String appComments) {
        this.appComments = appComments;
    }

    public String getAprvStatus() {
        return aprvStatus;
    }

    public void setAprvStatus(String aprvStatus) {
        this.aprvStatus = aprvStatus;
    }

    public LocalDate getAprvDate() {
        return aprvDate;
    }

    public void setAprvDate(LocalDate aprvDate) {
        this.aprvDate = aprvDate;
    }

    public String getAprvComment() {
        return aprvComment;
    }

    public void setAprvComment(String aprvComment) {
        this.aprvComment = aprvComment;
    }

    public Long getAprvBy() {
        return aprvBy;
    }

    public void setAprvBy(Long aprvBy) {
        this.aprvBy = aprvBy;
    }

    public Long getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Long notificationStatus) {
        this.notificationStatus = notificationStatus;
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

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public HrEmployeeInfo getInstEmpId() {
        return instEmpId;
    }

    public void setInstEmpId(HrEmployeeInfo HrEmployeeInfo) {
        this.instEmpId = HrEmployeeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgmsElpc pgmsElpc = (PgmsElpc) o;

        if ( ! Objects.equals(id, pgmsElpc.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PgmsElpc{" +
            "id=" + id +
            ", empCode='" + empCode + "'" +
            ", instCode='" + instCode + "'" +
            ", empName='" + empName + "'" +
            ", instName='" + instName + "'" +
            ", desigId='" + desigId + "'" +
            ", designation='" + designation + "'" +
            ", dateOfBirth='" + dateOfBirth + "'" +
            ", joinDate='" + joinDate + "'" +
            ", beginDateOfRetiremnt='" + beginDateOfRetiremnt + "'" +
            ", retirementDate='" + retirementDate + "'" +
            ", lastRcvPayscale='" + lastRcvPayscale + "'" +
            ", incrsDtOfYrlyPayment='" + incrsDtOfYrlyPayment + "'" +
            ", gainingLeave='" + gainingLeave + "'" +
            ", leaveType='" + leaveType + "'" +
            ", leaveTotal='" + leaveTotal + "'" +
            ", appRetirementDate='" + appRetirementDate + "'" +
            ", mainPayment='" + mainPayment + "'" +
            ", incrMonRateLeaving='" + incrMonRateLeaving + "'" +
            ", specialPayment='" + specialPayment + "'" +
            ", specialAllowance='" + specialAllowance + "'" +
            ", houserentAl='" + houserentAl + "'" +
            ", treatmentAl='" + treatmentAl + "'" +
            ", dearnessAl='" + dearnessAl + "'" +
            ", travellingAl='" + travellingAl + "'" +
            ", laundryAl='" + laundryAl + "'" +
            ", personalAl='" + personalAl + "'" +
            ", technicalAl='" + technicalAl + "'" +
            ", hospitalityAl='" + hospitalityAl + "'" +
            ", tiffinAl='" + tiffinAl + "'" +
            ", advOfMakingHouse='" + advOfMakingHouse + "'" +
            ", vechileStatus='" + vechileStatus + "'" +
            ", advTravAl='" + advTravAl + "'" +
            ", advSalary='" + advSalary + "'" +
            ", houseRent='" + houseRent + "'" +
            ", carRent='" + carRent + "'" +
            ", gasBill='" + gasBill + "'" +
            ", santryWaterTax='" + santryWaterTax + "'" +
            ", bankAcc='" + bankAcc + "'" +
            ", accBookNo='" + accBookNo + "'" +
            ", bookPageNo='" + bookPageNo + "'" +
            ", bankInterest='" + bankInterest + "'" +
            ", monlyDepRateFrSalary='" + monlyDepRateFrSalary + "'" +
            ", expectedDeposition='" + expectedDeposition + "'" +
            ", accDate='" + accDate + "'" +
            ", appNo='" + appNo + "'" +
            ", appDate='" + appDate + "'" +
            ", appType='" + appType + "'" +
            ", appComments='" + appComments + "'" +
            ", aprvStatus='" + aprvStatus + "'" +
            ", aprvDate='" + aprvDate + "'" +
            ", aprvComment='" + aprvComment + "'" +
            ", aprvBy='" + aprvBy + "'" +
            ", notificationStatus='" + notificationStatus + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateBy='" + updateBy + "'" +
            ", updateDate='" + updateDate + "'" +
            '}';
    }
}
