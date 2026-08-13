package org.itpc_advanced.newmodel;

import java.util.ArrayList;
import java.util.List;

import org.itpc_advanced.service.DataParser;

public class RecordsDatabase {
	
	private final List<TemperatureRecord> records = new ArrayList<>();
	private final TemperatureStats stats = new TemperatureStats();
	
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
	
	public List<TemperatureRecord> getRecords() {
		return records;
	}

	public void addRecords() {
		// mock method
		byte[] rawData1 = {22, -125, 0, 0, 123, -1, -123, 5, 0, 1, 0, 1, 0, 0, 0, 0, 0, 2, 0, 105, 2, 105, 2, 105, 2, 104, 2, 104, 2, 104, 2, 104, 2, 104, 2, 104, 2, 104, 2, 104, 2, 104, 2, 104, 2, 104, 2, 104, 2, 104, 2, 104, 2, 104, 2, 104, 2, 104, 2, 105, 2, 105, 2, 105, 2, 105, 2, 105, 2, 105, 2, 105, 2, 107, 2, 107, 2, 107, 2, 107, 2, 109, 2, 109, 2, 109, 2, 109, 2, 109, 2, 109, 2, 109, 2, 109, 2, 108, 2, 111, 2, 110, 2, 110, 2, 110, 2, 110, 2, 110, 2, 110, 2, 110, 2, 110, 2, -35, 125, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 99, -1};
		byte[] rawData2 = {22, -125, 0, 0, 123, -1, -123, 5, 0, 1, 0, 1, 0, 0, 0, 0, 0, 2, 0, 104, 2, 105, 2, 107, 2, 107, 2, 107, 2, 107, 2, 107, 2, 107, 2, 107, 2, 107, 2, 107, 2, 108, 2, 108, 2, 108, 2, 108, 2, 107, 2, 107, 2, 107, 2, 107, 2, 109, 2, 109, 2, 109, 2, 107, 2, 107, 2, 109, 2, 109, 2, 108, 2, 108, 2, 108, 2, 108, 2, 108, 2, 110, 2, 108, 2, 108, 2, -35, 125, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -73, -29};
		byte[] rawData3 = {22, -125, 0, 0, 123, -1, -123, 5, 0, 1, 0, 1, 0, 0, 0, 0, 0, 2, 0, 100, 2, 100, 2, 100, 2, 100, 2, 100, 2, 100, 2, 100, 2, 100, 2, 100, 2, 100, 2, 100, 2, 100, 2, 100, 2, 100, 2, 100, 2, 102, 2, 101, 2, 101, 2, 101, 2, 101, 2, 100, 2, 101, 2, 101, 2, 101, 2, 101, 2, 101, 2, 103, 2, 101, 2, 103, 2, 101, 2, 101, 2, -35, 125, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -36, -49};
		records.add(DataParser.parseFromBytes(rawData1));
		records.add(DataParser.parseFromBytes(rawData2));
		records.add(DataParser.parseFromBytes(rawData3));
		System.out.println("records added");
		
	}

	public TemperatureStats getStats() {
		// TODO Auto-generated method stub
		return stats;
	}

}
