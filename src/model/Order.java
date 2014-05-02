package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{
	
	private int guestID;
	private int id;
	private java.util.Date date;
	private ArrayList<Drink> drinkList;
	private java.sql.Date ddate;
	
	public Order(){		
		guestID = 0;
		id=(int) (Math.random() *1000);
		date = new java.util.Date();
		drinkList = new ArrayList<Drink>();		
		ddate = new java.sql.Date(date.getTime());
	}
	
	
	

	public Order(Drink drink) {
		super();
		this.guestID = 1;
		this.id = (int) (Math.random() *1000);
		this.date = new java.util.Date();
		drinkList.add(drink);
		this.ddate = new java.sql.Date(date.getTime());
		
	}
	
	public Order(ArrayList<Drink> drinkL) {
		super();
		this.guestID = 0;
		this.id = (int) (Math.random() *1000);
		this.date = new java.util.Date();
		drinkList = drinkL;
		this.ddate = new java.sql.Date(date.getTime());
		
	}
	
	public void addDrink(Drink drink){
		drinkList.add(drink);
	}

	public java.sql.Date getDdate() {
		return ddate;
	}



	public void setDdate(java.sql.Date ddate) {
		this.ddate = ddate;
	}
	
	public int getGuestID() {
		return guestID;
	}

	public void setGuestID(int guestID) {
		this.guestID = guestID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

	public ArrayList<Drink> getDrinkList() {
		return drinkList;
	}

	public void setDrinkList(ArrayList<Drink> drinkList) {
		this.drinkList = drinkList;
	}



	@Override
	public String toString() {
		return "Order [guestID=" + guestID + ", id=" + id + ", date=" + date
				+ ", drinkList=" + drinkList + "]";
	}
	
	
	
	
}
