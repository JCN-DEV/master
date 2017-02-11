package gov.step.app.domain;

import gov.step.app.domain.enumeration.EmployeeType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A StaffCount.
 */
@Entity
@Table(name = "staff_count")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "staffcount")
public class StaffCount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EmployeeType type;

    @Column(name = "number_of_principal")
    private Integer numberOfPrincipal;

    @Column(name = "num_of_male_teacher")
    private Integer numberOfMaleTeacher;

    @Column(name = "num_of_fmale_teacher")
    private Integer numberOfFemaleTeacher;

    @Column(name = "num_of_demonstrator")
    private Integer numberOfDemonstrator;

    @Column(name = "num_of_assistant_libra")
    private Integer numberOfAssistantLibrarian;

    @Column(name = "num_of_lab_assistant")
    private Integer numberOfLabAssistant;

    @Column(name = "num_of_science_lab_assist")
    private Integer numberOfScienceLabAssistant;

    @Column(name = "third_class")
    private Integer thirdClass;

    @Column(name = "fourth_class")
    private Integer fourthClass;

    @Column(name = "num_of_fmale_avail_by_qota")
    private Integer numberOfFemaleAvailableByQuota;

    @ManyToOne
    private Institute institute;

    @ManyToOne
    private User manager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public Integer getNumberOfPrincipal() {
        return numberOfPrincipal;
    }

    public void setNumberOfPrincipal(Integer numberOfPrincipal) {
        this.numberOfPrincipal = numberOfPrincipal;
    }

    public Integer getNumberOfMaleTeacher() {
        return numberOfMaleTeacher;
    }

    public void setNumberOfMaleTeacher(Integer numberOfMaleTeacher) {
        this.numberOfMaleTeacher = numberOfMaleTeacher;
    }

    public Integer getNumberOfFemaleTeacher() {
        return numberOfFemaleTeacher;
    }

    public void setNumberOfFemaleTeacher(Integer numberOfFemaleTeacher) {
        this.numberOfFemaleTeacher = numberOfFemaleTeacher;
    }

    public Integer getNumberOfDemonstrator() {
        return numberOfDemonstrator;
    }

    public void setNumberOfDemonstrator(Integer numberOfDemonstrator) {
        this.numberOfDemonstrator = numberOfDemonstrator;
    }

    public Integer getNumberOfAssistantLibrarian() {
        return numberOfAssistantLibrarian;
    }

    public void setNumberOfAssistantLibrarian(Integer numberOfAssistantLibrarian) {
        this.numberOfAssistantLibrarian = numberOfAssistantLibrarian;
    }

    public Integer getNumberOfLabAssistant() {
        return numberOfLabAssistant;
    }

    public void setNumberOfLabAssistant(Integer numberOfLabAssistant) {
        this.numberOfLabAssistant = numberOfLabAssistant;
    }

    public Integer getNumberOfScienceLabAssistant() {
        return numberOfScienceLabAssistant;
    }

    public void setNumberOfScienceLabAssistant(Integer numberOfScienceLabAssistant) {
        this.numberOfScienceLabAssistant = numberOfScienceLabAssistant;
    }

    public Integer getThirdClass() {
        return thirdClass;
    }

    public void setThirdClass(Integer thirdClass) {
        this.thirdClass = thirdClass;
    }

    public Integer getFourthClass() {
        return fourthClass;
    }

    public void setFourthClass(Integer fourthClass) {
        this.fourthClass = fourthClass;
    }

    public Integer getNumberOfFemaleAvailableByQuota() {
        return numberOfFemaleAvailableByQuota;
    }

    public void setNumberOfFemaleAvailableByQuota(Integer numberOfFemaleAvailableByQuota) {
        this.numberOfFemaleAvailableByQuota = numberOfFemaleAvailableByQuota;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User user) {
        this.manager = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StaffCount staffCount = (StaffCount) o;

        if (!Objects.equals(id, staffCount.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StaffCount{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", numberOfPrincipal='" + numberOfPrincipal + "'" +
            ", numberOfMaleTeacher='" + numberOfMaleTeacher + "'" +
            ", numberOfFemaleTeacher='" + numberOfFemaleTeacher + "'" +
            ", numberOfDemonstrator='" + numberOfDemonstrator + "'" +
            ", numberOfAssistantLibrarian='" + numberOfAssistantLibrarian + "'" +
            ", numberOfLabAssistant='" + numberOfLabAssistant + "'" +
            ", numberOfScienceLabAssistant='" + numberOfScienceLabAssistant + "'" +
            ", thirdClass='" + thirdClass + "'" +
            ", fourthClass='" + fourthClass + "'" +
            ", numberOfFemaleAvailableByQuota='" + numberOfFemaleAvailableByQuota + "'" +
            '}';
    }
}
