package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A EduBoard.
 */
@Entity
@Table(name = "edu_board")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "eduboard")
public class EduBoard implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="edu_board_seq")
    @SequenceGenerator(name="edu_board_seq", sequenceName="edu_board_seq")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false, unique=true)
    private String name;

    //@Size(min = 100)
    @Column(name = "description")
    private String description;

    //boardType values = university, board
    //this field to define university or board
    @Column(name = "board_type")
    private String boardType;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;


    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;


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

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EduBoard eduBoard = (EduBoard) o;

        if ( ! Objects.equals(id, eduBoard.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EduBoard{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", boardType='" + boardType + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
