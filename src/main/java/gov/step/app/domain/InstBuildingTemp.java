package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A InstBuildingTemp.
 */
@Entity
@Table(name = "inst_building_temp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instbuildingtemp")
public class InstBuildingTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "total_area", nullable = false)
    private String totalArea;

    @NotNull
    @Column(name = "total_room")
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
    private Integer direction;

    @Column(name = "teachers_room")
    private Integer teachersRoom;

    @Column(name = "training_room")
    private Integer trainingRoom;

    @Column(name = "workshop_room")
    private Integer workshopRoom;

    @Column(name = "meeting_room")
    private Integer meetingRoom;

    @Column(name = "research_room")
    private Integer researchRoom;

    @Column(name = "lab_room")
    private Integer labRoom;

    @Column(name = "counseling_room")
    private Integer counselingRoom;

    @Column(name = "auditorium_room")
    private Integer auditoriumRoom;

    @Column(name = "common_room")
    private Integer commonRoom;

    @Column(name = "male_bathroom")
    private Integer maleBathroom;

    @Column(name = "female_bathroom")
    private Integer femaleBathroom;

    @Column(name = "store_room")
    private Integer storeRoom;

    @Column(name = "total_library_room")
    private Integer totalLibraryRoom;

    @Column(name = "total_shop")
    private Integer totalShop;

    @Column(name = "d_name")
    private String dName;

    @Column(name = "d_status")
    private boolean dStatus;

    @Column(name = "d_mwash")
    private Integer dMwash;

    @Column(name = "d_fwash")
    private Integer dFwash;

    @Column(name = "lab_no")
    private Integer labNo;

    @Column(name = "workshop_No")
    private Integer workshopNo;

    @Column(name = "ramp_status")
    private boolean rampStatus;

    @Column(name = "create_date")
    private LocalDate dateCrated;

    @Column(name = "update_date")
    private LocalDate dateModified;

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

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getTeachersRoom() {
        return teachersRoom;
    }

    public void setTeachersRoom(Integer teachersRoom) {
        this.teachersRoom = teachersRoom;
    }

    public Integer getTrainingRoom() {
        return trainingRoom;
    }

    public void setTrainingRoom(Integer trainingRoom) {
        this.trainingRoom = trainingRoom;
    }

    public Integer getWorkshopRoom() {
        return workshopRoom;
    }

    public void setWorkshopRoom(Integer workshopRoom) {
        this.workshopRoom = workshopRoom;
    }

    public Integer getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(Integer meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public Integer getResearchRoom() {
        return researchRoom;
    }

    public void setResearchRoom(Integer researchRoom) {
        this.researchRoom = researchRoom;
    }

    public Integer getLabRoom() {
        return labRoom;
    }

    public void setLabRoom(Integer labRoom) {
        this.labRoom = labRoom;
    }

    public Integer getCounselingRoom() {
        return counselingRoom;
    }

    public void setCounselingRoom(Integer counselingRoom) {
        this.counselingRoom = counselingRoom;
    }

    public Integer getAuditoriumRoom() {
        return auditoriumRoom;
    }

    public void setAuditoriumRoom(Integer auditoriumRoom) {
        this.auditoriumRoom = auditoriumRoom;
    }

    public Integer getCommonRoom() {
        return commonRoom;
    }

    public void setCommonRoom(Integer commonRoom) {
        this.commonRoom = commonRoom;
    }

    public Integer getMaleBathroom() {
        return maleBathroom;
    }

    public void setMaleBathroom(Integer maleBathroom) {
        this.maleBathroom = maleBathroom;
    }

    public Integer getFemaleBathroom() {
        return femaleBathroom;
    }

    public void setFemaleBathroom(Integer femaleBathroom) {
        this.femaleBathroom = femaleBathroom;
    }

    public Integer getStoreRoom() {
        return storeRoom;
    }

    public void setStoreRoom(Integer storeRoom) {
        this.storeRoom = storeRoom;
    }

    public Integer getTotalLibraryRoom() {
        return totalLibraryRoom;
    }

    public void setTotalLibraryRoom(Integer totalLibraryRoom) {
        this.totalLibraryRoom = totalLibraryRoom;
    }

    public Integer getTotalShop() {
        return totalShop;
    }

    public void setTotalShop(Integer totalShop) {
        this.totalShop = totalShop;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public boolean isdStatus() {
        return dStatus;
    }

    public void setdStatus(boolean dStatus) {
        this.dStatus = dStatus;
    }

    public Integer getdMwash() {
        return dMwash;
    }

    public void setdMwash(Integer dMwash) {
        this.dMwash = dMwash;
    }

    public Integer getdFwash() {
        return dFwash;
    }

    public void setdFwash(Integer dFwash) {
        this.dFwash = dFwash;
    }

    public Integer getLabNo() {
        return labNo;
    }

    public void setLabNo(Integer labNo) {
        this.labNo = labNo;
    }

    public Integer getWorkshopNo() {
        return workshopNo;
    }

    public void setWorkshopNo(Integer workshopNo) {
        this.workshopNo = workshopNo;
    }

    public boolean isRampStatus() {
        return rampStatus;
    }

    public void setRampStatus(boolean rampStatus) {
        this.rampStatus = rampStatus;
    }

    public LocalDate getDateCrated() {
        return dateCrated;
    }

    public void setDateCrated(LocalDate dateCrated) {
        this.dateCrated = dateCrated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstBuildingTemp instBuildingTemp = (InstBuildingTemp) o;

        if ( ! Objects.equals(id, instBuildingTemp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstBuildingTemp{" +
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
            ", dName='" + dName + '\'' +
            ", dStatus=" + dStatus +
            ", dMwash=" + dMwash +
            ", dFwash=" + dFwash +
            ", labNo=" + labNo +
            ", workshopNo=" + workshopNo +
            ", rampStatus=" + rampStatus +
            '}';
    }
}
