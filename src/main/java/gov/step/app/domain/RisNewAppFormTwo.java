package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A RisNewAppFormTwo.
 */
@Entity
@Table(name = "ris_new_app_form_two")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "risnewappformtwo")
public class RisNewAppFormTwo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bank_draft_no")
    private String bankDraftNo;

    @Column(name = "date_fin_document")
    private LocalDate dateFinDocument;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "departmental_candidate")
    private String departmentalCandidate;

    @Lob
    @Column(name = "bank_invoice")
    private byte[] bankInvoice;

    @Column(name = "bank_invoice_content_type", nullable = false)
    private String bankInvoiceContentType;

    @Column(name = "bank_invoice_name", nullable = false)
    private String bankInvoiceName;

    @Lob
    @Column(name = "signature")
    private byte[] signature;

    @Column(name = "signature_content_type", nullable = false)
    private String signatureContentType;

    @Column(name = "signature_img_name", nullable = false)
    private String signatureImgName;



    @OneToOne
    @JoinColumn(name="ris_new_app_form_id")
    private RisNewAppForm risNewAppForm;


    public RisNewAppForm getRisNewAppForm() {
        return this.risNewAppForm;
    }

    public void setRisNewAppForm(RisNewAppForm risNewAppForm) {
        this.risNewAppForm = risNewAppForm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankDraftNo() {
        return bankDraftNo;
    }

    public void setBankDraftNo(String bankDraftNo) {
        this.bankDraftNo = bankDraftNo;
    }

    public LocalDate getDateFinDocument() {
        return this.dateFinDocument;
    }

    public void setDateFinDocument(LocalDate dateFinDocument) {
        this.dateFinDocument = dateFinDocument;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDepartmentalCandidate() {
        return departmentalCandidate;
    }

    public void setDepartmentalCandidate(String departmentalCandidate) {
        this.departmentalCandidate = departmentalCandidate;
    }

    public byte[] getBankInvoice() {
        return bankInvoice;
    }

    public void setBankInvoice(byte[] bankInvoice) {
        this.bankInvoice = bankInvoice;
    }

    public String getBankInvoiceContentType() {
        return bankInvoiceContentType;
    }

    public void setBankInvoiceContentType(String bankInvoiceContentType) {
        this.bankInvoiceContentType = bankInvoiceContentType;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureContentType() {
        return signatureContentType;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }



    public String getSignatureImgName() {
        return this.signatureImgName;
    }

    public void setSignatureImgName(String signatureImgName) {
        this.signatureImgName = signatureImgName;
    }

    public String getBankInvoiceName() {
        return this.bankInvoiceName;
    }

    public void setBankInvoiceName(String bankInvoiceName) {
        this.bankInvoiceName = bankInvoiceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RisNewAppFormTwo risNewAppFormTwo = (RisNewAppFormTwo) o;

        if ( ! Objects.equals(id, risNewAppFormTwo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RisNewAppFormTwo{" +
            "id=" + id +
            ", bankDraftNo='" + bankDraftNo + "'" +
            ", dateFinDocument='" + dateFinDocument + "'" +
            ", bankName='" + bankName + "'" +
            ", branchName='" + branchName + "'" +
            ", departmentalCandidate='" + departmentalCandidate + "'" +
            ", bankInvoice='" + bankInvoice + "'" +
            ", bankInvoiceContentType='" + bankInvoiceContentType + "'" +
            ", signature='" + signature + "'" +
            ", signatureContentType='" + signatureContentType + "'" +

            '}';
    }
}
