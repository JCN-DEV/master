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
 * A InstComiteFormationTemp.
 */
@Entity
@Table(name = "inst_comite_formation_temp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instcomiteformationtemp")
public class InstComiteFormationTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "comite_name")
    private String comiteName;

    @Column(name = "comite_type")
    private String comiteType;

    @Column(name = "address")
    private String address;

    @Column(name = "time_from")
    private Integer timeFrom;

    @Column(name = "time_to")
    private Integer timeTo;

    @Column(name = "formation_date")
    private LocalDate formationDate;

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
    private InstMemShip instMemShip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComiteName() {
        return comiteName;
    }

    public void setComiteName(String comiteName) {
        this.comiteName = comiteName;
    }

    public String getComiteType() {
        return comiteType;
    }

    public void setComiteType(String comiteType) {
        this.comiteType = comiteType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Integer timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Integer getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Integer timeTo) {
        this.timeTo = timeTo;
    }

    public LocalDate getFormationDate() {
        return formationDate;
    }

    public void setFormationDate(LocalDate formationDate) {
        this.formationDate = formationDate;
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

    public InstMemShip getInstMemShip() {
        return instMemShip;
    }

    public void setInstMemShip(InstMemShip instMemShip) {
        this.instMemShip = instMemShip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstComiteFormationTemp instComiteFormationTemp = (InstComiteFormationTemp) o;

        if ( ! Objects.equals(id, instComiteFormationTemp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstComiteFormationTemp{" +
            "id=" + id +
            ", comiteName='" + comiteName + "'" +
            ", comiteType='" + comiteType + "'" +
            ", address='" + address + "'" +
            ", timeFrom='" + timeFrom + "'" +
            ", timeTo='" + timeTo + "'" +
            ", formationDate='" + formationDate + "'" +
            '}';
    }
}
