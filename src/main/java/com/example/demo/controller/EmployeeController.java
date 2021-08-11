package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/")
public class EmployeeController{
	@Autowired
	private EmployeeRepository<?> employeeRepository;
	

	//get all employees
	
	@GetMapping("/employees")
	public List<Employee> getEmployees(){
		return employeeRepository.findAll();
	}
	
	//Create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee (@RequestBody Employee employee) {
		return (Employee) employeeRepository.save(employee);
	}
	
	// get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity <Employee> getEmployeeById (@PathVariable long id) throws Throwable {
		Employee employee= (Employee) employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist id :" +id));
		return ResponseEntity.ok(employee);
	}
	
	// Update Employee Rest API
	 @PutMapping("/employees/{id}")
	public ResponseEntity <Employee> updateEmployee (@PathVariable long id, @RequestBody Employee employeeDetails){
		
		 Employee employee=employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist id :" +id));
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updateEmployee= employeeRepository.save(employee);
		return ResponseEntity.ok(updateEmployee);
	}
	 
	 //Delete employee REST API
	 
	 @DeleteMapping("/employees/{id}")
	 public ResponseEntity<Map<String ,Boolean>>deleteEmployee(@PathVariable long id){
		 
		 Employee employee =employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist id :" +id));
		 employeeRepository.delete(employee);
		 
		 Map<String,Boolean> response = new HashMap<>();
		 response.put("Deleted", Boolean.TRUE);
		 
		 return ResponseEntity.ok(response);
	 }
	 
	 
	
}















