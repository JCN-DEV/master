package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import gov.step.app.domain.enumeration.miscConfigDataType;

/**
 * A MiscConfigSetup.
 */
@Entity
@Table(name = "misc_config_setup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "miscconfigsetup")
public class MiscConfigSetup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @NotNull
    @Column(name = "property_title", nullable = false)
    private String propertyTitle;

    @NotNull
    @Column(name = "property_value", nullable = false)
    private String propertyValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "property_data_type", nullable = false)
    private miscConfigDataType propertyDataType;

    @Column(name = "property_value_max")
    private String propertyValueMax;

    @Column(name = "property_desc")
    private String propertyDesc;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public miscConfigDataType getPropertyDataType() {
        return propertyDataType;
    }

    public void setPropertyDataType(miscConfigDataType propertyDataType) {
        this.propertyDataType = propertyDataType;
    }

    public String getPropertyValueMax() {
        return propertyValueMax;
    }

    public void setPropertyValueMax(String propertyValueMax) {
        this.propertyValueMax = propertyValueMax;
    }

    public String getPropertyDesc() {
        return propertyDesc;
    }

    public void setPropertyDesc(String propertyDesc) {
        this.propertyDesc = propertyDesc;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiscConfigSetup miscConfigSetup = (MiscConfigSetup) o;

        if ( ! Objects.equals(id, miscConfigSetup.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MiscConfigSetup{" +
            "id=" + id +
            ", propertyName='" + propertyName + "'" +
            ", propertyTitle='" + propertyTitle + "'" +
            ", propertyValue='" + propertyValue + "'" +
            ", propertyDataType='" + propertyDataType + "'" +
            ", propertyValueMax='" + propertyValueMax + "'" +
            ", propertyDesc='" + propertyDesc + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
