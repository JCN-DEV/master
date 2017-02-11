package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AlmWorkingUnit.
 */
@Entity
@Table(name = "alm_working_unit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "almworkingunit")
public class AlmWorkingUnit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @NotNull
//    @Column(name = "is_weekend", nullable = false)
//    private Boolean isWeekend;


    @Column(name = "sunday", nullable = false)
    private Boolean sunday;


    @Column(name = "sun_start_time", nullable = false)
    private String sunStartTime;


    @Column(name = "sun_delay_time", nullable = false)
    private String sunDelayTime;


    @Column(name = "sun_end_time", nullable = false)
    private String sunEndTime;


    @Column(name = "monday", nullable = false)
    private Boolean monday;


    @Column(name = "mon_start_time", nullable = false)
    private String monStartTime;


    @Column(name = "mon_delay_time", nullable = false)
    private String monDelayTime;


    @Column(name = "mon_end_time", nullable = false)
    private String monEndTime;


    @Column(name = "tuesday", nullable = false)
    private Boolean tuesday;


    @Column(name = "tue_start_time", nullable = false)
    private String tueStartTime;


    @Column(name = "tue_delay_time", nullable = false)
    private String tueDelayTime;


    @Column(name = "tue_end_time", nullable = false)
    private String tueEndTime;


    @Column(name = "wednesday", nullable = false)
    private Boolean wednesday;


    @Column(name = "wed_start_time", nullable = false)
    private String wedStartTime;


    @Column(name = "wed_delay_time", nullable = false)
    private String wedDelayTime;


    @Column(name = "wed_end_time", nullable = false)
    private String wedEndTime;


    @Column(name = "thursday", nullable = false)
    private Boolean thursday;


    @Column(name = "thu_start_time", nullable = false)
    private String thuStartTime;


    @Column(name = "thu_delay_time", nullable = false)
    private String thuDelayTime;


    @Column(name = "thu_end_time", nullable = false)
    private String thuEndTime;


    @Column(name = "friday", nullable = false)
    private Boolean friday;


    @Column(name = "fri_start_time", nullable = false)
    private String friStartTime;


    @Column(name = "fri_delay_time", nullable = false)
    private String friDelayTime;


    @Column(name = "fri_end_time", nullable = false)
    private String friEndTime;


    @Column(name = "saturday", nullable = false)
    private Boolean saturday;


    @Column(name = "sat_start_time", nullable = false)
    private String satStartTime;


    @Column(name = "sat_delay_time", nullable = false)
    private String satDelayTime;


    @Column(name = "sat_end_time", nullable = false)
    private String satEndTime;

    /*@Column(name = "is_half_day")
    private Boolean isHalfDay;

    @Column(name = "half_day")
    private String halfDay;

    @NotNull
    @Column(name = "day_name", nullable = false)
    private String dayName;

    @NotNull
    @Column(name = "office_start", nullable = false)
    private String officeStart;

    @NotNull
    @Column(name = "delay_time", nullable = false)
    private String delayTime;

    @NotNull
    @Column(name = "absent_time", nullable = false)
    private String absentTime;

    @NotNull
    @Column(name = "office_end", nullable = false)
    private String officeEnd;*/

    @NotNull
    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @NotNull
    @Column(name = "active_status", nullable = false)
    private Boolean activeStatus;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    private AlmShiftSetup almShiftSetup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }

    public String getSunStartTime() {
        return sunStartTime;
    }

    public void setSunStartTime(String sunStartTime) {
        this.sunStartTime = sunStartTime;
    }

    public String getSunDelayTime() {
        return sunDelayTime;
    }

    public void setSunDelayTime(String sunDelayTime) {
        this.sunDelayTime = sunDelayTime;
    }

    public String getSunEndTime() {
        return sunEndTime;
    }

    public void setSunEndTime(String sunEndTime) {
        this.sunEndTime = sunEndTime;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public String getMonStartTime() {
        return monStartTime;
    }

    public void setMonStartTime(String monStartTime) {
        this.monStartTime = monStartTime;
    }

    public String getMonDelayTime() {
        return monDelayTime;
    }

    public void setMonDelayTime(String monDelayTime) {
        this.monDelayTime = monDelayTime;
    }

    public String getMonEndTime() {
        return monEndTime;
    }

    public void setMonEndTime(String monEndTime) {
        this.monEndTime = monEndTime;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public String getTueStartTime() {
        return tueStartTime;
    }

    public void setTueStartTime(String tueStartTime) {
        this.tueStartTime = tueStartTime;
    }

    public String getTueDelayTime() {
        return tueDelayTime;
    }

    public void setTueDelayTime(String tueDelayTime) {
        this.tueDelayTime = tueDelayTime;
    }

    public String getTueEndTime() {
        return tueEndTime;
    }

    public void setTueEndTime(String tueEndTime) {
        this.tueEndTime = tueEndTime;
    }

    public Boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public String getWedStartTime() {
        return wedStartTime;
    }

    public void setWedStartTime(String wedStartTime) {
        this.wedStartTime = wedStartTime;
    }

    public String getWedDelayTime() {
        return wedDelayTime;
    }

    public void setWedDelayTime(String wedDelayTime) {
        this.wedDelayTime = wedDelayTime;
    }

    public String getWedEndTime() {
        return wedEndTime;
    }

    public void setWedEndTime(String wedEndTime) {
        this.wedEndTime = wedEndTime;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public String getThuDelayTime() {
        return thuDelayTime;
    }

    public void setThuDelayTime(String thuDelayTime) {
        this.thuDelayTime = thuDelayTime;
    }

    public String getThuStartTime() {
        return thuStartTime;
    }

    public void setThuStartTime(String thuStartTime) {
        this.thuStartTime = thuStartTime;
    }

    public String getThuEndTime() {
        return thuEndTime;
    }

    public void setThuEndTime(String thuEndTime) {
        this.thuEndTime = thuEndTime;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public String getFriStartTime() {
        return friStartTime;
    }

    public void setFriStartTime(String friStartTime) {
        this.friStartTime = friStartTime;
    }

    public String getFriDelayTime() {
        return friDelayTime;
    }

    public void setFriDelayTime(String friDelayTime) {
        this.friDelayTime = friDelayTime;
    }

    public String getFriEndTime() {
        return friEndTime;
    }

    public void setFriEndTime(String friEndTime) {
        this.friEndTime = friEndTime;
    }

    public Boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public String getSatStartTime() {
        return satStartTime;
    }

    public void setSatStartTime(String satStartTime) {
        this.satStartTime = satStartTime;
    }

    public String getSatDelayTime() {
        return satDelayTime;
    }

    public void setSatDelayTime(String satDelayTime) {
        this.satDelayTime = satDelayTime;
    }

    public String getSatEndTime() {
        return satEndTime;
    }

    public void setSatEndTime(String satEndTime) {
        this.satEndTime = satEndTime;
    }

   /* public Boolean getIsHalfDay() {
        return isHalfDay;
    }

    public void setIsHalfDay(Boolean isHalfDay) {
        this.isHalfDay = isHalfDay;
    }

    public String getHalfDay() {
        return halfDay;
    }

    public void setHalfDay(String halfDay) {
        this.halfDay = halfDay;
    }*/

    /*public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getOfficeStart() {
        return officeStart;
    }

    public void setOfficeStart(String officeStart) {
        this.officeStart = officeStart;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }

    public String getAbsentTime() {
        return absentTime;
    }

    public void setAbsentTime(String absentTime) {
        this.absentTime = absentTime;
    }

    public String getOfficeEnd() {
        return officeEnd;
    }

    public void setOfficeEnd(String officeEnd) {
        this.officeEnd = officeEnd;
    }*/

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
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

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public AlmShiftSetup getAlmShiftSetup() {
        return almShiftSetup;
    }

    public void setAlmShiftSetup(AlmShiftSetup AlmShiftSetup) {
        this.almShiftSetup = AlmShiftSetup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlmWorkingUnit almWorkingUnit = (AlmWorkingUnit) o;

        if ( ! Objects.equals(id, almWorkingUnit.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlmWorkingUnit{" +
                "id=" + id +
//                ", isWeekend='" + isWeekend + "'" +
                ", sunday='" + sunday + "'" +
                ", sunStartTime='" + sunStartTime + "'" +
                ", sunEndTime='" + sunEndTime + "'" +
                ", monday='" + monday + "'" +
                ", monStartTime='" + monStartTime + "'" +
                ", monEndTime='" + monEndTime + "'" +
                ", tuesday='" + tuesday + "'" +
                ", tueStartTime='" + tueStartTime + "'" +
                ", tueEndTime='" + tueEndTime + "'" +
                ", wednesday='" + wednesday + "'" +
                ", wedStartTime='" + wedStartTime + "'" +
                ", wedEndTime='" + wedEndTime + "'" +
                ", thursday='" + thursday + "'" +
                ", thuStartTime='" + thuStartTime + "'" +
                ", thuEndTime='" + thuEndTime + "'" +
                ", friday='" + friday + "'" +
                ", friStartTime='" + friStartTime + "'" +
                ", friEndTime='" + friEndTime + "'" +
                ", saturday='" + saturday + "'" +
                ", satStartTime='" + satStartTime + "'" +
                ", satEndTime='" + satEndTime + "'" +
//                ", isHalfDay='" + isHalfDay + "'" +
                /*", halfDay='" + halfDay + "'" +
                ", dayName='" + dayName + "'" +
                ", officeStart='" + officeStart + "'" +
                ", delayTime='" + delayTime + "'" +
                ", absentTime='" + absentTime + "'" +A
                ", officeEnd='" + officeEnd + "'" +*/
                ", effectiveDate='" + effectiveDate + "'" +
                ", activeStatus='" + activeStatus + "'" +
                ", createDate='" + createDate + "'" +
                ", createBy='" + createBy + "'" +
                ", updateDate='" + updateDate + "'" +
                ", updateBy='" + updateBy + "'" +
                '}';
    }
}
