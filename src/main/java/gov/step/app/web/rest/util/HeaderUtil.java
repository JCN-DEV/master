package gov.step.app.web.rest.util;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for http header creation.
 */
public class HeaderUtil {

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-stepApp-alert", message);
        headers.add("X-stepApp-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("stepApp." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("stepApp." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("stepApp." + entityName + ".deleted", param);
    }

    public static HttpHeaders createEntityDeclineAlert(String entityName, String param) {
        return createAlert("stepApp." + entityName + ".declined", param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-sampleHazelcastApp-error", "error." + errorKey);
        headers.add("X-sampleHazelcastApp-params", entityName);
        return headers;
    }
}
