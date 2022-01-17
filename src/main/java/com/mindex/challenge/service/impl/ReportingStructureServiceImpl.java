package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.ReportingStructureService;
import com.mindex.challenge.data.ReportingStructure;
import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Fetching employee for id: [{}]", id);
        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        LOG.debug("Getting reporting structure for employee: [{}]", employee);
        fixDirectReports(employee);
        return new ReportingStructure(employee);
    }

    //direct reports are broken in that they don't actually link to the employee under them and just has their id
    //This recursive function exists to make sure they are properly linked before creating the reporting structure
    private void fixDirectReports(Employee employee) {
        if (employee != null) {
            List<Employee> directReports = employee.getDirectReports();
            List<Employee> newDirectReports = new ArrayList<Employee>();
            if (directReports != null) {
                for (Employee e : employee.getDirectReports()) {
                    Employee temp = employeeRepository.findByEmployeeId(e.getEmployeeId());
                    fixDirectReports(temp);
                    newDirectReports.add(temp);
                }
                employee.setDirectReports(newDirectReports);
            }
        }
    }
    
}
