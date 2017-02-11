 package gov.step.app.domain;

import gov.step.app.domain.enumeration.instType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * A InstituteStatusInfo.
 */
@Entity
@Table(name = "institute_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "institutestatus")
public class InstituteStatus implements Serializable {
// status values : 0=empty, 1=pending, 2=approved, 3=rejected
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "gen_info")
    private Integer genInfo;

    @Column(name = "academic_info")
    private Integer academicInfo;

    @Column(name = "aca_info")
    private Integer acaInfo;

    @Column(name = "adm_info")
    private Integer admInfo;

    @Column(name = "building")
    private Integer building;

    @Column(name = "cmt_mem_info")
    private Integer cmtMemInfo;

    @Column(name = "comitee_info")
    private Integer comiteeInfo;

    @Column(name = "comitee_member")
    private Integer comiteeMember;

    @Column(name = "financial_info")
    private Integer financialInfo;

    @Column(name = "gov_body_access")
    private Integer govBodyAccess;

    @Column(name = "govern_body")
    private Integer governBody;

    @Column(name = "infra_info")
    private Integer infraInfo;

    @Column(name = "lab_info")
    private Integer labInfo;

    @Column(name = "land")
    private Integer land;

    @Column(name = "play_ground_info")
    private Integer playGroundInfo;

    @Column(name = "shop_info")
    private Integer shopInfo;

    @Column(name = "course")
    private Integer course;

    @Column(name = "curriculum")
    private Integer curriculum;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_by")
    private Long updatedBy;

    @Column(name = "update_Date")
    private LocalDate updatedDate;

    @ManyToOne
    @JoinColumn(name = "inst_info_id_id")
    private Institute institute;

    public InstituteStatus(Long id, Integer genInfo, Integer academicInfo, Integer acaInfo, Integer admInfo, Integer building, Integer cmtMemInfo, Integer comiteeInfo, Integer comiteeMember, Integer financialInfo, Integer govBodyAccess, Integer governBody, Integer infraInfo, Integer labInfo, Integer land, Integer playGroundInfo, Integer shopInfo, Integer course, Integer curriculum, Integer status, LocalDate createDate, Long createBy, Long updatedBy, LocalDate updatedDate, Institute institute) {
        this.id = id;
        this.genInfo = genInfo;
        this.academicInfo = academicInfo;
        this.acaInfo = acaInfo;
        this.admInfo = admInfo;
        this.building = building;
        this.cmtMemInfo = cmtMemInfo;
        this.comiteeInfo = comiteeInfo;
        this.comiteeMember = comiteeMember;
        this.financialInfo = financialInfo;
        this.govBodyAccess = govBodyAccess;
        this.governBody = governBody;
        this.infraInfo = infraInfo;
        this.labInfo = labInfo;
        this.land = land;
        this.playGroundInfo = playGroundInfo;
        this.shopInfo = shopInfo;
        this.course = course;
        this.curriculum = curriculum;
        this.status = status;
        this.createDate = createDate;
        this.createBy = createBy;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.institute = institute;
    }

    public InstituteStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGenInfo() {
        return genInfo;
    }

    public void setGenInfo(Integer genInfo) {
        this.genInfo = genInfo;
    }

    public Integer getAcademicInfo() {
        return academicInfo;
    }

    public void setAcademicInfo(Integer academicInfo) {
        this.academicInfo = academicInfo;
    }

    public Integer getAcaInfo() {
        return acaInfo;
    }

    public void setAcaInfo(Integer acaInfo) {
        this.acaInfo = acaInfo;
    }

    public Integer getAdmInfo() {
        return admInfo;
    }

    public void setAdmInfo(Integer admInfo) {
        this.admInfo = admInfo;
    }

    public Integer getBuilding() {
        return building;
    }

    public void setBuilding(Integer building) {
        this.building = building;
    }

    public Integer getCmtMemInfo() {
        return cmtMemInfo;
    }

    public void setCmtMemInfo(Integer cmtMemInfo) {
        this.cmtMemInfo = cmtMemInfo;
    }

    public Integer getComiteeInfo() {
        return comiteeInfo;
    }

    public void setComiteeInfo(Integer comiteeInfo) {
        this.comiteeInfo = comiteeInfo;
    }

    public Integer getComiteeMember() {
        return comiteeMember;
    }

    public void setComiteeMember(Integer comiteeMember) {
        this.comiteeMember = comiteeMember;
    }

    public Integer getFinancialInfo() {
        return financialInfo;
    }

    public void setFinancialInfo(Integer financialInfo) {
        this.financialInfo = financialInfo;
    }

    public Integer getGovBodyAccess() {
        return govBodyAccess;
    }

    public void setGovBodyAccess(Integer govBodyAccess) {
        this.govBodyAccess = govBodyAccess;
    }

    public Integer getGovernBody() {
        return governBody;
    }

    public void setGovernBody(Integer governBody) {
        this.governBody = governBody;
    }

    public Integer getInfraInfo() {
        return infraInfo;
    }

    public void setInfraInfo(Integer infraInfo) {
        this.infraInfo = infraInfo;
    }

    public Integer getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(Integer labInfo) {
        this.labInfo = labInfo;
    }

    public Integer getLand() {
        return land;
    }

    public void setLand(Integer land) {
        this.land = land;
    }

    public Integer getPlayGroundInfo() {
        return playGroundInfo;
    }

    public void setPlayGroundInfo(Integer playGroundInfo) {
        this.playGroundInfo = playGroundInfo;
    }

    public Integer getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(Integer shopInfo) {
        this.shopInfo = shopInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Integer getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Integer curriculum) {
        this.curriculum = curriculum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstituteStatus instituteStatus = (InstituteStatus) o;

        if ( ! Objects.equals(id, instituteStatus.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstGenInfo{" +
            "id=" + id +
            ", genInfo='" + genInfo + "'" +
            ", academicInfo='" + academicInfo + "'" +
            ", acaInfo='" + acaInfo + "'" +
            ", admInfo='" + admInfo + "'" +
            ", building='" + building + "'" +
            ", cmtMemInfo='" + cmtMemInfo + "'" +
            ", comiteeInfo='" + comiteeInfo + "'" +
            ", comiteeMember='" + comiteeMember + "'" +
            ", financialInfo='" + financialInfo + "'" +
            ", govBodyAccess='" + govBodyAccess + "'" +
            ", governBody='" + governBody + "'" +
            ", infraInfo='" + infraInfo + "'" +
            ", labInfo='" + labInfo + "'" +
            ", land='" + land + "'" +
            ", playGroundInfo='" + playGroundInfo + "'" +
            ", shopInfo='" + shopInfo + "'" +
            ", status='" + status + "'" +
            ", status='" + status + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            ", updatedDate='" + updatedDate + "'" +
            '}';
    }
}
