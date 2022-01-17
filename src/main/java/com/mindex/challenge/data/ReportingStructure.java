package com.mindex.challenge.data;

import java.util.List;

public class ReportingStructure {

    private Employee employee;

    public ReportingStructure() {

    }

    public ReportingStructure(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumberOfReports() {
        return getNumberOfReports(this.employee);
    }

    //recursive function to find the number of direct reports for an employee
    private int getNumberOfReports(Employee employee) {
        int count = 0;
        if (employee != null) {
            List<Employee> directReports = employee.getDirectReports();
            if (directReports != null) {
                for (Employee e : directReports) {
                    count++;//increment count by 1 as this employee is a direct report
                    count+=getNumberOfReports(e);//increment by the number of direct reports this employee has
                }
            }
        }

        return count;

    }
    
}
