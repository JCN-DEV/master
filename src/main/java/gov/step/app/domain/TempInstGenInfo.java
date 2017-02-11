package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import gov.step.app.domain.enumeration.instType;

/**
 * A TempInstGenInfo.
 */
@Entity
@Table(name = "temp_inst_gen_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tempinstgeninfo")
public class TempInstGenInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private instType type;

    @Column(name = "village")
    private String village;

    @Column(name = "post_office")
    private String postOffice;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "land_phone")
    private String landPhone;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "email")
    private String email;

    @Column(name = "cons_area")
    private String consArea;

    @Column(name = "update")
    private Boolean update;

    @OneToOne    private InstituteInfo instituteInfo;

    @ManyToOne
    private Upazila upazila;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public instType getType() {
        return type;
    }

    public void setType(instType type) {
        this.type = type;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getLandPhone() {
        return landPhone;
    }

    public void setLandPhone(String landPhone) {
        this.landPhone = landPhone;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConsArea() {
        return consArea;
    }

    public void setConsArea(String consArea) {
        this.consArea = consArea;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public InstituteInfo getInstituteInfo() {
        return instituteInfo;
    }

    public void setInstituteInfo(InstituteInfo instituteInfo) {
        this.instituteInfo = instituteInfo;
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

        TempInstGenInfo tempInstGenInfo = (TempInstGenInfo) o;

        if ( ! Objects.equals(id, tempInstGenInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TempInstGenInfo{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", village='" + village + "'" +
            ", postOffice='" + postOffice + "'" +
            ", postCode='" + postCode + "'" +
            ", landPhone='" + landPhone + "'" +
            ", mobileNo='" + mobileNo + "'" +
            ", email='" + email + "'" +
            ", consArea='" + consArea + "'" +
            ", update='" + update + "'" +
            '}';
    }
}
