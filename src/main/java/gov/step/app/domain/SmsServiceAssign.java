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
 * A SmsServiceAssign.
 */
@Entity
@Table(name = "sms_service_assign")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "smsserviceassign")
public class SmsServiceAssign implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @ManyToOne
    @JoinColumn(name = "sms_service_department_id")
    private SmsServiceDepartment smsServiceDepartment;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public SmsServiceDepartment getSmsServiceDepartment() {
        return smsServiceDepartment;
    }

    public void setSmsServiceDepartment(SmsServiceDepartment smsServiceDepartment) {
        this.smsServiceDepartment = smsServiceDepartment;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        SmsServiceAssign smsServiceAssign = (SmsServiceAssign) o;
        return Objects.equals(id, smsServiceAssign.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SmsServiceAssign{" +
            "id=" + id +
            ", activeStatus='" + activeStatus + "'" +
            '}';
    }
}
