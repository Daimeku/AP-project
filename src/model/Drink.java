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
	
	
	/**
	 * DrinkAdapter contains methods to be used directly by Views.
	 * @author Patrick Reid
	 */
	public static class DrinkAdapter{
		
		/**
		 * @return DefaultTableModel
		 */
		public static DefaultTableModel getTableModel(){
			
			Vector<Object> drinks = new Vector<Object>(); // to return
			Vector<Object> drinkRow = new Vector<Object>(); // a row
			Vector<Object> columns = new Vector<Object>(); // columns
			
			int i = 0; // reusable iterator
			
			try {
				Connection conn = DBConnect.getConnection();
				ResultSet driSet = conn.prepareStatement("SELECT * from drinks").executeQuery(), typeSet;
				ResultSetMetaData meta = driSet.getMetaData();
		        int columnCount = meta.getColumnCount();
		        
		        //store column names  
		        for (i = 2; i <= columnCount; i++) {
		            columns.add((meta.getColumnName(i).substring(0, 1).toUpperCase() + meta.getColumnName(i).substring(1)));
		        }
		        
				while(driSet.next()){
					// get row content (drink)
					drinkRow = new Vector<Object>();
					{
						i = 0;
						// add name and price
						drinkRow.addElement( (String) driSet.getObject(i+2).toString() );
						drinkRow.addElement( (Double) Double.parseDouble(driSet.getObject(i+3).toString()) );
						
						// fetch type string from drink_types table
						typeSet = conn.prepareStatement("SELECT * from drink_types WHERE id = " + Integer.parseInt(driSet.getObject(i+4).toString())).executeQuery();
						while(typeSet.next())
							drinkRow.addElement( (String) typeSet.getObject(i+2).toString() );
						typeSet.close();
					}
					
					drinks.addElement(drinkRow);
				}
				
				// close connection
				conn.close();
			}catch(SQLException e){
				log.error("SQLException: "+e.getCause());
			}catch(NumberFormatException e){
				log.error("NumberFormatException: "+e.getCause());
			}finally{
				// keep going...
			}
			
			return new DefaultTableModel(drinks, columns);
			
		}
		
		public interface DataChangedListener{
			/*
			 *  TODO 
			 *  Force some code to allow table update when drink is added or removed
			 */
			public void fireTableDataChanged();
		}
	}
	
}
