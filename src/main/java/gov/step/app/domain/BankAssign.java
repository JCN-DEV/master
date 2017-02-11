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
 * A BankAssign.
 */
@Entity
@Table(name = "bank_assign")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bankassign")
public class BankAssign implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="bank_assign_seq")
    @SequenceGenerator(name="bank_assign_seq", sequenceName="bank_assign_seq")
    private Long id;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_date")
    private LocalDate createdDate;

    @Column(name = "update_date")
    private LocalDate modifiedDate;


    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updatedBy;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private BankSetup bank;

    @OneToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User user) {
        this.updatedBy = user;
    }

    public BankSetup getBank() {
        return bank;
    }

    public void setBank(BankSetup bankSetup) {
        this.bank = bankSetup;
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

        BankAssign bankAssign = (BankAssign) o;

        if ( ! Objects.equals(id, bankAssign.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BankAssign{" +
            "id=" + id +
            ", createdDate='" + createdDate + "'" +
            ", modifiedDate='" + modifiedDate + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
