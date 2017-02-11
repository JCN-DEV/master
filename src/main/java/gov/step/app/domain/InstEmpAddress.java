package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InstEmpAddress.
 */
@Entity
@Table(name = "inst_emp_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instempaddress")
public class InstEmpAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "village_or_house")
    private String villageOrHouse;

    @Column(name = "road_block_sector")
    private String roadBlockSector;

    @Column(name = "post")
    private String post;

    @Column(name = "prev_village_or_house")
    private String prevVillageOrHouse;

    @Column(name = "prev_road_block_sector")
    private String prevRoadBlockSector;

    @Column(name = "prev_post")
    private String prevPost;

    @Column(name = "status")
    private Boolean status;

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


    @ManyToOne
    @JoinColumn(name = "inst_employee_id")
    private InstEmployee instEmployee;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    @ManyToOne
    @JoinColumn(name = "prev_upazila_id")
    private Upazila prevUpazila;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVillageOrHouse() {
        return villageOrHouse;
    }

    public void setVillageOrHouse(String villageOrHouse) {
        this.villageOrHouse = villageOrHouse;
    }

    public String getRoadBlockSector() {
        return roadBlockSector;
    }

    public void setRoadBlockSector(String roadBlockSector) {
        this.roadBlockSector = roadBlockSector;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPrevVillageOrHouse() {
        return prevVillageOrHouse;
    }

    public void setPrevVillageOrHouse(String prevVillageOrHouse) {
        this.prevVillageOrHouse = prevVillageOrHouse;
    }

    public String getPrevRoadBlockSector() {
        return prevRoadBlockSector;
    }

    public void setPrevRoadBlockSector(String prevRoadBlockSector) {
        this.prevRoadBlockSector = prevRoadBlockSector;
    }

    public String getPrevPost() {
        return prevPost;
    }

    public void setPrevPost(String prevPost) {
        this.prevPost = prevPost;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    public Upazila getUpazila() {
        return upazila;
    }

    public void setUpazila(Upazila upazila) {
        this.upazila = upazila;
    }

    public Upazila getPrevUpazila() {
        return prevUpazila;
    }

    public void setPrevUpazila(Upazila upazila) {
        this.prevUpazila = upazila;
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

        InstEmpAddress instEmpAddress = (InstEmpAddress) o;

        if ( ! Objects.equals(id, instEmpAddress.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstEmpAddress{" +
            "id=" + id +
            ", villageOrHouse='" + villageOrHouse + "'" +
            ", roadBlockSector='" + roadBlockSector + "'" +
            ", post='" + post + "'" +
            ", prevVillageOrHouse='" + prevVillageOrHouse + "'" +
            ", prevRoadBlockSector='" + prevRoadBlockSector + "'" +
            ", prevPost='" + prevPost + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
