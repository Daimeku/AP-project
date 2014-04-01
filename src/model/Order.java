package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{
	
	private String guestID;
	private String id;
	private Date date;
	private ArrayList<Drink> drinkList;
	
	Order(){		
		guestID = "1111";
		id="0000";
		date = new Date();
		drinkList = new ArrayList<Drink>();		
	}
	
	

	public Order(Drink drink) {
		super();
		this.guestID = "1111";
		this.id = "0000";
		this.date = new Date();
		drinkList.add(drink);
		
	}
	
	public void addDrink(Drink drink){
		drinkList.add(drink);
	}



	public String getGuestID() {
		return guestID;
	}

	public void setGuestID(String guestID) {
		this.guestID = guestID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ArrayList<Drink> getDrinkList() {
		return drinkList;
	}

	public void setDrinkList(ArrayList<Drink> drinkList) {
		this.drinkList = drinkList;
	}
	
	
	
}
