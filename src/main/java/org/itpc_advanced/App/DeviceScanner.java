package org.itpc_advanced.App;

import java.util.Arrays;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jssc.SerialPortException;
import jssc.SerialPort;

public class DeviceScanner {

	private static ObservableList<DataFile> files;
	private static DeviceScanner device;
	private boolean connected;
	private Settings settings;
	private MySerialPort port;
	private FXMLMainController mainController;
	public static Thread thread;

	public static DeviceScanner getInstance() {
		if(device == null) {
			device = new DeviceScanner();
		}

	return device;
	}

	private DeviceScanner() {
		settings = Settings.getInstance();
		mainController = FXMLMainController.getInstance();
		connected = false;
		files = FXCollections.observableArrayList();
	//	files = FXCollections.observableArrayList(new DataFile(1), new DataFile(2), new DataFile(3), new DataFile(4), new DataFile(5), new DataFile(1), new DataFile(2), new DataFile(3));
	}

		public void runScanner() {
			Runnable task = new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
						files.clear();
						mainController.clearFields();
						DataFile.resetFilesCounter();

						mainController.statusText.setText("Подключение");
						openCOMPort();
						connectToDevice();
						mainController.statusText.setText("Чтение файлов");
						readFiles();
						mainController.statusText.setText("Порт закрывается");
						closeCOMPort();
						System.out.println("Работа треда завершена.");
					} catch(SerialPortException | InterruptedException e) {
						e.printStackTrace(); 
						port.close();
						if(!Thread.interrupted())
							Thread.currentThread().interrupt(); 
					} finally {
						connected = false;
						mainController.statusText.setText("");
						mainController.subStatusText.setText("");
					}

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								mainController.hideLoadScreen();
							}
						});
				}
			};
			mainController.showLoadScreen();
			thread = new Thread(task);
			thread.start();
			System.out.println("Тред начал работу.");
		}

		private void openCOMPort() throws InterruptedException, SerialPortException {
			System.out.println("Открывается порт " + settings.getPort());
			mainController.subStatusText.setText("Открывается порт " + settings.getPort());
			port = new MySerialPort(settings.getPort());
			port.openPort();
			port.setParams(9600, 8, 1, 0);
		}

		private void closeCOMPort() throws SerialPortException  {
			System.out.println("Закрываем порт.");
			mainController.statusText.setText("Закрываем порт...");
			port.close();
		}

		private void connectToDevice() throws InterruptedException, SerialPortException {
			System.out.println("Пытаемся подключить устройство...");
			byte[] data;
			while(!connected) {
				Thread.sleep(settings.getConnectionTimeout());
				mainController.subStatusText.setText("Попытка соединения");
				data = readData();
				if(Arrays.equals(data, ByteSequence.DEVICE_SYNC) || Arrays.equals(data, ByteSequence.DEVICE_SYNC_SHIFT)) {
				System.out.println("Замечен прибор, подключаем...");
					if(Arrays.equals(readData(ByteSequence.CONNECTION_REQUEST), ByteSequence.CONNECTION_CONFIRM)) {
						System.out.println("Получен ответ от устройства. Соединение установлено.");
						mainController.subStatusText.setText("Подключение выполнено");
						connected = true;
						return;
					}
				} else {
					System.out.println("Не удалось установить соединение.");
				}
			}
		}

		private byte[] readData(byte[] request) throws SerialPortException, InterruptedException {
			byte [] data = new byte[] {};
			while(data.length < 1) {
				System.out.println("Получаем байты по запросу...");
				port.purgePort(0);
				port.writeBytes(request);
				Thread.sleep(settings.getConnectionTimeout() - 320);
				data = port.readBytes(port.getInputBufferBytesCount());
			}
			System.out.println("Отправленные байты: " + Arrays.toString(request));
			System.out.println("Полученные байты: " + Arrays.toString(data));

			return data;
		}

		private byte[] readData() throws SerialPortException, InterruptedException {
			byte [] data = new byte[] {};
			while(data.length < 1) {
				System.out.println("Получаем байты без запроса...");
				Thread.sleep(settings.getConnectionTimeout() - 320);
				if(port.getInputBufferBytesCount() > 0) {
					data = port.readBytes(10);
				}
				port.purgePort(0);
			}
			System.out.println("Полученные байты: " + Arrays.toString(data));

			return data;
		}

		private void readFiles() throws InterruptedException, SerialPortException {
			System.out.println("Начинаем чтение файлов...");
			byte[] request = ByteSequence.FIRST_FILE_REQUEST;
			for(int i = 1; i <= 8; i++) {
				mainController.subStatusText.setText("Прочитано файлов " + (i-1) + " из " + 8);
				files.add(new DataFile(readData(request)));
				request = ByteSequence.nextFile(request);
			}
			System.out.println("Успешно прочитано " + DataFile.getCount());

			for(DataFile df : files) {
				System.out.println("Датафайл " + df.getID() +  " состояние " + df.isFileExists());
				System.out.println("Полученные значения: " + df.getValues().toString());
			}
		}

		public boolean isScanning() {
			if(thread == null) {
				return false;
			}
			return thread.isAlive();
		}

		public void stopScanner() {
			if(thread != null) {
			thread.interrupt();
			System.out.println("Тред остановлен.");
			}
		}

		public ObservableList<DataFile> getDataFiles() {
			return files;
		};

}

/**
 * Auto-closeable SerialPort class.
 *
 */
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
