package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A InstituteBuilding.
 */
@Entity
@Table(name = "institute_building")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "institutebuilding")
public class InstituteBuilding implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "total_area", nullable = false)
    private String totalArea;

    @NotNull
    @Column(name = "total_room", precision=10, scale=2, nullable = false)
    private BigDecimal totalRoom;

    @NotNull
    @Column(name = "class_room", precision=10, scale=2, nullable = false)
    private BigDecimal classRoom;

    @NotNull
    @Column(name = "office_room", precision=10, scale=2, nullable = false)
    private BigDecimal officeRoom;

    @Column(name = "other_room", precision=10, scale=2, nullable = false)
    private BigDecimal otherRoom;

    @Column(name = "name")
    private String name;

    @Column(name = "direction")
    private int direction;

    @Column(name = "teachers_room")
    private int teachersRoom;

    @Column(name = "training_room")
    private int trainingRoom;

    @Column(name = "workshop_room")
    private int workshopRoom;

    @Column(name = "meeting_room")
    private int meetingRoom;

    @Column(name = "research_room")
    private int researchRoom;

    @Column(name = "lab_room")
    private int labRoom;

    @Column(name = "counseling_room")
    private int counselingRoom;

    @Column(name = "auditorium_room")
    private int auditoriumRoom;

    @Column(name = "common_room")
    private int commonRoom;

    @Column(name = "male_bathroom")
    private int maleBathroom;

    @Column(name = "female_bathroom")
    private int femaleBathroom;

    @Column(name = "store_room")
    private int storeRoom;

    @Column(name = "total_library_room")
    private int totalLibraryRoom;

    @Column(name = "total_shop")
    private int totalShop;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(String totalArea) {
        this.totalArea = totalArea;
    }

    public BigDecimal getTotalRoom() {
        return totalRoom;
    }

    public void setTotalRoom(BigDecimal totalRoom) {
        this.totalRoom = totalRoom;
    }

    public BigDecimal getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(BigDecimal classRoom) {
        this.classRoom = classRoom;
    }

    public BigDecimal getOfficeRoom() {
        return officeRoom;
    }

    public void setOfficeRoom(BigDecimal officeRoom) {
        this.officeRoom = officeRoom;
    }

    public BigDecimal getOtherRoom() {
        return otherRoom;
    }

    public void setOtherRoom(BigDecimal otherRoom) {
        this.otherRoom = otherRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getTeachersRoom() {
        return teachersRoom;
    }

    public void setTeachersRoom(int teachersRoom) {
        this.teachersRoom = teachersRoom;
    }

    public int getTrainingRoom() {
        return trainingRoom;
    }

    public void setTrainingRoom(int trainingRoom) {
        this.trainingRoom = trainingRoom;
    }

    public int getWorkshopRoom() {
        return workshopRoom;
    }

    public void setWorkshopRoom(int workshopRoom) {
        this.workshopRoom = workshopRoom;
    }

    public int getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(int meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public int getResearchRoom() {
        return researchRoom;
    }

    public void setResearchRoom(int researchRoom) {
        this.researchRoom = researchRoom;
    }

    public int getLabRoom() {
        return labRoom;
    }

    public void setLabRoom(int labRoom) {
        this.labRoom = labRoom;
    }

    public int getCounselingRoom() {
        return counselingRoom;
    }

    public void setCounselingRoom(int counselingRoom) {
        this.counselingRoom = counselingRoom;
    }

    public int getAuditoriumRoom() {
        return auditoriumRoom;
    }

    public void setAuditoriumRoom(int auditoriumRoom) {
        this.auditoriumRoom = auditoriumRoom;
    }

    public int getCommonRoom() {
        return commonRoom;
    }

    public void setCommonRoom(int commonRoom) {
        this.commonRoom = commonRoom;
    }

    public int getMaleBathroom() {
        return maleBathroom;
    }

    public void setMaleBathroom(int maleBathroom) {
        this.maleBathroom = maleBathroom;
    }

    public int getFemaleBathroom() {
        return femaleBathroom;
    }

    public void setFemaleBathroom(int femaleBathroom) {
        this.femaleBathroom = femaleBathroom;
    }

    public int getStoreRoom() {
        return storeRoom;
    }

    public void setStoreRoom(int storeRoom) {
        this.storeRoom = storeRoom;
    }

    public int getTotalLibraryRoom() {
        return totalLibraryRoom;
    }

    public void setTotalLibraryRoom(int totalLibraryRoom) {
        this.totalLibraryRoom = totalLibraryRoom;
    }

    public int getTotalShop() {
        return totalShop;
    }

    public void setTotalShop(int totalShop) {
        this.totalShop = totalShop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstituteBuilding instituteBuilding = (InstituteBuilding) o;

        if ( ! Objects.equals(id, instituteBuilding.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstituteBuilding{" +
            "id=" + id +
            ", totalArea='" + totalArea + '\'' +
            ", totalRoom=" + totalRoom +
            ", classRoom=" + classRoom +
            ", officeRoom=" + officeRoom +
            ", otherRoom=" + otherRoom +
            ", name='" + name + '\'' +
            ", direction=" + direction +
            ", teachersRoom=" + teachersRoom +
            ", trainingRoom=" + trainingRoom +
            ", workshopRoom=" + workshopRoom +
            ", meetingRoom=" + meetingRoom +
            ", researchRoom=" + researchRoom +
            ", labRoom=" + labRoom +
            ", counselingRoom=" + counselingRoom +
            ", auditoriumRoom=" + auditoriumRoom +
            ", commonRoom=" + commonRoom +
            ", maleBathroom=" + maleBathroom +
            ", femaleBathroom=" + femaleBathroom +
            ", storeRoom=" + storeRoom +
            ", totalLibraryRoom=" + totalLibraryRoom +
            ", totalShop=" + totalShop +
            '}';
    }
}
