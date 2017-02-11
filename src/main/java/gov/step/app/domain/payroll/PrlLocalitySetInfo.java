package gov.step.app.domain.payroll;

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
 * A PrlLocalitySetInfo.
 */
@Entity
@Table(name = "prl_locality_set_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prllocalitysetinfo")
public class PrlLocalitySetInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 6)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "fixed_set_type")
    private Boolean fixedSetType;

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "prl_locality_set_info_map",
               joinColumns = @JoinColumn(name="prl_locality_set_infos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="locality_infos_id", referencedColumnName="ID"))
    private Set<PrlLocalityInfo> localityInfos = new HashSet<>();


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

    public Boolean getFixedSetType() {return fixedSetType;}

    public void setFixedSetType(Boolean fixedSetType) {this.fixedSetType = fixedSetType;}

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

    public Set<PrlLocalityInfo> getLocalityInfos() {
        return localityInfos;
    }

    public void setLocalityInfos(Set<PrlLocalityInfo> PrlLocalityInfos) {
        this.localityInfos = PrlLocalityInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrlLocalitySetInfo prlLocalitySetInfo = (PrlLocalitySetInfo) o;
        return Objects.equals(id, prlLocalitySetInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrlLocalitySetInfo{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", fixedSetType='" + fixedSetType + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
