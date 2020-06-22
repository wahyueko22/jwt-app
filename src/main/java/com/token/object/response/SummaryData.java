package com.token.object.response;

public class SummaryData {
	private int countRest;
	private int countWSDL;
	private int countEJB;
		
	public SummaryData(int countRest, int countWSDL, int countEJB) {
		super();
		this.countRest = countRest;
		this.countWSDL = countWSDL;
		this.countEJB = countEJB;
	}
	public int getCountRest() {
		return countRest;
	}
	public void setCountRest(int countRest) {
		this.countRest = countRest;
	}
	public int getCountWSDL() {
		return countWSDL;
	}
	public void setCountWSDL(int countWSDL) {
		this.countWSDL = countWSDL;
	}
	public int getCountEJB() {
		return countEJB;
	}
	public void setCountEJB(int countEJB) {
		this.countEJB = countEJB;
	}
	
}
