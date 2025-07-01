package org.itpc_advanced.service;

import org.itpc_advanced.model.ComPort;
import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.model.Request;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jssc.SerialPortException;

public class DeviceScanner {
	
	public static ObservableList<DataFile> readData() {
		ObservableList<DataFile> files = FXCollections.observableArrayList();
		
		try (ComPort port = new ComPort("COM1")) {
			Thread.sleep(1000);
			port.openPort();
			port.setParams(9600, 8, 1, 0);
			
			
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return files;
	}

	
	
	
	
	
	
	
	
	@Deprecated
	private static byte[] readDatas(byte[] request) throws SerialPortException, InterruptedException {
		byte [] data = new byte[] {};
		while(data.length < 1) {
			System.out.println("Получаем байты по запросУ...");
	//		port.purgePort(0);
	//		port.writeBytes(request);
			Thread.sleep(650 - 320);
		//	data = port.readBytes(port.getInputBufferBytesCount());
		//	System.out.println("HEX: " + port.readHexString(port.getInputBufferBytesCount() )); // test, delete previous comment
		}
		System.out.println("Отправленные байты: " + Arrays.toString(request));
		System.out.println("Полученные байты: " + Arrays.toString(data));

		return data;
	}
	
	@Deprecated
	private static byte[] readDatas() throws SerialPortException, InterruptedException {
		byte [] data = new byte[] {};
		while(data.length < 1) {
			System.out.println("Получаем байты без запроса...");
			Thread.sleep(650 - 320);
		//	if(port.getInputBufferBytesCount() > 0) {
		//		data = port.readBytes(10);
		//	}
		//	port.purgePort(0);
		}
		System.out.println("Полученные байты: " + Arrays.toString(data));

		return data;
	}
	
}
