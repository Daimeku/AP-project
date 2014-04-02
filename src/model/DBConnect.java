/**
 * PR:
 * DBConnect class...
 * Holds constant members related to database connection
 */

package model;

import java.io.IOException;
import java.sql.*;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

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
			PreparedStatement prep = conn.prepareStatement("UPDATE drinks SET name='"+drink.getName()+"' price='"+drink.getPrice()+"' WHERE id = '"+drink.getID()); 
			int numChanged = prep.executeUpdate();
			if( numChanged > 0 ){
				conf = true;
			}
		}
		catch(SQLException ex){
			log.error("SQL exception in update drink");
		}
		catch(Exception ex){
			log.error("Exception in update drink");
		}
		return conf;
	}
	
	public boolean deleteDrink(String id){
		boolean conf = false;
		try{
			PreparedStatement prep = conn.prepareStatement("DELETE * FROM drinks WHERE id='"+id+"'"); 
			int numChanged = prep.executeUpdate();
			if( numChanged > 0 ){
				conf = true;
			}
		}
		catch(SQLException ex){
			log.error("SQL Exception in delete drink");
		}
		catch(Exception ex){
			log.error(" exception in delete Drink");
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
				System.out.println("got user");
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
	
	public boolean addOrder(Order order){
		
		boolean added = false;
		int k=0;
		try{
			PreparedStatement prep = conn.prepareStatement("INSERT into orders (date,guest_id) VALUES ('"+ order.getDdate() +"', '"+order.getGuestID()+"')");
			
			int p = prep.executeUpdate();
			//result = prep2.executeQuery();
			
			
				while(!order.getDrinkList().isEmpty())	{
						Drink tempDrink = new Drink();
						
						tempDrink = order.getDrinkList().remove(order.getDrinkList().size()-1);
						PreparedStatement prep2 = conn.prepareStatement("INSERT into orders_has_drinks (orders_id, drinks_id) VALUES ('"+ order.getId() +"', '" + tempDrink.getID() + "')");
						k = prep2.executeUpdate();
				}
					
				if (p>0)
					added = true;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return added;
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
