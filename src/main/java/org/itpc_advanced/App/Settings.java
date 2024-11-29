package org.itpc_advanced.App;

public class Settings {
	private String port;
	private boolean automaticTarget;
	private double connectionTimeout;
	private double timeStep;
	private double maxDeviation;
	private boolean multipleCharts;
	private boolean autoLoad;
	private boolean shuffleValues;
	private static Settings instance;
		
	private Settings() {
		port = "COM1";
		autoLoad = false;
		multipleCharts = false;
		automaticTarget = true;
		connectionTimeout = 1000.0;
		timeStep = 0.15;
		maxDeviation = 40.0;
		shuffleValues = true;
	}
	
	public static Settings getInstance() {
		if(instance == null) {
			instance = new Settings();
		}
		return instance;
	}
	
	public String 	getPort() 		{ return port; 	}
	public long	getConnectionTimeout() 	{ return (long)connectionTimeout; }
	public double 	getTimeStep() 	        { return timeStep; }
	public double 	getMaxDeviation() 	{ return maxDeviation;	}
	public boolean 	isAutomaticTarget()	{ return automaticTarget; }
	public boolean 	isAutoLoad() 		{ return autoLoad;  }
	public boolean 	isMultipleCharts() 	{ return multipleCharts; }
	public boolean 	isShuffleValues() 	{ return shuffleValues;	}
	
	public void setPort(String port) 			   { this.port = port;	}
	public void setConnectionTimeout(double connectionTimeout) { this.connectionTimeout = connectionTimeout; }
	public void setTimeStep(double timeStep)   		   { this.timeStep = timeStep;	}
	public void setAutomaticTarget(boolean automaticTarget)	   { this.automaticTarget = automaticTarget; }
	public void setAutoLoad(boolean autoLoad) 		   { this.autoLoad = autoLoad;  }
	public void setMultipleCharts(boolean multipleCharts) 	   { this.multipleCharts = multipleCharts; }
	public void setShuffleValues(boolean shuffleValues) 	   { this.shuffleValues = shuffleValues; }

	
}
