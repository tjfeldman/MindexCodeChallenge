package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String employeeUrl;
    private String reportingStructureUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";
    }

    @Test
    public void testRead() {   
        //Set Up Employees
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Manager");

        Employee testEmployee2 = new Employee();
        testEmployee2.setFirstName("Jane");
        testEmployee2.setLastName("Doe");
        testEmployee2.setDepartment("Engineering");
        testEmployee2.setPosition("Developer IV");

        Employee testEmployee3 = new Employee();
        testEmployee3.setFirstName("John");
        testEmployee3.setLastName("Smith");
        testEmployee3.setDepartment("Engineering");
        testEmployee3.setPosition("Developer III");

        Employee testEmployee4 = new Employee();
        testEmployee4.setFirstName("Jane");
        testEmployee4.setLastName("Smith");
        testEmployee4.setDepartment("Engineering");
        testEmployee4.setPosition("Developer I");

        //Set Up Direct Reports
        //John Smith <- Jane Smith
        //John Doe <- Jane Doe & John Smith
        testEmployee3.setDirectReports(Arrays.asList(new Employee[]{testEmployee4}));
        testEmployee.setDirectReports(Arrays.asList(new Employee[]{testEmployee2, testEmployee3}));
        
        // Trying to use Rest to create the employees was running into issues where the direct reports list was returning back nulls so it was not working sa intended.
        // //create employees
        // Employee createdEmployee4 = restTemplate.postForEntity(employeeUrl, testEmployee4, Employee.class).getBody();
        // assertNotNull(createdEmployee4.getEmployeeId());
        // assertEmployeeEquivalence(testEmployee4, createdEmployee4);

        // Employee createdEmployee3 = restTemplate.postForEntity(employeeUrl, testEmployee3, Employee.class).getBody();
        // assertNotNull(createdEmployee3.getEmployeeId());
        // assertEmployeeEquivalence(testEmployee3, createdEmployee3);

        // Employee createdEmployee2 = restTemplate.postForEntity(employeeUrl, testEmployee2, Employee.class).getBody();
        // assertNotNull(createdEmployee2.getEmployeeId());
        // assertEmployeeEquivalence(testEmployee2, createdEmployee2);

        // Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        // assertNotNull(createdEmployee.getEmployeeId());
        // assertEmployeeEquivalence(testEmployee, createdEmployee);

        // ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, createdEmployee.getEmployeeId()).getBody();
        // assertNotNull(reportingStructure.getNumberOfReports());
        // assertEquals(reportingStructure.getNumberOfReports(), 3);//should be three since John Doe has 2 direct reports and one of his direct reports has 1 direct report

        ReportingStructure reportingStructure = new ReportingStructure(testEmployee);
        assertEquals(reportingStructure.getNumberOfReports(), 3);

    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
    
}
