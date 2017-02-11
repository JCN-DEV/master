package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MpoCommitteePersonInfo.
 */
@Entity
@Table(name = "mpo_committee_person_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mpocommitteepersoninfo")
public class MpoCommitteePersonInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 20)
    @Column(name = "contact_no", length = 20)
    private String contactNo;

    @Size(max = 255)
    @Column(name = "address", length = 255)
    private String address;

    @Size(max = 50)
    @Column(name = "designation", length = 50)
    private String designation;

    @Size(max = 50)
    @Column(name = "org_name", length = 50)
    private String orgName;

    @Column(name = "date_created")
    private LocalDate dateCrated;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Column(name = "status")
    private Integer status;

    @Column(name = "activated")
    private Boolean activated;

    //gender values {1 = male, 2 = female, 3 = other},
    @Column(name = "gender")
    private Integer gender;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "org_contact_no")
    private String orgContactNo;


    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getActivated() {
        return activated;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getOrgContactNo() {
        return orgContactNo;
    }

    public void setOrgContactNo(String orgContactNo) {
        this.orgContactNo = orgContactNo;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MpoCommitteePersonInfo mpoCommitteePersonInfo = (MpoCommitteePersonInfo) o;

        if ( ! Objects.equals(id, mpoCommitteePersonInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MpoCommitteePersonInfo{" +
            "id=" + id +
            ", contactNo='" + contactNo + "'" +
            ", address='" + address + "'" +
            ", designation='" + designation + "'" +
            ", orgName='" + orgName + "'" +
            ", dateCrated='" + dateCrated + "'" +
            ", dateModified='" + dateModified + "'" +
            ", status='" + status + "'" +
            ", activated='" + activated + "'" +
            ", dob='" + dob + "'" +
            ", orgContactNo='" + orgContactNo + "'" +
            ", gender='" + gender + "'" +
            '}';
    }
}
