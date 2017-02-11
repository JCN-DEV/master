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
 * A InstPlayGroundInfoTemp.
 */
@Entity
@Table(name = "inst_play_ground_info_temp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instplaygroundinfotemp")
public class InstPlayGroundInfoTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "playground_no")
    private String playgroundNo;

    @Column(name = "area")
    private String area;

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
    @JoinColumn(name = "inst_infra_info_id")
    private InstInfraInfoTemp instInfraInfoTemp;

    @OneToOne
    @JoinColumn(name = "institute_ply_grnd_inf_id")
    private InstPlayGroundInfoTemp instPlayGroundInfoTemp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaygroundNo() {
        return playgroundNo;
    }

    public void setPlaygroundNo(String playgroundNo) {
        this.playgroundNo = playgroundNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public InstInfraInfoTemp getInstInfraInfoTemp() {
        return instInfraInfoTemp;
    }

    public void setInstInfraInfoTemp(InstInfraInfoTemp instInfraInfoTemp) {
        this.instInfraInfoTemp = instInfraInfoTemp;
    }

    public InstPlayGroundInfoTemp getInstPlayGroundInfoTemp() {
        return instPlayGroundInfoTemp;
    }

    public void setInstPlayGroundInfoTemp(InstPlayGroundInfoTemp instPlayGroundInfoTemp) {
        this.instPlayGroundInfoTemp = instPlayGroundInfoTemp;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstPlayGroundInfoTemp instPlayGroundInfoTemp = (InstPlayGroundInfoTemp) o;

        if ( ! Objects.equals(id, instPlayGroundInfoTemp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstPlayGroundInfoTemp{" +
            "id=" + id +
            ", playgroundNo='" + playgroundNo + '\'' +
            ", area='" + area + '\'' +
            ", instInfraInfoTemp=" + instInfraInfoTemp +
            ", instPlayGroundInfoTemp=" + instPlayGroundInfoTemp +
            '}';
    }
}
