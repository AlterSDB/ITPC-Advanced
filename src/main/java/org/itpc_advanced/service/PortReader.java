package org.itpc_advanced.service;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class PortReader implements SerialPortEventListener {

	@Override
	public void serialEvent(SerialPortEvent event) {
		if (event.isRXCHAR() && event.getEventValue() > 0) {
			try {
				System.out.println();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

}
