package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "country")
public class Country implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="country_seq")
    @SequenceGenerator(name="country_seq", sequenceName="country_seq")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "iso_code3", length = 20, nullable = false, unique=true)
    private String isoCode3;

    @Size(max = 50)
    @Column(name = "iso_code2", length = 50)
    private String isoCode2;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false, unique=true)
    private String name;

    @NotNull
    @Size(max = 50)
    @Column(name = "continent", length = 50, nullable = false)
    private String continent;

    @Size(max = 50)
    @Column(name = "region", length = 50)
    private String region;

    @Size(max = 50)
    @Column(name = "surface_area", length = 50)
    private String surfaceArea;

    @Size(max = 50)
    @Column(name = "indep_year", length = 50)
    private String indepYear;

    @Size(max = 50)
    @Column(name = "population", length = 50)
    private String population;

    @Size(max = 50)
    @Column(name = "life_expectancy", length = 50)
    private String lifeExpectancy;

    @Size(max = 50)
    @Column(name = "gnp", length = 50)
    private String gnp;

    @Size(max = 50)
    @Column(name = "gnp_old", length = 50)
    private String gnpOld;

    @Size(max = 50)
    @Column(name = "local_name", length = 50)
    private String localName;

    @Size(max = 50)
    @Column(name = "governmentform", length = 50)
    private String governmentform;

    @NotNull
    @Size(max = 50)
    @Column(name = "capital", length = 50, nullable = false)
    private String capital;

    @Size(max = 50)
    @Column(name = "head_of_state", length = 50)
    private String headOfState;

    @NotNull
    @Size(max = 50)
    @Column(name = "calling_code", length = 50, nullable = false)
    private String callingCode;

    @Column(name = "tech_growth", length = 3)
    private Integer techGrowth;

    @Column(name = "economy_growth", length = 3)
    private Integer economyGrowth;

    @Column(name = "edu_growth", length = 3)
    private Integer eduGrowth;

    @Column(name = "population_growth", length = 3)
    private Integer populationGrowth;

    @Column(name = "source_name")
    private String sourceName;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsoCode3() {
        return isoCode3;
    }

    public void setIsoCode3(String isoCode3) {
        this.isoCode3 = isoCode3;
    }

    public String getIsoCode2() {
        return isoCode2;
    }

    public void setIsoCode2(String isoCode2) {
        this.isoCode2 = isoCode2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(String surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public String getIndepYear() {
        return indepYear;
    }

    public void setIndepYear(String indepYear) {
        this.indepYear = indepYear;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(String lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public String getGnp() {
        return gnp;
    }

    public void setGnp(String gnp) {
        this.gnp = gnp;
    }

    public String getGnpOld() {
        return gnpOld;
    }

    public void setGnpOld(String gnpOld) {
        this.gnpOld = gnpOld;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getGovernmentform() {
        return governmentform;
    }

    public void setGovernmentform(String governmentform) {
        this.governmentform = governmentform;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getHeadOfState() {
        return headOfState;
    }

    public void setHeadOfState(String headOfState) {
        this.headOfState = headOfState;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }

    public Integer getPopulationGrowth() {
        return populationGrowth;
    }

    public void setPopulationGrowth(Integer populationGrowth) {
        this.populationGrowth = populationGrowth;
    }

    public Integer getTechGrowth() {
        return techGrowth;
    }

    public void setTechGrowth(Integer techGrowth) {
        this.techGrowth = techGrowth;
    }

    public Integer getEconomyGrowth() {
        return economyGrowth;
    }

    public void setEconomyGrowth(Integer economyGrowth) {
        this.economyGrowth = economyGrowth;
    }

    public Integer getEduGrowth() {
        return eduGrowth;
    }

    public void setEduGrowth(Integer eduGrowth) {
        this.eduGrowth = eduGrowth;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
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

        Country country = (Country) o;

        if ( ! Objects.equals(id, country.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Country{" +
            "id=" + id +
            ", isoCode3='" + isoCode3 + "'" +
            ", isoCode2='" + isoCode2 + "'" +
            ", name='" + name + "'" +
            ", continent='" + continent + "'" +
            ", region='" + region + "'" +
            ", surfaceArea='" + surfaceArea + "'" +
            ", indepYear='" + indepYear + "'" +
            ", population='" + population + "'" +
            ", lifeExpectancy='" + lifeExpectancy + "'" +
            ", gnp='" + gnp + "'" +
            ", gnpOld='" + gnpOld + "'" +
            ", localName='" + localName + "'" +
            ", governmentform='" + governmentform + "'" +
            ", capital='" + capital + "'" +
            ", headOfState='" + headOfState + "'" +
            ", callingCode='" + callingCode + "'" +
            '}';
    }
}
