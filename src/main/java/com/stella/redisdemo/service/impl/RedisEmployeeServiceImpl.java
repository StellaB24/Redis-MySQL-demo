/*
package com.stella.redisdemo.service.impl;

import com.stella.redisdemo.model.Employee;
import com.stella.redisdemo.service.EmployeeService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    public static final String KEY = "EMPLOYEE";
    private RedisTemplate<String, Employee> redisTemplate;
    private  HashOperations operations;

    public EmployeeServiceImpl(RedisTemplate<String, Employee> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.operations = redisTemplate.opsForHash();
    }

    @Override
    @CachePut(value = "employees")
    public void save(Employee employee) {
        operations.put(KEY, employee.getId(), employee);
    }

    @Override
    @CacheEvict(key = "#id", value = "employees")
    public void deleteById(String id) {
        operations.delete(KEY, id);
    }

    @Override
    public Map getAll() {
        return operations.entries(KEY);
    }

    @Override
    @Cacheable(value = "employees")
    public Employee getById(String id) {
        return (Employee) operations.get(KEY, id);
    }

    @Override
    @CachePut(key = "#id", value = "employees")
    public void update(Employee employee) {
        save(employee);
    }

}
*/
