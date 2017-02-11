package gov.step.app.domain;

import gov.step.app.domain.enumeration.proficiency;
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
 * A JpLanguageProficiency.
 */
@Entity
@Table(name = "jp_language_proficiency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jplanguageproficiency")
public class JpLanguageProficiency implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="jp_lang_prof_seq")
    @SequenceGenerator(name="jp_lang_prof_seq", sequenceName="jp_lang_prof_seq")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "reading")
    private proficiency reading;

    @Column(name = "writing")
    private proficiency writing;

    @Column(name = "speaking")
    private proficiency speaking;

    @Column(name = "listening")
    private proficiency listening;


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

    public proficiency getReading() {
        return reading;
    }

    public void setReading(proficiency reading) {
        this.reading = reading;
    }

    public proficiency getWriting() {
        return writing;
    }

    public void setWriting(proficiency writing) {
        this.writing = writing;
    }

    public proficiency getSpeaking() {
        return speaking;
    }

    public void setSpeaking(proficiency speaking) {
        this.speaking = speaking;
    }

    public proficiency getListening() {
        return listening;
    }

    public void setListening(proficiency listening) {
        this.listening = listening;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JpLanguageProficiency jpLanguageProficiency = (JpLanguageProficiency) o;

        if ( ! Objects.equals(id, jpLanguageProficiency.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JpLanguageProficiency{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", reading='" + reading + "'" +
            ", writing='" + writing + "'" +
            ", speaking='" + speaking + "'" +
            ", listening='" + listening + "'" +
            '}';
    }
}
