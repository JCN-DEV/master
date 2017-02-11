package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InstEmplHist.
 */
@Entity
@Table(name = "inst_empl_hist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instemplhist")
public class InstEmplHist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "designation")
    private String designation;

    @Column(name = "job_area")
    private String jobArea;

    @Column(name = "start")
    private LocalDate start;

    @Column(name = "end")
    private LocalDate end;

    @Column(name = "on_track")
    private Boolean onTrack;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "ext")
    private String ext;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "website")
    private String website;

    @Lob
    @Column(name = "certificate_copy")
    private byte[] certificateCopy;


    @Column(name = "cert_cpy_cnt_type")
    private String certificateCopyContentType;

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


    @ManyToOne
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getJobArea() {
        return jobArea;
    }

    public void setJobArea(String jobArea) {
        this.jobArea = jobArea;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Boolean getOnTrack() {
        return onTrack;
    }

    public void setOnTrack(Boolean onTrack) {
        this.onTrack = onTrack;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public byte[] getCertificateCopy() {
        return certificateCopy;
    }

    public void setCertificateCopy(byte[] certificateCopy) {
        this.certificateCopy = certificateCopy;
    }

    public String getCertificateCopyContentType() {
        return certificateCopyContentType;
    }

    public void setCertificateCopyContentType(String certificateCopyContentType) {
        this.certificateCopyContentType = certificateCopyContentType;
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

        InstEmplHist instEmplHist = (InstEmplHist) o;

        if ( ! Objects.equals(id, instEmplHist.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstEmplHist{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", designation='" + designation + "'" +
            ", jobArea='" + jobArea + "'" +
            ", start='" + start + "'" +
            ", end='" + end + "'" +
            ", onTrack='" + onTrack + "'" +
            ", telephone='" + telephone + "'" +
            ", ext='" + ext + "'" +
            ", email='" + email + "'" +
            ", mobile='" + mobile + "'" +
            ", website='" + website + "'" +
            ", certificateCopy='" + certificateCopy + "'" +
            ", certificateCopyContentType='" + certificateCopyContentType + "'" +
            '}';
    }
}
