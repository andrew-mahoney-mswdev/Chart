import java.io.File;

import org.omg.CORBA.IntHolder;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Interface extends Application {
	
	GraphCreator graph;

	@Override
	public void start(Stage pStage) throws Exception {
		
		VBox pane = new VBox();
		pane.setPadding(new Insets(25, 25, 25, 25));
		ObservableList<Node> children = pane.getChildren();
		
		final IntHolder seriesCount = new IntHolder();
		Label seriesLabel = new Label();
		
		Button loadCSV = new Button("Load CSV");
		children.add(loadCSV);
		Label fileName = new Label();
		children.add(fileName);
		children.add(new Label());
		
		loadCSV.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open CSV");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Yoga data", "*.csv"));
			File selectedFile = fileChooser.showOpenDialog(new Stage());
			
			graph = new GraphCreator(selectedFile.getPath());
			fileName.setText(selectedFile.getName());
			seriesLabel.setText("0 series added");
			seriesCount.value = 0;
		});
		
		children.add(new Label("Sensor"));
		ObservableList<String> sensorList = FXCollections.observableArrayList("Left Knee", "Right Knee", "Left Ankle", "Right Ankle", "Left Elbow", "Right Elbow", "Chest", "Lower Back", "Left Wrist", "Right Wrist", "Forehead");
		ComboBox sensorMenu = new ComboBox(sensorList);
		children.add(sensorMenu);
		children.add(new Label());
		
		children.add(new Label("Module"));
		ObservableList<String> moduleList = FXCollections.observableArrayList("Accelerometer", "Gyroscope", "Magnetometer");
		ComboBox moduleMenu = new ComboBox<>(moduleList);
		children.add(moduleMenu);
		children.add(new Label());
		
		children.add(new Label("Dimension"));
		ObservableList<String> xyzList = FXCollections.observableArrayList("X", "Y", "Z", "Magnitude");
		ComboBox xyzMenu = new ComboBox(xyzList);
		children.add(xyzMenu);
		children.add(new Label());
		
		children.add(new Label("Begin (seconds)"));
		TextField beginTime = new TextField();
		beginTime.setMaxWidth(150);
		children.add(beginTime);
		children.add(new Label());
		
		children.add(new Label("End (seconds)"));
		TextField endTime = new TextField();
		endTime.setMaxWidth(150);
		children.add(endTime);
		children.add(new Label());
		
		Button addSeries = new Button("Add series");
		children.add(addSeries);
		seriesLabel.setText("0 series added");
		children.add(seriesLabel);
		children.add(new Label());
		
		addSeries.setOnAction(e -> {
			boolean complete = true;
			
			Reading.Sensor sensor = Reading.Sensor.unknown;
			String sensorString = sensorMenu.getValue().toString();
			if (sensorString.equals("Left Knee")) sensor = Reading.Sensor.leftKnee;
			else if (sensorString.equals("Right Knee")) sensor = Reading.Sensor.rightKnee;
			else if (sensorString.equals("Left Ankle")) sensor = Reading.Sensor.leftAnkle;
			else if (sensorString.equals("Right Ankle")) sensor = Reading.Sensor.rightAnkle;
			else if (sensorString.equals("Left Elbow")) sensor = Reading.Sensor.leftElbow;
			else if (sensorString.equals("Right Elbow")) sensor = Reading.Sensor.rightElbow;
			else if (sensorString.equals("Chest")) sensor = Reading.Sensor.chest;
			else if (sensorString.equals("Lower Back")) sensor = Reading.Sensor.lowerBack;
			else if (sensorString.equals("Left Wrist")) sensor = Reading.Sensor.leftWrist;
			else if (sensorString.equals("Right Wrist")) sensor = Reading.Sensor.rightWrist;
			else if (sensorString.equals("Forehead")) sensor = Reading.Sensor.forehead;
			else complete = false;			
			
			Reading.Module module = Reading.Module.unknown;
			String moduleString = moduleMenu.getValue().toString();
			if (moduleString.equals("Accelerometer")) module = Reading.Module.accelerometer;
			else if (moduleString.equals("Gyroscope")) module = Reading.Module.gyroscope;
			else if (moduleString.equals("Magnetometer")) module = Reading.Module.magnetometer;
			else complete = false;
			
			GraphCreator.Dimension d = GraphCreator.Dimension.unknown;
			String dString = xyzMenu.getValue().toString();
			if (dString.equals("X")) d = GraphCreator.Dimension.x;
			else if (dString.equals("Y")) d = GraphCreator.Dimension.y;
			else if (dString.equals("Z")) d = GraphCreator.Dimension.z;
			else if (dString.equals("Magnitude")) d = GraphCreator.Dimension.m;
			else complete = false;
			
			long begin = 0, end = 0;
			try {begin = Integer.parseInt(beginTime.getText());}
			catch (NumberFormatException ex) {complete = false;}
			try {end = Integer.parseInt(endTime.getText());}
			catch (NumberFormatException ex) {complete = false;}

			if (complete == true) {
				String name = dString + ", " + sensorString + ", " + moduleString;
				graph.addSeries(name, sensor, module, d, begin, end);
				seriesCount.value++;
				seriesLabel.setText(seriesCount.value + " series added");
			}
				
		});
		
		Button createGraph = new Button("Create graph");
		createGraph.setMaxWidth(300);
		children.add(createGraph);
		
		createGraph.setOnAction(e -> {
			graph.display();
			seriesLabel.setText("0 series added");
			seriesCount.value = 0;
		});

		pStage.setTitle("Graph Creator");
		pStage.setWidth(350);
		pStage.setScene(new Scene(pane));
		pStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
