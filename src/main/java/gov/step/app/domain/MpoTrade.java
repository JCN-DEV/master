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
 * A MpoTrade.
 */
@Entity
@Table(name = "mpo_trade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mpotrade")
public class MpoTrade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "crated_date")
    private LocalDate cratedDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "CMSTRADE_ID")
    private CmsTrade cmsTrade;

    @ManyToOne
    @JoinColumn(name = "CREATE_BY")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "UPDATE_BY")
    private User updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCratedDate() {
        return cratedDate;
    }

    public void setCratedDate(LocalDate cratedDate) {
        this.cratedDate = cratedDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CmsTrade getCmsTrade() {
        return cmsTrade;
    }

    public void setCmsTrade(CmsTrade cmsTrade) {
        this.cmsTrade = cmsTrade;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User user) {
        this.createBy = user;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User user) {
        this.updateBy = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MpoTrade mpoTrade = (MpoTrade) o;

        if ( ! Objects.equals(id, mpoTrade.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MpoTrade{" +
            "id=" + id +
            ", cratedDate='" + cratedDate + "'" +
            ", updateDate='" + updateDate + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
