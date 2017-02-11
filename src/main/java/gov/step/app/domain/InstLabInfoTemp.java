package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InstLabInfoTemp.
 */
@Entity
@Table(name = "inst_lab_info_temp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instlabinfotemp")
public class InstLabInfoTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name_or_number")
    private String nameOrNumber;

    @Column(name = "building_name_or_number")
    private String buildingNameOrNumber;

    @Column(name = "length")
    private String length;

    @Column(name = "width")
    private String width;

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


    @Column(name = "total_books", precision=10, scale=2)
    private BigDecimal totalBooks;

    @ManyToOne
    @JoinColumn(name = "inst_infra_info_id")
    private InstInfraInfoTemp instInfraInfoTemp;

    @ManyToOne
    @JoinColumn(name = "institute_lab_info_id")
    private InstLabInfo instLabInfo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOrNumber() {
        return nameOrNumber;
    }

    public void setNameOrNumber(String nameOrNumber) {
        this.nameOrNumber = nameOrNumber;
    }

    public String getBuildingNameOrNumber() {
        return buildingNameOrNumber;
    }

    public void setBuildingNameOrNumber(String buildingNameOrNumber) {
        this.buildingNameOrNumber = buildingNameOrNumber;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public BigDecimal getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(BigDecimal totalBooks) {
        this.totalBooks = totalBooks;
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

    public InstInfraInfoTemp getInstInfraInfoTemp() {
        return instInfraInfoTemp;
    }

    public void setInstInfraInfoTemp(InstInfraInfoTemp instInfraInfoTemp) {
        this.instInfraInfoTemp = instInfraInfoTemp;
    }

    public InstLabInfo getInstLabInfo() {
        return instLabInfo;
    }

    public void setInstLabInfo(InstLabInfo instLabInfo) {
        this.instLabInfo = instLabInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstLabInfoTemp instLabInfoTemp = (InstLabInfoTemp) o;

        if ( ! Objects.equals(id, instLabInfoTemp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstLabInfoTemp{" +
            "id=" + id +
            ", nameOrNumber='" + nameOrNumber + '\'' +
            ", buildingNameOrNumber='" + buildingNameOrNumber + '\'' +
            ", length='" + length + '\'' +
            ", width='" + width + '\'' +
            ", totalBooks=" + totalBooks +
            ", instInfraInfoTemp=" + instInfraInfoTemp +
            ", instLabInfo=" + instLabInfo +
            '}';
    }
}
