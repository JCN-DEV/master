package gov.step.app.domain;

/**
 * Created by leads on 4/3/16.
 */
public class CvSearchCriteria {
    private Long jobId;
    private String gender;
    private Long districtId;
    private Double ageStart;
    private Double ageEnd;
    private Double expStart;
    private Double expEnd;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Double getExpEnd() {
        return expEnd;
    }

    public void setExpEnd(Double expEnd) {
        this.expEnd = expEnd;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Double getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(Double ageStart) {
        this.ageStart = ageStart;
    }

    public Double getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(Double ageEnd) {
        this.ageEnd = ageEnd;
    }

    public Double getExpStart() {
        return expStart;
    }

    public void setExpStart(Double expStart) {
        this.expStart = expStart;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "CvSearchCriteria{" +
            "gender='" + gender + '\'' +
            ", location='" + districtId + '\'' +
            ", ageStart=" + ageStart +
            ", ageEnd=" + ageEnd +
            ", expStart=" + expStart +
            ", expEnd=" + expEnd +
            '}';
    }
}
