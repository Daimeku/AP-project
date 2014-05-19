/**
 * PR:
 * DBConnect class...
 * Holds constant members related to database connection
 */

package model;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import network.OrderAdapter;

import org.apache.log4j.Logger;


//import com.mysql.jdbc.PreparedStatement;

public final class DBConnect {
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB = "jdbc:mysql://localhost/ap-project";
	public static final String USER = "root";
	public static final String PASS = "";
	public Connection conn;
	public ResultSet result;
	private Logger log;
	//public PreparedStatement state = conn.prepareStatement();
	public DBConnect(){
		log = Logger.getLogger(getClass());
		
		try{
				conn= this.getConnection();
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public boolean addDrink(Drink drink){
		boolean conf = false;
		try{
			log.info("Attempting to add drink");
			System.out.println("going to add drink");
			PreparedStatement prep = conn.prepareStatement("INSERT INTO drinks(name, price, type) VALUES ('" + drink.getName() + "', '" + drink.getPrice() + "', '" + drink.getType() + "')"); 
			
			int numChanged = prep.executeUpdate();
			log.info("drinks inserted? :"+numChanged);
			if( numChanged > 0 ){
				conf = true;
				System.out.println("Drink changed");
			}
			System.out.println("update successful? "+numChanged);
		}
		catch(SQLException ex){
		log.error("SQL Exception. error adding drink");
			ex.printStackTrace();
		}
		catch(Exception ex){
			log.error("General exception adding drink");
			ex.printStackTrace();
		}
		return conf;
	}
	
	public boolean updateDrink(Drink drink){
		boolean conf = false;
		try{
			PreparedStatement prePrep = conn.prepareStatement("SELECT * FROM drinks WHERE name = '"+drink.getName()+"'");
			result = prePrep.executeQuery();
			result.next();
			int id = (Integer) result.getObject(1);
			
			PreparedStatement prep = conn.prepareStatement("UPDATE drinks SET name='"+drink.getName()+"', price='"+drink.getPrice()+"', type='" + drink.getType() +"' WHERE id = '"+ id +"'"); 
			int numChanged = prep.executeUpdate();
			if( numChanged > 0 ){
				conf = true;
			}
		}
		catch(SQLException ex){
			log.error("SQL exception in update drink");
			ex.printStackTrace();
		}
		catch(Exception ex){
			log.error("Exception in update drink");
			ex.printStackTrace();
		}
		return conf;
	}
	
	public boolean deleteDrink(String name){
		boolean conf = false;
		try{
			PreparedStatement prep = conn.prepareStatement("DELETE FROM drinks WHERE name ='"+name+"'"); 
			int numChanged = prep.executeUpdate();
			if( numChanged > 0 ){
				conf = true;
			}
		}
		catch(SQLException ex){
			log.error("SQL Exception in delete drink");
			ex.printStackTrace();
		}
		catch(Exception ex){
			log.error(" exception in delete Drink");
			ex.printStackTrace();
		}
		return conf;
	}
	public boolean staffLogin(String user, String pass) throws SQLException{  
		boolean conf = false;
		try{
			java.sql.PreparedStatement prep = conn.prepareStatement("SELECT * FROM staff WHERE name = '"+user+"' AND password = '" + pass + "'");
			result = prep.executeQuery();
			
			int count = 0;
			while(result.next())
				++count;
			if(count == 1){
				
				conf = true;
				log.info("got user");
			}
			
		}
		
		catch(Exception ex){
			ex.printStackTrace();
		}
		return conf;
	}
	
	public boolean guestLogin(String user, String pass) throws SQLException{  
		boolean conf = false;
		try{
			java.sql.PreparedStatement prep = conn.prepareStatement("SELECT * FROM guests WHERE name = '"+user+"' AND password = '" + pass + "'");
			result = prep.executeQuery();
			
			int count = 0;
			while(result.next())
				++count;
			if(count == 1){
				
				conf = true;
				log.info("got user");
			}
			
		}
		
		catch(Exception ex){
			ex.printStackTrace();
		}
		return conf;
	}
	
	public int getDrinkId(String drinkName){
		int id=0;
		try{
		PreparedStatement prep = conn.prepareStatement("SELECT * FROM drinks WHERE name = '"+drinkName+"'");
		result = prep.executeQuery();
		while(result.next()){
			id = (Integer) result.getObject(1);
			log.info("id: "+id);
		}
		
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return id;
	}
	
	public boolean addOrder(Order order){
		
		boolean added = false;
		int k=0;
		try{
			PreparedStatement prep = conn.prepareStatement("INSERT into orders (id,date,guest_id) VALUES ('"+order.getId()+"', '"+ order.getDdate() +"', '"+order.getGuestID()+"')");
			
			int p = prep.executeUpdate();
			//result = prep2.executeQuery();
			
			ArrayList<Drink> dList = order.getDrinkList();
			int drinkCounter = dList.size()-1;
			log.info("tempDrinkList size: " + drinkCounter);
			
				while(drinkCounter >= 0)	{
						
						
					Drink tempDrink  = dList.get(drinkCounter);
					
					tempDrink.setName(dList.get(drinkCounter).getName());
					log.info("tempDrink name: " + tempDrink.getName());
					tempDrink.setID(this.getDrinkId(tempDrink.getName()));
			
			
						
					//	ArrayList<Drink> drinkList = OrderAdapter.getDrinks(orderId);
						//if(drinkList.)
						log.info("DRINK ID: "+tempDrink.getID());
						PreparedStatement prep2 = conn.prepareStatement("INSERT into orders_has_drinks (orders_id, drinks_id) VALUES ('"+ order.getId() +"', '" + tempDrink.getID() + "')");
						k = prep2.executeUpdate();
						drinkCounter--;
				}
					
				if (p>0)
					added = true;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return added;
	}
	
	public ArrayList<Drink> getDrinks(){
		ArrayList<Drink> drinkList = new ArrayList<Drink>();
		try{
			PreparedStatement state = conn.prepareStatement("SELECT * FROM drinks");
			result = state.executeQuery();
			
			while(result.next()){
				Drink drink = new Drink();
				drink.setID( (Integer) result.getObject(1) );
				drink.setName( (String) result.getObject(2) );
				drink.setPrice( (Double) result.getObject(3) );
				drink.setType((Integer) result.getObject(4));
				
				drinkList.add(drink);
			}
		
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return drinkList;
	}
	
	public Guest getGuest(String name){
		ArmBand ab = new ArmBand(); 
		
		log.info("trying to read gues & armband from DB");
		try{
			PreparedStatement statemen = conn.prepareStatement("SELECT * FROM guests WHERE name = '"+name+"'");
			result = statemen.executeQuery();
			result.next();
			String gName =(String) result.getObject(3);
			int aid = (Integer) result.getObject(6);
			
			
			PreparedStatement prep = conn.prepareStatement("SELECT * FROM armbands WHERE id = '"+aid+"'");
			result = prep.executeQuery();
			result.next();
			ab.setColour((Integer) result.getObject(4)); 
			int gid = (Integer) result.getObject(4);
			ab.setGuestID(gid);
			
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		Guest guest = new Guest(ab);
		
		log.info("got guest and armband color: "+ab.getColour());
		
		return guest;
	}
	public ArrayList<Drink> getDrinkReport(Date date){
		ArrayList<Drink> drinkList = new ArrayList<Drink>();
		
		return drinkList;
	}
	
	public static Connection getConnection() throws SQLException{
		try {
			Class.forName(DBConnect.JDBC_DRIVER); // Access JDBC driver from JAR 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(DBConnect.DB, DBConnect.USER, DBConnect.PASS);
	}
	
	
	
	
	
}
