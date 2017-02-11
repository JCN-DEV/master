package gov.step.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A DeoHistLog.
 */
@Entity
@Table(name = "deo_hist_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "deohistlog")
public class DeoHistLog implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="deo_hist_log_seq")
    @SequenceGenerator(name="deo_hist_log_seq", sequenceName="deo_hist_log_seq")
    private Long id;

    @Column(name = "create_date")
    private LocalDate dateCrated;

    @Column(name = "update_date")
    private LocalDate dateModified;

    @Column(name = "status")
    private Integer status;

    @Column(name = "activated")
    private Boolean activated;

    @ManyToOne
    @JoinColumn(name="deo_id")
    private Deo deo;

    @ManyToOne
    @JoinColumn(name="district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;

    /*@OneToMany(mappedBy = "deoHistLog")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<District> districts = new HashSet<>();*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setActivated(Boolean activated) {
        this.activated = activated;
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

    public Deo getDeo() {
        return deo;
    }

    public void setDeo(Deo deo) {
        this.deo = deo;
    }

   /* public Set<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Set<District> districts) {
        this.districts = districts;
    }*/

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeoHistLog deoHistLog = (DeoHistLog) o;

        if ( ! Objects.equals(id, deoHistLog.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DeoHistLog{" +
            "id=" + id +
            ", dateCrated='" + dateCrated + "'" +
            ", dateModified='" + dateModified + "'" +
            ", status='" + status + "'" +
            ", activated='" + activated + "'" +
            '}';
    }
}
