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
 * A HrEmpAddressInfoLog.
 */
@Entity
@Table(name = "hr_emp_address_info_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hrempaddressinfolog")
public class HrEmpAddressInfoLog implements Serializable {

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

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "log_status")
    private Long logStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "action_date")
    private LocalDate actionDate;

    @Column(name = "action_by")
    private Long actionBy;

    @Column(name = "action_comments")
    private String actionComments;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Long logStatus) {
        this.logStatus = logStatus;
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

    public String getActionComments() {
        return actionComments;
    }

    public void setActionComments(String actionComments) {
        this.actionComments = actionComments;
    }

    public HrEmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(HrEmployeeInfo HrEmployeeInfo) {
        this.employeeInfo = HrEmployeeInfo;
    }

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
        HrEmpAddressInfoLog hrEmpAddressInfoLog = (HrEmpAddressInfoLog) o;
        return Objects.equals(id, hrEmpAddressInfoLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEmpAddressInfoLog{" +
            "id=" + id +
            ", addressType='" + addressType + "'" +
            ", houseNumber='" + houseNumber + "'" +
            ", roadNumber='" + roadNumber + "'" +
            ", villageName='" + villageName + "'" +
            ", postOffice='" + postOffice + "'" +
            ", contactNumber='" + contactNumber + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", parentId='" + parentId + "'" +
            ", logStatus='" + logStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", actionDate='" + actionDate + "'" +
            ", actionBy='" + actionBy + "'" +
            ", actionComments='" + actionComments + "'" +
            '}';
    }
}
