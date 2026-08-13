package org.itpc_advanced.newmodel;

public enum ComRequest {

	// TX
	TO_CONNECT(new byte[] {22, 3, -1, 0, -4, -2, 0, -2, -1}), 
	FILE_1(new byte[] {22, 6, -1, 0, -7, -2, 33, 0, -38, -128, -125, -2}), 
	FILE_2(new byte[] {22, 6, -1, 0, -7, -2, 33, 0, -34, -128, 127, -2}),
	FILE_3(new byte[] {22, 6, -1, 0, -7, -2, 33, 0, -30, -128, 123, -2}),
	FILE_4(new byte[] {22, 6, -1, 0, -7, -2, 33, 0, -26, -128, 119, -2}),
	FILE_5(new byte[] {22, 6, -1, 0, -7, -2, 33, 0, -22, -128, 115, -2}),
	FILE_6(new byte[] {22, 6, -1, 0, -7, -2, 33, 0, -18, -128, 111, -2}),
	FILE_7(new byte[] {22, 6, -1, 0, -7, -2, 33, 0, -14, -128, 107, -2}),
	FILE_8(new byte[] {22, 6, -1, 0, -7, -2, 33, 0, -10, -128, 103, -2}),

	// RX
	DEVICE_SYNC(new byte[] {22, 4, 0, 0, -6, -1, -128, 7, 119, -1}),
	DEVICE_SYNC_SHIFT(new byte[] {-1, 22, 4, 0, 0, -6, -1, -128, 7, 119}),
	CONNECTION_CONFIRM(new byte[] {22, 6, 0, 0, -8, -1, -123, -48, 5, 4, -96, -2});

	private final byte[] bytes;

	ComRequest(byte[] bytes) {
		this.bytes = bytes;
	}

	public byte[] getBytes(){
		return bytes;
	}

}