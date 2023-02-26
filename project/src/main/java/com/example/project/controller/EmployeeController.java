package com.example.project.controller;

import com.example.project.exception.EmployeeNotFoundException;
import com.example.project.model.Employee;
import com.example.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("http://localhost:4200")
//http://localhost:8080/api/v1/employees
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    //get all employees

    @GetMapping("/employees")
    public List<Employee> getAllEmploees() {
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

 /* {
    "emailId":"tom@gmail.com",
    "firstName":"Tom",
    "lastName":"Ford"
   }
 */

    //get employee by id
    //http://localhost:8080/api/v1/employees/2
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee= employeeRepository.findById(id).orElseThrow
                (()-> new EmployeeNotFoundException(id));
        return ResponseEntity.ok(employee);
    }

    //update employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
                                                   @RequestBody Employee employeeDetails){
        Employee employee= employeeRepository.findById(id).orElseThrow
                (()-> new EmployeeNotFoundException(id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        Employee updatedEmployee = employeeRepository.save(employee);

        return ResponseEntity.ok(updatedEmployee);
    }

    //delete employee
    @DeleteMapping("/employees/{id}")
    public  ResponseEntity <Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee= employeeRepository.findById(id).orElseThrow
                (()-> new EmployeeNotFoundException(id));

        employeeRepository.delete(employee);
        Map<String, Boolean> responce = new HashMap<>();
        responce.put("deleted", Boolean.TRUE);

        return ResponseEntity.ok(responce);
    }


    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}