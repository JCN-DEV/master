package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * A InstLand.
 */
@Entity
@Table(name = "inst_land")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instland")
public class InstLand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "mouja", nullable = false)
    private String mouja;

    @NotNull
    @Column(name = "jl_no", nullable = false)
    private String jlNo;

    @NotNull
    @Column(name = "ledger_no", precision=10, scale=2, nullable = false)
    private BigDecimal ledgerNo;

    @NotNull
    @Column(name = "dag_no", nullable = false)
    private String dagNo;

    @NotNull
    @Column(name = "amount_of_land", precision=10, scale=2, nullable = false)
    private BigDecimal amountOfLand;

    @NotNull
    @Column(name = "land_registration_ledger_no", nullable = false)
    private String landRegistrationLedgerNo;

    @NotNull
    @Column(name = "land_registration_date", nullable = false)
    private LocalDate landRegistrationDate;

    @NotNull
    @Column(name = "last_tax_payment_date", nullable = false)
    private LocalDate lastTaxPaymentDate;

    @Column(name = "boundary_north", precision=10, scale=2, nullable = false)
    private BigDecimal boundaryNorth;

    @Column(name = "boundary_south", precision=10, scale=2, nullable = false)
    private BigDecimal boundarySouth;

    @Column(name = "boundary_east", precision=10, scale=2, nullable = false)
    private BigDecimal boundaryEast;

    @Column(name = "boundary_west", precision=10, scale=2, nullable = false)
    private BigDecimal boundaryWest;

    @Column(name = "distnc_from_dist_hq")
    private String distncFromDistHQ;

    @Column(name = "distnc_from_main_rd")
    private String distncFromMainRd;

    @Column(name = "height")
    private String height;

    @Column(name = "width")
    private String width;

    @Column(name = "mutation_no")
    private String mutationNo;

    @Column(name = "mutation_date")
    private Date mutationDate;

    @Column(name = "deed_no")
    private String deedNo;

    @Column(name = "giver_name")
    private String giverName;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_date")
    private Date receiverDate;

    @Column(name = "survey")
    private String survey;

    @Column(name = "porcha")
    private String porcha;

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


    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getMutationNo() {
        return mutationNo;
    }

    public void setMutationNo(String mutationNo) {
        this.mutationNo = mutationNo;
    }

    public Date getMutationDate() {
        return mutationDate;
    }

    public void setMutationDate(Date mutationDate) {
        this.mutationDate = mutationDate;
    }

    public String getDeedNo() {
        return deedNo;
    }

    public void setDeedNo(String deedNo) {
        this.deedNo = deedNo;
    }

    public String getGiverName() {
        return giverName;
    }

    public void setGiverName(String giverName) {
        this.giverName = giverName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Date getReceiverDate() {
        return receiverDate;
    }

    public void setReceiverDate(Date receiverDate) {
        this.receiverDate = receiverDate;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public String getPorcha() {
        return porcha;
    }

    public void setPorcha(String porcha) {
        this.porcha = porcha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMouja() {
        return mouja;
    }

    public void setMouja(String mouja) {
        this.mouja = mouja;
    }

    public String getJlNo() {
        return jlNo;
    }

    public void setJlNo(String jlNo) {
        this.jlNo = jlNo;
    }

    public BigDecimal getLedgerNo() {
        return ledgerNo;
    }

    public void setLedgerNo(BigDecimal ledgerNo) {
        this.ledgerNo = ledgerNo;
    }

    public String getDagNo() {
        return dagNo;
    }

    public void setDagNo(String dagNo) {
        this.dagNo = dagNo;
    }

    public BigDecimal getAmountOfLand() {
        return amountOfLand;
    }

    public void setAmountOfLand(BigDecimal amountOfLand) {
        this.amountOfLand = amountOfLand;
    }

    public String getLandRegistrationLedgerNo() {
        return landRegistrationLedgerNo;
    }

    public void setLandRegistrationLedgerNo(String landRegistrationLedgerNo) {
        this.landRegistrationLedgerNo = landRegistrationLedgerNo;
    }

    public LocalDate getLandRegistrationDate() {
        return landRegistrationDate;
    }

    public void setLandRegistrationDate(LocalDate landRegistrationDate) {
        this.landRegistrationDate = landRegistrationDate;
    }

    public LocalDate getLastTaxPaymentDate() {
        return lastTaxPaymentDate;
    }

    public void setLastTaxPaymentDate(LocalDate lastTaxPaymentDate) {
        this.lastTaxPaymentDate = lastTaxPaymentDate;
    }

    public BigDecimal getBoundaryNorth() {
        return boundaryNorth;
    }

    public void setBoundaryNorth(BigDecimal boundaryNorth) {
        this.boundaryNorth = boundaryNorth;
    }

    public BigDecimal getBoundarySouth() {
        return boundarySouth;
    }

    public void setBoundarySouth(BigDecimal boundarySouth) {
        this.boundarySouth = boundarySouth;
    }

    public BigDecimal getBoundaryEast() {
        return boundaryEast;
    }

    public void setBoundaryEast(BigDecimal boundaryEast) {
        this.boundaryEast = boundaryEast;
    }

    public BigDecimal getBoundaryWest() {
        return boundaryWest;
    }

    public void setBoundaryWest(BigDecimal boundaryWest) {
        this.boundaryWest = boundaryWest;
    }

    public String getDistncFromDistHQ() {
        return distncFromDistHQ;
    }

    public void setDistncFromDistHQ(String distncFromDistHQ) {
        this.distncFromDistHQ = distncFromDistHQ;
    }

    public String getDistncFromMainRd() {
        return distncFromMainRd;
    }

    public void setDistncFromMainRd(String distncFromMainRd) {
        this.distncFromMainRd = distncFromMainRd;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstLand instLand = (InstLand) o;

        if ( ! Objects.equals(id, instLand.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstLand{" +
            "id=" + id +
            ", mouja='" + mouja + "'" +
            ", jlNo='" + jlNo + "'" +
            ", ledgerNo='" + ledgerNo + "'" +
            ", dagNo='" + dagNo + "'" +
            ", amountOfLand='" + amountOfLand + "'" +
            ", landRegistrationLedgerNo='" + landRegistrationLedgerNo + "'" +
            ", landRegistrationDate='" + landRegistrationDate + "'" +
            ", lastTaxPaymentDate='" + lastTaxPaymentDate + "'" +
            ", boundaryNorth='" + boundaryNorth + "'" +
            ", boundarySouth='" + boundarySouth + "'" +
            ", boundaryEast='" + boundaryEast + "'" +
            ", boundaryWest='" + boundaryWest + "'" +
            '}';
    }
}
