package org.itpc_advanced.service;

import org.itpc_advanced.model.DataFile;

import java.time.LocalDateTime;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DataParser {
	
	private static final String[] TC_TYPES = {"XK(L)", "XA(K)", "ТПР (B)", "TПП(S)"};
	private static final Double[] TIME_STEPS = {0.5, 1.0, 2.0, 5.0, 15.0, 30.0, 60.0};
	private static final Integer  OFFSET = 7;
	private static final Integer  STOP_BYTES = valueOf((byte)-35, (byte)125); 
	
	public static DataFile parse(byte[] rawData) throws Exception {
		if (rawData == null || 
			rawData.length < 2 || 
			rawData[0] != (byte)22 || 
			rawData[1] !=(byte)-125) 
				throw new Exception("Error creating DataFile: Corrupted raw data.");
		
		ByteBuffer buffer = ByteBuffer.wrap(rawData);		
		buffer.position(OFFSET); 
		String tcType = TC_TYPES[valueOf(buffer.get(), buffer.get()) - 2];
		int month = valueOf(buffer.get(), buffer.get());
		int day = valueOf(buffer.get(), buffer.get());
		int hours = valueOf(buffer.get(), buffer.get());
		int minutes = valueOf(buffer.get(), buffer.get());	
		LocalDateTime timeStamp = LocalDateTime.of(2025, day, month, hours, minutes);
		Double timeStep = TIME_STEPS[valueOf(buffer.get(), buffer.get()) - 1];
		List<Double> values = new ArrayList<Double>();
		
		while(buffer.remaining() > 2) {
			Integer value = valueOf(buffer.get(), buffer.get());

			if(value.equals(STOP_BYTES)) {
				break;
			}
			values.add(new Double((double)value / 10));
		}
		
		return new DataFile(tcType, timeStamp, timeStep, values);
	}

	
	private static int valueOf (byte x, byte y) {
		return ((y << 8) | (x & 0xFF));
	}

}
