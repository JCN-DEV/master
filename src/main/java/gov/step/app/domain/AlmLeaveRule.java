package gov.step.app.domain;

import gov.step.app.domain.enumeration.AlmGender;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AlmLeaveRule.
 */
@Entity
@Table(name = "alm_leave_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "almleaverule")
public class AlmLeaveRule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @NotNull
//    @Column(name = "leave_rule_name", nullable = false)
//    private String leaveRuleName;

    @NotNull
    @Column(name = "no_of_days_entitled", nullable = false)
    private Long noOfDaysEntitled;

    @NotNull
    @Column(name = "max_consecutive_leaves", nullable = false)
    private Long maxConsecutiveLeaves;

    @NotNull
    @Column(name = "min_gap_between_two_leaves", nullable = false)
    private Long minGapBetweenTwoLeaves;

    @NotNull
    @Column(name = "no_of_instance_allowed", nullable = false)
    private Long noOfInstanceAllowed;

    @Column(name = "is_neg_balance_allowed")
    private Boolean isNegBalanceAllowed;

    @Column(name = "max_neg_balance")
    private Long maxNegBalance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "alm_gender", nullable = false)
    private AlmGender almGender;

    @NotNull
    @Column(name = "applicable_service_length", nullable = false)
    private Long applicableServiceLength;

    @Column(name = "is_certificate_required")
    private Boolean isCertificateRequired;

    @Column(name = "required_no_of_days")
    private Long requiredNoOfDays;

    @Column(name = "is_earn_leave")
    private Boolean isEarnLeave;

    @Column(name = "days_required_to_earn")
    private Long daysRequiredToEarn;

    @Column(name = "no_of_leaves_earned")
    private Long noOfLeavesEarned;

    @Column(name = "is_leave_without_pay")
    private Boolean isLeaveWithoutPay;

    @Column(name = "is_carry_forward")
    private Boolean isCarryForward;

    @Column(name = "max_carry_forward")
    private Long maxCarryForward;

    @Column(name = "max_balance_forward")
    private Long maxBalanceForward;

