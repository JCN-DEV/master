package gov.step.app.service;

import gov.step.app.domain.Institute;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.search.InstituteSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class InstituteService {

    private final Logger log = LoggerFactory.getLogger(InstituteService.class);

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstituteSearchRepository instituteSearchRepository;

    @Transactional(readOnly = true)
    public Institute getInstituteWithCourses() {
        return instituteRepository.findOneByUserIsCurrentUser();
    }

}
