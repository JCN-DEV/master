package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A JpEmployeeTraining.
 */
@Entity
@Table(name = "jp_employee_training")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jpemployeetraining")
public class JpEmployeeTraining implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="jp_emp_train_seq")
    @SequenceGenerator(name="jp_emp_train_seq", sequenceName="jp_emp_train_seq")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "training_title", length = 100, nullable = false)
    private String trainingTitle;

    //@NotNull
    @Column(name = "topic_covered")
    private String topicCovered;

    //@NotNull
    @Column(name = "institute")
    private String institute;

    @Column(name = "location")
    private String location;

    //@Size(max = 20)
    @Column(name = "duration")
    private String duration;

//    @Size(max = 20)
    @Column(name = "result")
    private String result;

    @Column(name = "completion_date")
    private LocalDate completionDate;


    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;


    @ManyToOne
    @JoinColumn(name = "jp_employee_id")
    private JpEmployee jpEmployee;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainingTitle() {
        return trainingTitle;
    }

    public void setTrainingTitle(String trainingTitle) {
        this.trainingTitle = trainingTitle;
    }

    public String getTopicCovered() {
        return topicCovered;
    }

    public void setTopicCovered(String topicCovered) {
        this.topicCovered = topicCovered;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public JpEmployee getJpEmployee() {
        return jpEmployee;
    }

    public void setJpEmployee(JpEmployee JpEmployee) {
        this.jpEmployee = JpEmployee;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JpEmployeeTraining jpEmployeeTraining = (JpEmployeeTraining) o;

        if ( ! Objects.equals(id, jpEmployeeTraining.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JpEmployeeTraining{" +
            "id=" + id +
            ", trainingTitle='" + trainingTitle + "'" +
            ", topicCovered='" + topicCovered + "'" +
            ", institute='" + institute + "'" +
            ", location='" + location + "'" +
            ", duration='" + duration + "'" +
            ", result='" + result + "'" +
            '}';
    }
}
