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
 * A RisAppFormEduQ.
 */
@Entity
@Table(name = "ris_app_form_edu_q")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "risappformeduq")
public class RisAppFormEduQ implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "exam_name", nullable = false)
    private String examName;

    @Column(name = "subject")
    private String subject;

    @Column(name = "educational_institute")
    private String educationalInstitute;

    @Column(name = "passing_year")
    private Integer passingYear;

    @Column(name = "board_university")
    private String boardUniversity;

    @Column(name = "additional_information")
    private String additionalInformation;

    @Column(name = "experience")
    private String experience;

    @Column(name = "qouta")
    private String qouta;


    @ManyToOne
    @JoinColumn(name = "ris_new_app_form_id")
    private RisNewAppForm risNewAppForm;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEducationalInstitute() {
        return educationalInstitute;
    }

    public void setEducationalInstitute(String educationalInstitute) {
        this.educationalInstitute = educationalInstitute;
    }

    public Integer getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(Integer passingYear) {
        this.passingYear = passingYear;
    }

    public String getBoardUniversity() {
        return boardUniversity;
    }

    public void setBoardUniversity(String boardUniversity) {
        this.boardUniversity = boardUniversity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
    public RisNewAppForm getRisNewAppForm() {return this.risNewAppForm;}

    public void setRisNewAppForm(RisNewAppForm risNewAppForm) {this.risNewAppForm = risNewAppForm;}
    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getQouta() {
        return qouta;
    }

    public void setQouta(String qouta) {
        this.qouta = qouta;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RisAppFormEduQ risAppFormEduQ = (RisAppFormEduQ) o;

        if ( ! Objects.equals(id, risAppFormEduQ.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RisAppFormEduQ{" +
            "id=" + id +
            ", examName='" + examName + "'" +
            ", subject='" + subject + "'" +
            ", educationalInstitute='" + educationalInstitute + "'" +
            ", passingYear='" + passingYear + "'" +
            ", boardUniversity='" + boardUniversity + "'" +
            ", additionalInformation='" + additionalInformation + "'" +
            ", experience='" + experience + "'" +
            ", qouta='" + qouta + "'" +
            '}';
    }
}
