package gov.step.app.web.rest.dto;

import gov.step.app.domain.RisNewAppForm;
import gov.step.app.domain.RisNewAppFormTwo;

/**
 * Created by rana on 12/23/15.
 */
public class RisNewAppDetail {
    private RisNewAppForm risNewAppForm;
    private RisNewAppFormTwo risNewAppFormTwo;

    public RisNewAppForm getRisNewAppForm() {
        return this.risNewAppForm;
    }

    public void setRisNewAppForm(RisNewAppForm risNewAppForm) {
        this.risNewAppForm = risNewAppForm;
    }

    public RisNewAppFormTwo getRisNewAppFormTwo() {
        return this.risNewAppFormTwo;
    }

    public void setRisNewAppFormTwo(RisNewAppFormTwo risNewAppFormTwo) {
        this.risNewAppFormTwo = risNewAppFormTwo;
    }
}
