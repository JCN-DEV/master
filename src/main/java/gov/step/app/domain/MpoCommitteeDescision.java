package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MpoCommitteeDescision.
 */
@Entity
@Table(name = "mpo_committee_descision")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mpocommitteedescision")
public class MpoCommitteeDescision implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "comments")
    private String comments;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name="mpo_comt_hist_id")
    private MpoCommitteeHistory mpoCommitteeHistory;

    @ManyToOne
    @JoinColumn(name="mpo_application_id")
    private MpoApplication mpoApplication;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public MpoCommitteeHistory getMpoCommitteeHistory() {
        return mpoCommitteeHistory;
    }

    public void setMpoCommitteeHistory(MpoCommitteeHistory mpoCommitteeHistory) {
        this.mpoCommitteeHistory = mpoCommitteeHistory;
    }

    public MpoApplication getMpoApplication() {
        return mpoApplication;
    }

    public void setMpoApplication(MpoApplication mpoApplication) {
        this.mpoApplication = mpoApplication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MpoCommitteeDescision mpoCommitteeDescision = (MpoCommitteeDescision) o;

        if ( ! Objects.equals(id, mpoCommitteeDescision.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MpoCommitteeDescision{" +
            "id=" + id +
            ", comments='" + comments + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
