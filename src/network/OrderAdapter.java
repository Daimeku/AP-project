package network;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import model.DBConnect;
import model.Drink;

public  class OrderAdapter{
	
	private static Logger log = Logger.getLogger(OrderAdapter.class);
	
	/**
	 * Get the orders table.
	 * @return DefaultTableModel
	 */
	public static DefaultTableModel getTableModel(Date date){
		
		Vector<Object> orders = new Vector<Object>(); // to return
		Vector<Object> orderRow = new Vector<Object>(); // a row
		Vector<Object> columns = new Vector<Object>(); // columns
		
		log.info("getting order table model");
		try {
			Connection conn = DBConnect.getConnection();
			ResultSet orderSet;
			orderSet = (date == null)?conn.prepareStatement("SELECT * from orders").executeQuery():
				conn.prepareStatement("SELECT * from orders WHERE date = " + date).executeQuery();
			ResultSetMetaData meta = orderSet.getMetaData();
	        int columnCount = meta.getColumnCount();
	        
	        //store column names  
	        for (int i = 1; i <= columnCount; i++) {
	            columns.add((meta.getColumnName(i).substring(0, 1).toUpperCase() + meta.getColumnName(i).substring(1)));
	            
	        }
	        columns.add("Drinks");
	        columns.add("Bill Total");
	        
			while(orderSet.next()){
				// get row content (order)
				orderRow = new Vector<Object>();
				{
					final int orderId = (Integer) Integer.parseInt(orderSet.getObject(1).toString());
					final Vector<Drink> drinksVec = getDrinks(orderId);
					// add elements of row
					orderRow.addElement( orderId );
					orderRow.addElement( (String) orderSet.getObject(2).toString() );
					orderRow.addElement( (Integer) Integer.parseInt(orderSet.getObject(3).toString()) );
					// list drinks from vector
					String drinks = new String();
					double orderTotal = 0.0;
					for(int i = 0; i < drinksVec.size(); i++){
						Drink current = drinksVec.get(i);
						drinks += current.getName();
						orderTotal += current.getPrice();
						if(i < drinksVec.size()-1) drinks += ", ";
					}
					orderRow.addElement(drinks);
					orderRow.addElement(String.format("$%.02f", orderTotal));
				}
				
				orders.addElement(orderRow);
			}
			
			// close connection
			orderSet.close();
			conn.close();
		}catch(SQLException e){
			log.error("SQLException: "+e.getStackTrace());
			e.printStackTrace();
		}catch(NumberFormatException e){
			log.error("NumberFormatException: "+e.getCause());
		}finally{
			// keep going...
		}
		DefaultTableModel tableModel = new DefaultTableModel(orders, columns);
		
		log.info("got order table model");
		return tableModel;
	}
	
	/**
	 * Get all drinks on an order.
	 * @param orderId
	 * @return Vector of drinks.
	 */
	public static Vector<Drink> getDrinks(int orderId){
		
		Vector<Drink> drinks = new Vector<Drink>();
		
		Connection conn;
		try {
			conn = DBConnect.getConnection();
			// retrieve drinks on order
			ResultSet drinksOnOrder = conn.prepareStatement(
					"SELECT drinks_id FROM orders_has_drinks WHERE orders_id =" + orderId).executeQuery(), singleDrink;

			while(drinksOnOrder.next()){
				// retrieve details of each drink
				singleDrink = conn.prepareStatement(
						"SELECT * from drinks WHERE id = "
								+ Integer.parseInt(drinksOnOrder.getObject(1)
										.toString())).executeQuery();
				if(singleDrink.next()){
					Drink drink = new Drink(singleDrink.getObject(2).toString(), // drink name
							Integer.parseInt(singleDrink.getObject(4).toString()), // drink type
							Double.parseDouble(singleDrink.getObject(3).toString()) // drink price
					);
					
					drinks.add(drink);
					singleDrink.close();
				}
			}
			drinksOnOrder.close();	
		} catch (SQLException e) {
			log.error("SQLException in getDrinks(): " +e.getStackTrace());
			e.printStackTrace();
		}
		return drinks;
	}
}