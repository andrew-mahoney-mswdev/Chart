import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
public class LineChartSample extends Application {
 
    @Override public void start(Stage stage) {
		ArrayList<Reading> readings = new ArrayList<Reading>();
//		File file = new File("yOGASensorData_1208104041.csv");
		File file = new File("yOGASensorData_1208104050.csv");
		
		try {
			Scanner fileReader = new Scanner(file);
			fileReader.useDelimiter(",");
			
			while (fileReader.hasNextLine())
				readings.add(new Reading(fileReader));
			
			System.out.println("Loaded " + readings.size() + " records");

			fileReader.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
    	
    	stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x axis label");
        yAxis.setLabel("y axis label");
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setCreateSymbols(false);
        lineChart.setTitle("Title");

        //defining a series
        XYChart.Series series = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series.setName("series name");

        //populating the series with data
        long start = readings.get(0).getEpoch();
        for (Reading r : readings) {
        	if (r.getSensor() == Reading.Sensor.leftKnee && r.getModule() == Reading.Module.accelerometer && (r.getEpoch() - start) < 20000)
        		series.getData().add(new XYChart.Data((r.getEpoch() - start), r.getZ()));
        }
        
        for (Reading r : readings) {
        	if (r.getSensor() == Reading.Sensor.rightKnee && r.getModule() == Reading.Module.accelerometer && (r.getEpoch() - start) < 20000)
        		series2.getData().add(new XYChart.Data((r.getEpoch() - start), r.getZ()));
        }
        
/*        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));
*/        
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
        lineChart.getData().add(series2);
       
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}
