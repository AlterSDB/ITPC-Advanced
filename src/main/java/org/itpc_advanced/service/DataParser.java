package org.itpc_advanced.service;

import org.itpc_advanced.newmodel.TemperatureRecord;

import java.time.LocalDateTime;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DataParser {

	private static final String[] TC_TYPES = {"types.l", "types.k", "types.b", "types.s"};
	private static final Double[] TIME_STEPS = {0.5, 1.0, 2.0, 5.0, 15.0, 30.0, 60.0};
	private static final Integer  OFFSET = 7;
	private static final Integer  STOP_BYTES = valueOf((byte)-35, (byte)125); 
	
	
	public static TemperatureRecord parseFromText(String rawData) {
		
		List<Double> values = new ArrayList<Double>();
		
		try {
			if (rawData == null || rawData.trim().isEmpty()) {
				return new TemperatureRecord();
			}
			String cleanData = rawData.replaceAll("[.,]", "");
			cleanData = cleanData.replaceAll("[^\\d\\s]", "");
			
			String[] tokens = cleanData.split("[\\s]+");
			
			for (String token : tokens) {
				token = token.trim();
				if (!token.isEmpty()) {
					Integer value = Integer.parseInt(token);
					values.add(new Double((double)value / 10));
				}
			}

			return new TemperatureRecord(values);

		} catch(Exception e) {
			System.out.println("Error parsing data: " + e.getMessage());
		}
			return new TemperatureRecord();
		}

	public static TemperatureRecord parseFromBytes(byte[] rawData) {
		try {
			if (rawData == null || 
					rawData.length < 2 || 
					rawData[0] != (byte)22 || 
					rawData[1] !=(byte)-125) {
					throw new Exception("Error creating DataFile: Corrupted raw data.");
			}

			if (rawData[OFFSET + 1] == -1 && rawData[OFFSET + 2] == -1 ) {
				System.out.println("Note: DataFile is empty.");
				return new TemperatureRecord();
			}

			ByteBuffer buffer = ByteBuffer.wrap(rawData);		
			buffer.position(OFFSET); 
			Double timeStep = TIME_STEPS[valueOf(buffer.get(), buffer.get()) - 1];

			int month = valueOf(buffer.get(), buffer.get());
			int day = valueOf(buffer.get(), buffer.get());
			int hours = valueOf(buffer.get(), buffer.get());
			int minutes = valueOf(buffer.get(), buffer.get());	
			LocalDateTime timeStamp = LocalDateTime.of(LocalDateTime.now().getYear(), day, month, hours, minutes);

			String tcType = TC_TYPES[valueOf(buffer.get(), buffer.get()) - 1];
			List<Double> values = new ArrayList<Double>();

			while (buffer.remaining() > 2) {
				Integer value = valueOf(buffer.get(), buffer.get());
				if (value.equals(STOP_BYTES)) {
					break;
				}
				values.add(new Double((double)value / 10));
			}

		return new TemperatureRecord(tcType, timeStamp, timeStep, values);
		} catch(Exception e) {
			System.out.println("Error parsing data: " + e.getMessage());
		}
			return null;
		}

	private static int valueOf (byte x, byte y) {
		return ((y << 8) | (x & 0xFF));
	}

}