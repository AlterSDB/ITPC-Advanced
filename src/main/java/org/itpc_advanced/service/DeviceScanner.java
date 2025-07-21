package org.itpc_advanced.service;


import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import org.itpc_advanced.model.ComPort;
import org.itpc_advanced.model.DataFile; 
import org.itpc_advanced.model.ProcessedDataFile;
import org.itpc_advanced.model.Request;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class DeviceScanner {
	private static final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	private static volatile State currentState = State.WAITING_FOR_DEVICE;
	private static volatile int filesCounter = 1;
	private static final int beyondFilesTimeout = 190;
	private static volatile long timer;
	
	private enum State {
		WAITING_FOR_DEVICE,
		RECEIVING_FILES,
		DONE
	}

	public static ObservableList<ProcessedDataFile> readDataFiles() {
		ObservableList<ProcessedDataFile> files  = FXCollections.observableArrayList();
		try(ComPort port = new ComPort("COM1")){
			port.openPort();
			port.setParams(9600, 8, 1, 0);
			port.addEventListener(new SerialPortEventListener() {

				@Override
				public void serialEvent(SerialPortEvent serialPortEvent) {
					try {
						byte[] received = port.readBytes();
						if (received == null) return;
						System.out.println("Received now: " + System.currentTimeMillis() + " " + Arrays.toString(received));
						switch(currentState){
							case WAITING_FOR_DEVICE: {
								if (Arrays.equals(received, Request.DEVICE_SYNC.getBytes())) {
									port.writeBytes(Request.TO_CONNECT.getBytes());
									System.out.println("Device founded. Sended request to connect");
								}
								if (Arrays.equals(received, Request.CONNECTION_CONFIRM.getBytes())) {
									System.out.println("Connection confirmed - start receiving");
									currentState = State.RECEIVING_FILES;
									System.out.println("Status changed to receieve");
									System.out.println("Trying to read file " + filesCounter);
									updateTimer();
									System.out.println(System.currentTimeMillis());
									port.writeBytes(Request.valueOf("FILE_1").getBytes());
								}
								break;
							}
							case RECEIVING_FILES: {
								if(timer > System.currentTimeMillis()) {
									buffer.write(received);
									break;
								}
								System.out.println("Readed fIle: " + filesCounter + " " + Arrays.toString(buffer.toByteArray() ));
								DataFile dataFile = DataParser.parse(buffer.toByteArray());
								ProcessedDataFile processedDataFile = DataFileProcessor.process(dataFile);
								files.add(processedDataFile);
								buffer.reset();
								filesCounter++;
							//	port.purgePort(0);
								
								if(filesCounter > 8) {
									System.out.println("Status changed to DONE");
									currentState = State.DONE;
									break;
								}
								updateTimer();
								System.out.println(System.currentTimeMillis());
								port.writeBytes(Request.valueOf("FILE_" + filesCounter).getBytes());
								break;
							}
							case DONE: {
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				private void updateTimer() {
					timer = System.currentTimeMillis() + beyondFilesTimeout;
				}
				
			});
			
			final long timeout = System.currentTimeMillis() + 20000;
			while(currentState != State.DONE){
				if(System.currentTimeMillis() > timeout) {
					throw new TimeoutException("Time is out.");
				} else {
					Thread.sleep(30);
				}
			}
			System.out.println("Done!");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("All files has been readed: " + files.toString());
		return files;
	}
}
