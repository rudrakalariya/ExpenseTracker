package com.Project.ExpenseTracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project.ExpenseTracker.dto.GraphDTO;
import com.Project.ExpenseTracker.services.stats.StatsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StatsController {
	private final StatsService statsService;
	
	public StatsController(StatsService statsService) {
		this.statsService = statsService;
	}
	
	@GetMapping("/chart")
	public ResponseEntity<GraphDTO> getChartDetails(){
		return ResponseEntity.ok(statsService.getChartData());
	}
	
	@GetMapping
	public ResponseEntity<?>getStats(){
		return ResponseEntity.ok(statsService.getStats());
	} 
}
