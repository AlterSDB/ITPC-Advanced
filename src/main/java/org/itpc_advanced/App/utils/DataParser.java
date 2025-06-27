package org.itpc_advanced.App;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javafx.beans.property.DoubleProperty;

public class DataParser {
	
	private static final String[] TC_TYPES = {"XK(L)", "XA(K)", "?","?", "ТПР (B)", "TПП(S)"};
	private static final Double[] TIME_STEPS = {0.5, 1.0, 2.0, 5.0, 15.0, 30.0, 60.0}; // ??
	private static final Integer  OFFSET = 5;
	
	
	public static DataFileN parse(byte[] rawData) {
		int position = 0;
		position += 7;
		
		String tcType = parseTcType(Arrays.copyOfRange(rawData, position, position += 2));
		LocalDateTime timeStamp = parseTimeStamp(Arrays.copyOfRange(rawData, position, position += 8));
		Double timeStep = parseTimeStep(Arrays.copyOfRange(rawData, position, position += 2));
		List<Double> values = parseValues(Arrays.copyOfRange(rawData, 0, rawData.length - 1));
		
		return new DataFileN(tcType, timeStamp, timeStep, values);
	}
	
	
	
	public static ArrayList<Double> parseValues(byte[] data) {
		ArrayList<Double> values = new ArrayList<>();
		try {
			for(int i = 19; i < data.length; i += 2) {
				if(data[i] == -35 && data[i + 1] == 125) {
					break;
				}

				if(data[i] == -1) {
					break;
				}

				values.add(parseTemperaturePoint(data[i], data[i + 1]));
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Неожиданный конец файла, чтение завершено");
		}

		return values;
	}
	
	private static Double parseTemperaturePoint(byte x, byte y) {
		return new Double((double)valueFromBytes(x, y) / 10);
	}



	private static int valueFromBytes (byte x, byte y) {
		return ((y << 8) | (x & 0xFF));
	}

	public static Double parseTimeStep(byte[] bytes) {
		if(bytes.length < 2) {
			return null;
		}
		
		return TIME_STEPS[valueFromBytes(bytes[0], bytes[1])];
	}

	public static String parseTcType(byte[] bytes) {
		if(bytes.length < 2) {
			return null;
		}
		return TC_TYPES[6 - (valueFromBytes(bytes[0], bytes[1]))];
	}

	public static LocalDateTime parseTimeStamp(byte[] bytes) {
		int position = 0;
		int month = valueFromBytes(bytes[position++], bytes[position++]);
		int day = valueFromBytes(bytes[position++], bytes[position++]);
		int hours = valueFromBytes(bytes[position++], bytes[position++]);
		int minutes = valueFromBytes(bytes[position++], bytes[position++]);;
		return LocalDateTime.of(2025, day, month, hours, minutes);
	}

}
