package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BankBranch.
 */
@Entity
@Table(name = "bank_branch")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bankbranch")
public class BankBranch implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="bank_branch_seq")
    @SequenceGenerator(name="bank_branch_seq", sequenceName="bank_branch_seq")
    private Long id;

    @NotNull
    @Column(name = "br_name", nullable = false)
    private String brName;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;


    @Column(name = "update_date")
    private LocalDate updateDate;


    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;

    @ManyToOne
    @JoinColumn(name = "bank_setup_id")
    private BankSetup bankSetup;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrName() {
        return brName;
    }

    public void setBrName(String brName) {
        this.brName = brName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public BankSetup getBankSetup() {
        return bankSetup;
    }

    public void setBankSetup(BankSetup bankSetup) {
        this.bankSetup = bankSetup;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Upazila getUpazila() {
        return upazila;
    }

    public void setUpazila(Upazila upazila) {
        this.upazila = upazila;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BankBranch bankBranch = (BankBranch) o;
        return Objects.equals(id, bankBranch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BankBranch{" +
            "id=" + id +
            ", brName='" + brName + "'" +
            ", address='" + address + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
