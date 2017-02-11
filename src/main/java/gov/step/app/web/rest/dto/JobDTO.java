package gov.step.app.web.rest.dto;

import gov.step.app.domain.Job;

/**
 * Created by leads on 12/29/15.
 */
public class JobDTO {

    private Job job;

    private int totalApplications;

    private int cvViewed;

    private int cvNotViewed;

    private int shortListed;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public int getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(int totalApplications) {
        this.totalApplications = totalApplications;
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
}
