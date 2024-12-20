package org.itpc_advanced.App;

public class ByteSequence {
	
	// TX
	public static final byte[] CONNECTION_REQUEST = new byte[] { 22, 3, -1, 0, -4, -2, 0, -2, -1 };
	public static final byte[] FIRST_FILE_REQUEST = new byte[] { 22, 6, -1, 0, -7, -2, 33, 0, -38, -128, -125, -2 };
	
	// RX
	public static final byte[] DEVICE_SYNC = new byte[]        { 22, 4, 0, 0, -6, -1, -128, 7, 119, -1 };
	public static final byte[] DEVICE_SYNC_SHIFT = new byte[]  { -1, 22, 4, 0, 0, -6, -1, -128, 7, 119 };
	public static final byte[] CONNECTION_CONFIRM = new byte[] { 22, 6, 0, 0, -8, -1, -123, -48, 5, 4, -96, -2 };
	
	public static byte[] nextFile(byte[] request) {
		byte[] result = request;
		request[8] += (byte)4;
		request[10] -= (byte) 4;
		return result;
	}
	
	public static class Examples {
		public static final byte[] EMPTYDATA_EXAMPLE = new byte[] { 22, -125, 0, 0, 123, -1, -123, -1, -1, -1, -1, -1, -1, -1, -1,
                                                              -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                                              -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                                              -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                                              -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                                              -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                                              -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                                               7, -103, 22, 4, 0, 0, -6, -1, -128, 7, 119, -1 };
		
		public static final byte[] DATA_EXAMPLE_1 = new byte[] { 22, -125, 0, 0, 123, -1, -123, 5, 0, 1, 0, 1, 0, 0, 0, 0, 0, 2, 0, -60,
                                                               7, -68, 7, -72, 7, -78, 7, -84, 7, -93, 7, -97, 7, -97, 7, -92, 7, -85,
                                                               7, -78, 7, -72, 7, -67, 7, -67, 7, -68, 7, -74, 7, -78, 7, -83, 7, -89, 7, -93,
                                                               7, -93, 7, -92, 7, -87, 7, -79, 7, -72, 7, -66, 7, -63, 7, -63, 7, -65, 7, -70,
                                                               7, -74, 7, -78, 7, -82, 7, -89, 7, -89, 7, -87, 7, -83, 7, -75, 7, -72, 7, -70,
                                                               7, -70, 7, -69, 7, -73, 7, -77, 7, -83, 7, -90, 7, -92, 7, -90, 7, -35, 125, -1,
                                                              -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -92, -21, 22,
                                                               4, 0, 0, -6, -1, -128, 7, 119, -1 };

		public static final byte[] DATA_EXAMPLE_2 = new byte[] { 22, -125, 0, 0, 123, -1, -123, 5, 0, 1, 0, 1, 0, 0, 0, 0, 0, 2,
                                                               0, -99, 7, -107, 7, -115, 7, -114, 7, -113, 7, -103, 7, -93, 7, -83, 7, -73, 7,
                                                             -70, 7, -70, 7, -73, 7, -79, 7, -85, 7, -91, 7, -99, 7, -107, 7, -113, 7, -115,
                                                               7, -109, 7, -99, 7, -88, 7, -80, 7, -80, 7, -83, 7, -88, 7, -92, 7, -100, 7, -101,
                                                               7, -107, 7, -111, 7, -111, 7, -103, 7, -94, 7, -83, 7, -75, 7, -71, 7, -77, 7,
                                                             -81, 7, -85, 7, -90, 7, -35, 125, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                                              -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                                              -1, -7, -32, 22, 4, 0, 0, -6, -1, -128, 7, 119, -1 };

		public static final byte[] DATA_EXAMPLE_3 = new byte[] { 22, -125, 0, 0, 123, -1, -123, 5, 0, 1, 0, 1, 0, 0, 0, 0, 0, 2,
                                                               0, -99, 19, -107, 19, -115, 19, -114, 19, -113, 19, -103, 19, -93, 19, -83, 19, -73, 19,
                                                             -70, 19, -70, 19, -73, 19, -79, 19, -85, 19, -91, 19, -99, 19, -107, 19, -113, 19, -115,
                                                              19, -109, 19, -99, 19, -88, 19, -80, 19, -80, 19, -83, 19, -88, 19, -92, 19, -100, 19, -101,
                                                              19, -107, 19, -111, 19, -111, 21, -103, 21, -94, 21, -83, 21, -75, 19, -71, 19, -77, 19,
                                                             -81, 19, -85, 19, -90, 19, -35, 125, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                                              -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                                              -1, -7, -32, 22, 4, 0, 0, -6, -1, -128, 7, 119, -1 };

		public static final byte[] DATA_EXAMPLE_4 = new byte[] { 22, -125, 0, 0, 123, -1, -123, 5, 0, 1, 0, 1, 0, 0, 0, 0, 0, 2,
                                                               0, 7, 8, -6, 7, -16, 7, -34, 7, -41, 7, -36, 7, -15, 7, 8, 8, 35, 8, 35, 8, 36,
                                                               8, 20, 8, 4, 8, -2, 7, -24, 7, -35, 7, -53, 7, -55, 7, -52, 7, -33, 7, -6, 7, 11,
                                                               8, 26, 8, 23, 8, 11, 8, -8, 7, -18, 7, -34, 7, -41, 7, -55, 7, -56, 7, -42, 7,
                                                             -22, 7, -2, 7, 17, 8, 26, 8, 19, 8, -35, 125, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                                              -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                                              -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -59, -27, 22, 4, 0, 0, -6, -1, -128,
                                                               7, 119, -1 };

		public static final byte[] DATA_EXAMPLE_5 = new byte[] { 22, -125, 0, 0, 123, -1, -123, 5, 0, 1, 0, 1, 0, 0, 0, 0, 0, 2,
                                                               0, 62, 24, 65, 24, 57, 24, 47, 24, 46, 24, 52, 24, 55, 24, 52, 24, 54, 24, 58,
                                                              24, 57, 24, 56, 24, 60, 24, 62, 24, 61, 24, 63, 24, 63, 24, 60, 24, 55 };

	}

}
