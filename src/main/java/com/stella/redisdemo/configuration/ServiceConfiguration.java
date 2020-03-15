package com.stella.redisdemo.configuration;

import com.stella.redisdemo.repository.EmployeeRepository;
import com.stella.redisdemo.service.EmployeeService;
import com.stella.redisdemo.service.impl.EmployeeServiceImpl;
import org.springframework.context.annotation.Bean;

public class ServiceConfiguration {

    @Bean
    EmployeeService getEmployeeService(EmployeeRepository repository) {
        return new EmployeeServiceImpl(repository);
    }
}
