package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InstInfraInfoTemp.
 */
@Entity
@Table(name = "inst_infra_info_temp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instinfrainfotemp")
public class InstInfraInfoTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status")
    private Integer status;

    @Column(name = "decline_remarks")
    private String declineRemarks;

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
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @OneToOne
    @JoinColumn(name = "inst_building_id")
    private InstBuildingTemp instBuildingTemp;

    @OneToOne
    @JoinColumn(name = "inst_land_id")
    private InstLandTemp instLandTemp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public InstBuildingTemp getInstBuildingTemp() {
        return instBuildingTemp;
    }

    public void setInstBuildingTemp(InstBuildingTemp instBuildingTemp) {
        this.instBuildingTemp = instBuildingTemp;
    }

    public InstLandTemp getInstLandTemp() {
        return instLandTemp;
    }

    public void setInstLandTemp(InstLandTemp instLandTemp) {
        this.instLandTemp = instLandTemp;
    }

    public String getDeclineRemarks() {
        return declineRemarks;
    }

    public void setDeclineRemarks(String declineRemarks) {
        this.declineRemarks = declineRemarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstInfraInfoTemp instInfraInfoTemp = (InstInfraInfoTemp) o;

        if ( ! Objects.equals(id, instInfraInfoTemp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstInfraInfoTemp{" +
            "id=" + id +
            ", status=" + status +
            ", declineRemarks='" + declineRemarks + '\'' +
            ", institute=" + institute +
            ", instBuildingTemp=" + instBuildingTemp +
            ", instLandTemp=" + instLandTemp +
            '}';
    }
}
