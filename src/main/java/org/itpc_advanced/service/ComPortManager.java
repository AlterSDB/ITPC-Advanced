package org.itpc_advanced.service;

import org.itpc_advanced.model.ComPort;

import jssc.SerialPort;
import jssc.SerialPortException;

public class ComPortManager {

	public static void openPort(String portName) {
		try (ComPort port = new ComPort(portName)) {
			port.setParams(9600, 8, 1, 0);
			port.openPort();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
