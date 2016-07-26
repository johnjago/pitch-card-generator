package controller;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import model.CoachPitchCard;
import model.PitchData;
import model.PlayerPitchCard;

public class Controller {
	
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextField freq1;
	@FXML
	private TextField freq2;
	@FXML
	private TextField freq3;
	@FXML
	private TextField freq4;
	@FXML
	private TextField freq5;
	@FXML
	private TextField freq6;
	@FXML
	private TextField freq7;
	@FXML
	private TextField freq8;
	@FXML
	private TextField freq9;
	@FXML
	private TextField freq10;
	@FXML
	private TextField pitchAbbrev1;
	@FXML
	private TextField pitchAbbrev2;
	@FXML
	private TextField pitchAbbrev3;
	@FXML
	private TextField pitchAbbrev4;
	@FXML
	private TextField pitchAbbrev5;
	@FXML
	private TextField pitchAbbrev6;
	@FXML
	private TextField pitchAbbrev7;
	@FXML
	private TextField pitchAbbrev8;
	@FXML
	private TextField pitchAbbrev9;
	@FXML
	private TextField pitchAbbrev10;
	@FXML
	private TextField pitchName1;
	@FXML
	private TextField pitchName2;
	@FXML
	private TextField pitchName3;
	@FXML
	private TextField pitchName4;
	@FXML
	private TextField pitchName5;
	@FXML
	private TextField pitchName6;
	@FXML
	private TextField pitchName7;
	@FXML
	private TextField pitchName8;
	@FXML
	private TextField pitchName9;
	@FXML
	private TextField pitchName10;
	@FXML
	private Slider slider1;
	@FXML
	private Slider slider2;
	@FXML
	private Slider slider3;
	@FXML
	private Slider slider4;
	@FXML
	private Slider slider5;
	@FXML
	private Slider slider6;
	@FXML
	private Slider slider7;
	@FXML
	private Slider slider8;
	@FXML
	private Slider slider9;
	@FXML
	private Slider slider10;
	@FXML
	private Button generateExcelButton;
	@FXML
    private Label totalPercentageLabel;
	
	List<Slider> sliderList = new ArrayList<>();
	List<TextField> percentageList;
	List<TextField> pitchNameList;
	List<TextField> pitchAbbrevList;
	List<PitchData> pitchDataList;
	
	private static final int INIT_VALUE = 0;
	private static final int NUMBER_OF_PITCHEs = 10;
	
	SimpleIntegerProperty totalPercentageValue = new SimpleIntegerProperty();
	
    @FXML
    void initialize() {
    	injectionCheck();
    	
    	setTotalPercentageLabelStyle();
    	
    	initializePitchList();
    	initializePitchName();
    	initializePitchAbbrev();
    	initializeSliderList();
    	initializepercentageList();
 
    	bindSlidersAndFrequencyTextFields();
    	
    	addTotalPercentageValueListener();
    	addButtonListeners();
    }

