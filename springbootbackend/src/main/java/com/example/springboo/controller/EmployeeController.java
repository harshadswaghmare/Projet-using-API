package com.example.springboo.controller;

//https://www.javaguides.net/2021/08/spring-boot-postgresql-crud-example.html

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.springboo.Model.Employee;
import com.example.springboo.exception.ResourceNotFoundException;
import com.example.springboo.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:5432")
@RestController
@RequestMapping("/api")

public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee()
    {
       return employeeRepository.findAll();

    }

    @PostMapping("/create/employees")
    public Employee CreateEmployee(@RequestBody Employee employee)
    {
        return employeeRepository.save(employee);
    }

    //get EmployeeID by rest api
    @GetMapping("/update/employees/{id}")
    public ResponseEntity<Employee>updateEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("no Id "));
         /*employee.setFirstName(employee.getFirstName());
         employee.setLastName(employee.getLastName());
         employee.setEmailId(employee.getEmailId());
         Employee updatedEmployee = employeeRepository.save(employee);*/
         return ResponseEntity.ok(employee);
    }

    //delete Employee rest api
    @DeleteMapping("/delete/employee/{id}")
    public ResponseEntity<Map<String, Boolean>>deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee with Associated id not found"));
                employeeRepository.delete(employee);
               Map<String, Boolean> response = new HashMap<>();
               response.put("Deleted Successfully",Boolean.TRUE);
               return ResponseEntity.ok(response);
    }

    // Update record on the basis of id
    @PutMapping("/update/employees/{id}")
    public ResponseEntity<Employee> UpdateEmployeeOnID(@PathVariable long id)
    {
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee with associate id not found"));
        employee.setFirstName(employee.getFirstName());
        employee.setLastName(employee.getLastName());
        employee.setEmailId(employee.getEmailId());
        Employee UpdatedEmployee= employeeRepository.save(employee);
        return ResponseEntity.ok(UpdatedEmployee);

    }

}
