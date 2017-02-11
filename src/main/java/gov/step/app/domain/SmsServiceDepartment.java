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
 * A SmsServiceDepartment.
 */
@Entity
@Table(name = "sms_service_department")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "smsservicedepartment")
public class SmsServiceDepartment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "sms_service_type_id")
    private SmsServiceType smsServiceType;

    @ManyToOne
    @JoinColumn(name = "sms_service_name_id")
    private SmsServiceName smsServiceName;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SmsServiceType getSmsServiceType() {
        return smsServiceType;
    }

    public void setSmsServiceType(SmsServiceType smsServiceType) {
        this.smsServiceType = smsServiceType;
    }

    public SmsServiceName getSmsServiceName() {
        return smsServiceName;
    }

    public void setSmsServiceName(SmsServiceName smsServiceName) {
        this.smsServiceName = smsServiceName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SmsServiceDepartment smsServiceDepartment = (SmsServiceDepartment) o;
        return Objects.equals(id, smsServiceDepartment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SmsServiceDepartment{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", activeStatus='" + activeStatus + "'" +
            '}';
    }
}
