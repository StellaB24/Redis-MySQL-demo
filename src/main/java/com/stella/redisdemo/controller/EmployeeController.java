package com.stella.redisdemo.controller;

import com.stella.redisdemo.exception.EmployeeAlreadyExistsException;
import com.stella.redisdemo.exception.EmployeeNotFoundException;
import com.stella.redisdemo.model.Employee;
import com.stella.redisdemo.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/Service/")
public class EmployeeController {

    private EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping("/employee")
    public ResponseEntity<String> saveNewEmployee(@RequestBody Employee employee) {
        try {
            employeeService.save(employee);
            return ResponseEntity.accepted().body(
                    String.format("The new employee %s %s added successfully.", employee.getFirstName(), employee.getLastName()));

        } catch (EmployeeAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getEmployeeById(@PathVariable String id) {
        try {
            Employee employee = employeeService.getByEmployeeId(id);
            return new ResponseEntity(employee,HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllEmployees() {
        try {
            List<Employee> all = employeeService.getAll();
            return new ResponseEntity(all, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
    }


    @PutMapping("/update/{id}")
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeService.update(employee);
        return employee;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable String id) {
        employeeService.deleteByEmployeeId(id);
        return id;
    }
}
