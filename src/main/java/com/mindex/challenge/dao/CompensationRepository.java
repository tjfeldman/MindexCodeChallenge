package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.springframework.data.repository.Repository;

public interface CompensationRepository extends Repository<Compensation, String> {
    Compensation insert(Compensation compensation);
    Compensation findByEmployee(Employee employee);
}
