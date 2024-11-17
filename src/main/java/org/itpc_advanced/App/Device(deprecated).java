package org.itpc_advanced.App;

import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jssc.SerialPortException;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class Device {
	
	private static ObservableList<DataFile> files;
	private static Device device;
	private boolean connected;
	private long delay;
	private Settings settings;
	private MySerialPort port;
	private boolean receieved;
	public static Thread thread;
	
	
	
		public static Device getInstance() {
			if(device == null)
				device = new Device();
			return device;
		}	
	
		private Device() {
		settings = Settings.getInstance();
		connected = false;
		delay = 20;
		files = FXCollections.observableArrayList();
	//	files = FXCollections.observableArrayList(new DataFile(5), new DataFile(2), new DataFile(4), new DataFile(1), new DataFile(2), new DataFile(3), new DataFile(1), new DataFile(2));		
		Runnable task = new Runnable() {
		 	@Override
		   public void run() {
		 		try {
					openCOMPort();
					while(!connected) {
						System.out.println("Начинаем спать...");
						Thread.sleep(settings.getConnectionTimeout());
					}
					readAllFiles();
					closeCOMPort();
					System.out.println("Работа треда завершена.");
				} 	
		 		catch(SerialPortException | InterruptedException e) { 
					e.printStackTrace(); 
				//	System.out.println("Ошибка: " + e.getExceptionType());
					port.close();
					if(!Thread.interrupted())
						Thread.currentThread().interrupt(); 
					}		
		 	}
		};

		thread = new Thread(task);
		thread.start();
		System.out.println("Тред начал работу.");
		}	
		
		private void openCOMPort() throws InterruptedException, SerialPortException {		
			Thread.sleep(delay);
			System.out.println("Открывается порт " + settings.getPort());
			port = new MySerialPort(settings.getPort());
			port.openPort();
			port.setParams(9600, 8, 1, 0);
			port.setEventsMask(SerialPort.MASK_RXCHAR);
			port.addEventListener(new SerialPortEventListener() {
				@Override
				public void serialEvent(SerialPortEvent event) {			
					try {
						if(event.isRXCHAR()) {
							byte[] buffer = port.readBytes(port.getInputBufferBytesCount());
							System.out.println("СЛОВЛЕНЫ ДАННЫЕ " + Arrays.toString(buffer));
							if(Arrays.equals(buffer, Request.SYNC_DEVICE)) {
								System.out.println("Замечен прибор, подключаем...");
								connected = true;
								return;
							}
							if(buffer.length > 0 && buffer[1] == -125) {
								System.out.println("Обнаружен файл, создаём файл.");
								files.add(new DataFile(buffer));
								receieved = true;
								return;
							}
						}
					}
					catch(SerialPortException e) {
						e.printStackTrace();
					}
				}
			});
			
		}
		
		
		private void closeCOMPort() {
			System.out.println("Закрываем порт.");
			port.close();
		}
		
		private void readAllFiles() throws InterruptedException, SerialPortException {
			System.out.println("Начинаем чтение файлов...");
			byte[] request = Request.FIRST_FILE;
			for(int i = 1; i <= 8; i++) {
				receieved = false;
				while(receieved == false) {
				port.writeBytes(request);
				Thread.sleep(1500);
				}
				request = Request.nextFile(request);
			}
			System.out.println("Успешно прочитано " + DataFile.getCount());
			for(DataFile df : files) {
				System.out.println("Датафайл " + df.getID() +  " состояние " + df.isFileExists());
				System.out.println("Полученные значения: " + df.getValues().toString());
			}
		}

		
		public boolean isConnected() 					{	return connected;	};		
		public ObservableList<DataFile> getDataFiles() 	{	return files;		};

}


class MySerialPort extends SerialPort implements AutoCloseable{
	
	public MySerialPort(String portName) {
		super(portName);
	}

	@Override
	public void close() {
		try {
			if(isOpened()) {
				closePort();
				System.out.println("Порт закрыт");
			}
		} catch (SerialPortException e) {
			System.out.println("Произошла ошибка при закрытии порта.");
			e.printStackTrace();
		}
	}
}
