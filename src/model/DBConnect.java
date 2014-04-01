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
	public static final String USER = "Yondaimeku";
	public static final String PASS = "ashani";
	public Connection conn;
	public ResultSet result;
	private Logger log;
	//public PreparedStatement state = conn.prepareStatement();
	public DBConnect(){
		try{
				conn= 	this.getConnection();
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
			PreparedStatement prep = conn.prepareStatement("INSERT INTO drinks(name, price, type) VALUES ('" + drink.getName() + "', '" + drink.getPrice() + "', '" + drink.getType() + "')"); 
			int numChanged = prep.executeUpdate();
			log.info("drinks inserted? :"+numChanged);
			if( numChanged > 0 ){
				conf = true;
			}
		}
		catch(SQLException ex){
			log.error("SQL Exception. error adding drink");
		}
		catch(Exception ex){
			log.error("General exception adding drink");
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
	
	public boolean addOrder(Order order){
		
		boolean added = false;
		int k=0;
		try{
			PreparedStatement prep = conn.prepareStatement("INSERT into orders (date,guest_id) VALUES ('"+ "','" + order.getDdate() +"', '"+order.getGuestID()+"')");
			
			int p = prep.executeUpdate();
			//result = prep2.executeQuery();
			
			
				while(!order.getDrinkList().isEmpty())	{
						Drink tempDrink = new Drink();
						
						tempDrink = order.getDrinkList().remove(order.getDrinkList().size()-1);
						PreparedStatement prep2 = conn.prepareStatement("INSERT into orders_has_drinks (orders_id, drinks_id) VALUES ('"+ tempDrink.getID() +"', '" + order.getGuestID() + "')");
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
	
	
	public static class DrinkAdapter{
		
		/**
		 * @return DefaultTableModel
		 */
		public static DefaultTableModel getTableModel(){
			
			/*
			 *  TODO 
			 *  Capitalize column titles and drink types
			 */
			
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
		            columns.add(meta.getColumnName(i));
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
			//	log.error("SQLException: "+e.getCause());
			}catch(NumberFormatException e){
			//	log.error("NumberFormatException: "+e.getCause());
			}finally{
				// keep going...
			}
			
			return new DefaultTableModel(drinks, columns);
			
		}
	}
	
	
}