//    @NotNull
//    @Column(name = "is_suffix_prefix", nullable = false)
//    private Boolean isSuffixPrefix;

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
    @JoinColumn(name = "alm_leave_type_id")
    private AlmLeaveType almLeaveType;

    @ManyToOne
    @JoinColumn(name = "alm_leave_group_id")
    private AlmLeaveGroup almLeaveGroup;

  /*  @ManyToOne
    @JoinColumn(name = "alm_earning_method_id")
    private AlmEarningMethod almEarningMethod;

    @ManyToOne
    @JoinColumn(name = "alm_earning_frequency_id")
    private AlmEarningFrequency almEarningFrequency;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getLeaveRuleName() {
//        return leaveRuleName;
//    }
//
//    public void setLeaveRuleName(String leaveRuleName) {
//        this.leaveRuleName = leaveRuleName;
//    }

    public Long getNoOfDaysEntitled() {
        return noOfDaysEntitled;
    }

    public void setNoOfDaysEntitled(Long noOfDaysEntitled) {
        this.noOfDaysEntitled = noOfDaysEntitled;
    }

    public Long getMaxConsecutiveLeaves() {
        return maxConsecutiveLeaves;
    }

    public void setMaxConsecutiveLeaves(Long maxConsecutiveLeaves) {
        this.maxConsecutiveLeaves = maxConsecutiveLeaves;
    }

    public Long getMinGapBetweenTwoLeaves() {
        return minGapBetweenTwoLeaves;
    }

    public void setMinGapBetweenTwoLeaves(Long minGapBetweenTwoLeaves) {
        this.minGapBetweenTwoLeaves = minGapBetweenTwoLeaves;
    }

    public Long getNoOfInstanceAllowed() {
        return noOfInstanceAllowed;
    }

    public void setNoOfInstanceAllowed(Long noOfInstanceAllowed) {
        this.noOfInstanceAllowed = noOfInstanceAllowed;
    }

    public Boolean getIsNegBalanceAllowed() {
        return isNegBalanceAllowed;
    }

    public void setIsNegBalanceAllowed(Boolean isNegBalanceAllowed) {
        this.isNegBalanceAllowed = isNegBalanceAllowed;
    }

    public Long getMaxNegBalance() {
        return maxNegBalance;
    }

    public void setMaxNegBalance(Long maxNegBalance) {
        this.maxNegBalance = maxNegBalance;
    }

    public AlmGender getAlmGender() {
        return almGender;
    }

    public void setAlmGender(AlmGender almGender) {
        this.almGender = almGender;
    }

    public Long getApplicableServiceLength() {
        return applicableServiceLength;
    }

    public void setApplicableServiceLength(Long applicableServiceLength) {
        this.applicableServiceLength = applicableServiceLength;
    }

    public Boolean getIsCertificateRequired() {
        return isCertificateRequired;
    }

    public void setIsCertificateRequired(Boolean isCertificateRequired) {
        this.isCertificateRequired = isCertificateRequired;
    }

    public Long getRequiredNoOfDays() {
        return requiredNoOfDays;
    }

    public void setRequiredNoOfDays(Long requiredNoOfDays) {
        this.requiredNoOfDays = requiredNoOfDays;
    }

    public Boolean getIsEarnLeave() {
        return isEarnLeave;
    }

    public void setIsEarnLeave(Boolean isEarnLeave) {
        this.isEarnLeave = isEarnLeave;
    }

    public Long getDaysRequiredToEarn() {
        return daysRequiredToEarn;
    }

    public void setDaysRequiredToEarn(Long daysRequiredToEarn) {
        this.daysRequiredToEarn = daysRequiredToEarn;
    }

    public Long getNoOfLeavesEarned() {
        return noOfLeavesEarned;
    }

    public void setNoOfLeavesEarned(Long noOfLeavesEarned) {
        this.noOfLeavesEarned = noOfLeavesEarned;
    }

    public Boolean getIsLeaveWithoutPay() {
        return isLeaveWithoutPay;
    }

    public void setIsLeaveWithoutPay(Boolean isLeaveWithoutPay) {
        this.isLeaveWithoutPay = isLeaveWithoutPay;
    }

    public Boolean getIsCarryForward() {
        return isCarryForward;
    }

    public void setIsCarryForward(Boolean isCarryForward) {
        this.isCarryForward = isCarryForward;
    }

    public Long getMaxCarryForward() {
        return maxCarryForward;
    }

    public void setMaxCarryForward(Long maxCarryForward) {
        this.maxCarryForward = maxCarryForward;
    }

    public Long getMaxBalanceForward() {
        return maxBalanceForward;
    }

    public void setMaxBalanceForward(Long maxBalanceForward) {
        this.maxBalanceForward = maxBalanceForward;
    }

//    public Boolean getIsSuffixPrefix(){
//        return isSuffixPrefix;
//    }
//
//    public void setIsSuffixPrefix(Boolean isSuffixPrefix){
//        this.isSuffixPrefix = isSuffixPrefix;
//    }

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

    public AlmLeaveType getAlmLeaveType() {
        return almLeaveType;
    }

    public void setAlmLeaveType(AlmLeaveType AlmLeaveType) {
        this.almLeaveType = AlmLeaveType;
    }

    public AlmLeaveGroup getAlmLeaveGroup() {
        return almLeaveGroup;
    }

    public void setAlmLeaveGroup(AlmLeaveGroup AlmLeaveGroup) {
        this.almLeaveGroup = AlmLeaveGroup;
    }

   /* public AlmEarningMethod getAlmEarningMethod() {
        return almEarningMethod;
    }

    public void setAlmEarningMethod(AlmEarningMethod AlmLeaveType) {
        this.almEarningMethod = AlmLeaveType;
    }


    public AlmEarningFrequency getAlmEarningFrequency() {
        return almEarningFrequency;
    }

    public void setAlmEarningFrequency(AlmEarningFrequency AlmEarningFrequency) {
        this.almEarningFrequency = AlmEarningFrequency;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlmLeaveRule almLeaveRule = (AlmLeaveRule) o;

        if ( ! Objects.equals(id, almLeaveRule.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlmLeaveRule{" +
            "id=" + id +
//            ", leaveRuleName='" + leaveRuleName + "'" +
            ", noOfDaysEntitled='" + noOfDaysEntitled + "'" +
            ", maxConsecutiveLeaves='" + maxConsecutiveLeaves + "'" +
            ", minGapBetweenTwoLeaves='" + minGapBetweenTwoLeaves + "'" +
            ", noOfInstanceAllowed='" + noOfInstanceAllowed + "'" +
            ", isNegBalanceAllowed='" + isNegBalanceAllowed + "'" +
            ", maxNegBalance='" + maxNegBalance + "'" +
            ", almGender='" + almGender + "'" +
            ", applicableServiceLength='" + applicableServiceLength + "'" +
            ", isCertificateRequired='" + isCertificateRequired + "'" +
            ", requiredNoOfDays='" + requiredNoOfDays + "'" +
            ", isEarnLeave='" + isEarnLeave + "'" +
            ", daysRequiredToEarn='" + daysRequiredToEarn + "'" +
            ", noOfLeavesEarned='" + noOfLeavesEarned + "'" +
            ", isLeaveWithoutPay='" + isLeaveWithoutPay + "'" +
            ", isCarryForward='" + isCarryForward + "'" +
            ", maxCarryForward='" + maxCarryForward + "'" +
            ", maxBalanceForward='" + maxBalanceForward + "'" +
            ", activeStatus='" + activeStatus + "'" +
//            ", isSuffixPrefix='" + isSuffixPrefix + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
