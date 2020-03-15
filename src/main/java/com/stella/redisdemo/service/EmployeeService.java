package com.stella.redisdemo.service;

import com.stella.redisdemo.model.Employee;

import java.util.List;

public interface EmployeeService {

    void save(Employee employee);

    void deleteByEmployeeId(String employeeId);

    Employee getByEmployeeId(String employeeId);

    void update(Employee employee);

    List<Employee> getAll();
}
