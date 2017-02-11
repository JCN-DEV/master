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

import gov.step.app.domain.enumeration.addressTypes;

/**
 * A HrEmpAddressInfo.
 */
@Entity
@Table(name = "hr_emp_address_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrempaddressinfo")
public class HrEmpAddressInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    private addressTypes addressType;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "house_number_bn")
    private String houseNumberBn;

    @Column(name = "road_number")
    private String roadNumber;

    @Column(name = "road_number_bn")
    private String roadNumberBn;

    @Column(name = "village_name")
    private String villageName;

    @Column(name = "village_name_bn")
    private String villageNameBn;

    @Column(name = "post_office")
    private String postOffice;

    @Column(name = "post_office_bn")
    private String postOfficeBn;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_name_bn")
    private String contactNameBn;

    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "active_status", nullable = false)
    private Boolean activeStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "log_id")
    private Long logId;

    @Column(name = "log_status")
    private Long logStatus;

    @Column(name = "log_comments")
    private String logComments;

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public addressTypes getAddressType() {
        return addressType;
    }

    public void setAddressType(addressTypes addressType) {
        this.addressType = addressType;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Long logStatus) {
        this.logStatus = logStatus;
    }

    public String getLogComments() {
        return logComments;
    }

    public void setLogComments(String logComments) {this.logComments = logComments;}

    public String getHouseNumberBn() {return houseNumberBn;}

    public void setHouseNumberBn(String houseNumberBn) {this.houseNumberBn = houseNumberBn;}

    public String getRoadNumberBn() {return roadNumberBn;}

    public void setRoadNumberBn(String roadNumberBn) {this.roadNumberBn = roadNumberBn;}

    public String getVillageNameBn() {return villageNameBn;}

    public void setVillageNameBn(String villageNameBn) {this.villageNameBn = villageNameBn;}

    public String getPostOfficeBn() {return postOfficeBn;}

    public void setPostOfficeBn(String postOfficeBn) {this.postOfficeBn = postOfficeBn;}

    public String getContactName() {return contactName;}

    public void setContactName(String contactName) {this.contactName = contactName;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getContactNameBn() {return contactNameBn;}

    public void setContactNameBn(String contactNameBn) {this.contactNameBn = contactNameBn;}

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division Division) {
        this.division = Division;
    }

    public District getDistrict() {return district;}

    public void setDistrict(District district) {this.district = district;}

    public Upazila getUpazila() {return upazila;}

    public void setUpazila(Upazila upazila) {this.upazila = upazila;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEmpAddressInfo hrEmpAddressInfo = (HrEmpAddressInfo) o;
        return Objects.equals(id, hrEmpAddressInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpAddressInfo{" +
            "id=" + id +
            ", addressType='" + addressType + "'" +
            ", houseNumber='" + houseNumber + "'" +
            ", roadNumber='" + roadNumber + "'" +
            ", villageName='" + villageName + "'" +
            ", postOffice='" + postOffice + "'" +
            ", contactNumber='" + contactNumber + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
