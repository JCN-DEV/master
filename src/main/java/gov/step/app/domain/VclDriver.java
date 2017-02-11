package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A VclDriver.
 */
@Entity
@Table(name = "vcl_driver")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "vcldriver")
public class VclDriver implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "driver_name", nullable = false)
    private String driverName;

    @NotNull
    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @NotNull
    @Size(max = 250)
    @Column(name = "present_address", length = 250, nullable = false)
    private String presentAddress;

    @NotNull
    @Size(max = 250)
    @Column(name = "permanent_address", length = 250, nullable = false)
    private String permanentAddress;

    @NotNull
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @NotNull
    @Column(name = "emergency_number", nullable = false)
    private String emergencyNumber;

    @NotNull
    @Column(name = "join_date", nullable = false)
    private LocalDate joinDate;

    @NotNull
    @Column(name = "retirement_date", nullable = false)
    private LocalDate retirementDate;

    @Column(name = "photo_name")
    private String photoName;

    @Lob
    @Column(name = "driver_photo")
    private byte[] driverPhoto;

    @Column(name = "driver_photo_content_type")
    private String driverPhotoContentType;

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

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDate getRetirementDate() {
        return retirementDate;
    }

    public void setRetirementDate(LocalDate retirementDate) {
        this.retirementDate = retirementDate;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public byte[] getDriverPhoto() {
        return driverPhoto;
    }

    public void setDriverPhoto(byte[] driverPhoto) {
        this.driverPhoto = driverPhoto;
    }

    public String getDriverPhotoContentType() {
        return driverPhotoContentType;
    }

    public void setDriverPhotoContentType(String driverPhotoContentType) {
        this.driverPhotoContentType = driverPhotoContentType;
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
        VclDriver vclDriver = (VclDriver) o;
        return Objects.equals(id, vclDriver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VclDriver{" +
            "id=" + id +
            ", driverName='" + driverName + "'" +
            ", licenseNumber='" + licenseNumber + "'" +
            ", presentAddress='" + presentAddress + "'" +
            ", permanentAddress='" + permanentAddress + "'" +
            ", mobileNumber='" + mobileNumber + "'" +
            ", emergencyNumber='" + emergencyNumber + "'" +
            ", joinDate='" + joinDate + "'" +
            ", retirementDate='" + retirementDate + "'" +
            ", photoName='" + photoName + "'" +
            ", driverPhoto='" + driverPhoto + "'" +
            ", driverPhotoContentType='" + driverPhotoContentType + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
