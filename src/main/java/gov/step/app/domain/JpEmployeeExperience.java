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
 * A JpEmployeeExperience.
 */
@Entity
@Table(name = "jp_employee_experience")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jpemployeeexperience")
public class JpEmployeeExperience implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="jp_emp_exp_seq")
    @SequenceGenerator(name="jp_emp_exp_seq", sequenceName="jp_emp_exp_seq")
    private Long id;

    @Size(max = 50)
    @Column(name = "skill", length = 50)
    private String skill;

    @Size(max = 200)
    @Column(name = "skill_description", length = 200)
    private String skillDescription;

    @Column(name = "skill_level")
    private proficiency skillLevel;


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
    @JoinColumn(name = "jp_skill_id")
    private JpSkill jpSkill;

    @ManyToOne
    @JoinColumn(name = "jp_skill_level_id")
    private JpSkillLevel jpSkillLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getSkillDescription() {
        return skillDescription;
    }

    public void setSkillDescription(String skillDescription) {
        this.skillDescription = skillDescription;
    }

    public proficiency getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(proficiency skillLevel) {
        this.skillLevel = skillLevel;
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

    public JpSkill getJpSkill() {
        return jpSkill;
    }

    public void setJpSkill(JpSkill jpSkill) {
        this.jpSkill = jpSkill;
    }

    public JpSkillLevel getJpSkillLevel() {
        return jpSkillLevel;
    }

    public void setJpSkillLevel(JpSkillLevel jpSkillLevel) {
        this.jpSkillLevel = jpSkillLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JpEmployeeExperience jpEmployeeExperience = (JpEmployeeExperience) o;

        if ( ! Objects.equals(id, jpEmployeeExperience.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JpEmployeeExperience{" +
            "id=" + id +
            ", skill='" + skill + "'" +
            ", skillDescription='" + skillDescription + "'" +
            '}';
    }
}
