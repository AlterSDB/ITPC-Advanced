package org.itpc_advanced.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemperatureStatsRepository {
	
	private List<TemperatureStats> temperatureStatsList = new ArrayList<TemperatureStats>();
	
	public TemperatureStatsRepository() {

	}

	public List<TemperatureStats> getTemperatureStatsList() {
		// TODO Auto-generated method stub
		return temperatureStatsList;
	}

	public TemperatureStats getSelectedTemperatureStats() {
		// TODO Auto-generated method stub
		return temperatureStatsList.get(0);
	}

	public void addStatsFile() {
		List<Double> list = new ArrayList<Double>(Arrays.asList(new Double[] 
				{12.2, 12.3, 14.2, 12.2, 12.3, 14.2, 12.2, 12.3, 14.2, 12.2, 
				12.3, 14.2, 12.2, 12.3, 14.2,12.2, 12.3, 14.2, 12.2, 12.3, 
				14.2, 12.2, 12.3, 14.2, 12.2, 12.3, 14.2}
		));
		temperatureStatsList.add(new TemperatureStats(new TemperatureFileRecord(getRandomValues())));
		
	}	
	
	private List<Double> getRandomValues() {
		List<Double> values = new ArrayList<Double>();
		for (int i = 0; i < (Math.random() * 80) + 30; i++) {
			values.add(Math.random() * 1000);
		}
		return values;
	}

}
