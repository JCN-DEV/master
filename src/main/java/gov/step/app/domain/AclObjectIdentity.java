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
 * A AclObjectIdentity.
 */
@Entity
@Table(name = "acl_object_identity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "aclobjectidentity")
public class AclObjectIdentity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "entries_inheriting", nullable = false)
    private Boolean entriesInheriting;

    @ManyToOne
    private AclClass aclClass;

    @ManyToOne
    private AclSid aclSid;

    @OneToOne    private AclObjectIdentity parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEntriesInheriting() {
        return entriesInheriting;
    }

    public void setEntriesInheriting(Boolean entriesInheriting) {
        this.entriesInheriting = entriesInheriting;
    }

    public AclClass getAclClass() {
        return aclClass;
    }

    public void setAclClass(AclClass aclClass) {
        this.aclClass = aclClass;
    }

    public AclSid getAclSid() {
        return aclSid;
    }

    public void setAclSid(AclSid aclSid) {
        this.aclSid = aclSid;
    }

    public AclObjectIdentity getParent() {
        return parent;
    }

    public void setParent(AclObjectIdentity aclObjectIdentity) {
        this.parent = aclObjectIdentity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AclObjectIdentity aclObjectIdentity = (AclObjectIdentity) o;

        if ( ! Objects.equals(id, aclObjectIdentity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AclObjectIdentity{" +
            "id=" + id +
            ", entriesInheriting='" + entriesInheriting + "'" +
            '}';
    }
}
