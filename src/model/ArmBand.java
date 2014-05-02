package model;

import java.util.Date;


import java.io.Serializable;

public class ArmBand implements Serializable{

	private Date startDate;
	private Date endDate;
	private int colour;
	private String code;
	private int guestID;
	
	public ArmBand(){
		startDate= new Date();
		endDate= new Date();
		colour = 0;
		code="";
	}
		
	public ArmBand(Date sDate, Date eDate, int colour, String code) {
		this.startDate = sDate;
		this.endDate = eDate;
		this.colour = colour;
		this.code = code;
	}
	
	public Date getSDate() {
		return startDate;
	}
	public void setSDate(Date sDate) {
		this.startDate = sDate;
	}
	public Date getEDate() {
		return endDate;
	}
	public void setEDate(Date eDate) {
		this.endDate = eDate;
	}
	public int getColour() {
		return colour;
	}
	public void setColour(int colour) {
		this.colour = colour;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ArmBand [startDate=" + startDate + ", endDate=" + endDate
				+ ", colour=" + colour + ", code=" + code + ", guestID="
				+ guestID + "]";
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getGuestID() {
		return guestID;
	}

	public void setGuestID(int guestID) {
		this.guestID = guestID;
	}
	
	
}
