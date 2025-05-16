package com.Project.ExpenseTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.Project.ExpenseTracker.dto.ExpenseDTO;
import com.Project.ExpenseTracker.entity.Expense;
import com.Project.ExpenseTracker.services.category.CategoryService;
import com.Project.ExpenseTracker.services.expense.ExpenseService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExpenseController {
	
	private final ExpenseService expenseService;
	
	public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
	
	@Autowired
    private CategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<?> postExpense(@RequestBody ExpenseDTO dto) {
	    try {
	        Expense createdExpense = expenseService.postExpense(dto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
	    } catch (EntityNotFoundException | IllegalArgumentException ex) {
	        // 🔴 Return error message in response body instead of internal error
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
	    }
	}

	
	@GetMapping("/all")
	public ResponseEntity<?> getAllExepense(){
		return ResponseEntity.ok(expenseService.getAllExpenses());
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getExpenseById(@PathVariable Long id){
		try {
			return ResponseEntity.ok(expenseService.getExpenseById(id));
		}catch(EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> updateExpense(@PathVariable Long id,@RequestBody ExpenseDTO dto){
		try {
			return ResponseEntity.ok(expenseService.updatExpense(id, dto));
		}catch(EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteExpense(@PathVariable Long id){
		try {
			expenseService.deleteExpense(id);
			return ResponseEntity.ok(null);
		}catch(EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
		}
	}

}
