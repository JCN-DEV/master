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

/**
 * A Deo.
 */
@Entity
@Table(name = "deo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "deo")
public class Deo implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="deo_seq")
    @SequenceGenerator(name="deo_seq", sequenceName="deo_seq")
    private Long id;

    @Size(max = 20)
    @Column(name = "contact_no", length = 20)
    private String contactNo;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Size(max = 50)
    @Column(name = "designation", length = 50)
    private String designation;

    @Size(max = 50)
    @Column(name = "org_name", length = 50)
    private String orgName;

    @Column(name = "CREATE_DATE")
    private LocalDate dateCrated;

    @Column(name = "UPDATE_DATE")
    private LocalDate dateModified;

    @Column(name = "status")
    private Integer status;

    @Column(name = "email")
    private String email;

    @Column(name = "activated")
    private Boolean activated;

    @ManyToOne
    @JoinColumn(name="CREATE_BY")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name="UPDATE_BY")
    private User updateBy;

    @OneToOne
    @JoinColumn(name="user_id")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActivated() {
        return activated;
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

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
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

        Deo deo = (Deo) o;

        if ( ! Objects.equals(id, deo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Deo{" +
            "id=" + id +
            ", contactNo='" + contactNo + "'" +
            ", name='" + name + "'" +
            ", designation='" + designation + "'" +
            ", orgName='" + orgName + "'" +
            ", dateCrated='" + dateCrated + "'" +
            ", dateModified='" + dateModified + "'" +
            ", status='" + status + "'" +
            ", email='" + email + "'" +
            ", activated='" + activated + "'" +
            '}';
    }
}
