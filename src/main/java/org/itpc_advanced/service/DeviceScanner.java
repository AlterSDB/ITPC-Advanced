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
		
		try (ComPort port = new ComPort("COM1")){
			port.openPort();
			port.setParams(9600, 8, 1, 0); 
			port.addEventListener(new SerialPortEventListener() {
				@Override
				public void serialEvent(SerialPortEvent serialPortEvent) {
					try {
						if(currentState == State.RECEIVING_FILES) {
						Thread.sleep(200);  // entry delay
						}
						byte[] received = port.readBytes();
						if (received == null) {
							return;
						}
						buffer.write(received);	
						switch(currentState) {
						    case WAITING_FOR_DEVICE: {
								if (Arrays.equals(received, Request.DEVICE_SYNC.getBytes())) {
									port.writeBytes(Request.TO_CONNECT.getBytes());
									System.out.println("Device founded. Sended request to connect");
									break;
								}
								if (Arrays.equals(received, Request.CONNECTION_CONFIRM.getBytes())) {
									System.out.println("Connection confirmed - start receiving");
									currentState = State.RECEIVING_FILES;
									System.out.println("Status changed to receieve");
									System.out.println("Trying to read file " + filesCounter);
									port.writeBytes(Request.valueOf("FILE_1").getBytes());
								}
								buffer.reset();
								port.purgePort(0);
								break;
						    }
						    case RECEIVING_FILES: {
								System.out.println("Readed fIle: " + filesCounter + " " + Arrays.toString(buffer.toByteArray() ));
								files.add(DataProcessor.process(DataParser.parse(buffer.toByteArray())));
								buffer.reset();
								port.purgePort(0);
								filesCounter++;
								if(filesCounter <= 8) {
									port.writeBytes(Request.valueOf("FILE_" + filesCounter).getBytes());
									break;
								}
								System.out.println("All files readed! Status: DONE");
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
				}
			});
			
			final long timeout = System.currentTimeMillis() + 10000; // 10 seconds
			
			while (currentState != State.DONE){
				if(System.currentTimeMillis() > timeout) {
					throw new TimeoutException("Time Out!");
				}
				
				Thread.sleep(20); // update timer delay
			}
			System.out.println("Done!");			
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
		
		return files;
	}
	
}
