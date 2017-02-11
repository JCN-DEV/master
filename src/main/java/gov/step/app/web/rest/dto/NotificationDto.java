package gov.step.app.web.rest.dto;

import gov.step.app.domain.PgmsNotification;

import java.util.List;

/**
 * Created by ism on 6/4/16.
 */
public class NotificationDto
{
    private List<PgmsNotification> notificationList;
    private String comments;

    public List<PgmsNotification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<PgmsNotification> notificationList) {
        this.notificationList = notificationList;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
