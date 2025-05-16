package com.Project.ExpenseTracker.services.stats;

import com.Project.ExpenseTracker.dto.GraphDTO;
import com.Project.ExpenseTracker.dto.StatsDTO;

public interface StatsService {
	
	GraphDTO getChartData();
	
	StatsDTO getStats();

}
