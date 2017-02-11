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
 * A AclEntry.
 */
@Entity
@Table(name = "acl_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "aclentry")
public class AclEntry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "ace_order", nullable = false)
    private Integer aceOrder;

    @NotNull
    @Column(name = "mask", nullable = false)
    private Integer mask;

    @NotNull
    @Column(name = "granting", nullable = false)
    private Boolean granting;

    @NotNull
    @Column(name = "audit_success", nullable = false)
    private Boolean auditSuccess;

    @NotNull
    @Column(name = "audit_failure", nullable = false)
    private Boolean auditFailure;

    @ManyToOne
    private AclObjectIdentity aclObjectIdentity;

    @ManyToOne
    private AclSid aclSid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAceOrder() {
        return aceOrder;
    }

    public void setAceOrder(Integer aceOrder) {
        this.aceOrder = aceOrder;
    }

    public Integer getMask() {
        return mask;
    }

    public void setMask(Integer mask) {
        this.mask = mask;
    }

    public Boolean getGranting() {
        return granting;
    }

    public void setGranting(Boolean granting) {
        this.granting = granting;
    }

    public Boolean getAuditSuccess() {
        return auditSuccess;
    }

    public void setAuditSuccess(Boolean auditSuccess) {
        this.auditSuccess = auditSuccess;
    }

    public Boolean getAuditFailure() {
        return auditFailure;
    }

    public void setAuditFailure(Boolean auditFailure) {
        this.auditFailure = auditFailure;
    }

    public AclObjectIdentity getAclObjectIdentity() {
        return aclObjectIdentity;
    }

    public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
        this.aclObjectIdentity = aclObjectIdentity;
    }

    public AclSid getAclSid() {
        return aclSid;
    }

    public void setAclSid(AclSid aclSid) {
        this.aclSid = aclSid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AclEntry aclEntry = (AclEntry) o;

        if ( ! Objects.equals(id, aclEntry.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AclEntry{" +
            "id=" + id +
            ", aceOrder='" + aceOrder + "'" +
            ", mask='" + mask + "'" +
            ", granting='" + granting + "'" +
            ", auditSuccess='" + auditSuccess + "'" +
            ", auditFailure='" + auditFailure + "'" +
            '}';
    }
}
