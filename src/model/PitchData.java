package model;

import java.util.ArrayList;
import java.util.List;

public class PitchData {
	
	private String fullName;
	private String abbrev;
	private int percentage;
	
	private List<String> pitchDataSet;
	
	public PitchData(String fullName, String abbrev, int percentage) {
		this.fullName = (fullName);
		this.abbrev = (abbrev);
		this.percentage = (percentage);
		pitchDataSet = new ArrayList<>();
	}
	
	public List<String> getDataSet() {
		return pitchDataSet;
	}
	
	public PitchData() {
		this("","",0);
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public String getAbbrev() {
		return abbrev;
	}

	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}
}
