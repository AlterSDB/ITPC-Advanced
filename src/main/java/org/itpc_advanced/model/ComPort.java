package org.itpc_advanced.model;

import jssc.SerialPort;
import jssc.SerialPortException;

public class ComPort extends SerialPort implements AutoCloseable {

	
	public ComPort(String portName) {
		super(portName);
	}

	@Override
	public void close() {
		try {
			if(isOpened()) {
				this.removeEventListener();
				closePort();
				System.out.println("Порт закрыт");
			}
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
	
}