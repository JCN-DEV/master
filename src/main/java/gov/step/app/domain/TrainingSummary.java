package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TrainingSummary.
 */
@Entity
@Table(name = "training_summary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "trainingsummary")
public class TrainingSummary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "topics_covered", nullable = false)
    private String topicsCovered;

    @NotNull
    @Column(name = "institute", nullable = false)
    private String institute;

    @NotNull
    @Column(name = "country", nullable = false)
    private String country;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "started_date", nullable = false)
    private ZonedDateTime startedDate;

    @Column(name = "ended_date", nullable = false)
    private ZonedDateTime endedDate;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopicsCovered() {
        return topicsCovered;
    }

    public void setTopicsCovered(String topicsCovered) {
        this.topicsCovered = topicsCovered;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ZonedDateTime getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(ZonedDateTime startedDate) {
        this.startedDate = startedDate;
    }

    public ZonedDateTime getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(ZonedDateTime endedDate) {
        this.endedDate = endedDate;
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

        TrainingSummary trainingSummary = (TrainingSummary) o;

        if (!Objects.equals(id, trainingSummary.id))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TrainingSummary{" + "id=" + id + ", title='" + title + "'" + ", topicsCovered='" + topicsCovered + "'"
            + ", institute='" + institute + "'" + ", country='" + country + "'" + ", location='" + location + "'"
            + ", startedDate='" + startedDate + "'" + ", endedDate='" + endedDate + "'" + '}';
    }
}