	private void addButtonListeners() {
		generateExcelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				collectPitchdata();
				PlayerPitchCard a = new PlayerPitchCard(pitchDataList);
				a.createPitchCardSheet();
				a.write();
		
				CoachPitchCard ad = new CoachPitchCard(pitchDataList);
				ad.createPitchCardSheet();
				ad.write();
			}	
		});
	}
	
	private void setTotalPercentageLabelStyle() {
		totalPercentageLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: red;");
		totalPercentageLabel.setText(INIT_VALUE + "%");	
	}

	private void initializePitchList() {
		pitchDataList = new ArrayList<>();
		for(int i=0; i<NUMBER_OF_PITCHEs; i++) {
			pitchDataList.add(new PitchData());
		}
	}

	private void initializePitchAbbrev() {
		pitchAbbrevList = new ArrayList<>();
		pitchAbbrevList.add(pitchAbbrev1);
		pitchAbbrevList.add(pitchAbbrev2);
		pitchAbbrevList.add(pitchAbbrev3);
		pitchAbbrevList.add(pitchAbbrev4);
		pitchAbbrevList.add(pitchAbbrev5);
		pitchAbbrevList.add(pitchAbbrev6);
		pitchAbbrevList.add(pitchAbbrev7);
		pitchAbbrevList.add(pitchAbbrev8);
		pitchAbbrevList.add(pitchAbbrev9);
		pitchAbbrevList.add(pitchAbbrev10);
	}

	private void initializePitchName() {
		pitchNameList = new ArrayList<>();
		pitchNameList.add(pitchName1);
		pitchNameList.add(pitchName2);
		pitchNameList.add(pitchName3);
		pitchNameList.add(pitchName4);
		pitchNameList.add(pitchName5);
		pitchNameList.add(pitchName6);
		pitchNameList.add(pitchName7);
		pitchNameList.add(pitchName8);
		pitchNameList.add(pitchName9);
		pitchNameList.add(pitchName10);
	}

	private void collectPitchdata() {
		for(int i=0; i<NUMBER_OF_PITCHEs; i++) {
			pitchDataList.get(i).getDataSet().clear();
			pitchDataList.get(i).setFullName(pitchNameList.get(i).getText());
			pitchDataList.get(i).setAbbrev(pitchAbbrevList.get(i).getText());
			pitchDataList.get(i).setPercentage(Integer.parseInt(percentageList.get(i).getText()));	
		}
	}

	private void addTotalPercentageValueListener() {
		totalPercentageValue.addListener( e -> {
			int totalPercent = (Integer)totalPercentageValue.getValue();
			if (totalPercent == 100) {
				totalPercentageLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: green;");
				totalPercentageLabel.setText(totalPercent + "%");
			}
			else {
				totalPercentageLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: red;");
				totalPercentageLabel.setText(totalPercent + "%");
			}
		});
	}

	private void bindSlidersAndFrequencyTextFields() {
		NumberFormat format = NumberFormat.getIntegerInstance();
		
		for (int i=0; i < NUMBER_OF_PITCHEs; i++) {
			percentageList.get(i).textProperty().bindBidirectional(sliderList.get(i).valueProperty(), format);
		}	
	}
	
	private void initializepercentageList() {
		percentageList = new ArrayList<>();
		percentageList.add(freq1);
		percentageList.add(freq2);
		percentageList.add(freq3);
		percentageList.add(freq4);
		percentageList.add(freq5);
		percentageList.add(freq6);
		percentageList.add(freq7);
		percentageList.add(freq8);
		percentageList.add(freq9);
		percentageList.add(freq10);
		
		for(TextField t: percentageList) 
			t.setText(new Integer(INIT_VALUE).toString());
	}

	private void initializeSliderList() {
		sliderList.add(slider1);
		sliderList.add(slider2);
		sliderList.add(slider3);
		sliderList.add(slider4);
		sliderList.add(slider5);
		sliderList.add(slider6);
		sliderList.add(slider7);
		sliderList.add(slider8);
		sliderList.add(slider9);
		sliderList.add(slider10);
		
		for(Slider s: sliderList) {
			s.setValue(INIT_VALUE);
			s.setMajorTickUnit(1);
			s.setMinorTickCount(0);
			s.setSnapToTicks(true);
			s.setBlockIncrement(1);	
			s.valueProperty().addListener(new sliderChangeListener());
		}
	}

	private class sliderChangeListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			double totalPercent = 0;
			for (Slider s: sliderList) {
				totalPercent += s.getValue();
			}
			totalPercentageValue.setValue((int)totalPercent);		
		}		
	}
	
	private void injectionCheck() {
			assert freq1 != null : "fx:id=\"freq1\" was not injected: check your FXML file 'pp.fxml'.";
	        assert freq2 != null : "fx:id=\"freq2\" was not injected: check your FXML file 'pp.fxml'.";
	        assert freq3 != null : "fx:id=\"freq3\" was not injected: check your FXML file 'pp.fxml'.";
	        assert freq4 != null : "fx:id=\"freq4\" was not injected: check your FXML file 'pp.fxml'.";
	        assert freq5 != null : "fx:id=\"freq5\" was not injected: check your FXML file 'pp.fxml'.";
	        assert freq6 != null : "fx:id=\"freq6\" was not injected: check your FXML file 'pp.fxml'.";
	        assert freq7 != null : "fx:id=\"freq7\" was not injected: check your FXML file 'pp.fxml'.";
	        assert freq8 != null : "fx:id=\"freq8\" was not injected: check your FXML file 'pp.fxml'.";
	        assert freq9 != null : "fx:id=\"freq9\" was not injected: check your FXML file 'pp.fxml'.";
	        assert freq10 != null : "fx:id=\"freq10\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchAbbrev1 != null : "fx:id=\"pitchAbbrev1\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchAbbrev2 != null : "fx:id=\"pitchAbbrev2\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchAbbrev3 != null : "fx:id=\"pitchAbbrev3\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchAbbrev4 != null : "fx:id=\"pitchAbbrev4\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchAbbrev5 != null : "fx:id=\"pitchAbbrev5\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchAbbrev6 != null : "fx:id=\"pitchAbbrev6\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchAbbrev7 != null : "fx:id=\"pitchAbbrev7\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchAbbrev8 != null : "fx:id=\"pitchAbbrev8\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchAbbrev9 != null : "fx:id=\"pitchAbbrev9\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchAbbrev10 != null : "fx:id=\"pitchAbbrev10\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchName1 != null : "fx:id=\"pitchName1\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchName2 != null : "fx:id=\"pitchName2\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchName3 != null : "fx:id=\"pitchName3\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchName4 != null : "fx:id=\"pitchName4\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchName5 != null : "fx:id=\"pitchName5\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchName6 != null : "fx:id=\"pitchName6\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchName7 != null : "fx:id=\"pitchName7\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchName8 != null : "fx:id=\"pitchName8\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchName9 != null : "fx:id=\"pitchName9\" was not injected: check your FXML file 'pp.fxml'.";
	        assert pitchName10 != null : "fx:id=\"pitchName10\" was not injected: check your FXML file 'pp.fxml'.";
	        assert slider1 != null : "fx:id=\"slider1\" was not injected: check your FXML file 'pp.fxml'.";
	        assert slider2 != null : "fx:id=\"slider2\" was not injected: check your FXML file 'pp.fxml'.";
	        assert slider3 != null : "fx:id=\"slider3\" was not injected: check your FXML file 'pp.fxml'.";
	        assert slider4 != null : "fx:id=\"slider4\" was not injected: check your FXML file 'pp.fxml'.";
	        assert slider5 != null : "fx:id=\"slider5\" was not injected: check your FXML file 'pp.fxml'.";
	        assert slider6 != null : "fx:id=\"slider6\" was not injected: check your FXML file 'pp.fxml'.";
	        assert slider7 != null : "fx:id=\"slider7\" was not injected: check your FXML file 'pp.fxml'.";
	        assert slider8 != null : "fx:id=\"slider8\" was not injected: check your FXML file 'pp.fxml'.";
	        assert slider9 != null : "fx:id=\"slider9\" was not injected: check your FXML file 'pp.fxml'.";
	        assert slider10 != null : "fx:id=\"slider10\" was not injected: check your FXML file 'pp.fxml'.";
	        assert generateExcelButton != null : "fx:id=\"coachesSheetButton\" was not injected: check your FXML file 'pp.fxml'.";
	        assert totalPercentageLabel != null : "fx:id=\"totalPercentageLabel\" was not injected: check your FXML";
	}
}
