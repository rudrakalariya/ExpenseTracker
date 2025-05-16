package com.Project.ExpenseTracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.Project.ExpenseTracker.dto.IncomeDTO;
import com.Project.ExpenseTracker.entity.Income;
import com.Project.ExpenseTracker.services.income.IncomeService;
import com.Project.ExpenseTracker.services.income.IncomeServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
@CrossOrigin("*")

public class IncomeController {
	
	private final IncomeService incomeService;
	
	public IncomeController(IncomeService incomeService) {
		this.incomeService = incomeService;
	}
	
	
	@PostMapping
	public ResponseEntity<?> postIncome(@RequestBody IncomeDTO incomeDTO){
		try {
		Income createdIncome = incomeService.postIncome(incomeDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdIncome);
		} catch (EntityNotFoundException | IllegalArgumentException ex) {
	        // 🔴 Return error message in response body instead of internal error
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
	    }
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllIncomes(){
		return ResponseEntity.ok(incomeService.getAllIncomes());
		
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> updateIncome(@PathVariable Long id,@RequestBody IncomeDTO incomeDTO){
		try {
			return ResponseEntity.ok(incomeService.updateIncome(id, incomeDTO));
		}catch(EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?>getIncomeById(@PathVariable Long id){
		try {
			return ResponseEntity.ok(incomeService.getIncomeById(id));
			
		}catch(EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteIncome(@PathVariable Long id){
		try {
			incomeService.deleteIncome(id);
			return ResponseEntity.ok(null);
			
		}catch(EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
		}
	}
}
