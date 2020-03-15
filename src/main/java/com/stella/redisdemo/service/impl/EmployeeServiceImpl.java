package com.stella.redisdemo.service.impl;

import com.stella.redisdemo.exception.EmployeeAlreadyExistsException;
import com.stella.redisdemo.exception.EmployeeNotFoundException;
import com.stella.redisdemo.model.Employee;
import com.stella.redisdemo.repository.EmployeeRepository;
import com.stella.redisdemo.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private  EmployeeRepository repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    @CachePut("employees")
    public void save(Employee employee) {
        boolean alreadyExists = checkIfEmployeeAlreadyExists(employee);
        if (!alreadyExists) {
            repository.save(employee);
            logger.info("A new employee was added to the system.");
        }else {
            logger.info("An employee with this id already exists in the system.");

            throw new EmployeeAlreadyExistsException
                    (String.format("Employee %s %s already exists", employee.getFirstName(), employee.getLastName()));
        }
    }

    //TODO: Check what happens to cache when a non employee wes searched by employeeId
    @Override
    @Cacheable("employees")
    public Employee getByEmployeeId(String employeeId) {
        List<Employee> all = getAll();
        for (Employee employee : all) {
            if (employee.getEmployeeId().contentEquals(employeeId)) {
                logger.info((String.format("Employee with id %s found successfully", employeeId)));
                return employee;
            }
        }
        logger.info((String.format("Employee with id %s wasn't found", employeeId)));
        throw new EmployeeNotFoundException(String.format("Employee with id %s was not found", employeeId));
    }

    @Override
    @CacheEvict("employees")
    public void deleteByEmployeeId(String employeeId) {
        Employee employee = getByEmployeeId(employeeId);
        repository.delete(employee);
    }


    // TODO: When DB will be updated one the update was through cache?
    @Override
    @CachePut("employees")
    public void update(Employee employee) {
        Employee newEmployee = getByEmployeeId(employee.getEmployeeId());
        repository.save(newEmployee);
        logger.info(String.format("employee was updated updated."));
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = getEmployeesList();
        if (!employees.isEmpty()) {
            logger.info(String.format("A list of employees found successfully."));
            return employees;
        }
        logger.info(String.format("No employees were found"));
        throw new EmployeeNotFoundException(String.format("No employees were found"));
    }

    private boolean checkIfEmployeeAlreadyExists(Employee newEmployee) {
        List<Employee> employeeList = getEmployeesList();
        if (!employeeList.isEmpty()) {
            for (Employee employee : employeeList) {
                if (newEmployee.getEmployeeId().contentEquals(employee.getEmployeeId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Employee> getEmployeesList() {
        List<Employee> employeeList = new ArrayList<>();
        repository.findAll().forEach(employee -> employeeList.add(employee));
        return employeeList;
    }
}
