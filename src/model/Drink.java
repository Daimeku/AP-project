package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

public class Drink implements Serializable{
	private static final Logger log = Logger.getLogger(Drink.class); //logger for Drink class
	public final static double MIN_PRICE = 100.0;
	public final static String TYPES[] = {"Alcoholic", "Non-Alcoholic"};
	
	
	private double price;
	private String name;
	private int type;
	private int ID;
	
	public Drink(String drinkName, int drinkType, Double drinkPrice) {
		super();
		this.name = drinkName;
		this.type = drinkType;
		this.price = drinkPrice;
	}
	
	public Drink(){
		price=0;
		name="drink";
		type=0;
	}
	
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean save() throws SQLException{
		boolean flag = false;
		Connection conn = DBConnect.getConnection();
		if(conn.prepareStatement("INSERT INTO drinks(name, price, type) VALUES ('" + this.name + "', '" + this.price + "', '" + this.type + "')").executeUpdate() > 0){
			System.out.println("Drink "+this.name+" was inserted successfully!");
			flag = true;
		}
		conn.close();
		return flag;
	}
	
	

	
		
	
	
}
