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
 * A FeePaymentCollection.
 */
@Entity
@Table(name = "fee_payment_collection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "feepaymentcollection")
public class FeePaymentCollection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @NotNull
    @Column(name = "voucher_no", nullable = false)
    private String voucherNo;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_account_no")
    private String bankAccountNo;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_api_id")
    private String paymentApiID;

    @Column(name = "payment_instrument_type")
    private String paymentInstrumentType;

    @Column(name = "currency")
    private Long currency;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_time", nullable = false)
    private LocalDate updatedTime;

    @ManyToOne
    private FeePaymentTypeSetup paymentType;

    @ManyToOne
    private FeePaymentCategorySetup paymentCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentApiID() {
        return paymentApiID;
    }

    public void setPaymentApiID(String paymentApiID) {
        this.paymentApiID = paymentApiID;
    }

    public String getPaymentInstrumentType() {
        return paymentInstrumentType;
    }

    public void setPaymentInstrumentType(String paymentInstrumentType) {
        this.paymentInstrumentType = paymentInstrumentType;
    }

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDate updatedTime) {
        this.updatedTime = updatedTime;
    }

    public FeePaymentTypeSetup getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(FeePaymentTypeSetup FeePaymentTypeSetup) {
        this.paymentType = FeePaymentTypeSetup;
    }

    public FeePaymentCategorySetup getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(FeePaymentCategorySetup FeePaymentCategorySetup) {
        this.paymentCategory = FeePaymentCategorySetup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeePaymentCollection feePaymentCollection = (FeePaymentCollection) o;

        if ( ! Objects.equals(id, feePaymentCollection.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FeePaymentCollection{" +
            "id=" + id +
            ", transactionId='" + transactionId + "'" +
            ", voucherNo='" + voucherNo + "'" +
            ", status='" + status + "'" +
            ", description='" + description + "'" +
            ", amount='" + amount + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", paymentDate='" + paymentDate + "'" +
            ", bankName='" + bankName + "'" +
            ", bankAccountNo='" + bankAccountNo + "'" +
            ", paymentMethod='" + paymentMethod + "'" +
            ", paymentApiID='" + paymentApiID + "'" +
            ", paymentInstrumentType='" + paymentInstrumentType + "'" +
            ", currency='" + currency + "'" +
            ", updatedBy='" + updatedBy + "'" +
            ", updatedTime='" + updatedTime + "'" +
            '}';
    }
}
