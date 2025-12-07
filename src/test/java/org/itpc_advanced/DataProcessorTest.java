package org.itpc_advanced;

import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.service.DataParser;
import org.itpc_advanced.service.DataProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class DataProcessorTest {

	@Test
	public void testFile() {
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
		DataFile df = DataParser.parseFromBytes(rawData);
		DataProcessor.calculate(df);

		Assertions.assertEquals(DataFile.class, df.getClass());
		Assertions.assertEquals(120, df.getTargetTemperature());
		Assertions.assertEquals(121.3, df.getAverageMax());
		Assertions.assertEquals(118.9, df.getAverageMin());
		Assertions.assertEquals(10, df.getMaxTemps().size());
		Assertions.assertEquals(10, df.getMinTemps().size());
		Assertions.assertEquals(121.3, df.getMaxTemps().get(0));
		Assertions.assertEquals(121.2, df.getMaxTemps().get(5));
		Assertions.assertEquals(121.1, df.getMaxTemps().get(9));
		Assertions.assertEquals(119.5, df.getMinTemps().get(0));
		Assertions.assertEquals(118.5, df.getMinTemps().get(6));
		Assertions.assertEquals(118.2, df.getMinTemps().get(9));
	}

}