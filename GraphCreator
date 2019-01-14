import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class GraphCreator {
	public enum Dimension {x, y, z, m, unknown};

	final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
	LineChart<Number,Number> lineChart = null;
	
	ArrayList<Reading> readings = new ArrayList<Reading>();
	long start;
	
	public GraphCreator(String path) {
		File file = new File(path);
		
		try {
			Scanner fileReader = new Scanner(file);
			fileReader.useDelimiter(",");
			
			while (fileReader.hasNextLine())
				readings.add(new Reading(fileReader));
			
			fileReader.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		start = readings.get(0).getEpoch();
		lineChart = null;
	}
	
	public void addSeries(String name, Reading.Sensor sensor, Reading.Module module, Dimension d, long begin, long end) {
		XYChart.Series series = new XYChart.Series();

        series.setName(name);
		begin = start + (begin * 1000);
		end = start + (end * 1000);

        for (Reading r : readings) {
        	double value = 0;
        	
        	if (r.getSensor() == sensor && r.getModule() == module && r.getEpoch() > begin && r.getEpoch() < end) {	
        		switch (d) {
        			case x: value = r.getX(); break;
        			case y: value = r.getY(); break;
        			case z: value = r.getZ(); break;
        			case m: value = Math.sqrt((r.getX() * r.getX()) + (r.getY() * r.getY()) + (r.getZ() * r.getZ())); break;  
        			default: value = 0;
        		}
        		
        		series.getData().add(new XYChart.Data(r.getEpoch() - begin, value));
        	}
        }
        
        if (lineChart == null) lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.getData().add(series);
	}
	
	public void display() {
		if (lineChart != null) {
			Stage graphStage = new Stage();
		    Scene scene = new Scene(lineChart, 800, 600);
			
			xAxis.setLabel("Milliseconds");
		    lineChart.setCreateSymbols(false);
//		    lineChart.setTitle("Title");
		    graphStage.setScene(scene);
		    graphStage.show();
		    lineChart = null;
		}
	}
}
