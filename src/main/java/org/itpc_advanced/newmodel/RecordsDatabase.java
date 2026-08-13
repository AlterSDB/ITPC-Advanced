package org.itpc_advanced.newmodel;

import java.util.ArrayList;
import java.util.List;

public class RecordsDatabase {
	
	private final List<TemperatureRecord> records = new ArrayList<>();
	
	public RecordsDatabase(List<TemperatureRecord> records) {
		this.records.addAll(records);
	}
	
	public RecordsDatabase() {
		
	}
	
	public void putRecord(TemperatureRecord record) {
		if (record != null) {
			records.add(record);
		}
		else {
			System.out.println("invalid record file for add to base");
		}
	}
	
	public void clearRecords() {
		records.clear();
	}
	
	public TemperatureRecord getRecord(int n) {
		try {
			if (n > 8 || n < 1) {
				throw new NumberFormatException();
			}
			return records.get(n);
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void addRecords() {
		// mock method
		records.add(new TemperatureRecord());
		System.out.println("record added");
		
	}

	public List<TemperatureRecord> getAllRecords() {
		// TODO Auto-generated method stub
		return records;
	}

}
