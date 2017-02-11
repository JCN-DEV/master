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
 * A PgmsAppRetirmntNmine.
 */
@Entity
@Table(name = "pgms_app_retirmnt_nmine")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pgmsappretirmntnmine")
public class PgmsAppRetirmntNmine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "app_retirmnt_pen_id", nullable = false)
    private Long appRetirmntPenId;

    @Column(name = "nominee_status")
    private Boolean nomineeStatus;

    @NotNull
    @Column(name = "nominee_name", nullable = false)
    private String nomineeName;

    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotNull
    @Column(name = "relation", nullable = false)
    private String relation;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "present_address", nullable = false)
    private String presentAddress;

    @NotNull
    @Column(name = "nid", nullable = false)
    private String nid;

    @NotNull
    @Column(name = "occupation", nullable = false)
    private String occupation;

    @NotNull
    @Column(name = "designation", nullable = false)
    private String designation;

    @NotNull
    @Column(name = "marital_status", nullable = false)
    private String maritalStatus;

    @NotNull
    @Column(name = "mobile_no", nullable = false)
    private Long mobileNo;

    @NotNull
    @Column(name = "get_pension", nullable = false)
    private Long getPension;

    @Column(name = "hr_nominee_info")
    private Boolean hrNomineeInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppRetirmntPenId() {
        return appRetirmntPenId;
    }

    public void setAppRetirmntPenId(Long appRetirmntPenId) {
        this.appRetirmntPenId = appRetirmntPenId;
    }

    public Boolean getNomineeStatus() {
        return nomineeStatus;
    }

    public void setNomineeStatus(Boolean nomineeStatus) {
        this.nomineeStatus = nomineeStatus;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Long getGetPension() {
        return getPension;
    }

    public void setGetPension(Long getPension) {
        this.getPension = getPension;
    }

    public Boolean getHrNomineeInfo() {
        return hrNomineeInfo;
    }

    public void setHrNomineeInfo(Boolean hrNomineeInfo) {
        this.hrNomineeInfo = hrNomineeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgmsAppRetirmntNmine pgmsAppRetirmntNmine = (PgmsAppRetirmntNmine) o;

        if ( ! Objects.equals(id, pgmsAppRetirmntNmine.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PgmsAppRetirmntNmine{" +
            "id=" + id +
            ", appRetirmntPenId='" + appRetirmntPenId + "'" +
            ", nomineeStatus='" + nomineeStatus + "'" +
            ", nomineeName='" + nomineeName + "'" +
            ", gender='" + gender + "'" +
            ", relation='" + relation + "'" +
            ", dateOfBirth='" + dateOfBirth + "'" +
            ", presentAddress='" + presentAddress + "'" +
            ", nid='" + nid + "'" +
            ", occupation='" + occupation + "'" +
            ", designation='" + designation + "'" +
            ", maritalStatus='" + maritalStatus + "'" +
            ", mobileNo='" + mobileNo + "'" +
            ", getPension='" + getPension + "'" +
            ", hrNomineeInfo='" + hrNomineeInfo + "'" +
            '}';
    }
}
