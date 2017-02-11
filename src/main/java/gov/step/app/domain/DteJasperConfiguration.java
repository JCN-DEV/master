package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DteJasperConfiguration.
 */
@Entity
@Table(name = "dte_jasper_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dtejasperconfiguration")
public class DteJasperConfiguration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "domain", length = 50, nullable = false)
    private String domain;

    @NotNull
    @Size(max = 4)
    @Column(name = "port", length = 4, nullable = false)
    private String port;

    @NotNull
    @Size(max = 30)
    @Column(name = "username", length = 30, nullable = false)
    private String username;

    @NotNull
    @Size(max = 30)
    @Column(name = "password", length = 30, nullable = false)
    private String password;

    @Column(name = "create_url")
    private String createUrl;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "modified_date", nullable = false)
    private LocalDate modifiedDate;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateUrl() {
        return createUrl;
    }

    public void setCreateUrl(String createUrl) {
        this.createUrl = createUrl;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

        DteJasperConfiguration dteJasperConfiguration = (DteJasperConfiguration) o;

        if ( ! Objects.equals(id, dteJasperConfiguration.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DteJasperConfiguration{" +
            "id=" + id +
            ", domain='" + domain + "'" +
            ", port='" + port + "'" +
            ", username='" + username + "'" +
            ", password='" + password + "'" +
            ", createUrl='" + createUrl + "'" +
            ", createDate='" + createDate + "'" +
            ", modifiedDate='" + modifiedDate + "'" +
            ", createBy='" + createBy + "'" +
            ", modifiedBy='" + modifiedBy + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
