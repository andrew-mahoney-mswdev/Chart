import java.util.Scanner;

public class Reading {
	public enum Sensor {leftKnee, rightKnee, leftAnkle, rightAnkle, leftElbow, rightElbow, chest, lowerBack, leftWrist, rightWrist, forehead, unknown}
	public enum Module {accelerometer, gyroscope, magnetometer, unknown}
	
	private Sensor sensor;
	private Module module;
	private double x, y, z;
	private long epoch;
	private int disconnects;

	Reading(Scanner fileReader) {
		fileReader.next(); //Skip time
		String sensorStr = fileReader.next();
		
		String sensorSubStr = sensorStr.substring(16);
		if (sensorSubStr.equals("50")) sensor = Sensor.leftKnee;
		else if (sensorSubStr.equals("2B")) sensor = Sensor.rightKnee;
		else if (sensorSubStr.equals("17")) sensor = Sensor.leftAnkle;
		else if (sensorSubStr.equals("F2")) sensor = Sensor.rightAnkle;
		else if (sensorSubStr.equals("DB")) sensor = Sensor.leftElbow;	
		else if (sensorSubStr.equals("DD")) {
			String subStr = sensorStr.substring(13, 15);
			if (subStr.equals("E3")) sensor = Sensor.rightElbow;
			else if (subStr.equals("FD")) sensor = Sensor.leftWrist;		
		}
		else if (sensorSubStr.equals("48")) sensor = Sensor.chest;
		else if (sensorSubStr.equals("38")) sensor = Sensor.lowerBack;
		else if (sensorSubStr.equals("A1")) sensor = Sensor.rightWrist;
		else if (sensorSubStr.equals("F1")) sensor = Sensor.forehead;
		else sensor = Sensor.unknown;
		
		String moduleStr = fileReader.next().substring(1);
		if (moduleStr.equals("0")) module = Module.accelerometer;
		else if (moduleStr.equals("1")) module = Module.gyroscope;
		else if (moduleStr.equals("2")) module = Module.magnetometer;
		
		x = Double.parseDouble(fileReader.next().substring(1));
		y = Double.parseDouble(fileReader.next());
		z = Double.parseDouble(fileReader.next());
		
		epoch = Long.parseLong(fileReader.next().substring(1));
		
		disconnects = Integer.parseInt(fileReader.nextLine().substring(2));
	}
	
//	void setSensor(sensor sensorType) {this.sensorType = sensorType;}
//	void setModule(module moduleType) {this.moduleType = moduleType;}
//	void setX(double x) {this.x = x;}
//	void setY(double y) {this.y = y;}
//	void setZ(double z) {this.z = z;}
//	void setEpoch(long epoch) {this.epoch = epoch;}
//	void setDisconnects(int disconnects) {this.disconnects = disconnects;}
	
	Sensor getSensor() {return sensor;}
	Module getModule() {return module;}
	double getX() {return x;}
	double getY() {return y;}
	double getZ() {return z;}
	long getEpoch() {return epoch;}
	int getDisconnects() {return disconnects;}
}
