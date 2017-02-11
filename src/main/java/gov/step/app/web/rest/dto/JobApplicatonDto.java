package gov.step.app.web.rest.dto;

import gov.step.app.domain.Jobapplication;

import java.util.List;

/**
 * Created by leads on 12/29/15.
 */
public class JobApplicatonDto {

    private List<Jobapplication> jobapplications;

    private int cvViewed;

    private int cvNotViewed;

    private int shortListed;

    private int interviewed;

    private int rejected;

    private int selected;

    public List<Jobapplication> getJobapplications() {
        return jobapplications;
    }

    public void setJobapplications(List<Jobapplication> jobapplications) {
        this.jobapplications = jobapplications;
    }

    public int getCvViewed() {
        return cvViewed;
    }

    public void setCvViewed(int cvViewed) {
        this.cvViewed = cvViewed;
    }

    public int getCvNotViewed() {
        return cvNotViewed;
    }

    public void setCvNotViewed(int cvNotViewed) {
        this.cvNotViewed = cvNotViewed;
    }

    public int getShortListed() {
        return shortListed;
    }

    public void setShortListed(int shortListed) {
        this.shortListed = shortListed;
    }

    public int getInterviewed() {
        return interviewed;
    }

    public void setInterviewed(int interviewed) {
        this.interviewed = interviewed;
    }

    public int getRejected() {
        return rejected;
    }

    public void setRejected(int rejected) {
        this.rejected = rejected;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
