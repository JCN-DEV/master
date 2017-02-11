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

import gov.step.app.domain.enumeration.relationType;

/**
 * A InstEmpSpouseInfo.
 */
@Entity
@Table(name = "inst_emp_spouse_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instempspouseinfo")
public class InstEmpSpouseInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @NotNull
    @Column(name = "is_nominee", nullable = false)
    private Boolean isNominee;

    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "relation", nullable = false)
    private relationType relation;

    @Column(name = "nominee_percentage", precision=10, scale=2, nullable = false)
    private BigDecimal nomineePercentage;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "tin")
    private String tin;

    @NotNull
    @Column(name = "nid", nullable = false)
    private String nid;

    @Column(name = "designation")
    private String designation;

    @Column(name = "gov_job_id")
    private String govJobId;

    @Column(name = "mobile")
    private Integer mobile;

    @Column(name = "office_contact")
    private String officeContact;

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


    @OneToOne
    @JoinColumn(name = "inst_employee_id")
    private InstEmployee instEmployee;

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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Boolean getIsNominee() {
        return isNominee;
    }

    public void setIsNominee(Boolean isNominee) {
        this.isNominee = isNominee;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public relationType getRelation() {
        return relation;
    }

    public void setRelation(relationType relation) {
        this.relation = relation;
    }

    public BigDecimal getNomineePercentage() {
        return nomineePercentage;
    }

    public void setNomineePercentage(BigDecimal nomineePercentage) {
        this.nomineePercentage = nomineePercentage;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getGovJobId() {
        return govJobId;
    }

    public void setGovJobId(String govJobId) {
        this.govJobId = govJobId;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public String getOfficeContact() {
        return officeContact;
    }

    public void setOfficeContact(String officeContact) {
        this.officeContact = officeContact;
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

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstEmpSpouseInfo instEmpSpouseInfo = (InstEmpSpouseInfo) o;

        if ( ! Objects.equals(id, instEmpSpouseInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstEmpSpouseInfo{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", dob='" + dob + "'" +
            ", isNominee='" + isNominee + "'" +
            ", gender='" + gender + "'" +
            ", relation='" + relation + "'" +
            ", nomineePercentage='" + nomineePercentage + "'" +
            ", occupation='" + occupation + "'" +
            ", tin='" + tin + "'" +
            ", nid='" + nid + "'" +
            ", designation='" + designation + "'" +
            ", govJobId='" + govJobId + "'" +
            ", mobile='" + mobile + "'" +
            ", officeContact='" + officeContact + "'" +
            '}';
    }
}
