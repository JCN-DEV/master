package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A JasperReportParameter.
 */
@Entity
@Table(name = "jasper_report_parameter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jasperreportparameter")
public class JasperReportParameter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(max = 30)
    @Column(name = "type", length = 30, nullable = false)
    private String type;

    @NotNull
    @Size(max = 100)
    @Column(name = "flevel", length = 100, nullable = false)
    private String flevel;

    @Column(name = "position")
    private Integer position;

    @Column(name = "datatype")
    private String datatype;

    @Column(name = "servicename")
    private String servicename;

    @Column(name = "combodisplayfield")
    private String combodisplayfield;

    @Column(name = "validationexp")
    private String validationexp;

    @Column(name = "maxlength")
    private Integer maxlength;

    @Column(name = "minlength")
    private Integer minlength;

    @Column(name = "actionname")
    private String actionname;

    @Column(name = "actiontype")
    private String actiontype;

    @ManyToOne
    private JasperReport jasperReport;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlevel() {
        return flevel;
    }

    public void setFlevel(String flevel) {
        this.flevel = flevel;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getCombodisplayfield() {
        return combodisplayfield;
    }

    public void setCombodisplayfield(String combodisplayfield) {
        this.combodisplayfield = combodisplayfield;
    }

    public String getValidationexp() {
        return validationexp;
    }

    public void setValidationexp(String validationexp) {
        this.validationexp = validationexp;
    }

    public Integer getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(Integer maxlength) {
        this.maxlength = maxlength;
    }

    public Integer getMinlength() {
        return minlength;
    }

    public void setMinlength(Integer minlength) {
        this.minlength = minlength;
    }

    public String getActionname() {
        return actionname;
    }

    public void setActionname(String actionname) {
        this.actionname = actionname;
    }

    public String getActiontype() {
        return actiontype;
    }

    public void setActiontype(String actiontype) {
        this.actiontype = actiontype;
    }

    public JasperReport getJasperReport() {
        return jasperReport;
    }

    public void setJasperReport(JasperReport JasperReport) {
        this.jasperReport = JasperReport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JasperReportParameter jasperReportParameter = (JasperReportParameter) o;

        if ( ! Objects.equals(id, jasperReportParameter.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JasperReportParameter{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", flevel='" + flevel + "'" +
            ", position='" + position + "'" +
            ", datatype='" + datatype + "'" +
            ", servicename='" + servicename + "'" +
            ", combodisplayfield='" + combodisplayfield + "'" +
            ", validationexp='" + validationexp + "'" +
            ", maxlength='" + maxlength + "'" +
            ", minlength='" + minlength + "'" +
            ", actionname='" + actionname + "'" +
            ", actiontype='" + actiontype + "'" +
            '}';
    }
}
