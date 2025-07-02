package org.itpc_advanced.service;

import org.itpc_advanced.model.ComPort;
import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.model.Request;

import com.sun.marlin.ByteArrayCache;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class DeviceScanner {
	
	private static ComPort port;
	private static ByteBuffer buffer = ByteBuffer.wrap(new byte[] {});
	private static byte[] stopBytes = new byte[] {-35, 125};
	
	public static ObservableList<DataFile> readData() {
		ObservableList<DataFile> files = FXCollections.observableArrayList();
		port = new ComPort("COM1");
		try {
			port.openPort();
			System.out.println("Port is opened");
			port.setParams(SerialPort.BAUDRATE_9600, 
						   SerialPort.DATABITS_8,
						   SerialPort.STOPBITS_1,
						   SerialPort.PARITY_NONE);
		//	port.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
		//			                SerialPort.FLOWCONTROL_RTSCTS_OUT);
			port.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
		/*	Thread.sleep(1000);
			port.openPort();
			port.setParams(9600, 8, 1, 0);
			
			port.addEventListener(SerialPortEventListener);
			
			
			System.out.println("Пытаемся подключить устройство...");
			byte[] data;
			while(true) {
				Thread.sleep(1000);
				data = readDatas();
				if(Arrays.equals(data, Request.DEVICE_SYNC.getBytes()) || Arrays.equals(data, Request.DEVICE_SYNC_SHIFT.getBytes())) {
				System.out.println("Замечен прибор, подключаем...");
					if(Arrays.equals(readDatas(Request.TO_CONNECT.getBytes()), Request.CONNECTION_CONFIRM.getBytes())) {
						System.out.println("Получен ответ от устройства. Соединение установлено.");
						break;
					}
				} else {
					System.out.println("Не удалось установить соединение.");
				}
			}
			
			System.out.println("Начинаем чтение файлов...");
			byte[] request = Request.FILE_1.getBytes();
			for(int i = 1; i <= 8; i++) {
				request = Request.valueOf("FILE_" + i).getBytes();
				files.add(DataParser.parse(readDatas(request)));
			}
			port.close();
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return files;
	}
	
	
	private static class PortReader implements SerialPortEventListener {
		@Override
		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR() && event.getEventValue() > 0) {
				try {
					byte[] receivedData = port.readBytes();
					buffer.put(receivedData);
					if(containsStopBytes(buffer.array())) {
						
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		private boolean containsStopBytes(byte[] array) {
			// TODO Auto-generated method stub
			return false;
		}

	}	
}
