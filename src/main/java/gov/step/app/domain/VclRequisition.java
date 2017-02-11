package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import gov.step.app.domain.enumeration.requisitionTypes;

import gov.step.app.domain.enumeration.vehicleTypes;

import gov.step.app.domain.enumeration.requisitionStatus;

/**
 * A VclRequisition.
 */
@Entity
@Table(name = "vcl_requisition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "vclrequisition")
public class VclRequisition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "requisition_type", nullable = false)
    private requisitionTypes requisitionType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private vehicleTypes vehicleType;

    @NotNull
    @Column(name = "source_location", nullable = false)
    private String sourceLocation;

    @NotNull
    @Column(name = "destination_location", nullable = false)
    private String destinationLocation;

    @NotNull
    @Column(name = "expected_date", nullable = false)
    private LocalDate expectedDate;

    @NotNull
    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @Column(name = "requisition_cause")
    private String requisitionCause;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "requisition_status", nullable = false)
    private requisitionStatus requisitionStatus;

    @Column(name = "meter_reading")
    private String meterReading;

    @Column(name = "fuel_reading")
    private String fuelReading;

    @Column(name = "bill_amount", precision=10, scale=2)
    private BigDecimal billAmount;

    @Column(name = "expected_arrival_date")
    private LocalDate expectedArrivalDate;

    @Column(name = "comments")
    private String comments;

    @Column(name = "action_date")
    private LocalDate actionDate;

    @Column(name = "action_by")
    private Long actionBy;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VclVehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "alter_driver_id")
    private VclDriver alterDriver;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public requisitionTypes getRequisitionType() {
        return requisitionType;
    }

    public void setRequisitionType(requisitionTypes requisitionType) {
        this.requisitionType = requisitionType;
    }

    public vehicleTypes getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(vehicleTypes vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(String sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public LocalDate getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(LocalDate expectedDate) {
        this.expectedDate = expectedDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getRequisitionCause() {
        return requisitionCause;
    }

    public void setRequisitionCause(String requisitionCause) {
        this.requisitionCause = requisitionCause;
    }

    public requisitionStatus getRequisitionStatus() {
        return requisitionStatus;
    }

    public void setRequisitionStatus(requisitionStatus requisitionStatus) {
        this.requisitionStatus = requisitionStatus;
    }

    public String getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(String meterReading) {
        this.meterReading = meterReading;
    }

    public String getFuelReading() {
        return fuelReading;
    }

    public void setFuelReading(String fuelReading) {
        this.fuelReading = fuelReading;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public LocalDate getExpectedArrivalDate() {
        return expectedArrivalDate;
    }

    public void setExpectedArrivalDate(LocalDate expectedArrivalDate) {
        this.expectedArrivalDate = expectedArrivalDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
    }

    public Long getActionBy() {
        return actionBy;
    }

    public void setActionBy(Long actionBy) {
        this.actionBy = actionBy;
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

    public User getUser() {
        return user;
    }

    public void setUser(User User) {
        this.user = User;
    }

    public VclVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(VclVehicle VclVehicle) {
        this.vehicle = VclVehicle;
    }

    public VclDriver getAlterDriver() {
        return alterDriver;
    }

    public void setAlterDriver(VclDriver VclDriver) {
        this.alterDriver = VclDriver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VclRequisition vclRequisition = (VclRequisition) o;
        return Objects.equals(id, vclRequisition.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VclRequisition{" +
            "id=" + id +
            ", requisitionType='" + requisitionType + "'" +
            ", vehicleType='" + vehicleType + "'" +
            ", sourceLocation='" + sourceLocation + "'" +
            ", destinationLocation='" + destinationLocation + "'" +
            ", expectedDate='" + expectedDate + "'" +
            ", returnDate='" + returnDate + "'" +
            ", requisitionCause='" + requisitionCause + "'" +
            ", requisitionStatus='" + requisitionStatus + "'" +
            ", meterReading='" + meterReading + "'" +
            ", fuelReading='" + fuelReading + "'" +
            ", billAmount='" + billAmount + "'" +
            ", expectedArrivalDate='" + expectedArrivalDate + "'" +
            ", comments='" + comments + "'" +
            ", actionDate='" + actionDate + "'" +
            ", actionBy='" + actionBy + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
