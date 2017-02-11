package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import gov.step.app.domain.enumeration.vehicleTypes;

import gov.step.app.domain.enumeration.vehicleStatus;

/**
 * A VclVehicle.
 */
@Entity
@Table(name = "vcl_vehicle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "vclvehicle")
public class VclVehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "vehicle_name", nullable = false)
    private String vehicleName;

    @NotNull
    @Column(name = "vehicle_number", nullable = false)
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private vehicleTypes vehicleType;

    @NotNull
    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @NotNull
    @Column(name = "chassis_number", nullable = false)
    private String chassisNumber;

    @NotNull
    @Column(name = "date_of_buying", nullable = false)
    private LocalDate dateOfBuying;

    @Column(name = "supplier_name")
    private String supplierName;

    @NotNull
    @Column(name = "no_of_seats", nullable = false)
    private Integer noOfSeats;

    @NotNull
    @Column(name = "passenger_capacity", nullable = false)
    private Integer passengerCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_availability")
    private vehicleStatus vehicleAvailability;

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

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public vehicleTypes getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(vehicleTypes vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public LocalDate getDateOfBuying() {
        return dateOfBuying;
    }

    public void setDateOfBuying(LocalDate dateOfBuying) {
        this.dateOfBuying = dateOfBuying;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(Integer noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public Integer getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(Integer passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public vehicleStatus getVehicleAvailability() {
        return vehicleAvailability;
    }

    public void setVehicleAvailability(vehicleStatus vehicleAvailability) {
        this.vehicleAvailability = vehicleAvailability;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VclVehicle vclVehicle = (VclVehicle) o;
        return Objects.equals(id, vclVehicle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VclVehicle{" +
            "id=" + id +
            ", vehicleName='" + vehicleName + "'" +
            ", vehicleNumber='" + vehicleNumber + "'" +
            ", vehicleType='" + vehicleType + "'" +
            ", licenseNumber='" + licenseNumber + "'" +
            ", chassisNumber='" + chassisNumber + "'" +
            ", dateOfBuying='" + dateOfBuying + "'" +
            ", supplierName='" + supplierName + "'" +
            ", noOfSeats='" + noOfSeats + "'" +
            ", passengerCapacity='" + passengerCapacity + "'" +
            ", vehicleAvailability='" + vehicleAvailability + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
