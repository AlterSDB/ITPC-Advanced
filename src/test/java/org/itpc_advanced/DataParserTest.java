package org.itpc_advanced;

import java.time.LocalDateTime;

import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.service.DataParser;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class DataParserTest {
	
	@Test
	public void myFirstTest() {
		int one = 5;
		int two = 5;
		Assertions.assertEquals(one, two);
	}
	@Test
	public void testFileTPPS() {
		final byte[] rawData = {22, -125, 0, 0, 123, -1, -123, 1, 
				0, 24, 0, 12, 0, 15, 0, 11, 0, 4, 0, 34, 1, 34, 1, 34, 1, 34, 1, 
				34, 1, 34, 1, 32, 78, 34, 1, 34, 1, 34, 1, 34, 1, 32, 78, 32, 78, 
				34, 1, 34, 1, 34, 1, 34, 1, 34, 1, 32, 78, 22, 1, -35, 125, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, 74, -66}; 
		
		DataFile df = null;
		try {
			df = DataParser.parse(rawData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(DataFile.class, df.getClass());
		Assertions.assertEquals("ТПП(S)", df.getTcType());
		Assertions.assertEquals(LocalDateTime.of(2025, 12, 24, 15, 11), df.getTimeStamp());
		Assertions.assertEquals(0.5, df.getTimeStep());
		Assertions.assertEquals(20, df.getValues().size());
		Assertions.assertEquals(29.0, df.getValues().get(2));

	}
	
	@Test
	public void testFileXA() {
		final byte[] rawData = {22, -125, 0, 0, 123, -1, -123, 5, 
				0, 1, 0, 1, 0, 0, 0, 0, 0, 2, 0, -98, 4, -98, 4, -96, 
				4, -95, 4, -93, 4, -91, 4, -89, 4, -87, 4, -87, 4, -85, 
				4, -83, 4, -81, 4, -81, 4, -79, 4, -77, 4, -75, 4, -76, 4, 
				-74, 4, -74, 4, -72, 4, -72, 4, -70, 4, -70, 4, -70, 4, -70, 
				4, -68, 4, -69, 4, -69, 4, -69, 4, -69, 4, -67, 4, -67, 4, 
				-69, 4, -67, 4, -67, 4, -68, 4, -67, 4, -70, 4, -70, 4, -70,
				4, -72, 4, -72, 4, -72, 4, -73, 4, -73, 4, -35, 125, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -31, -25}; 
		
		DataFile df = null;
		try {
			df = DataParser.parse(rawData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(DataFile.class, df.getClass());
		Assertions.assertEquals("ТХА(K)", df.getTcType());
		Assertions.assertEquals(LocalDateTime.of(2025, 01, 01, 00, 00), df.getTimeStamp());
		Assertions.assertEquals(15.0, df.getTimeStep());
		Assertions.assertEquals(45, df.getValues().size());
		Assertions.assertEquals(118.4, df.getValues().get(2));
		Assertions.assertEquals(120.4, df.getValues().get(16));

	}
	
	@Test
	public void testFile1() {
		final byte[] rawData = {22, -125, 0, 0, 123, -1, -123, 1, 0, 1, 0, 1, 
				0, 0, 0, 0, 0, 1, 0, 32, 78, 32, 78, 32, 78, 32, 78, 32, 78, 32, 
				78, 32, 78, 32, 78, 32, 78, 32, 78, 32, 78, 32, 78, 32, 78, 32, 
				78, 32, 78, 32, 78, 32, 78, 32, 78, 32, 78, 32, 78, 32, 78, 32, 
				78, 32, 78, 32, 78, -35, 125, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, -40}; 
		
		DataFile df = null;
		try {
			df = DataParser.parse(rawData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(DataFile.class, df.getClass());
		Assertions.assertEquals("ТХК(L)", df.getTcType());
		Assertions.assertEquals(LocalDateTime.of(2025, 01, 01, 00, 00), df.getTimeStamp());
		Assertions.assertEquals(0.5, df.getTimeStep());
		Assertions.assertEquals(24, df.getValues().size());
		Assertions.assertEquals(2000, df.getValues().get(7));
		Assertions.assertEquals(2000, df.getValues().get(23));

	}
	
	@Test
	public void testFile2() {
		final byte[] rawData = {22, -125, 0, 0, 123, -1, -123, 2, 0, 4, 
				0, 3, 0, 5, 0, 6, 0, 2, 0, 32, 78, 32, 78, 32, 78, 32, 78,
32, 78, 32, 78, 32, 78, 32, 78, 32, 78, 32, 78, 32, 78, -35, 125, -1, -1,
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -85, -65}; 
		
		DataFile df = null;
		try {
			df = DataParser.parse(rawData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(DataFile.class, df.getClass());
		Assertions.assertEquals("ТХА(K)", df.getTcType());
		Assertions.assertEquals(LocalDateTime.of(2025, 03, 04, 05, 06), df.getTimeStamp());
		Assertions.assertEquals(1.0, df.getTimeStep());
		Assertions.assertEquals(11, df.getValues().size());
		Assertions.assertEquals(2000, df.getValues().get(10));
		Assertions.assertEquals(2000, df.getValues().get(2));

	}
	
	@Test
	public void testFile3() {
		final byte[] rawData = {22, -125, 0, 0, 123, -1, -123, 3, 0, 1, 0, 5, 0, 0, 
				0, 21, 0, 3, 0, 32, 78, 32, 78, 32, 78, 32, 78, 32, 78, 32, 78, 32, 
				78, 32, 78, 32, 78, 32, 78, -35, 125, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, 16, -70}; 
		
		DataFile df = null;
		try {
			df = DataParser.parse(rawData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(DataFile.class, df.getClass());
		Assertions.assertEquals("ТПР(B)", df.getTcType());
		Assertions.assertEquals(LocalDateTime.of(2025, 05, 01, 00, 21), df.getTimeStamp());
		Assertions.assertEquals(2.0, df.getTimeStep());
		Assertions.assertEquals(10, df.getValues().size());
		Assertions.assertEquals(2000, df.getValues().get(4));
		Assertions.assertEquals(2000, df.getValues().get(9));

	}
	
	@Test
	public void testFile4() {
		final byte[] rawData = {22, -125, 0, 0, 123, -1, -123, 4, 0, 1, 0, 
				1, 0, 0, 0, 0, 0, 4, 0, 32, 78, 32, 78, 32, 78, 32, 78, -35, 
				125, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -57, -84}; 
		
		DataFile df = null;
		try {
			df = DataParser.parse(rawData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(DataFile.class, df.getClass());
		Assertions.assertEquals("ТПП(S)", df.getTcType());
		Assertions.assertEquals(LocalDateTime.of(2025, 01, 01, 00, 00), df.getTimeStamp());
		Assertions.assertEquals(5.0, df.getTimeStep());
		Assertions.assertEquals(4, df.getValues().size());
		Assertions.assertEquals(2000, df.getValues().get(0));
		Assertions.assertEquals(2000, df.getValues().get(3));

	}
	
	@Test
	public void testFile5() {
		final byte[] rawData = {22, -125, 0, 0, 123, -1, -123, 5, 0, 1, 0, 1, 
				0, 0, 0, 0, 0, 2, 0, 32, 78, 32, 78, 32, 78, 32, 78, 32, 78, 
				32, 78, 32, 78, 32, 78, -35, 125, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 8, -81}; 
		
		DataFile df = null;
		try {
			df = DataParser.parse(rawData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(DataFile.class, df.getClass());
		Assertions.assertEquals("ТХА(K)", df.getTcType());
		Assertions.assertEquals(LocalDateTime.of(2025, 01,01,00,00), df.getTimeStamp());
		Assertions.assertEquals(15.0, df.getTimeStep());
		Assertions.assertEquals(8, df.getValues().size());
		Assertions.assertEquals(2000, df.getValues().get(1));
		Assertions.assertEquals(2000, df.getValues().get(5));

	}
	
	@Test
	public void testFileEmpty() {
		final byte[] rawData = {22, -125, 0, 0, 123, -1, -123, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -7, -111};
		
		DataFile df = null;
		try {
			df = DataParser.parse(rawData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(DataFile.class, df.getClass());
		Assertions.assertEquals("-", df.getTcType());
		Assertions.assertEquals(LocalDateTime.MIN, df.getTimeStamp());
		Assertions.assertEquals(0.0, df.getTimeStep());
		Assertions.assertEquals(0, df.getValues().size());
	}
	
	
	
	
	
	
	
	
	
	
	
	
}