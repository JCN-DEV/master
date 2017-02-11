package gov.step.app.service;

import gov.step.app.domain.Employee;
import gov.step.app.repository.EmployeeRepository;
import gov.step.app.repository.search.EmployeeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private EmployeeSearchRepository employeeSearchRepository;


    @Transactional(readOnly = true)
    public Employee findOneWithEagerRelationships(Long id) {
        Employee employee = employeeRepository.findOneWithEagerRelationships(id);
        log.debug("total educations for this: {}", employee.getApplicantEducations().size());
        log.debug("total trainings for this: {}", employee.getTrainings().size());
        return employee;
    }
}
