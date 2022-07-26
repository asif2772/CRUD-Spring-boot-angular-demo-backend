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
@RequestMapping("/api/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees list
	@GetMapping("/employees")
	@CrossOrigin(origins = "http://localhost:4200/")
	public List<Employee> getAllEmpolyees () {
		return employeeRepository.findAll();
	}
  
	//save employee
	@PostMapping("/employees") 
	@CrossOrigin(origins = "http://localhost:4200/")
	public Employee createEmployee (@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	} 
	
	//get employee by id
	@GetMapping("/employees/{id}")
	@CrossOrigin(origins = "http://localhost:4200/")
	public ResponseEntity<Employee>  getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee is not exist: "+id));
				return ResponseEntity.ok(employee);
	}
	
	//get employee by Email
	@GetMapping("/employees/email/{emailId}")
	@CrossOrigin(origins = "http://localhost:4200/")
	public Boolean getEmployeeByEmail(@PathVariable String emailId) {
		
		System.out.println("Email "+emailId);
		
		List<Employee> employee = employeeRepository.findByEmailId(emailId);
		
		Boolean response = false;
		if (!employee.isEmpty()) {
			response = true;
		} 
		
		System.out.println("response "+response);
		
		return response;
	}
	
	@PutMapping("/employees/{id}")
	@CrossOrigin(origins = "http://localhost:4200/")
	public ResponseEntity<Employee> updateEmployee (@PathVariable Long id, @RequestBody Employee employeeDetails) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee is not exist: "+id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updateEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updateEmployee);
	}
	
	@DeleteMapping("/employees/{id}")
	@CrossOrigin(origins = "http://localhost:4200/")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee (@PathVariable Long id) {
		
		System.out.println(id);
		
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee is not exist: "+id));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("seleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}


