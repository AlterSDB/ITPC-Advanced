package org.itpc_advanced.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.itpc_advanced.model.ComPort;
import org.itpc_advanced.model.ComRequest;
import org.itpc_advanced.model.TemperatureRecord;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DeviceScanner {

	private enum State {
		WAITING_FOR_DEVICE,
		RECEIVING_FILES,
		DONE
	}

	private static final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	private static volatile State currentState = State.WAITING_FOR_DEVICE;
	private static volatile int filesCounter = 1;

	public static ObservableList<TemperatureRecord> readDataFiles() {
		ObservableList<TemperatureRecord> files  = FXCollections.observableArrayList();
		currentState = State.WAITING_FOR_DEVICE;
		filesCounter = 1;
		buffer.reset();

		try (ComPort port = new ComPort("COM1")){
			port.openPort();
			port.setParams(9600, 8, 1, 0); 
			port.addEventListener((serialPortEvent) -> {
					try {
						if (currentState == State.RECEIVING_FILES) {
							Thread.sleep(200);  // entry delay
						}
						byte[] received = port.readBytes();
						if (received == null) {
							return;
						}
						buffer.write(received);	
						switch(currentState) {
						    case WAITING_FOR_DEVICE: {
								if (Arrays.equals(received, ComRequest.DEVICE_SYNC.getBytes())) {
									port.writeBytes(ComRequest.TO_CONNECT.getBytes());
									// Device found, sending request to connect
									break;
								}
								if (Arrays.equals(received, ComRequest.CONNECTION_CONFIRM.getBytes())) {
									// Connection confirmed - start receiving
									currentState = State.RECEIVING_FILES;
									// Trying to read first file
									port.writeBytes(ComRequest.valueOf("FILE_1").getBytes());
								}
								buffer.reset();
								port.purgePort(0);
								break;
						    }
						    case RECEIVING_FILES: {
								// Received file chunk
						    	System.out.println("FILE: " + Arrays.toString(buffer.toByteArray()));
								TemperatureRecord df = DataParser.parseFromBytes(buffer.toByteArray());
							//	DataProcessor.calculate(df);
								files.add(df);
								buffer.reset();
								port.purgePort(0);
								filesCounter++;
								if (filesCounter <= 8) {
									port.writeBytes(ComRequest.valueOf("FILE_" + filesCounter).getBytes());
									break;
								}
								// All files read - status done
								currentState = State.DONE;
								break;
						    }
						    case DONE: {
						    	break;
						    }
						}
						
					}catch(Exception e) {
						e.printStackTrace();
					}
			});

		final long deadline = System.currentTimeMillis() +  2000; 

			while (currentState != State.DONE){
				if (System.currentTimeMillis() > deadline) {
					throw new TimeoutException("Time Out!");
				}
				Thread.sleep(20); // update timer delay
			}
		} catch(Exception e) {
			e.printStackTrace();
		}	

		return files;
	}

	public static List<TemperatureRecord> readTemperatureRecords() {
		List<TemperatureRecord> files  = new ArrayList<>();
		currentState = State.WAITING_FOR_DEVICE;
		filesCounter = 1;
		buffer.reset();

		try (ComPort port = new ComPort("COM1")){
			port.openPort();
			port.setParams(9600, 8, 1, 0); 
			port.addEventListener((serialPortEvent) -> {
				try {
					if (currentState == State.RECEIVING_FILES) {
						Thread.sleep(200);  // entry delay
					}
					byte[] received = port.readBytes();
					if (received == null) {
						return;
					}
					buffer.write(received);	
					switch(currentState) {
					    case WAITING_FOR_DEVICE: {
					    	if (Arrays.equals(received, ComRequest.DEVICE_SYNC.getBytes())) {
								port.writeBytes(ComRequest.TO_CONNECT.getBytes());
								break;
							}
							if (Arrays.equals(received, ComRequest.CONNECTION_CONFIRM.getBytes())) {
								currentState = State.RECEIVING_FILES;
								port.writeBytes(ComRequest.valueOf("FILE_1").getBytes());
							}
							buffer.reset();
							port.purgePort(0);
							break;
					    }
					    case RECEIVING_FILES: {
					    	System.out.println("FILE: " + Arrays.toString(buffer.toByteArray()));
							TemperatureRecord df = DataParser.parseFromBytes(buffer.toByteArray());
							files.add(df);
							buffer.reset();
							port.purgePort(0);
							filesCounter++;
							if (filesCounter <= 8) {
								port.writeBytes(ComRequest.valueOf("FILE_" + filesCounter).getBytes());
								break;
							}
							currentState = State.DONE;
							break;
					    }
					    case DONE: {
						   	break;
					    }
					}

					}catch(Exception e) {
						e.printStackTrace();
					}
			});

			final long timeout = System.currentTimeMillis() +  5000; 
			while (currentState != State.DONE){
				if (System.currentTimeMillis() > timeout) {
					throw new TimeoutException("Time Out!");
				}
				Thread.sleep(20);
			}

		} catch(Exception e) {
			e.printStackTrace();
		}	

		return files;
	}

}