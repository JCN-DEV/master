package gov.step.app.domain;

import gov.step.app.domain.enumeration.EmployeeStatusPfms;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PfmsEmpMembershipForm.
 */
@Entity
@Table(name = "pfms_emp_membership_form")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pfmsempmembershipform")
public class PfmsEmpMembershipForm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status_pfms")
    private EmployeeStatusPfms employeeStatusPfms;

    @NotNull
    @Column(name = "is_minimum_three_yrs", nullable = false)
    private Boolean isMinimumThreeYrs;

    @NotNull
    @Column(name = "is_mandatory_contribute", nullable = false)
    private Boolean isMandatoryContribute;

    @Column(name = "is_another_cont_fund")
    private Boolean isAnotherContFund;

    @Column(name = "fund_name")
    private String fundName;

    @NotNull
    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @NotNull
    @Column(name = "is_emp_family", nullable = false)
    private Boolean isEmpFamily;

    @NotNull
    @Column(name = "percent_of_deduct", nullable = false)
    private Double percentOfDeduct;

    @NotNull
    @Column(name = "is_money_section", nullable = false)
    private Boolean isMoneySection;

    @Column(name = "nominee_name")
    private String nomineeName;

    @Column(name = "age_of_nominee")
    private Double ageOfNominee;

    @Column(name = "nominee_address")
    private String nomineeAddress;

    @Column(name = "witness_name_one")
    private String witnessNameOne;

    @Column(name = "witness_mobile_no_one")
    private String witnessMobileNoOne;

    @Column(name = "witness_address_one")
    private String witnessAddressOne;

    @Column(name = "witness_name_two")
    private String witnessNameTwo;

    @Column(name = "witness_mobile_no_two")
    private String witnessMobileNoTwo;

    @Column(name = "witness_address_two")
    private String witnessAddressTwo;

    @Column(name = "station_name")
    private String stationName;

    @NotNull
    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @NotNull
    @Column(name = "remarks", nullable = false)
    private String remarks;

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
    @JoinColumn(name = "relationship_id")
    private Relationship relationship;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsMinimumThreeYrs() {
        return isMinimumThreeYrs;
    }

    public void setIsMinimumThreeYrs(Boolean isMinimumThreeYrs) {
        this.isMinimumThreeYrs = isMinimumThreeYrs;
    }

    public Boolean getIsMandatoryContribute() {
        return isMandatoryContribute;
    }

    public void setIsMandatoryContribute(Boolean isMandatoryContribute) {
        this.isMandatoryContribute = isMandatoryContribute;
    }

    public EmployeeStatusPfms getEmployeeStatusPfms(){
        return employeeStatusPfms;
    }

    public void setEmployeeStatusPfms(EmployeeStatusPfms employeeStatusPfms){
        this.employeeStatusPfms = employeeStatusPfms;
    }

    public String getAccountNo(){
        return accountNo;
    }

    public void setAccountNo(String accountNo){
        this.accountNo = accountNo;
    }

    public Boolean getIsAnotherContFund() {
        return isAnotherContFund;
    }

    public void setIsAnotherContFund(Boolean isAnotherContFund) {
        this.isAnotherContFund = isAnotherContFund;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public Boolean getIsEmpFamily() {
        return isEmpFamily;
    }

    public void setIsEmpFamily(Boolean isEmpFamily) {
        this.isEmpFamily = isEmpFamily;
    }

    public Double getPercentOfDeduct() {
        return percentOfDeduct;
    }

    public void setPercentOfDeduct(Double percentOfDeduct) {
        this.percentOfDeduct = percentOfDeduct;
    }

    public Boolean getIsMoneySection() {
        return isMoneySection;
    }

    public void setIsMoneySection(Boolean isMoneySection) {
        this.isMoneySection = isMoneySection;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public Double getAgeOfNominee() {
        return ageOfNominee;
    }

    public void setAgeOfNominee(Double ageOfNominee) {
        this.ageOfNominee = ageOfNominee;
    }

    public String getNomineeAddress() {
        return nomineeAddress;
    }

    public void setNomineeAddress(String nomineeAddress) {
        this.nomineeAddress = nomineeAddress;
    }

    public String getWitnessNameOne() {
        return witnessNameOne;
    }

    public void setWitnessNameOne(String witnessNameOne) {
        this.witnessNameOne = witnessNameOne;
    }

    public String getWitnessMobileNoOne() {
        return witnessMobileNoOne;
    }

    public void setWitnessMobileNoOne(String witnessMobileNoOne) {
        this.witnessMobileNoOne = witnessMobileNoOne;
    }

    public String getWitnessAddressOne() {
        return witnessAddressOne;
    }

    public void setWitnessAddressOne(String witnessAddressOne) {
        this.witnessAddressOne = witnessAddressOne;
    }

    public String getWitnessNameTwo() {
        return witnessNameTwo;
    }

    public void setWitnessNameTwo(String witnessNameTwo) {
        this.witnessNameTwo = witnessNameTwo;
    }

    public String getWitnessMobileNoTwo() {
        return witnessMobileNoTwo;
    }

    public void setWitnessMobileNoTwo(String witnessMobileNoTwo) {
        this.witnessMobileNoTwo = witnessMobileNoTwo;
    }

    public String getWitnessAddressTwo() {
        return witnessAddressTwo;
    }

    public void setWitnessAddressTwo(String witnessAddressTwo) {
        this.witnessAddressTwo = witnessAddressTwo;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship Relationship) {
        this.relationship = Relationship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PfmsEmpMembershipForm pfmsEmpMembershipForm = (PfmsEmpMembershipForm) o;

        if ( ! Objects.equals(id, pfmsEmpMembershipForm.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PfmsEmpMembershipForm{" +
            "id=" + id +
            ", isMinimumThreeYrs='" + isMinimumThreeYrs + "'" +
            ", isMandatoryContribute='" + isMandatoryContribute + "'" +
            ", isAnotherContFund='" + isAnotherContFund + "'" +
            ", fundName='" + fundName + "'" +
            ", isEmpFamily='" + isEmpFamily + "'" +
            ", percentOfDeduct='" + percentOfDeduct + "'" +
            ", isMoneySection='" + isMoneySection + "'" +
            ", nomineeName='" + nomineeName + "'" +
            ", ageOfNominee='" + ageOfNominee + "'" +
            ", nomineeAddress='" + nomineeAddress + "'" +
            ", witnessNameOne='" + witnessNameOne + "'" +
            ", witnessMobileNoOne='" + witnessMobileNoOne + "'" +
            ", witnessAddressOne='" + witnessAddressOne + "'" +
            ", witnessNameTwo='" + witnessNameTwo + "'" +
            ", witnessMobileNoTwo='" + witnessMobileNoTwo + "'" +
            ", witnessAddressTwo='" + witnessAddressTwo + "'" +
            ", stationName='" + stationName + "'" +
            ", applicationDate='" + applicationDate + "'" +
            ", remarks='" + remarks + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
